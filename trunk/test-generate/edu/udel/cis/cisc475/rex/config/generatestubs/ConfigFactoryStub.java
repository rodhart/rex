package edu.udel.cis.cisc475.rex.config.generatestubs;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.Constraint;
import edu.udel.cis.cisc475.rex.config.impl.GroupConstraint;
import edu.udel.cis.cisc475.rex.config.impl.RequiredProblemConstraint;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class ConfigFactoryStub implements ConfigFactoryIF {

	@Override
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		return new ConfigStub(pdf, numVersions);
	}

	@Override
	public ConstraintIF newConstraint(SourceIF source) {
		return new Constraint(source);
	}

	@Override
	public GroupConstraintIF newGroupConstraint(IntervalIF difficulty,
			int numProblems, int points, String topic, SourceIF source) {
		return new GroupConstraint(difficulty, numProblems, points, topic, source);
	}

	@Override
	public RequiredProblemConstraintIF newRequiredProblemConstraint(
			String label, int points, SourceIF source) {
		return new RequiredProblemConstraint(label, points, source);
	}

}
