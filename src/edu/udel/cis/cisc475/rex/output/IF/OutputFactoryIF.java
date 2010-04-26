package edu.udel.cis.cisc475.rex.output.IF;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * The OutputFactoryIF specifies classes for writing
 * exam objects and corresponding answerKey objects
 * 
 * @author cardona
 */

public interface OutputFactoryIF {
	
	/**
	 * This method utilizes a print writer and an precreated Exam
     * class to output parts of the exam class to the print writer.
     * 
	 * @param exam an exam object with preamble, front matter, and problems
	 */
	ExamWriterIF newExamWriter(ExamIF exam); 
	
	/**
	 * This method writes an answerKey object to any PrintWriter object
	 *  
	 * @param answerKey an answerKey object that corresponds to a completed exam object
	 */
	AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey);
	
} 


