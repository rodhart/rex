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
	 * Given an ArrayList of SubsetContainers and a String topic, will return the SubsetContainer	
	 * with that topic.
	 * 
	 * @param satisfiedContainers
	 * @param topic
	 * @return
	 */
	
	private SubsetContainer findSubsetContainer(ArrayList<SubsetContainer> subsetContainers, String topic)
	{
		Iterator<SubsetContainer> subsetIterator = subsetContainers.iterator();
		SubsetContainer theSubsetContainer = null;
		boolean found = false;
		
		while (!found && subsetIterator.hasNext())
		{
			theSubsetContainer = subsetIterator.next();
			
			if (theSubsetContainer.getTopic().equals(topic))
				found = true;
		}
		
		if (found)
			return theSubsetContainer;
		
		else
		{
			theSubsetContainer = new SubsetContainer(topic);
			subsetContainers.add(theSubsetContainer);
			
			return theSubsetContainer;
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
			
			if (theSatisfiedContainer.getTopic().equals(topic))
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
		 * 1.) satisfied is an ArrayList of SatisfiedContainers, used for housing all ProblemsIFs,
		 *     and establishing an order.
		 * 2.) theSatisfiedContainer is used as a reference to a specific SatisfiedContainer
		 *     in satisfied throughout control flow.
		 * 3.) subset is an ArrayList of SubsetContainers, used for housing all ExamElementIFs
		 *     and establishing an order.
		 * 4.) theSubsetContainer is used as a reference to a specific SubsetContainer
		 *     in subset throughout control flow.
		 */
		
		ArrayList<SatisfiedContainer> satisfied = new ArrayList<SatisfiedContainer>();
		SatisfiedContainer theSatisfiedContainer = null;
		ArrayList<SubsetContainer> subset = new ArrayList<SubsetContainer>();
		SubsetContainer theSubsetContainer = null;
		
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
		
		for (int i = 0; i < requiredConstraints.length; i++)
		{	
			labelGivesProblem = requiredConstraints[i].label();
			problemGivesTopic = (ProblemIF) master.elementWithLabel(labelGivesProblem);
			theRequiredTopic = problemGivesTopic.topic();
			
			theSatisfiedContainer = findSatisfiedContainer(satisfied, theRequiredTopic);
			theSatisfiedContainer.addRequired(problemGivesTopic);
		}
		
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
			theSubsetContainer = findSubsetContainer(subset, theRemainingTopic);
			
			for (ProblemIF currentProblem : byTopic)		
				if ((currentProblem.points() == points) &&
				   (low <= currentProblem.difficulty()) &&
				   (currentProblem.difficulty() <= high))
					theSatisfiedContainer.addRemaining(currentProblem);
			
			if (theSatisfiedContainer.getRemainingProblems().size() < numProblems)
				throw new RexUnsatisfiableException();
			
			theSubsetContainer.setSubset(selectProblems(theSatisfiedContainer, theRandomizer, numProblems));
		}

		
		
		
		
		
		
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
