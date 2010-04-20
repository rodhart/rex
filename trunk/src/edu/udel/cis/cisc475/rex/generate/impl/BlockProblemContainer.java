package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class BlockProblemContainer 
{
	private BlockIF theBlock;
	private ArrayList<ProblemIF> theProblems;
	private ArrayList<FigureProblemContainer> theFPCs = new ArrayList<FigureProblemContainer>();
	
	public BlockProblemContainer(BlockIF theBlock)
	{
		this.theBlock = theBlock;
	}
	
	public BlockIF getBlock()
	{
		return this.theBlock;
	}
	
	public ArrayList<ProblemIF> getProblems()
	{
		return this.theProblems;
	}
	
	public ArrayList<FigureProblemContainer> getFPCs()
	{
		return this.theFPCs;
	}
	
	public void addProblem(ProblemIF theProblem)
	{
		this.theProblems.add(theProblem);
	}
	
	public FigureProblemContainer getFPC(FigureIF theFigure)
	{
		Iterator<FigureProblemContainer> FPCIterator = theFPCs.iterator();
		FigureProblemContainer theFPC = null;
		boolean found = false;
		
		while (!found && FPCIterator.hasNext())
		{
			theFPC = FPCIterator.next();
			
			if (theFPC.getFigure().label().equals(theFigure.label()))
				found = true;
		}
		
		if (found)
			return theFPC;
		
		else
		{
			theFPC = new FigureProblemContainer(theFigure);
			this.theFPCs.add(theFPC);
			return theFPC;
		}
	}

	
}
