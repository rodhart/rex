package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * The interface to the answer class.
 * 
 * @author fxfitz
 * 
 */
public class Answer implements AnswerIF {
	private boolean correct;
	private SourceIF source;

	public Answer(boolean correct, SourceIF source) {
		this.correct = correct;
		this.source = source;
	}

	/**
	 * True if answer is true; false if false
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * 
	 * Reference to where in the UEF the particular answer is referenced.
	 */
	public SourceIF source() {
		return source;
	}

}
