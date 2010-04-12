package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
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

	Generator(ExamIF master, ConfigIF config) {
		this.master = master;
		this.config = config;
		generate();
	}

	private void generate() 
	{
		int numExams = config.numVersions();
		Collection<ConstraintIF> theConstraints = config.constraints();
			
		generatedExams = new ExamIF[numExams];
		ExamFactoryIF theExamFactory = new ExamFactory();
		
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config.seed());
		

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
				
					// loop until 'master' END OF FILE
					// we can probably define an Iterator i and do a while(i.hasNext())
					// fill a container with all problems that match the difficulty / topic / points constraints
					// (will be of type ExamElementIF)
					
					// then make a call the randomizer's choose() method
					// like, theRandomizer.choose(numProblems, Collection<ExamElementIF> theProblems)
					
					// the choose() method returns a randomized array of Objects[] equal to numProblems
					
					// now, do any necessary type casting
					
					// now call the ExamFactory addProblem method and add it to generatedExams[i]
					
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
		return config.numVersions(); // CHECK THIS!!!
	}

}
