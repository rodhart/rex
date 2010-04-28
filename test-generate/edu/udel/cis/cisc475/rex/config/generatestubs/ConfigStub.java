package edu.udel.cis.cisc475.rex.config.generatestubs;

import java.util.Collection;
import java.util.HashSet;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.GroupConstraint;
import edu.udel.cis.cisc475.rex.config.impl.RequiredProblemConstraint;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.generatestubs.IntervalFactoryStub;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.generatestubs.SourceFactoryStub;

/**
 * This is a STUB for the Config class. To be used for testing purposes only.
 */
public class ConfigStub implements ConfigIF {
	long seed;
	String[] versionStrings;
	String finalBlock;
	boolean pdfOption;
	int numVersions;
	HashSet<ConstraintIF> constraints = new HashSet<ConstraintIF>();

	SourceFactoryIF sourceFactory = new SourceFactoryStub();
	ConfigFactoryIF configFactory = new ConfigFactoryStub();
	IntervalFactoryIF intervalFactory = new IntervalFactoryStub();

	public ConfigStub(boolean pdf, int numVersions) {
		this.pdfOption = pdf;
		this.numVersions = numVersions;

		SourceIF source1 = sourceFactory.newSource("testFile.txt");
		source1.addText("question1");

		SourceIF source2 = sourceFactory.newSource("testFile.txt");
		source1.addText("question2");
		IntervalIF interval = null;

		try {
			interval = intervalFactory.interval(true, 1.0, true, 5.0);
		} catch (RexUnsatisfiableException e) {
			System.out.println(e);
			System.exit(1);
		}

		RequiredProblemConstraint constraint1 = new RequiredProblemConstraint(
				"label1", 4, source1);
		GroupConstraint constraint2 = new GroupConstraint(interval, 1, 3,
				"topic2", source2);

		this.constraints.add(constraint1);
		this.constraints.add(constraint2);
	}

	@Override
	public RequiredProblemConstraintIF addRequiredProblemConstraint(
			String label, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConstraintIF> constraints() {
		// TODO Auto-generated method stub
		return constraints;
	}

	@Override
	public String finalBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numVersions() {
		// TODO Auto-generated method stub
		return numVersions;
	}

	@Override
	public boolean pdfOption() {
		// TODO Auto-generated method stub
		return pdfOption;
	}

	@Override
	public long seed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFinalBlock(String label) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSeed(long value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersionStrings(String[] names) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] versionStrings() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public GroupConstraintIF addGroupConstraintIF(String topic,
	// IntervalIF difficulty, int numProblems, int points, SourceIF source) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public GroupConstraintIF addGroupConstraint(String topic,
			IntervalIF difficulty, int numProblems, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
	}

}
