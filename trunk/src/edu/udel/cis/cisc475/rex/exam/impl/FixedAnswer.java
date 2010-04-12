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
		return index;
	}

	@Override
	public boolean isCorrect() {
		return isCorrect;
	}

	@Override
	public SourceIF source() {
		return source;
	}

}