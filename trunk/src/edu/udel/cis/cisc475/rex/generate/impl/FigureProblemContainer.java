package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;


/**
 * FigureProblemContainers (FPCs) have a unique FigureIF,
 * and an ArrayList of ProblemIFs that reference that FigureIF.
 * 
 * While a ProblemIF can reference more than one FigureIF, 
 * all ProblemIFs have already been grouped based on
 * BlockIFs; it is assumed that the number of FigureIFs
 * references within a BlockProblemContainer will
 * be small.
 * 
 * @author Greg Simons
 */

public class FigureProblemContainer 
{
	private FigureIF theFigure;
	private ArrayList<ProblemIF> theProblems = new ArrayList<ProblemIF>();
	
	public FigureProblemContainer(FigureIF theFigure)
	{
		this.theFigure = theFigure;
	}
	
	public FigureIF getFigure()
	{
		return this.theFigure;
	}
	
	public ArrayList<ProblemIF> getProblems()
	{
		return this.theProblems;
	}
	
	public void addProblem(ProblemIF theProblem)
	{	
		this.theProblems.add(theProblem);
	}
}
