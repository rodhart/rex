package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;


/**
 * 
 * 
 * 
 * @author Reed Martz (martz)
 *
 */
public class ExamFactory implements ExamFactoryIF {

	// @Override
	public AnswerIF newAnswer(boolean correct, SourceIF text) {
		// TODO Change this to use the specified Answer constructor.
	return new Answer();
	}

	// @Override
	public BlockIF newBlock(String topic, String label, SourceIF text) {
		// TODO Change this to use the specified Block constructor.
	return new Block();
	}

	// @Override
	public FigureIF newFigure(String label, SourceIF text) {
		// TODO Change this to use the specified Figure constructor.
		return new Figure();
	}

	// @Override
	public FixedAnswerIF newFixedAnswer(boolean correct, int index,
			SourceIF text) {
		// TODO Change this to use the specified FixedAnswer constructor.
		return new FixedAnswer();
	}

	// @Override
		public ExamIF newGeneratedExam() {
			return new Exam(false);
		}

	// @Override
	public ExamIF newMasterExam() {
		return new Exam(true);
	}

	// @Override
	public ProblemIF newProblem(String topic, String label, SourceIF question,
			AnswerIF[] answers) {
		// TODO Change this to use the specified Problem constructor.	
		return new Problem(topic, label, question, answers);
	}

}
