package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * GroupConstraintIF is the interface for the GroupConstraint class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */

public interface GroupConstraintIF extends ConstraintIF{
	/**
	 * Getter for the number of problems in the group
	 * @return number of problems
	 */
	int numProblems();
	
	/**
	 * Getter for the difficulty interval of the group
	 * @return difficulty interval
	 */
	IntervalIF difficultyInterval();
	
	/**
	 * Getter for the topic of the group
	 * @return group topic
	 */
	String topic();
	
	/**
	 * Getter for the number of points for the group
	 * @return required points
	 */
	int points();
	
	/**
	 * Getter for the source of the requirement
	 * @return requirement source
	 */
	SourceIF source();
}// end of interface