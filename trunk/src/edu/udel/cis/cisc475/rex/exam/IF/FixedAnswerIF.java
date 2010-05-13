package edu.udel.cis.cisc475.rex.exam.IF;

/**
 * The interfaced for a Fixed Answer. A fixed answer will hold a specific place
 * in the list of answers. For example, an Fixed Answer of "None of the Above"
 * will always be last.
 * 
 * @author fxfitz
 * 
 */
public interface FixedAnswerIF extends AnswerIF {
	/**
	 * If you have a question that has the following answers: A) Blue B) Green
	 * C) Yellow D) A and B E) None of the Above
	 * 
	 * Index returned: Blue: 0 Green: 1 Yellow: 2 etc.
	 * 
	 * @return index of where the answer will appear in the list of answers
	 */
	int index();
}
