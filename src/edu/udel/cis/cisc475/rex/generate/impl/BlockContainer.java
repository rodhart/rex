package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.HashMap;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;

/**
 * @author Greg Simons
 * 
 * A BlockContainer houses a BlockIF, and a HashMap of <FigureIF, FigureContainer> pairs.
 * BlockContainers live within TopicContainers; a TopicContainer will never contain
 * two BlockContainers referring to the same BlockIF.
 * 
 * A null BlockIF is permitted, since a ProblemIF does not necessarily refer to a
 * BlockIF. HashMaps allow null as a key.
 */

public class BlockContainer 
{
	private BlockIF theBlock;
	private HashMap theFCs = new HashMap();
	
	/**
	 * @param theBlock
	 * 					-BlockIF representative of the BlockContainer being instantiated.
	 */
	
	public BlockContainer(BlockIF theBlock)
	{
		this.theBlock = theBlock;
	}
	
	public BlockIF getBlock()
	{
		return this.theBlock;
	}
	
	public HashMap getFCs()
	{
		return this.theFCs;
	}
	
	/**
	 * @param theFigure
	 * 					-FigureIF identifying the FigureContainer to be returned
	 * 					 (A new FigureContainer will be instantiated if one does not already exist.)
	 * @return
	 * 					-FigureContainer specified by the FigureIF taken as input.
	 */
	
	public FigureContainer getFC(FigureIF theFigure)
	{
		FigureContainer theFC;
		
		if (this.theFCs.containsKey(theFigure))
			return (FigureContainer) this.theFCs.get(theFigure);
		
		else
		{
			theFC = new FigureContainer(theFigure);
			this.theFCs.put(theFigure, theFC);
			
			return theFC;
		}
	}
}
