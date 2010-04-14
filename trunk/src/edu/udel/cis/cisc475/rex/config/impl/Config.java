package edu.udel.cis.cisc475.rex.config.impl;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * @author aplatt + verchick
 * 
 */

public class Config implements ConfigIF {
	private long seed;
	private String[] versionStrings;
	private String finalBlock;
	private boolean pdfOption;
	private int numVersions;
	private Collection<ConstraintIF> constraints;
	
	public Config(boolean pdf, int numVersions){
		this.numVersions = numVersions;
		this.pdfOption = pdf;
	}
	
	@Override
	public GroupConstraintIF addGroupConstraintIF(String topic,
			IntervalIF difficulty, int numProblems, int points, SourceIF source) {
		
		GroupConstraintIF constraint = new GroupConstraint(difficulty, numProblems, points, topic, source);
		constraints.add(constraint);
		
		return constraint;
	}

	@Override
	public RequiredProblemConstraintIF addRequiredProblemConstraint(
			String label, int points, SourceIF source) {
		RequiredProblemConstraintIF constraint = new RequiredProblemConstraint(label, points, source);
		constraints.add(constraint);
		
		return constraint;
	}

	@Override
	public Collection<ConstraintIF> constraints() {
		return constraints;
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
