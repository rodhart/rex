package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.RandomizerFactory;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

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

	private void generate() {
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
				/* Test Constraint type; extract information from Constraint.
				 * Meet constraints using MasterExam (master).
				 * Pass collection of met constraint problems to randomizer.
				 * Add what randomizer returns to generatedExams[i].
				 */
				
				if (c.getClass().getName() == "GroupConstraintIF")
				{
					//Do stuff if it's a GroupConstraint
				}
				
				if (c.getClass().getName() == "RequiredProblemConstraintIF")
				{
					//Do stuff if it's a RequiredProblemConstraint
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
