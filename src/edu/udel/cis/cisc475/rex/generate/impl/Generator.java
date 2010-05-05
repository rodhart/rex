package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * @author Greg Simons, Zach Hine, Keith McLoughlin
 * 
 *         Generates randomized ExamIFs and their corresponding AnswerKeyIFs.
 * 
 */

public class Generator implements GeneratorIF {
	private ExamIF master;
	private ConfigIF config;

	private ExamIF[] generatedExams;
	private AnswerKeyIF[] answerKeys;

	private int numExams;

	/**
	 * @param master
	 *            -ExamIF representing the master ExamIF. Given to Generator by
	 *            UEFParser.
	 * @param config
	 *            -ConfigIF containing all of the ConstraintIFs used for each
	 *            randomly generated ExamIF, as well as other information.
	 * @throws RexException
	 *             -If the ConfigIF contains a ConstraintIF that is not a
	 *             RequiredProblemConstraintIF or a GroupConstraintIF, or if a
	 *             ConstraintIF is not satisfiable.
	 * @throws NullPointerException
	 *             -If theRandomizer.choose() fails (or a logic error causes an
	 *             invalid data set to be randomized).
	 */

	Generator(ExamIF master, ConfigIF config) throws RexException {
		this.master = master;
		this.config = config;

		this.numExams = config.numVersions();

		// try
		// {
		generate();
		/*
		 * }
		 * 
		 * catch (Exception e) { e.printStackTrace(); }
		 */
	}

	public ExamIF getMaster() {
		return this.master;
	}

	public ConfigIF getConfig() {
		return this.config;
	}

	public ExamIF getGeneratedExam(int i) {
		return this.generatedExams[i];
	}

	public AnswerKeyIF getAnswerKey(int i) {
		return this.answerKeys[i];
	}

	public int numGeneratedExams() {
		return this.numExams;
	}

	private void generate() throws RexException {
		this.generatedExams = new ExamIF[this.numExams];
		this.answerKeys = new AnswerKeyIF[this.numExams];

		ExamFactoryIF theExamFactory = new ExamFactory();
		AnswerKeyFactoryIF theAKF = new AnswerKeyFactory();
		RandomizerFactoryIF theRandomizerFactory = new RandomizerFactory();
		RandomizerIF theRandomizer = theRandomizerFactory.newRandomizer(config
				.seed());

		MasterExamController mec = new MasterExamController(master);
		VersionExamController vec;

		ConstraintIF[] theConstraints = (ConstraintIF[]) config.constraints()
				.toArray(new ConstraintIF[config.constraints().size()]);
		Collection<RequiredProblemConstraintIF> theRPCs = new ArrayList<RequiredProblemConstraintIF>();
		Collection<GroupConstraintIF> theGCs = new ArrayList<GroupConstraintIF>();

		/*
		 * First, divide the ConstraintIFs into a RequiredProblemConstraintIFs
		 * and GroupConstraintIFs.
		 */

		for (ConstraintIF theConstraint : theConstraints) {
			/*
			 * Replacing with .equals attempt for now if
			 * (theConstraint.getClass().isInstance(GroupConstraintIF.class))
			 * theGCs.add((GroupConstraintIF) theConstraint);
			 * 
			 * else if
			 * (theConstraint.getClass().isInstance(RequiredProblemConstraintIF
			 * .class)) theRPCs.add((RequiredProblemConstraintIF)
			 * theConstraint);
			 */

			// if
			// (theConstraint.getClass().getSimpleName().equals("GroupConstraint"))
			if (theConstraint instanceof GroupConstraintIF)
				theGCs.add((GroupConstraintIF) theConstraint);

			// else if
			// (theConstraint.getClass().getSimpleName().equals("RequiredProblemConstraint"))
			else if (theConstraint instanceof RequiredProblemConstraintIF)
				theRPCs.add((RequiredProblemConstraintIF) theConstraint);

			else
				throw new RexParseException(
						"Generator recieved a ConstraintIF that is not a "
								+ "RequiredProblemConstraintIF or a GroupConstraintIF.",
						theConstraint.source());
		}

		// Add all RequiredProblemConstraintIFs to the MasterExamController.
		for (RequiredProblemConstraintIF theRPC : theRPCs)
			mec.addRequiredProblem(theRPC);

		// Add all GroupConstraintIFs to the MasterExamController.
		for (GroupConstraintIF theGC : theGCs)
			mec.distributeGroupConstraint(theGC);

		/*
		 * For each generated ExamIF, create a VersionExamController.
		 * 
		 * Add all the required problems to the vec, satisfy all constraints in
		 * the vec, distribute all the problems within the vec, then fill out a
		 * generated ExamIF and AnswerKeyIF.
		 */

		for (int i = 0; i < this.numExams; i++) {
			generatedExams[i] = theExamFactory.newGeneratedExam();
			answerKeys[i] = theAKF.newAnswerKey(config.versionStrings()[i],
					"examName", "date"); // I have no idea what these are, or
											// where they come from.

			generatedExams[i].setFrontMatter(master.frontMatter());
			generatedExams[i].setPreamble(master.preamble());

			vec = new VersionExamController(mec, theRandomizer);
			vec.addRequiredProblems();
			vec.satisfyConstraints();
			vec.distributeProblems();
			vec.fillExam(generatedExams[i], answerKeys[i]);
		}
	}
}
