package edu.udel.cis.cisc475.rex.exam.IF;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * The interface for a Problem. This interface allows access to any required
 * blocks, figures, the problem's points, answers, and difficulty.
 * 
 * @author fxfitz
 * 
 */
public interface ProblemIF extends ExamElementIF {

	/**
	 * If the problem referrences a block, this will return that block,
	 * otherwise it will be null
	 */
	BlockIF requiredBlock();

	/**
	 * Collection of FigureIF.
	 */
	Collection<FigureIF> referencedFigures();

	/**
	 * points set amount of points the problem is worth
	 */
	void setPoints(int points);

	/**
	 * sets the difficulty of the problems
	 */
	void setDifficulty(double difficulty);

	/**
	 * problem topic
	 */
	String topic();

	/**
	 * the number of points a problem is worth
	 */
	Integer points();

	/**
	 * reference to question in the source UEF file
	 */
	SourceIF question();

	/**
	 * array of answers (correct and incorrect) to the question
	 */
	AnswerIF[] answers();

	/**
	 * 
	 * @return array of correct answers to the question
	 */
	AnswerIF[] correctAnswers();

	/**
	 * difficulty of the problem
	 */
	double difficulty();

}
