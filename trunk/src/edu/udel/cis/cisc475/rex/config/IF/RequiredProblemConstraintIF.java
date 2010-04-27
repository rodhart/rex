package edu.udel.cis.cisc475.rex.config.IF;

/**
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 * RequiredProblemConstraintIF is the interface for the RequiredProblemConstraint class. 
 * It provides a list of the class's public methods.
 * 
 */

public interface RequiredProblemConstraintIF extends ConstraintIF{
	String label();

	int points();

}// end of interface