package edu.udel.cis.cisc475.rex.config.impl;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Config implements ConfigIF {
	private long seed;
	private String[] versionStrings;
	private String finalBlock;
	private boolean pdfOption;
	private int numVersions;
	
	public Config(boolean pdf, int numVersions){
		this.numVersions = numVersions;
		this.pdfOption = pdf;
	}
	
	@Override
	public GroupConstraintIF addGroupConstraintIF(String topic,
			IntervalIF difficulty, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
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
		return this.finalBlock;
	}

	@Override
	public int numVersions() {
		return this.numVersions;
	}

	@Override
	public boolean pdfOption() {
		return this.pdfOption;
	}

	@Override
	public long seed() {
		return this.seed;
	}

	@Override
	public void setFinalBlock(String label) {
		this.finalBlock = label;
	}

	@Override
	public void setSeed(long value) {
		this.seed = value;

	}

	@Override
	public void setVersionStrings(String[] names) {
		this.versionStrings = names;
	}

	@Override
	public String[] versionStrings() {
		return this.versionStrings;
	}

}
