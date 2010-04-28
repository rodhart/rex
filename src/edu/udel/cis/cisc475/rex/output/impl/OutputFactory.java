package edu.udel.cis.cisc475.rex.output.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;

/**
 * This class implments OutputFactoryIF and is used to create instances of
 * OutputFactory
 * 
 * @author jsong
 * @author cardona
 */
public class OutputFactory implements OutputFactoryIF {

	/**
	 * creates a new instance of ExamWriter
     * 
	 * @param exam an exam object with preamble, front matter, and problems
	 * @return exam an exam object with preamble, front matter, and problems
	 */
	
	public ExamWriterIF newExamWriter(ExamIF exam) {
		return new ExamWriter(exam);
	}
	
	/**
	 * creates a new instance of AnswerKeyWriter
	 *  
	 * @param answerKey an answerKey object that corresponds to a completed exam object
	 * @return answerKey an answerKey object that corresponds to a completed exam object
	 */
	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		return new AnswerKeyWriter(answerKey) ;
	}

}
