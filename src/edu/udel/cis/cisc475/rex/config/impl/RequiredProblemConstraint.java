package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class RequiredProblemConstraint implements RequiredProblemConstraintIF {

	private String label;
	private int points;
	private SourceIF source;
	
	public RequiredProblemConstraint(String label, int points, SourceIF source){
		this.label = label;
		this.points = points;
		this.source = source;
	}
	@Override
	public String label() {
		return this.label;
	}

	@Override
	public int points() {
		return this.points;
	}

	@Override
	public SourceIF source() {
		return this.source;
	}

}
