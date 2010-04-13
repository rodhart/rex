package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/*
 * @author team 2
 * Edited by team 3 to correctly reflect the design document (5pm, 4/10/2010)
 * (Extended ConstraintIF)
 */

public interface GroupConstraintIF extends ConstraintIF{
	int numProblems();

	IntervalIF difficultyInterval();

	String topic();

	int points();

}// end of interface