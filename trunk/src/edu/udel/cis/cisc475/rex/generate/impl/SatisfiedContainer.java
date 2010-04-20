package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * SatisfiedContainers have a unique topic, an ArrayList
 * of ProblemIFs that are required on an exam, and an
 * ArrayList of ConstraintContainers.
 * 
 * Before the ProblemIFs that match a given ConstraintIF
 * are added to a ConstraintContainer in SatisfiedContainer,
 * all ProblemIFs in requiredProblems, and all existing
 * ConstraintContainers in SatisfiedContainer are checked; if
 * the ProblemIF in question is found, it is not added.
 * 
 * @author Greg Simons
 */

public class SatisfiedContainer 
{
	private String topic;
	private ArrayList<ProblemIF> requiredProblems = new ArrayList<ProblemIF>();
	private ArrayList<ConstraintContainer> theConstraintContainers = new ArrayList<ConstraintContainer>();
	
	public SatisfiedContainer(String topic)
	{
		this.topic = topic;
	}
	
	public String getTopic()
	{
		return this.topic;
	}
	
	public ArrayList<ProblemIF> getRequiredProblems()
	{
		return this.requiredProblems;
	}
	
	public ArrayList<ConstraintContainer> getSatisfiedConstraints()
	{
		return this.theConstraintContainers;
	}
	
	public void addRequired(ProblemIF requiredProblem)
	{
		ProblemIF[] requiredBlacklist = (ProblemIF[]) this.requiredProblems.toArray();
		boolean add = true;
		
		for (int i = 0; i < requiredBlacklist.length && add; i++)
			if (requiredBlacklist[i].label().equals(requiredProblem.label()))
				add = false;
		
		this.requiredProblems.add(requiredProblem);
	}
	
	public ConstraintContainer getLastConstraintContainer()
	{
		ConstraintContainer[] theConstraintContainers = (ConstraintContainer[]) this.theConstraintContainers.toArray();
		
		return theConstraintContainers[theConstraintContainers.length - 1];
	}
	
	public void addRemaining(ProblemIF remainingProblem, int constraintValue, boolean makeNewConstraintContainer)
	{
		ConstraintContainer[] theConstraintContainersArray = (ConstraintContainer[]) this.theConstraintContainers.toArray();
		ConstraintContainer theConstraintContainer;
		
		ProblemIF[] requiredBlacklist = (ProblemIF[]) this.requiredProblems.toArray();
		boolean add = true;
		
		if (makeNewConstraintContainer)
		{
			theConstraintContainer = new ConstraintContainer(constraintValue);
			this.theConstraintContainers.add(theConstraintContainer);
		}
		
		else
			theConstraintContainer = getLastConstraintContainer();
		
		for (int i = 0; i < requiredBlacklist.length && add; i++)
			if (requiredBlacklist[i].label().equals(remainingProblem.label()))
				add = false;
		
		for (int i = 0; i < theConstraintContainersArray.length && add; i++)
			for (ProblemIF currentProblem : theConstraintContainersArray[i].getSatisfiedProblems())
				if (currentProblem.label().equals(remainingProblem.label()))
					add = false;
		
		if (add)
			theConstraintContainer.addSatisfiedProblem(remainingProblem);
	}
}