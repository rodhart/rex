package edu.udel.cis.cisc475.rex.output.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;

/**
 * 
 * @author mcloughl
 * @author cardona
 */
public class OutputFactory implements OutputFactoryIF {

	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		return new AnswerKeyWriter(answerKey) ;
	}

	public ExamWriterIF newExamWriter(ExamIF exam) {
		return new ExamWriter(exam);
	}

}
