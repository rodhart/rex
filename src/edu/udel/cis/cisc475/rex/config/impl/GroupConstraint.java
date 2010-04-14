package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/** 
 * 
 * @author aplatt + verchick
 *
 */
public class GroupConstraint implements GroupConstraintIF {
	private IntervalIF difficultyInterval;
	private int numProblems;
	private int points;
	private String topic;
	private SourceIF source;
	
	public GroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source){
		this.difficultyInterval = difficulty;
		this.numProblems = numProblems;
		this.points = points;
		this.topic = topic;
		this.source = source;
	}
	
	@Override
	public IntervalIF difficultyInterval() {
		return this.difficultyInterval;
	}

	@Override
	public int numProblems() {
		return this.numProblems;
	}

	@Override
	public int points() {
		return this.points;
	}

	@Override
	public String topic() {
		return this.topic;
	}

	@Override
	public SourceIF source() {
		return this.source;
	}

}
