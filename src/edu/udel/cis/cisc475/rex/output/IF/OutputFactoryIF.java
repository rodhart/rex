package edu.udel.cis.cisc475.rex.output.IF;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * The OutputFactoryIF specifies classes for writing
 * exam objects and corresponding answerKey objects
 * 
 * @author Team 5
 * 
 */
public interface OutputFactoryIF {
	ExamWriterIF newExamWriter(ExamIF exam); 
	AnswerKeyWriterIF newAnswerKeyWriter(AnswerKeyIF answerKey);
	
} 


