package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 * GroupConstraintIF is the interface for the GroupConstraint class. 
 * It provides a list of the class's public methods.
 * 
 */

public interface GroupConstraintIF extends ConstraintIF{
	int numProblems();
	IntervalIF difficultyInterval();
	String topic();
	int points();
	SourceIF source();
}// end of interface