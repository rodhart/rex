package edu.udel.cis.cisc475.rex.output.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;

/**
 * OutputFactory creates classes used for writing an Exam object
 * and corresponding AnswerKey to a PrintWriter object
 * 
 *  @author Trevor
 * @author jsong
 * @author cardona
 */
public class OutputFactory implements OutputFactoryIF {

	/**
	 * This method utilizes a print writer and an precreated Exam
     * class to output parts of the exam class to the print writer.
     * 
	 * @param exam an exam object with preamble, front matter, and problems
	 * @return exam an exam object with preamble, front matter, and problems
	 */
	
	public ExamWriterIF newExamWriter(ExamIF exam) {
		return new ExamWriter(exam);
	}
	
	/**
	 * This method writes an answerKey object to any PrintWriter object
	 *  
	 * @param answerKey an answerKey object that corresponds to a completed exam object
	 * @return answerKey an answerKey object that corresponds to a completed exam object
	 */
	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		return new AnswerKeyWriter(answerKey) ;
	}



}
