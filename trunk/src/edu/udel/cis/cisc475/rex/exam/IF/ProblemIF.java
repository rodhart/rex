package edu.udel.cis.cisc475.rex.exam.IF;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * The interface for a Problem. This interface allows access to any
 * required blocks, figures, the problem's points, answers, and 
 * difficulty.
 * @author fxfitz
 * 
 */
public interface ProblemIF extends ExamElementIF {

	/**
	 * may be null
	 * @return
	 */
	BlockIF requiredBlock(); 

	/**
	 * @return Collection of FigureIF.
	 */
	Collection<FigureIF> referencedFigures();

	/**
	 * @param points set amount of points the problem is worth
	 */
	void setPoints(int points);
	
	/**
	 * @param sets the difficulty of the problems
	 */
	void setDifficulty(double difficulty);

	/**
	 * @return problem topic
	 */
	String topic();

	/**
	 * @return the number of points a problem is worth
	 */
	Integer points();

	/**
	 * @return reference to question in the source UEF file
	 */
	SourceIF question();

	/**
	 * @return array of answers (correct and incorrect) to the 
	 * question
	 */
	AnswerIF[] answers();

	/**
	 * 
	 * @return array of correct answers to the question 
	 */
	AnswerIF[] correctAnswers();

	/**
	 * @return difficulty of the problem
	 */
	double difficulty();
	
}
