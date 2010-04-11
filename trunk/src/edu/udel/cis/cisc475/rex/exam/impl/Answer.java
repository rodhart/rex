package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Answer implements AnswerIF {

	private boolean correct;
	
	@Override
	public boolean isCorrect() {
		// TODO Auto-generated method stub
		return correct;
	}

	@Override
	public SourceIF source() {
		// TODO Auto-generated method stub
		return null;
	}

}
