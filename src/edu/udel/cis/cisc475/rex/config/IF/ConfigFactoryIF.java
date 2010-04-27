package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * ConfigFactoryIF is the interface for the ConfigFactory class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */

public interface ConfigFactoryIF {
	/**
	 * Creates a new instance of Config
	 * @return new instance of Config
	 */
	ConfigIF newConfig(boolean pdf, int numVersions);
	
	/**
	 * Creates a new instance of Constraint
	 * @return new instance of Constraint
	 */
	ConstraintIF newConstraint(SourceIF source);
	
	/**
	 * Creates a new instance of GroupConstraint
	 * @return new instance of GroupConstraint
	 */
	GroupConstraintIF newGroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source);
	
	/**
	 * Creates a new instance of RequiredProblemConstraint
	 * @return new instance of RequiredProblemConstraint
	 */
	RequiredProblemConstraintIF newRequiredProblemConstraint(String label, int points, SourceIF source);
}