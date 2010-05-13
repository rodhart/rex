package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * The class for a Fixed Answer. A fixed answer will hold a specific place in
 * the list of answers. For example, an Fixed Answer of "None of the Above" will
 * always be last.
 * 
 * @author fxfitz
 * 
 */
public class FixedAnswer implements FixedAnswerIF {
	private boolean isCorrect;
	private int index;
	private SourceIF source;

	public FixedAnswer(int index, boolean isCorrect, SourceIF source) {
		this.index = index;
		this.isCorrect = isCorrect;
		this.source = source;
	}

	/**
	 * If you have a question that has the following answers: A) Blue B) Green
	 * C) Yellow D) A and B E) None of the Above
	 * 
	 * Index returned: Blue: 0 Green: 1 Yellow: 2 etc.
	 * 
	 * @return index of where the answer will appear in the list of answers
	 */
	public int index() {
		return this.index;
	}

	/**
	 * True if answer is true; false if false
	 */
	public boolean isCorrect() {
		return this.isCorrect;
	}

	/**
	 * 
	 * Reference to where in the UEF the particular answer is referenced.
	 */
	public SourceIF source() {
		return this.source;
	}

}
