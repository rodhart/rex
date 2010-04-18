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
import edu.udel.cis.cisc475.rex.exam.impl.FixedAnswer;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

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
	
//	class FigureProblemContainer
//	{
//		private FigureIF theFigure = null;
//		private ArrayList<ProblemIF> theProblems;
//		
//		public FigureProblemContainer(FigureIF theFigure)
//		{
//			this.theFigure = theFigure;
//		}
//		
//		public void addProblem(ProblemIF newProblem)
//		{
//			//assert ((theFigure == null) || (newProblem.referencedFigures().contains(theFigure)));
//			
//			theProblems.add(newProblem);
//		}
//		
//		public FigureIF getFigure()
//		{
//			return this.theFigure;
//		}
//		
//		public ArrayList<ProblemIF> getProblems()
//		{
//			return this.theProblems;
//		}
//	}
//	
//	class SatisfiedContainer
//	{
//		private String name;
//		private ArrayList<ProblemIF> requiredProblems = new ArrayList<ProblemIF>();
//		private ArrayList<ProblemIF> remainingProblems = new ArrayList<ProblemIF>();
//		private ArrayList<FigureProblemContainer> FPCs = new ArrayList<FigureProblemContainer>();
//		
//		public SatisfiedContainer(String name) 
//		{
//			this.name = name;
//		}
//		
//		private void addRequired(ProblemIF requiredProblem)
//		{
//			requiredProblems.add(requiredProblem);
//		}
//		
//		private void addRemaining(ProblemIF remainingProblem)
//		{
//			ProblemIF[] blacklist = (ProblemIF[]) requiredProblems.toArray();
//			boolean add = true;
//			
//			for (int i = 0; i < blacklist.length && add; i++)
//				if (blacklist[i].label().equals(remainingProblem.label()))
//					add = false;
//					
//			if (add)
//				remainingProblems.add(remainingProblem);
//		}
//		
//		private void addFPC(FigureProblemContainer newFPC)
//		{
//			FPCs.add(newFPC);
//		}
//		
//		public String getName()
//		{
//			return this.name;
//		}
//		
//		public ArrayList<ProblemIF> getRequiredProblems()
//		{
//			return this.requiredProblems;
//		}
//		
//		public ArrayList<ProblemIF> getRemainingProblems()
//		{
//			return this.remainingProblems;
//		}
//		
//		public ArrayList<FigureProblemContainer> getFPCs()
//		{
//			return this.FPCs;
//		}
//	}
	
	private SatisfiedContainer findContainer(ArrayList<SatisfiedContainer> satisfiedContainers, String topic)
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
	
	private ArrayList<FigureProblemContainer> findFPCs(SatisfiedContainer theSatisfiedContainer, ProblemIF theProblem)
	{
		ArrayList<FigureProblemContainer> referencedFigures = new ArrayList<FigureProblemContainer>();
		
		Collection<FigureIF> theFigures = theProblem.referencedFigures();
		Iterator<FigureIF> figureIterator = theFigures.iterator();
		FigureIF theFigure = null;
		
		ArrayList<FigureProblemContainer> theFPCs = theSatisfiedContainer.getFPCs();
		Iterator<FigureProblemContainer> FPCIterator = null;
		FigureProblemContainer theFPC = null;
		
		while (figureIterator.hasNext())
		{
			theFigure = figureIterator.next();
			FPCIterator = theFPCs.iterator();
			
			while (FPCIterator.hasNext())
			{
				theFPC = FPCIterator.next();
				
				if (theFigure.label().equals(theFPC.getFigure().label()))
					referencedFigures.add(theFPC);
			}
		}
		
		return referencedFigures;
	}
	
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
	
	private void generate() throws Exception
	{
		ExamFactoryIF theExamFactory = new ExamFactory();
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config.seed());
		ConstraintIF[] theConstraints = (ConstraintIF[]) config.constraints().toArray();
		
		this.generatedExams = new ExamIF[this.numExams];
		
		ArrayList<GroupConstraintIF> groupConstraintCollection = new ArrayList<GroupConstraintIF>();
		GroupConstraintIF[] groupConstraints;
		ArrayList<RequiredProblemConstraintIF> requiredConstraintCollection = new ArrayList<RequiredProblemConstraintIF>();
		RequiredProblemConstraintIF[] requiredConstraints;
		
		ArrayList<SatisfiedContainer> satisfied = new ArrayList<SatisfiedContainer>();
		SatisfiedContainer theSatisfiedContainer = null;
		
		String labelGivesProblem;
		ProblemIF problemGivesTopic;
		String theRequiredTopic;
		
		ArrayList<ProblemIF> byTopic = new ArrayList<ProblemIF>();
		String theRemainingTopic;
		IntervalIF difficulty;
		int numProblems, points;
		double low, high;
		
		for (ConstraintIF currentConstraint : theConstraints)
		{
			if (currentConstraint.getClass().isInstance(GroupConstraintIF.class))
				groupConstraintCollection.add((GroupConstraintIF) currentConstraint);
			
			else if (currentConstraint.getClass().isInstance(RequiredProblemConstraintIF.class))
				requiredConstraintCollection.add((RequiredProblemConstraintIF) currentConstraint);
			
			else
				throw new Exception();
		}
		
		groupConstraints = (GroupConstraintIF[]) groupConstraintCollection.toArray();
		requiredConstraints = (RequiredProblemConstraintIF[]) requiredConstraintCollection.toArray();
		
		for (int i = 0; i < requiredConstraints.length; i++)
		{	
			labelGivesProblem = requiredConstraints[i].label();
			problemGivesTopic = (ProblemIF) master.elementWithLabel(labelGivesProblem);
			theRequiredTopic = problemGivesTopic.topic();
			
			theSatisfiedContainer = findContainer(satisfied, theRequiredTopic);
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
			
			byTopic = (ArrayList<ProblemIF>) master.problemsWithTopic(theRemainingTopic);
			theSatisfiedContainer = findContainer(satisfied, theRemainingTopic);
			
			for (ProblemIF currentProblem : byTopic)		
				if ((currentProblem.points() == points) &&
				   (low <= currentProblem.difficulty()) &&
				   (currentProblem.difficulty() <= high))
					theSatisfiedContainer.addRemaining(currentProblem);
			
			if (theSatisfiedContainer.getRemainingProblems().size() < numProblems)
				throw new RexUnsatisfiableException();
		}
		
		//Start here
		
		
		
		
		
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
