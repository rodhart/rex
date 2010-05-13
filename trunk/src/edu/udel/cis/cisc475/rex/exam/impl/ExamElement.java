package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;

/**
 * There are three kinds of elements: Problems, Blocks, and Figures. All of
 * these elements implement the ExamElementIF interface, allowing this interface
 * to serve as a "parent" to problems, blocks, and figures. This allows us to
 * extend the elements without having to change the Exam interface.
 * 
 * @author fxfitz
 * 
 */
public class ExamElement implements ExamElementIF {

	private String label;

	public ExamElement(String label) {
		this.label = label;
	}

	/**
	 * The element's label, this is an optional argument
	 */
	public String label() {
		return label;
	}

}
