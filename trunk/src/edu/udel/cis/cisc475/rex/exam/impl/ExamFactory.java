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

	/**
	 * Creates a new instance of AnswerIF.
	 * 
	 * @return AnswerIF
	 */
	public AnswerIF newAnswer(boolean correct, SourceIF text) {
		return new Answer(correct, text);
	}

	/**
	 * Creates a new instance of BlockIF.
	 * 
	 * @return BlockIF
	 */
	public BlockIF newBlock(String topic, String label, SourceIF text) {
		return new Block(topic, label, text);
	}

	/**
	 * Creates a new instance of FigureIF.
	 * 
	 * @return FigureIF
	 */
	public FigureIF newFigure(String label, SourceIF text) {
		return new Figure(label, text);
	}

	/**
	 * Creates a new instance of FixedAnswerIF.
	 * 
	 * @return FixedAnswerIF
	 */
	public FixedAnswerIF newFixedAnswer(boolean correct, int index,
			SourceIF text) {
		return new FixedAnswer(index, correct, text);
	}

	/**
	 * Creates a new instance of ExamIF for use as a output Exam.
	 * 
	 * @return ExamIF
	 */
	public ExamIF newGeneratedExam() {
		return new Exam(false);
	}

	/**
	 * Creates a new instance ExamIF for use as the master repository.
	 * 
	 * @return ExamIF
	 */
	public ExamIF newMasterExam() {
		return new Exam(true);
	}

	/**
	 * Creates a new instance of ProblemIF.
	 * 
	 * @return ProblemIF
	 */
	public ProblemIF newProblem(String topic, String label, SourceIF question, AnswerIF[] answers) {
		return new Problem(topic, label, question, answers);
	}
}
