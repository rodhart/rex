package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

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
	private SourceIF theSource;
	
	
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
		this.theSource = theGC.source();
		
		Collection<ProblemIF> problemsByTopic = master.problemsWithTopic(this.topic);
		
		for (ProblemIF currentProblem : problemsByTopic)
		{
			if (this.difficultyInterval.contains(currentProblem.difficulty()))
			{
				currentProblem.setPoints(this.pointValue);
				this.set.add(currentProblem);
			}
		}
			
		if (this.set.size() < this.constraintValue)
		{
			throw new RexUnsatisfiableException("The constraint requesting " + this.constraintValue +
					   							" problem(s) from " + this.topic +
					   							" of point value " + this.pointValue +
					   							" within difficulty " + theGC.difficultyInterval().low() +
					   							" through " + theGC.difficultyInterval().high() +
												" is unsatisfiable.", theGC.source());
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
	
	public SourceIF getSource()
	{
		return this.theSource;
	}
	
	public Collection<ProblemIF> getSet()
	{
		return this.set; 
	}
}

