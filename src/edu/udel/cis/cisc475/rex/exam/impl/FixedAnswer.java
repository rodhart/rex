package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * @author fxfitz
 *
 */
public class FixedAnswer implements FixedAnswerIF {
	private boolean isCorrect;
	private int index;
	private SourceIF source;
	
	public FixedAnswer(int index, boolean isCorrect, SourceIF source){
		this.index = index;
		this.isCorrect = isCorrect;
		this.source = source;
	}
	
	@Override
	public int index() {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public boolean isCorrect() {
		// TODO Auto-generated method stub
		return isCorrect;
	}

	@Override
	public SourceIF source() {
		// TODO Auto-generated method stub
		return source;
	}

}
