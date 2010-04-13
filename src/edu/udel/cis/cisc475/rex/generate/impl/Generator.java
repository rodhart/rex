package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
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

	
	Generator(ExamIF master, ConfigIF config) {
		this.master = master;
		this.config = config;
		generate();
	}

	private void generate() 
	{
		Collection<ConstraintIF> theConstraints = config.constraints();
			
		generatedExams = new ExamIF[numExams];
		ExamFactoryIF theExamFactory = new ExamFactory();
		
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config.seed());
		IntervalFactoryIF theIntervalFactory = new IntervalFactory();

		for (int i = 0; i < numExams; i++)
		{
			generatedExams[i] = theExamFactory.newGeneratedExam();
			
			for (ConstraintIF c : theConstraints)
			{
				/* Overview:
				 * Test Constraint type; extract information from Constraint.
				 * Meet constraints using MasterExam (master).
				 * Pass collection of met constraint problems to randomizer.
				 * Add what randomizer returns to generatedExams[i].
				 */				
				
				if (c.getClass().isInstance(GroupConstraintIF.class))	
				{
					// cast to a GroupConstraintIF
					GroupConstraintIF gc = (GroupConstraintIF) c;

					// extract relevant data
					SourceIF source = gc.source(); // necessary?
					int numProblems = gc.numProblems();
					IntervalIF difficultyInterval = gc.difficultyInterval();
					String topic = gc.topic();
					int points = gc.points();					

					IntervalIF theInterval = 
						theIntervalFactory.interval(difficultyInterval.strictLow(), 
													difficultyInterval.low(),
													difficultyInterval.strictHigh(),
													difficultyInterval.high());
					
					
					// container for holding elements in master with specified topic
					Collection<ExamElementIF> elementsWithTopic = master.elementsWithTopic(topic);
					Iterator<ExamElementIF> iterate = elementsWithTopic.iterator();
					
					// container for holding elements in master with specified topic/difficulty/points
					Collection<ExamElementIF> desiredElements = null;
					
					while(iterate.hasNext())
					{
						ExamElementIF currentElement = iterate.next();
						
						// see what kind of element the current element is (problem, block, figure)
						// first check determines if it's a problem
						if (currentElement.getClass().isInstance(ProblemIF.class))
						{
							ProblemIF tempProblem = (ProblemIF) currentElement;
						
							// check to make sure it satisfies the difficulty constraint
							if (theInterval.low() <= tempProblem.difficulty() 
									&& tempProblem.difficulty() <= theInterval.high())
							{
								// assuming it did, make sure it satisfies the points constraint
								if (tempProblem.points() == points)
								{
									// this means it has the correct topic, difficulty, and points 
									// add it to our collection container
									desiredElements.add(tempProblem);
								}
							}
						}
						
						/**
						 * Question: ARE BLOCKS AND FIGURES ALWAYS RequiredProblemConstraints?
						 * OR WILL THEY BE ENCOUNTERED AS WE ITERATE THROUGH ALL GroupConstraints?
						 */
						
						
						// second check determines if it's a figure
						if (currentElement.getClass().isInstance(FigureIF.class))
						{
							// is this possible?
							
						}
						
						// third check determines if it's a block
						if (currentElement.getClass().isInstance(BlockIF.class))
						{
							// is this possible?
						}
						
						else
						{
							// throw new RexUnsatisfiableException();
						}
						
					}
					
					// fill an array with the contents of the desiredElements collection
					Object[] passableDesiredElements = desiredElements.toArray();
					
					/**
					 * Question: WHY DOES THE choose() METHOD HAVE TO TAKE AN ARRAY OF OBJECTS?
					 * IT'D BE A LOT EASIER IF ITS SECOND ARGUMENT WAS A COLLECTION
					 * THE WAY I HAVE IT CODED ABOVE IS FINE, BUT A CLUNKY STEP
					 */
					
					// now, make a call to the randomizer, and choose [numProblems] problems
					// from the set of these corresponding problems
					passableDesiredElements = theRandomizer.choose(numProblems, passableDesiredElements);
					
					// now, convert it back to a Collection of ExamElementIFs (seems wasteful)
		
					
					/**
					 * THIS WHOLE PART HERE IS A MESS
					 * CAN'T FIGURE OUT HOW TO CONVERT Object[] BACK TO Collection<ExamElementIF>
					 *
					 * Keith: you can just loop through the array and do 
					 * 		  Collection.add() for each element
					 */
					
		//			List<ExamElementIF> finalDesiredElements = Arrays.asList(passableDesiredElements);
		//			finalDesiredElements = (Collection<ExamElementIF>) finalDesiredElements;
					
					// now call the ExamFactory addProblem method and add it to generatedExams[i]
		//			generatedExams[i].addElementIF(finalDesiredElements);
				}
				
				else if (c.getClass().isInstance(RequiredProblemConstraintIF.class))	
				{
					// cast to a RequiredProblemConstraintIF
					RequiredProblemConstraintIF rpc = (RequiredProblemConstraintIF) c;
				
					// extract relevant data
					SourceIF source = rpc.source(); // necessary?
					String label = rpc.label();
					int points = rpc.points();
					
					// repeat everything listed above
					
					// Question: A RequiredProblemConstraint object contains a label for whatever the
					// required Figure / Block is, and a possible points value. But if we're iterating
					// through a myriad of GroupConstraints AND RequiredProblemConstraints, and the
					// RequiredProblemConstraints are the only things telling us when we need a figure,
					// I'm assuming that order matters in the config file, right? 
					// What I mean is: unless a RequiredProblemConstraint for a figure is listed in 
					// the config file directly before the GroupConstraints outlining the questions
					// it references, how else will we know to group the two together? The only thing
					// logically linking the two constraints is that they both extend the ConstraintIF
				}
				
				else
				{
					// throw new RexUnsatisfiableException();
				}
				
				
			}	
		}
	}

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

}
