package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * ConstraintIF is the interface for the Constraint class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */

public interface ConstraintIF {
	/**
	 * @return source (location of constraint in ECF file)
	 */
	SourceIF source();

}// end of interface