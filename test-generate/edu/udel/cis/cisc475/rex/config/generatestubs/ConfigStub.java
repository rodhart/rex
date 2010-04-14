package edu.udel.cis.cisc475.rex.config.generatestubs;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * This is a STUB for the Config class. To be used for testing purposes only.
 */
public class ConfigStub implements ConfigIF {
	Boolean c_pdf;
	int c_numVersions;

	public ConfigStub(boolean pdf, int numVersions) {
		this.c_pdf = pdf;
		this.c_numVersions = numVersions;
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
		return null;
	}

	@Override
	public String finalBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numVersions() {
		// TODO Auto-generated method stub
		return c_numVersions;
	}

	@Override
	public boolean pdfOption() {
		// TODO Auto-generated method stub
		return c_pdf;
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

	@Override
	public GroupConstraintIF addGroupConstraintIF(String topic,
			IntervalIF difficulty, int numProblems, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
	}

}
