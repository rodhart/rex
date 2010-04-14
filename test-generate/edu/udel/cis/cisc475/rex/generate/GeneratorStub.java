package edu.udel.cis.cisc475.rex.generate;

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
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class GeneratorStub implements GeneratorIF {
	
	public GeneratorStub(ExamIF master, ConfigIF config) {
	}

	private void generate() throws RexUnsatisfiableException 
	{
	}

	@Override
	public AnswerKeyIF getAnswerKey(int i) {
		return null;
	}

	@Override
	public ConfigIF getConfig() {
		return null;
	}

	@Override
	public ExamIF getGeneratedExam(int i) {
		return null;
	}

	@Override
	public ExamIF getMaster() {
		return null;
	}

	@Override
	public int numGeneratedExams() {
		return 1;
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
		return null;
	}

}
