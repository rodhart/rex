package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class SatisfiedContainer
{
	private String name;
	private ArrayList<ProblemIF> requiredProblems = new ArrayList<ProblemIF>();
	private ArrayList<ProblemIF> remainingProblems = new ArrayList<ProblemIF>();
	private ArrayList<FigureProblemContainer> FPCs = new ArrayList<FigureProblemContainer>();
	
	public SatisfiedContainer(String name) 
	{
		this.name = name;
	}
	
	public void addRequired(ProblemIF requiredProblem)
	{
		requiredProblems.add(requiredProblem);
	}
	
	public void addRemaining(ProblemIF remainingProblem)
	{
		ProblemIF[] blacklist = (ProblemIF[]) requiredProblems.toArray();
		boolean add = true;
		
		for (int i = 0; i < blacklist.length && add; i++)
			if (blacklist[i].label().equals(remainingProblem.label()))
				add = false;
				
		if (add)
			remainingProblems.add(remainingProblem);
	}
	
	private void addFPC(FigureProblemContainer newFPC)
	{
		FPCs.add(newFPC);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ArrayList<ProblemIF> getRequiredProblems()
	{
		return this.requiredProblems;
	}
	
	public ArrayList<ProblemIF> getRemainingProblems()
	{
		return this.remainingProblems;
	}
	
	public ArrayList<FigureProblemContainer> getFPCs()
	{
		return this.FPCs;
	}
}
