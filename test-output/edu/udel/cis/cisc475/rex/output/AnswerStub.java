package edu.udel.cis.cisc475.rex.output;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 *
 */
public class AnswerStub implements AnswerIF {
	private boolean correct;
	private SourceIF source;
	
	public AnswerStub(boolean correct, SourceIF source){
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
