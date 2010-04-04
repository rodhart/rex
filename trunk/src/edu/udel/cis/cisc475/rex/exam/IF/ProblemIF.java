package edu.udel.cis.cisc475.rex.exam.IF;

import java.util.Collection;

/**
 * 
 * @author Team 1
 * 
 */
public interface ProblemIF extends ExamElementIF {

	/**
	 * may be null
	 * @return
	 */
	BlockIF requiredBlock(); 

	/**
	 * 
	 * @return
	 */
	Collection<FigureIF> referencedFigures();

	/**
	 * 
	 * @param points
	 */
	void setPoints(int points);

	/**
	 * 
	 * @return
	 */
	String topic();

	/**
	 * 
	 * @return
	 */
	Integer points();

	/**
	 * 
	 * @return
	 */
	SourceIF question();

	/**
	 * 
	 * @return
	 */
	AnswerIF[] answers();

	/**
	 * 
	 * @return
	 */
	AnswerIF[] correctAnswers();

	/**
	 * 
	 * @return
	 */
	double difficulty();
	
}
