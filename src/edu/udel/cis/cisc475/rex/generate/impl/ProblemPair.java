package edu.udel.cis.cisc475.rex.generate.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

public class ProblemPair 
{
	private ProblemIF beforeRandomization;
	private ProblemIF afterRandomization;
	
	public ProblemPair(ProblemIF beforeRandomization, ProblemIF afterRandomization)
	{
		this.beforeRandomization = beforeRandomization;
		this.afterRandomization = afterRandomization;
	}
	
	public ProblemIF getBeforeRandomization()
	{
		return this.beforeRandomization;
	}
	
	public ProblemIF getAfterRandomization()
	{
		return this.afterRandomization;
	}
}
