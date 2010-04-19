package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * 
 * @author Greg Simons, Zach Hine
 */

public class newGenerator implements GeneratorIF 
{
	private ExamIF master;
	private ConfigIF config;
	private AnswerKeyIF[] keys;
	private ExamIF[] generatedExams;
	private int numExams = config.numVersions();

	newGenerator(ExamIF master, ConfigIF config) throws Exception
	{
		this.master = master;
		this.config = config;
		
		try
		{
			generate();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Given an ArrayList of SatisfiedContainers and a String topic, will return the SatisfiedContainer	
	 * with that topic.
	 * 
	 * @param satisfiedContainers
	 * @param topic
	 * @return
	 */
	
	private SatisfiedContainer findSatisfiedContainer(ArrayList<SatisfiedContainer> satisfiedContainers, String topic)
	{
		Iterator<SatisfiedContainer> satisfiedIterator = satisfiedContainers.iterator();
		SatisfiedContainer theSatisfiedContainer = null;
		boolean found = false;
		
		while (!found && satisfiedIterator.hasNext())
		{
			theSatisfiedContainer = satisfiedIterator.next();
			
			if (theSatisfiedContainer.getName().equals(topic))
				found = true;
		}
		
		if (found)
			return theSatisfiedContainer;
		
		else
		{
			theSatisfiedContainer = new SatisfiedContainer(topic);
			satisfiedContainers.add(theSatisfiedContainer);
			
			return theSatisfiedContainer;
		}
	}
	
	/**
	 * Given a ProblemIF and a RandomizerIF, will return a new ProblemIF
	 * with shuffled non-FixedAnswerIFs, while maintaining order on
	 * FixedAnswerIFs
	 * 
	 * @param inputProblem
	 * @param theRandomizer
	 * @return
	 */
	
	private ProblemIF randomizeAnswers(ProblemIF inputProblem, RandomizerIF theRandomizer)
	{
		ExamFactoryIF theExamFactory = new ExamFactory();
		AnswerIF[] theAnswers = inputProblem.answers();
		ArrayList<AnswerIF> beforeRandomization = new ArrayList<AnswerIF>();
		AnswerIF[] afterRandomization;
		int j = 0;
		
		for (int i = 0; i < theAnswers.length; i++)
			if (!(theAnswers[i].getClass().isInstance(FixedAnswerIF.class)))
				beforeRandomization.add(theAnswers[i]);

		afterRandomization = (AnswerIF[]) theRandomizer.choose(beforeRandomization.size(), beforeRandomization.toArray());
		
		for (int i = 0; i < theAnswers.length; i++)
			if (!(theAnswers[i].getClass().isInstance(FixedAnswerIF.class)))
			{
				theAnswers[i] = afterRandomization[j];
				j++;
			}
		
		return theExamFactory.newProblem(inputProblem.topic(), inputProblem.label(),
				                         inputProblem.question(), theAnswers);	
	}
	
	/**
	 * Given a SatisfiedContainer, RandomizerIF, and integer, will return an ArrayList
	 * of ProblemIFs consisting of all of the SatisfiedContainer's required ProblemIFs,
	 * plus randomly selected integer-value number of ProblemIFs from the SatisfiedContainer's 
	 * remaining ProblemIFs.
	 * 
	 * @param theSatisfiedContainer
	 * @param theRandomizer
	 * @param constraintValue
	 * @return
	 */
	
	private ArrayList<ProblemIF> selectProblems(SatisfiedContainer theSatisfiedContainer, RandomizerIF theRandomizer, int constraintValue)
	{
		ArrayList<ProblemIF> returnSet = new ArrayList<ProblemIF>();
		
		ProblemIF[] requiredProblems = (ProblemIF[]) theSatisfiedContainer.getRequiredProblems().toArray();
		
		ProblemIF[] beforeSelection = (ProblemIF[]) theSatisfiedContainer.getRemainingProblems().toArray();
		ProblemIF[] afterSelection = (ProblemIF[]) theRandomizer.choose(constraintValue, beforeSelection);
		
		for (int i = 0; i < requiredProblems.length; i++)
			returnSet.add(randomizeAnswers(requiredProblems[i], theRandomizer));
		
		for (int i = 0; i < afterSelection.length; i++)
			returnSet.add(randomizeAnswers(afterSelection[i], theRandomizer));
		
		return returnSet;
	}
	
	private void generate() throws Exception
	{
		/* generatedExams is an ExamIF[] that will contain
		 * all generated Exams once finished.
		 */
		this.generatedExams = new ExamIF[this.numExams];
		
		/* Variables used throughout generate():
		 * 1.) theExamFactory will create each Exam.
		 * 2.) theRandomizerFactory creates theRandomizer.
		 * 3.) theRandomizer randomizes where necessary.
		 * 4.) theConstraints is an array of Constraints, given by config.
		 */
		
		ExamFactoryIF theExamFactory = new ExamFactory();
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config.seed());
		ConstraintIF[] theConstraints = (ConstraintIF[]) config.constraints().toArray();
		
		/* Variables used to separate different types of ConstraintIFs:
		 * 1.) groupConstraintCollection represents a collection of all GroupConstraintIFs.
		 * 2.) groupConstraints is groupConstraintCollection as an array.
		 * 3.) requiredConstraintCollection represents a collection of all RequiredProblemConstraintIFs.
		 * 4.) requiredConstraints is requiredConstraintCollection as an array.
		 */
		
		ArrayList<GroupConstraintIF> groupConstraintCollection = new ArrayList<GroupConstraintIF>();
		GroupConstraintIF[] groupConstraints;
		ArrayList<RequiredProblemConstraintIF> requiredConstraintCollection = new ArrayList<RequiredProblemConstraintIF>();
		RequiredProblemConstraintIF[] requiredConstraints;
		
		/* Container variables used for organization:
		 * 1.) satisfied is an ArrayList of SatisfiedContainers, used for housing all ExamElementIFs,
		 *     and establishing an order.
		 * 2.) theSatisfiedContainer is used as a reference to a specific SatisfiedContainer
		 *     in satisfied throughout control flow.
		 */
		
		ArrayList<SatisfiedContainer> satisfied = new ArrayList<SatisfiedContainer>();
		SatisfiedContainer theSatisfiedContainer = null;
		
		/* Variables used in distributing required ProblemIFs:
		 * 1.) labelGivesProblem is the label of a RequiredProblemConstraintIF.
		 * 2.) problemGivesTopic is the ProblemIF specified by labelGivesProblem.
		 * 3.) theRequiredTopic is the topic specified by problemGivesTopic.
		 * 
		 * Note: This is the only way to get a topic out of a RequiredProblemConstraintIF.
		 */
		
		String labelGivesProblem;
		ProblemIF problemGivesTopic;
		String theRequiredTopic;
		
		/* Variables used in distributing remaining ProblemIFs:
		 * 1.) byTopic is an ArrayList of every ProblemIF of a specified topic.
		 * 2.) theRemainingTopic represents this specified topic.
		 * 3.) difficulty represents a given GroupConstraint's difficulty.
		 * 4.) numProblems represents a given GroupConstraint's number of problems.
		 * 5.) points represents a given GroupConstraint's point specification.
		 * 6.) low represents the low value of difficulty.
		 * 7.) high represents the high value of a difficulty.
		 */
		
		ArrayList<ProblemIF> byTopic = new ArrayList<ProblemIF>();
		String theRemainingTopic;
		IntervalIF difficulty;
		int numProblems, points;
		double low, high;
		
		/* Variables used to distribute ProblemIFs among FigureProblemContainers:
		 * 1.) makeNewFPC determines if a ProblemIF should be added to an existing
		 *     FPC, or if a new FPC should be created for it.
		 * 2.) newFPC is used as a reference to a specific FPC throughout
		 *     control flow.
		 */
		
		boolean makeNewFPC = true;
		FigureProblemContainer newFPC;
		
		/* Sort ConstraintIFs into two categories:
		 * GroupConstraintIFs, and RequiredProblemConstraintIFs.
		 * 
		 * IF a ConstraintIF is neither of the two, throw an Exception.
		 */
		
		for (ConstraintIF currentConstraint : theConstraints)
		{
			if (currentConstraint.getClass().isInstance(GroupConstraintIF.class))
				groupConstraintCollection.add((GroupConstraintIF) currentConstraint);
			
			else if (currentConstraint.getClass().isInstance(RequiredProblemConstraintIF.class))
				requiredConstraintCollection.add((RequiredProblemConstraintIF) currentConstraint);
			
			else
				throw new Exception();
		}
		
		//Cast the newly separated ConstraintIFs.
		
		groupConstraints = (GroupConstraintIF[]) groupConstraintCollection.toArray();
		requiredConstraints = (RequiredProblemConstraintIF[]) requiredConstraintCollection.toArray();
		
		/* For each RequiredProblemConstraintIF, find the SatisfiedContainer in
		 * satisfied that matches its topic (a new one is created if none exist),
		 * then add the ProblemIF specified by the RequiredProblemConstraintIF
		 * to the SatisfiedContainer (as a required problem).
		 */
		
		for (int i = 0; i < requiredConstraints.length; i++)
		{	
			labelGivesProblem = requiredConstraints[i].label();
			problemGivesTopic = (ProblemIF) master.elementWithLabel(labelGivesProblem);
			theRequiredTopic = problemGivesTopic.topic();
			
			theSatisfiedContainer = findSatisfiedContainer(satisfied, theRequiredTopic);
			theSatisfiedContainer.addRequired(problemGivesTopic);
		}
		
		/* For each GroupConstraintIF, find the SatisfiedContainer in
		 * satisfied that matches its topic (again, a new one is created if none
		 * exist), then, if the ProblemIF is within the correct interval, and
		 * is worth the correct number of points, add the ProblemIF specified by 
		 * the GroupConstraintIF to the SatisfiedContainer (as a remaining problem).
		 * 
		 * Finally, pick n of these ProblemIFs, where n is specified by the
		 * GroupConstraintIF. These n ProblemIFs will have their non-FixedAnswerIFs
		 * shuffled.
		 * 
		 * If the number of ProblemIFs chosen is less than n, a
		 * RexUnsatisfiableException is thrown.
		 */
		
		for (int i = 0; i < groupConstraints.length; i++)
		{
			theRemainingTopic = groupConstraints[i].topic();
			difficulty = groupConstraints[i].difficultyInterval();
			numProblems = groupConstraints[i].numProblems();
			points = groupConstraints[i].points();
			
			low = difficulty.low();
			high = difficulty.high();
			
			if (difficulty.strictLow())
				low++;
			
			if (difficulty.strictHigh())
				high--;
			
			//This approach concerning low and high will not work since they are doubles, not integers.
			
			byTopic = (ArrayList<ProblemIF>) master.problemsWithTopic(theRemainingTopic);
			theSatisfiedContainer = findSatisfiedContainer(satisfied, theRemainingTopic);
			
			for (ProblemIF currentProblem : byTopic)		
				if ((currentProblem.points() == points) &&
				   (low <= currentProblem.difficulty()) &&
				   (currentProblem.difficulty() <= high))
					theSatisfiedContainer.addRemaining(currentProblem);
			
			if (theSatisfiedContainer.getRemainingProblems().size() < numProblems)
				throw new RexUnsatisfiableException();
			
			theSatisfiedContainer.setSubset(selectProblems(theSatisfiedContainer, theRandomizer, numProblems));
		}
		
		/* For each SatisfiedContainer in satisfied, for each of its ProblemIFs,
		 * for each of *their* referenced FigureIFs, iterate through all FPCs in the
		 * current SatisfiedContainer, and determine if any of them are already housing
		 * the FigureIF in question.
		 * 
		 * If an FPC is found, add the current ProblemIF to that FPC.
		 * 
		 * IF no such FPC is found, create one, have it house the FigureIF in question
		 * as well as the ProblemIF in question, and then add this FPC to the current
		 * SatisfiedContainer.
		 */
		
		for (SatisfiedContainer currentSatisfiedContainer : satisfied)
		{
			for (ProblemIF currentProblem : currentSatisfiedContainer.getSelectedSubset())
			{
				for (FigureIF currentFigure : currentProblem.referencedFigures())
				{
					for (FigureProblemContainer currentFPC : currentSatisfiedContainer.getFPCs())
					{
						if (currentFPC.getFigure().label().equals(currentFigure.label()))
						{
							currentFPC.addProblem(currentProblem);
							makeNewFPC = false;
						}
					}
					
					if (makeNewFPC)
					{
						newFPC = new FigureProblemContainer(currentFigure);
						newFPC.addProblem(currentProblem);
						//((SatisfiedContainer) currentSatisfiedContainer).addFPC(newFPC); //Why isn't this working?
					}
					
					makeNewFPC = true;
				}
			}
		}
		
		//start here
		
		
		
		
	} 
	// end generate()

	@Override
	public AnswerKeyIF getAnswerKey(int i) 
	{
		return keys[i];
	}

	@Override
	public ConfigIF getConfig() 
	{
		return config;
	}

	@Override
	public ExamIF getGeneratedExam(int i) 
	{
		return generatedExams[i];
	}

	@Override
	public ExamIF getMaster() 
	{
		return master;
	}

	@Override
	public int numGeneratedExams() 
	{
		return numExams;
	}
}
