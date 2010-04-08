package edu.udel.cis.cisc475.rex.output.IF;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * 
 * @author team 5
 * 
 */
public interface OutputFactoryIF {
	ExamWriterIF newExamWriter(ExamIF exam); 
	AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey);
	
} 


