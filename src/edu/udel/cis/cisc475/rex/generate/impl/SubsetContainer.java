package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;

public class SubsetContainer 
{
	private String topic;
	private ArrayList<ProblemPair> subset = new ArrayList<ProblemPair>();
	private ArrayList<BlockProblemContainer> theBPCs = new ArrayList<BlockProblemContainer>();
	
	public SubsetContainer(String topic)
	{
		this.topic = topic;
	}
	
	public String getTopic()
	{
		return this.topic;
	}
	
	public ArrayList<ProblemPair> getSubset()
	{
		return this.subset;
	}
	
	public ArrayList<BlockProblemContainer> getBPCs()
	{
		return this.theBPCs;
	}
	
	public void setSubset(ArrayList<ProblemPair> theSubset)
	{
		this.subset.addAll(theSubset);
	}
	
	public BlockProblemContainer getBPC(BlockIF theBlock)
	{
		Iterator<BlockProblemContainer> BPCIterator = theBPCs.iterator();
		BlockProblemContainer theBPC = null;
		boolean found = false;
		
		while (!found && BPCIterator.hasNext())
		{
			theBPC = BPCIterator.next();
			
			if (theBPC.getBlock().label().equals(theBlock.label()))
				found = true;
		}
		
		if (found)
			return theBPC;
		
		else
		{
			theBPC = new BlockProblemContainer(theBlock);
			this.theBPCs.add(theBPC);
			return theBPC;
		}
	}
}
