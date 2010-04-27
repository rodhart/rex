package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 * ConfigFactoryIF is the interface for the ConfigFactory class. 
 * It provides a list of the class's public methods.
 * 
 */

public interface ConfigFactoryIF {
	ConfigIF newConfig(boolean pdf, int numVersions);
	ConstraintIF newConstraint(SourceIF source);
	GroupConstraintIF newGroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source);
	RequiredProblemConstraintIF newRequiredProblemConstraint(String label, int points, SourceIF source);
}