package edu.udel.cis.cisc475.rex.output;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;

/**
 * 
 * @author mcloughl
 * needed this class to compile main module
 *
 */
public class OutputFactory implements OutputFactoryIF {

	// unsure of how to implement this method
	// tried return new AnswerKeyWriter(answerKey)
	// says it can't find a constructor for it
	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		return null;	
	}

	public ExamWriterIF newExamWriter(ExamIF exam) {
		return new ExamWriter(exam);
	}

}
