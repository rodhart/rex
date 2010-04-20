package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class SatisfiedContainer 
{
	private String topic;
	private ArrayList<ProblemIF> requiredProblems = new ArrayList<ProblemIF>();
	private ArrayList<ProblemIF> remainingProblems = new ArrayList<ProblemIF>();
	
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
	
	public ArrayList<ProblemIF> getRemainingProblems()
	{
		return this.remainingProblems;
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
	
	public void addRemaining(ProblemIF remainingProblem)
	{
		ProblemIF[] requiredBlacklist = (ProblemIF[]) this.requiredProblems.toArray();
		ProblemIF[] remainingBlacklist = (ProblemIF[]) this.remainingProblems.toArray();
		boolean add = true;
		
		for (int i = 0; i < requiredBlacklist.length && add; i++)
			if (requiredBlacklist[i].label().equals(remainingProblem.label()))
				add = false;
		
		for (int i = 0; i < remainingBlacklist.length && add; i++)
			if (remainingBlacklist[i].label().equals(remainingProblem.label()))
				add = false;
		
		if (add)
			this.remainingProblems.add(remainingProblem);
	}
}
