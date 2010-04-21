package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/** 
 * GroupConstraint stores and accesses the different properties of a group constraint
 * and is used by the config class.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 */
public class GroupConstraint implements GroupConstraintIF {
	private IntervalIF difficultyInterval;
	private int numProblems;
	private int points;
	private String topic;
	private SourceIF source;
	
	/**
	 * Constructor creates a new instance of GroupConstraint with the passed parameters.
	 * @param difficulty
	 * @param numProblems
	 * @param points
	 * @param topic
	 * @param source
	 */
	public GroupConstraint(IntervalIF difficulty, int numProblems, int points, String topic, SourceIF source){
		this.difficultyInterval = difficulty;
		this.numProblems = numProblems;
		this.points = points;
		this.topic = topic;
		this.source = source;
	}
	
	/**
	 * returns the difficulty interval
	 */
	public IntervalIF difficultyInterval() {
		return this.difficultyInterval;
	}

	/**
	 * returns the number of problems
	 */
	public int numProblems() {
		return this.numProblems;
	}

	/**
	 * returns the required points
	 */
	public int points() {
		return this.points;
	}

	/**
	 * returns the group topic
	 */
	public String topic() {
		return this.topic;
	}

	/**
	 * returns the requirement source
	 */
	public SourceIF source() {
		return this.source;
	}

}
