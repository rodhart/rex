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

	@Override
	public AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamWriterIF newExamWriter(ExamIF exam) {
		// TODO Auto-generated method stub
		return null;
	}

}
