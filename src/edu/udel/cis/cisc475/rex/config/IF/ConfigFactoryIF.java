package edu.udel.cis.cisc475.rex.config.IF;

import edu.udel.cis.cisc475.rex.config.impl.RequiredProblemConstraint;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public interface ConfigFactoryIF {

	ConfigIF Config(boolean pdf, int numVersions);

	ConstraintIF Constraint(SourceIF source);

	GroupConstraintIF GroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source);
	
	RequiredProblemConstraint RequiredProblemConstraint(String label, int points, SourceIF source);
	
}