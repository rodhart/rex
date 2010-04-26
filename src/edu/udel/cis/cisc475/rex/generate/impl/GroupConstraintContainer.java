package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

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
	
	private double low;
	private double high;
	private boolean strictLow;
	private boolean strictHigh;
	
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
		
		this.low = theGC.difficultyInterval().low();
		this.high = theGC.difficultyInterval().high();
		this.strictLow = theGC.difficultyInterval().strictLow();
		this.strictHigh = theGC.difficultyInterval().strictHigh();
		
		Collection<ProblemIF> problemsByTopic = master.problemsWithTopic(this.topic);
		
		/* Here, the if-else statement is lifted outside of the for loop;
		 * in this manner, the if-else check is performed once, rather than
		 * for each time through the loop.
		 * 
		 * However, using the variable name currentProblem confused
		 * either Java or Eclipse; hence the use of currentProblem1,
		 * currentProblem2, etc.
		 */
		
		if (this.strictLow && this.strictHigh)
			for (ProblemIF currentProblem1 : problemsByTopic)
				if ((this.low < currentProblem1.difficulty()) &&
					(currentProblem1.difficulty() < this.high))
				{
					currentProblem1.setPoints(this.pointValue);
					this.set.add(currentProblem1);
				}
					
		else if (strictLow && !strictHigh)
			for (ProblemIF currentProblem2 : problemsByTopic)
				if ((this.low < currentProblem2.difficulty()) &&
					(currentProblem2.difficulty() <= this.high))
				{
					currentProblem2.setPoints(this.pointValue);
					this.set.add(currentProblem2);
				}
					
		else if (!strictLow && strictHigh)
			for (ProblemIF currentProblem3 : problemsByTopic)
				if ((this.low <= currentProblem3.difficulty()) &&
					(currentProblem3.difficulty() < this.high))
				{
					currentProblem3.setPoints(this.pointValue);
					this.set.add(currentProblem1);
				}
					
		else
			for (ProblemIF currentProblem4 : problemsByTopic)
				if ((this.low <= currentProblem4.points()) &&
					(currentProblem4.points() <= this.high))
				{
					currentProblem4.setPoints(this.pointValue);
					this.set.add(currentProblem1);
				}
					
		if (this.set.size() < this.constraintValue)
		{
			System.err.println("The constraint requesting " + this.constraintValue +
							   " problems from " + this.topic +
							   " of point value " + this.pointValue +
							   " within difficulty " + this.low +
							   " through " + this.high +
							   " is unsatisfiable.");
			throw new RexUnsatisfiableException();
		}
	}
	
	public String getTopic() { return this.topic; }
	public int getConstraintValue() { return this.constraintValue; }
	public int getPointValue() { return this.pointValue; }
	public double getLow() { return this.low; }
	public double getHigh() { return this.high; }
	public boolean getStrictLow() { return this.strictLow; }
	public boolean getStrictHigh() { return this.strictHigh; }
	public Collection<ProblemIF> getSet() { return this.set; }
}

