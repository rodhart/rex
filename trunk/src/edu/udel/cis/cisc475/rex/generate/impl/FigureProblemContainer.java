package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class FigureProblemContainer 
{
	private FigureIF theFigure = null;
	private ArrayList<ProblemIF> theProblems;
	
	public FigureProblemContainer(FigureIF theFigure)
	{
		this.theFigure = theFigure;
	}
	
	public void addProblem(ProblemIF newProblem)
	{
		//assert ((theFigure == null) || (newProblem.referencedFigures().contains(theFigure)));
		
		theProblems.add(newProblem);
	}
	
	public FigureIF getFigure()
	{
		return this.theFigure;
	}
	
	public ArrayList<ProblemIF> getProblems()
	{
		return this.theProblems;
	}
}
