package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/*
 * @author team 2
 *
 */

public interface GroupConstraintIF {
	int numProblems();

	IntervalIF difficultyInterval();

	String topic();

	int points();

}// end of interface