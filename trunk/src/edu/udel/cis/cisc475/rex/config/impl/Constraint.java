package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * Constraint stores the different types of requirements for the exam documents.
 * It is used by the Config class.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 */
public class Constraint implements ConstraintIF {
	private SourceIF source;
	
	/**
	 * Constructor creates a new instance of Constraint using the parameters passed.
	 * 
	 * @param source - The location of the constraint if the ECF file.
	 */
	public Constraint(SourceIF source){
		this.source = source;
	}
	
	/**
	 * returns the constraint source.
	 */
	public SourceIF source() {
		return this.source;
	}

}
