package edu.udel.cis.cisc475.rex.output.IF;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * Factory class for creating objects of type ExamWriterIF and AnswerKeyIF
 * 
 * @author cardona
 */

public interface OutputFactoryIF {
	
	/**
	 * Create new object that implements ExamWriterIF
     * 
	 * @param exam an exam object with preamble, front matter, and problems
	 */
	ExamWriterIF newExamWriter(ExamIF exam); 
	
	/**
	 * Create new object that implements AnswerKeyWriterIF
	 *  
	 * @param answerKey an answerKey object that corresponds to a completed exam object
	 */
	AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey);
	
} 


