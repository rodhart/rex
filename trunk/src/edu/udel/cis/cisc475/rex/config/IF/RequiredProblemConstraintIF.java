package edu.udel.cis.cisc475.rex.config.IF;

/**
 * GroupConstraintIF is the interface for the GroupConstraint class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 */

public interface RequiredProblemConstraintIF extends ConstraintIF{
	String label();

	int points();

}// end of interface