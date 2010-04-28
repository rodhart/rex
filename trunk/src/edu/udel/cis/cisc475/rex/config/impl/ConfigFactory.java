package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * ConfigFactory creates a new instance of the various classes
 * contained in the config package.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */
public class ConfigFactory implements ConfigFactoryIF {

	/**
	 * Creates a new instance of Config
	 * @return new instance of Config
	 */
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		return new Config(pdf, numVersions);
	}

	/**
	 * Creates a new instance of Constraint
	 * @return new instance of Constraint
	 */
	public ConstraintIF newConstraint(SourceIF source){
		if(source == null)
			throw new NullPointerException("argument 'label' cannot be null");
		
		return new Constraint(source);
	}
	
	/**
	 * Creates a new instance of GroupConstraint
	 * @return new instance of GroupConstraint
	 */
	public GroupConstraintIF newGroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source){
		if(difficulty == null)
			throw new NullPointerException("argument 'difficulty' cannot be null");
		if(topic == null)
			throw new NullPointerException("argument 'topic' cannot be null");
		if(source == null)
			throw new NullPointerException("argument 'source' cannot be null");

		return new GroupConstraint(difficulty, numProblems, points, topic, source);
	}
	
	/**
	 * Creates a new instance of RequiredProblemConstraint
	 * @return new instance of RequiredProblemConstraint
	 */
	public RequiredProblemConstraintIF newRequiredProblemConstraint(String label, int points, SourceIF source){
		if(label == null) 
			throw new NullPointerException("argument 'label' cannot be null");
		if(source == null)
			throw new NullPointerException("argument 'source' cannot be null");

		return new RequiredProblemConstraint(label, points, source);
	}
	
}
