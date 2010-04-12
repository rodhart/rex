package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * @author fxfitz
 *
 */
public class Answer implements AnswerIF {
	private boolean correct;
	private SourceIF source;
	
	public Answer(boolean correct, SourceIF source){
		this.correct = correct;
		this.source = source;
	}
	
	@Override
	public boolean isCorrect() {
		return correct;
	}

	@Override
	public SourceIF source() {
		return source;
	}

}
