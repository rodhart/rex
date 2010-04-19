package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * A SatisfiedContianer is a useful way of collecting all ProblemIFs
 * at varying stages of generating ExamIFs. They have a name, representing
 * a different topic on an ExamIF.
 * 
 * 1.) requiredProblems is an ArrayList of ProblemIFs specified as being
 *     required within a topic.
 * 2.) remainingProblems is an ArrayList of all ProblemIFs that fulfill
 *     the other criteria specified by GroupConstraintIFs, within a topic.
 * 3.) selectedSubset is n ProblemIFs chosen from remainingProblems (n being
 *     specified by GroupConstraintIFs), and all ProblemIFs from requiredProblems.
 *     
 *     Note that none of these n ProblemIFs were specified by a
 *     RequiredProblemConstraintIF.
 * 4.) FPCs is an ArrayList of FigureProblemContainers, each housing a different
 *     FigureIF referenced by ProblemIFs in selectedSubset, as well as the ProblemIFs
 *     that reference them.
 * 
 * @author Greg Simons, Zach Hine
 */

public class SatisfiedContainer
{
	private String name;
	private ArrayList<ProblemIF> requiredProblems = new ArrayList<ProblemIF>();
	private ArrayList<ProblemIF> remainingProblems = new ArrayList<ProblemIF>();
	private ArrayList<ProblemIF> selectedSubset = new ArrayList<ProblemIF>();
	private ArrayList<FigureProblemContainer> FPCs = new ArrayList<FigureProblemContainer>();
	
	public SatisfiedContainer(String name) 
	{
		this.name = name;
	}
	
	public void addRequired(ProblemIF requiredProblem)
	{
		requiredProblems.add(requiredProblem);
	}
	
	/* addRemaining ensures that a ProblemIF specified by a
	 * RequiredProblemConstraintIF is not marked as a
	 * remaining problem.
	 */
	
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
	
	public void setSubset(ArrayList<ProblemIF> subset)
	{
		this.selectedSubset = subset;
	}
	
	/* This method does not seem to be working.
	 */
	
	public void addFPC(FigureProblemContainer newFPC)
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
	
	public ArrayList<ProblemIF> getSelectedSubset()
	{
		return this.selectedSubset;
	}
	
	public ArrayList<FigureProblemContainer> getFPCs()
	{
		return this.FPCs;
	}
}
