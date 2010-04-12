package edu.udel.cis.cisc475.rex.exam.IF;

/**
 * There are three kinds of elements: Problems, Blocks, and Figures.
 * All of these elements implement the ExamElementIF interface, allowing
 * this interface to serve as a "parent" to problems, blocks, and figures.
 * This allows us to extend the elements without having to change the Exam
 * interface.
 * 
 * @author fxfitz
 *
 */
public interface ExamElementIF {

	/**
	 * @return The element's label
	 */
	String label();
	
}
