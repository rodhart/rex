package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * A ConstraintContainer holds information coming from
 * a GroupConstraintIF. numProblems represents the specified
 * number of ProblemIFs the GroupConstraintIF requested, while
 * satisfiedProblems holds all ProblemIFs from the master exam
 * that satisfy the GroupConstraintIF.
 * 
 * @author Greg Simons
 */

public class ConstraintContainer 
{
	private int numProblems;
	private ArrayList<ProblemIF> satisfiedProblems = new ArrayList<ProblemIF>();
	
	public ConstraintContainer(int numProblems)
	{
		this.numProblems = numProblems;
	}
	
	public int getNumProblems()
	{
		return this.numProblems;
	}
	
	public void addSatisfiedProblem(ProblemIF theProblem)
	{
		this.satisfiedProblems.add(theProblem);
	}
	
	public ArrayList<ProblemIF> getSatisfiedProblems()
	{
		return this.satisfiedProblems;
	}
}
