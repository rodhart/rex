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

	/* (non-Javadoc)
	 * @see edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF#newAnswerKeyWriter(edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF)
	 */
	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		return new AnswerKeyWriter(answerKey) ;
	}

	/* (non-Javadoc)
	 * @see edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF#newExamWriter(edu.udel.cis.cisc475.rex.exam.IF.ExamIF)
	 */
	public ExamWriterIF newExamWriter(ExamIF exam) {
		return new ExamWriter(exam);
	}

}
