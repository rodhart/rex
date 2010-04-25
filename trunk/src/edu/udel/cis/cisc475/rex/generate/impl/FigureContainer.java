package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * @author Greg Simons
 * 
 * A FigureContainer houses a FigureIF, and a Collection of ProblemIFs.
 * FigureContainers live within BlockContainers; a BlockContainer will never
 * contain two FigureContainers referring to the same FigureIF.
 * 
 * A null FigureIF is permitted, since a ProblemIF does not necessarily
 * refer to a FigureIF.
 */

public class FigureContainer 
{
	private FigureIF theFigure;
	private Collection<ProblemIF> theProblems = new ArrayList<ProblemIF>();
	
	/**
	 * @param theFigure
	 * 					-FigureIF representative of the FigureContainer being instantiated.
	 */
	
	public FigureContainer(FigureIF theFigure) { this.theFigure = theFigure; }
	
	public FigureIF getFigure() { return this.theFigure; }
	public Collection<ProblemIF> getProblems() { return this.theProblems; }
	
	/**
	 * @param theProblem
	 * 					 -ProblemIF to be added to the FigureContainer.
	 */
	
	public void addProblem(ProblemIF theProblem) { theProblems.add(theProblem); }
}
