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
	 * Given a ProblemIF and a RandomizerIF, will return a ProblemPair
	 * consisting of the input ProblemIF, plus a new ProblemIF with
	 * shuffled non-FixedAnswerIFs, while maintaining order on
	 * FixedAnswerIFs
	 * 
	 * @param inputProblem
	 * @param theRandomizer
	 * @return
	 */
	
	private ProblemPair randomizeAnswers(ProblemIF inputProblem, RandomizerIF theRandomizer)
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
		
		return new ProblemPair(inputProblem, 
				               theExamFactory.newProblem(inputProblem.topic(), inputProblem.label(),
				        		                         inputProblem.question(), theAnswers));	
	}
	
	/**
	 * Given an ArrayList of SatisfiedContainers, an arrayList of SubsetContainers, and a
	 * RandomizerIF, this method will distribute to each SubsetContainer a subset of ProblemIFs
	 * from each ConstraintContainer in the SatisfiedContainer of matching name, as well as all
	 * ProblemIFs that are required.
	 * 
	 * @param satisfied
	 * @param subset
	 * @param theRandomizer
	 */
	
	private void subsetSelector(ArrayList<SatisfiedContainer> satisfied, ArrayList<SubsetContainer> subset,
			                    RandomizerIF theRandomizer)
	{	
		ArrayList<ProblemIF> problemSubset = new ArrayList<ProblemIF>();
		ProblemIF[] problemSubsetArray;
		SubsetContainer theSubsetContainer;
		
		ProblemIF[] requiredProblems;
		ProblemIF[] nProblems;
		
		
		for (SatisfiedContainer currentSatisfiedContainer : satisfied)
		{
			problemSubset.clear();
			
			requiredProblems = (ProblemIF[]) currentSatisfiedContainer.getRequiredProblems().toArray();
			
			for (int i = 0; i < requiredProblems.length; i++)
				problemSubset.add(requiredProblems[i]);
		
			for (ConstraintContainer currentConstraintContainer : currentSatisfiedContainer.getSatisfiedConstraints())
			{
				nProblems = (ProblemIF[]) theRandomizer.choose(currentConstraintContainer.getNumProblems(), 
						                                       currentConstraintContainer.getSatisfiedProblems().toArray());
				
				for (int i = 0; i < nProblems.length; i++)
					problemSubset.add(nProblems[i]);
			}
			
			theSubsetContainer = findSubsetContainer(subset, currentSatisfiedContainer.getTopic());
			problemSubsetArray = (ProblemIF[]) problemSubset.toArray();
			
			for (int i = 0; i < problemSubsetArray.length; i++)
				theSubsetContainer.addToSubset(randomizeAnswers(problemSubsetArray[i], 
						                                        theRandomizer));
		}
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
		 * 
		 * 1.) satisfied is the master ArrayList of SatisfiedContainers.
		 * 2.) theSatisfiedContainer is used as a reference to a specific
		 *     SatisfiedContainer in satisfied throughout control flow.
		 * 3.) makeNewConstraintContainer aids in the creation of
		 *     ConstraintContainers in the SatisfiedContainers of satisfied.
		 * 4.) subset is the master ArrayList of SubsetContainers.
		 * 5.) theSubsetContainer is used as a reference to a specific
		 *     SubsetContainer in subset throughout control flow.
		 * 6.) theBPC is used as a reference to a specific
		 *     BlockProblemContainer in the SubsetContainers of subset
		 *     throughout control flow.
		 * 7.) theFPC is used as a reference to a specific
		 *     FigureProblemContainer in the BPCs in the
		 *     SubsetContainers of subset throughout control flow.
		 */
		
		
		ArrayList<SatisfiedContainer> satisfied = new ArrayList<SatisfiedContainer>();
		SatisfiedContainer theSatisfiedContainer = null;
		boolean makeNewConstraintContainer;
		ArrayList<SubsetContainer> subset = new ArrayList<SubsetContainer>();
		SubsetContainer theSubsetContainer = null;
		BlockProblemContainer theBPC = null;
		FigureProblemContainer theFPC = null;
		
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
		
		/* For each RequiredProblemConstraintIF, find the SatisfiedContainer in satisfied
		 * that matches its topic (a new one is created if none exist), then add the
		 * ProblemIF specified by the RequiredProblemConstraintIF to the
		 * SatisfiedContainer (as a required problem).
		 * 
		 * If, by accident, the same ProblemIF is listed as required more than
		 * once, it will only be added once.
		 */
		
		for (int i = 0; i < requiredConstraints.length; i++)
		{	
			labelGivesProblem = requiredConstraints[i].label();
			problemGivesTopic = (ProblemIF) master.elementWithLabel(labelGivesProblem);
			theRequiredTopic = problemGivesTopic.topic();
			
			theSatisfiedContainer = findSatisfiedContainer(satisfied, theRequiredTopic);
			theSatisfiedContainer.addRequired(problemGivesTopic);
		}
		
		/* For each GroupConstraintIF, find the SatisfiedContainer in satisfied that
		 * matches its topic (again, a new one is created if none exist).
		 * Then, if the ProblemIF is within the correct interval, and is worth the correct 
		 * number of points, add the ProblemIF specified by the GroupConstraintIF to the matching
		 * ConstraintContainer in the SatisfiedContainer.
		 * 
		 * If, however unlikely, a ProblemIF satisfies two GroupConstraintIFs, it will
		 * only be added once.
		 * 
		 * If the number of ProblemIFs in the ConstraintContainer is less than
		 * the number specified by the GroupConstraintIF, a
		 * RexUnsatisfiableException is thrown.
		 * 
		 */
		
		for (int i = 0; i < groupConstraints.length; i++)
		{
			makeNewConstraintContainer = true;
			
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
				{
					theSatisfiedContainer.addRemaining(currentProblem, numProblems, makeNewConstraintContainer);
					makeNewConstraintContainer = false;
				}
			
			if (theSatisfiedContainer.getLastConstraintContainer().getSatisfiedProblems().size() < numProblems)
				throw new RexUnsatisfiableException();
		}
		
		/* Now we can begin to iterate on the number of ExamIFs desired;
		 * the above parsing into SatisfiedContainers should be the same
		 * for every version of the exam.
		 */
		
		/* subsetSelector grabs a randomized subset of ProblemIFs
		 * from satisfied, and distributes them to subset.
		 */
		
		subsetSelector(satisfied, subset, theRandomizer);

		/* For each SubsetContainer in subset, for each ProblemPair
		 * in that SubsetContainer, find the corresponding BlockProblemContainer
		 * that houses that Pair's old ProblemIF's BlockIF, and add the
		 * Pair's new ProblemIF to that BlockProblemContainer. A new
		 * BlockProblemContainer is created if none already exist for that
		 * BlockIF.
		 * 
		 * Then, for each BlockProblemContainer, for each ProblemIF,
		 * find the corresponding FigureProblemContainer that houses that
		 * ProblemIF's FigureIF. A new FigureProblemContainer is created
		 * if none already exist.
		 * 
		 * Note that there are BlockProblemContainers and
		 * FigureProblemContainers that have null BlockIFs
		 * and FigureIFs; these correspond to ProblemIFs
		 * that do not have BlockIFs and FigureIFs respectively.
		 */
		
		for (SubsetContainer currentSubset : subset)
		{
			for (ProblemPair currentProblemPair : currentSubset.getSubset())
			{
				theBPC = currentSubset.getBPC(currentProblemPair.getBeforeRandomization().requiredBlock());
				theBPC.addProblem(currentProblemPair.getAfterRandomization());
				
				for (FigureIF currentFigure : currentProblemPair.getBeforeRandomization().referencedFigures())
				{
					theFPC = theBPC.getFPC(currentFigure);
					theFPC.addProblem(currentProblemPair.getAfterRandomization());
				}
			}
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
