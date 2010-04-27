package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/**
 * @author Greg Simons
 * 
 * A GroupConstraintContainer houses all of the pertinent information
 * surrounding a GroupConstraintIF, and a Collection of ProblemIFs from
 * a master ExamIF that could satisfy the GroupConstraintIF.
 * 
 * GroupConstraintContainers live within TopicOrganizers, and have
 * no particular order.
 * 
 * GroupConstraintContainers throw a RexUnsatisfiableException if the
 * set of ProblemIFs that could satisfy the GroupConstraintIF is less
 * than the GroupConstraintIF's specified number of ProblemIFs required.
 */

public class GroupConstraintContainer 
{
	private String topic;
	private int constraintValue;
	private int pointValue;
	private IntervalIF difficultyInterval;
	
	private Collection<ProblemIF> set = new ArrayList<ProblemIF>();

	/**
	 * @param theGC
	 * 				-The GroupConstraintIF to be unpacked.
	 * @param master
	 * 				-The master ExamIF, needed to obtain the set of ProblemIFs that
	 * 				 could satisfy the GroupConstraintIF.
	 * @throws RexUnsatisfiableException
	 * 				-If the set of ProblemIFs that could satisfy the GroupConstraintIF
	 * 				 is less than the GroupConstraintIF's specified number of
	 * 				 ProblemIFs required.
	 */
	
	public GroupConstraintContainer(GroupConstraintIF theGC, ExamIF master) throws RexUnsatisfiableException
	{
		this.topic = theGC.topic();
		this.constraintValue = theGC.numProblems();
		this.pointValue = theGC.points();
		this.difficultyInterval = theGC.difficultyInterval();
		
		Collection<ProblemIF> problemsByTopic = master.problemsWithTopic(this.topic);
		
		for (ProblemIF currentProblem : problemsByTopic)
		{
			if (this.difficultyInterval.contains(currentProblem.difficulty()));
			{
				currentProblem.setPoints(this.pointValue);
				this.set.add(currentProblem);
			}
		}
			
		if (this.set.size() < this.constraintValue)
		{
			System.err.println("The constraint requesting " + this.constraintValue +
							   " problems from " + this.topic +
							   " of point value " + this.pointValue +
							   " within difficulty " + theGC.difficultyInterval().low() +
							   " through " + theGC.difficultyInterval().high() +
							   " is unsatisfiable.");
			throw new RexUnsatisfiableException();
		}
	}
	
	public String getTopic()
	{ 
		return this.topic; 
	}
	
	public int getConstraintValue()
	{ 
		return this.constraintValue;
	}
	
	public int getPointValue()
	{ 
		return this.pointValue; 
	}
	
	public IntervalIF getDifficultyInterval()
	{ 
		return this.difficultyInterval; 
	}
	
	public Collection<ProblemIF> getSet()
	{
		return this.set; 
	}
}
