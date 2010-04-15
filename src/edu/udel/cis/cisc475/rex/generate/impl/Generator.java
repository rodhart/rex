package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Generator implements GeneratorIF {

	private ExamIF master;
	private ConfigIF config;
	private AnswerKeyIF[] keys;
	private ExamIF[] generatedExams;
	private int numExams = config.numVersions();

	
	Generator(ExamIF master, ConfigIF config) throws RexUnsatisfiableException {
		this.master = master;
		this.config = config;
		generate();
	}

	private void generate() throws RexUnsatisfiableException 
	{
		// create containers for holding constraints
		Collection<ConstraintIF> theConstraints = config.constraints();
		ArrayList<GroupConstraintIF> groupConstraints = new ArrayList<GroupConstraintIF>();
		ArrayList<RequiredProblemConstraintIF> reqProbConstraints = new ArrayList<RequiredProblemConstraintIF>();
		
		generatedExams = new ExamIF[numExams];
		ExamFactoryIF theExamFactory = new ExamFactory();
		
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config.seed());
		IntervalFactoryIF theIntervalFactory = new IntervalFactory();
		
		ArrayList<ArrayList> allCollections = new ArrayList<ArrayList>();
		ArrayList<ExamElementIF> allRequired = new ArrayList<ExamElementIF>();
		
		// this will be implemented later once we figure out how to iterate through
		// or we make it so choose() doesn't return an array [Beta]
		// Object[] constraintArray = theConstraints.toArray();
		// constraintArray = randomizeConstraints(theRandomizer, theConstraints.size(), theConstraints.toArray());

		// iterate through constraints and logically group them
		for (ConstraintIF c : theConstraints)
		{				
			if (c.getClass().isInstance(GroupConstraintIF.class))	
			{
				groupConstraints.add((GroupConstraintIF) c);
			}
			else if (c.getClass().isInstance(RequiredProblemConstraintIF.class))
			{
				reqProbConstraints.add((RequiredProblemConstraintIF) c);
			}
		}
		
		for (int i = 0; i < numExams; i++)
		{
			generatedExams[i] = theExamFactory.newGeneratedExam();
			
			for (RequiredProblemConstraintIF rpc : reqProbConstraints)
			{
				// extract label
				String label = rpc.label();
				
				// find the corresponding problem
				ExamElementIF theReqProblem = master.elementWithLabel(label);
				
				// add it to our container
				allRequired.add(theReqProblem);
			}
				
			
			for (GroupConstraintIF gc : groupConstraints)
			{
				// extract relevant data
				int numProblems = gc.numProblems();
				IntervalIF difficultyInterval = gc.difficultyInterval();
				String topic = gc.topic();
				int points = gc.points();					
					
					
				// container for holding elements in master with specified topic
				Collection<ProblemIF> problemsWithTopic = master.problemsWithTopic(topic);
				Iterator<ProblemIF> iterate = problemsWithTopic.iterator();
					
				// container for holding elements in master with specified topic/difficulty/points
				ArrayList<ProblemIF> desiredProblems = new ArrayList<ProblemIF>();
				
				while(iterate.hasNext())
				{
					ProblemIF tempProblem = iterate.next();
				
					// check to make sure it satisfies the difficulty constraint
					if (difficultyInterval.low() <= tempProblem.difficulty() 
								&& tempProblem.difficulty() <= difficultyInterval.high())
					{
						// assuming it did, make sure it satisfies the points constraint
						if (tempProblem.points() == points)
						{
							// now add it to our container of desired problems
							// NOTE: this container will also contain required problems
							desiredProblems.add(tempProblem);						
						}
					}
											
					else
					{
						throw new RexUnsatisfiableException();
					}
					
				}
				
				// now add this collection from a specific topic to our master collection
				allCollections.add(desiredProblems);
			} // end for
			
			// now, iterate through the collection of required problems
			Iterator<ExamElementIF> reqIterate = allRequired.iterator();
			
			while (reqIterate.hasNext())
			{
				ExamElementIF tempReqProblem = reqIterate.next();
				Iterator<ArrayList> allIterate = allCollections.iterator();
				
				while (allIterate.hasNext())
				{
					ArrayList<ProblemIF> something = allIterate.next();
					
					ProblemIF otherTemp = (ProblemIF) something.get(0);
					
					// because we couldn't cast in the if statement
					ProblemIF castTempReq = (ProblemIF) tempReqProblem;
					
					// check to see if the first problem in the allCollections collection
					// has a topic that matches the topic of the singular required
					// problem in the allRequired array list
					if (otherTemp.topic().equals(castTempReq.topic()))
					{
						// now add it to the kth element of allCollections
						something.add(castTempReq);
					}		
				}			
			}
			
			// fill an array with the contents of the desiredElements collection
			Object[] passableDesiredElements = allCollections.toArray();
			
			// now a linked hash map
			LinkedHashMap<Integer, Object> lhm = new LinkedHashMap<Integer, Object>();
			for (int n = 0; n < allCollections.size(); n++) 
			{
				lhm.put(n, passableDesiredElements[n]);
			}
			
			// create an array of the keys
			// Object[] keys = theRandomizer.choose(numProblems, lhm.keySet().toArray());
			
			// create a linked hash set of final desired problems
			LinkedHashSet<ProblemIF> finalDesiredProblems = new LinkedHashSet<ProblemIF>();
			
			// now add the elements returned from the randomizer to the hash set
			for (int j=0; j < passableDesiredElements.length; j++)
			{
				// finalDesiredProblems.add((ProblemIF) lhm.get((Integer) keys[j]));
			}
			
			// now call the ExamFactory addProblem method and add it to generatedExams[i]
			for (ExamElementIF e : finalDesiredProblems)
			{
				generatedExams[i].addElementIF(e);
			}
			
			
			
			
		}
	} // end generate()

	@Override
	public AnswerKeyIF getAnswerKey(int i) {
		return keys[i];
	}

	@Override
	public ConfigIF getConfig() {
		return config;
	}

	@Override
	public ExamIF getGeneratedExam(int i) {
		return generatedExams[i];
	}

	@Override
	public ExamIF getMaster() {
		return master;
	}

	@Override
	public int numGeneratedExams() {
		return numExams;
	}
	
	/**
	 * Takes a RandomizerIF, a number of constraints, and an object array of constraints,
	 * and returns an array of randomized constraint objects
	 * @param r
	 * @param numConstraints
	 * @param constraints
	 * @return
	 */
	public Object[] randomizeConstraints(RandomizerIF r, int numConstraints, Object[] constraints)
	{
		return r.choose(numConstraints, constraints);
	}

}
