package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class SubsetContainer 
{
	private String topic;
	private ArrayList<ProblemIF> subset = new ArrayList<ProblemIF>();
	private ArrayList<BlockProblemContainer> theBPCs = new ArrayList<BlockProblemContainer>();
	
	public SubsetContainer(String topic)
	{
		this.topic = topic;
	}
	
	public String getTopic()
	{
		return this.topic;
	}
	
	public ArrayList<ProblemIF> getSubset()
	{
		return this.subset;
	}
	
	public ArrayList<BlockProblemContainer> getBPCs()
	{
		return this.theBPCs;
	}
	
	public void setSubset(ArrayList<ProblemIF> theSubset)
	{
		this.subset.addAll(theSubset);
	}
	
	public void addBPC(BlockProblemContainer theBPC)
	{
		this.theBPCs.add(theBPC);
	}
}
