package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/** 
 * RequiredProblemConstraint stores and accesses the different properties of a problem constraint
 * and is used by the config class.
 * 
 * @author Anthony Platt
 * @author Jeremy Verchick
 *
 */
public class RequiredProblemConstraint implements RequiredProblemConstraintIF {

	private String label;
	private int points;
	private SourceIF source;
	
	/**
	 * Constructor creates a new instance of GroupConstraint with the passed parameters.
	 * 
	 * @param label
	 * @param points
	 * @param source
	 */
	public RequiredProblemConstraint(String label, int points, SourceIF source){
		this.label = label;
		this.points = points;
		this.source = source;
	}

	/**
	 * returns the problem label
	 */
	public String label() {
		return this.label;
	}

	/**
	 * returns the required point value
	 */
	public int points() {
		return this.points;
	}

	/**
	 * returns the constraint source
	 */
	public SourceIF source() {
		return this.source;
	}

}
