package edu.udel.cis.cisc475.rex.generate.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * A ProblemPair relates to ProblemIFs.
 * 
 * Since the Generator module cannot change a ProblemIFs AnswerIFs, yet must
 * randomize all of a ProblemIFs non-FixedAnswerIFs, Generator creates a new ProblemIF.
 * 
 * However, in doing this, the new ProblemIF loses all of its use constraints,
 * i.e. it does not know what Figures or Blocks it uses, and Figures and Blocks
 * do not know that the new ProblemIF use them.
 * 
 * By grouping the new ProblemIF with shuffled non-FixedAnswerIFs with the original
 * ProblemIF, these use constraints are maintained.
 * 
 * @author Greg Simons
 */

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
