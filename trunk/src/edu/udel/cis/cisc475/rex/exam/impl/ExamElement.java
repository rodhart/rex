package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;

/**
 * 
 * @author fxfitz
 *
 */
public class ExamElement implements ExamElementIF {

	private String label;
	
	public ExamElement(String label){
		this.label = label;
	}
	
	public String label() {
		return label;
	}

}
