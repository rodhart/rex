package edu.udel.cis.cisc475.rex.generate.IF;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public interface GeneratorIF
{
	/**
	 * @author Team 3
	 */
	
	ExamIF getMaster();
	ConfigIF getConfig();
	int numGeneratedExams();
	AnswerKeyIF getAnswerKey(int i);
	ExamIF getGeneratedExam(int i);
}