package edu.udel.cis.cisc475.rex.generate.IF;

import edu.udel.cis.cisc475.rex.exam.IF.*;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.config.*;

public interface GeneratorIF {
	
	/**
	 * @author Team 3
	 */
	
	ExamIF getMaster();
	Config getConfig();
	int numGeneratedExams();
	AnswerKeyIF getAnswerKey(int i);
	ExamIF getGeneratedExam(int i);

}