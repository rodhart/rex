package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * A FigureProblemContainer (FPC) is a useful way of pairing
 * FigureIFs with ProblemIFs.
 * 
 * Each FPC has a FigureIF and all ProblemIFs within a topic
 * that refer to it.
 * 
 * A collection of FPCs is then stored in SatisfiedContainers.
 * 
 * @author Greg Simons, Zach Hine
 */

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
