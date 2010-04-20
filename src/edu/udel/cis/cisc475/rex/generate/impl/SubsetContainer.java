package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;

/**
 * A SubsetContainer has a unique topic, an ArrayList of
 * ProblemPairs, and an ArrayList of BlockProblemContainers.
 * 
 * The ArrayList of ProblemPairs represents a subset of the
 * ProblemIFs in (ConstraintContainers union requiredProblems) of
 * the SatisfiedContainer whose name matches the SubsetContainer's name.
 * 
 * These ProblemIFs are then distributed accordingly into BlockProblemContainers,
 * maintained in the ArrayList of BPCs.
 * 
 * @author Greg Simons
 */

public class SubsetContainer 
{
	private String topic;
	private ArrayList<ProblemPair> subset = new ArrayList<ProblemPair>();
	private ArrayList<BlockProblemContainer> theBPCs = new ArrayList<BlockProblemContainer>();
	private BlockProblemContainer nullBPC = new BlockProblemContainer(null);
	
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
	
	public void addToSubset(ProblemPair newProblemPair)
	{
		this.subset.add(newProblemPair);
	}
	
	public BlockProblemContainer getBPC(BlockIF theBlock)
	{
		if (theBlock == null)
		{
			return this.nullBPC;
		}
		
		else
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
}
