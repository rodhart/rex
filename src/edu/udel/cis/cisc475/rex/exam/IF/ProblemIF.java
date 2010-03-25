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
	public BlockIF requiredBlock(); 

	/**
	 * 
	 * @return
	 */
	public Collection<FigureIF> referencedFigures();

	/**
	 * 
	 * @param points
	 */
	public void setPoints(int points);

	/**
	 * 
	 * @return
	 */
	public String topic();

	/**
	 * 
	 * @return
	 */
	public Integer points();

	/**
	 * 
	 * @return
	 */
	public SourceIF question();

	/**
	 * 
	 * @return
	 */
	public AnswerIF[] answers();

	/**
	 * 
	 * @return
	 */
	public AnswerIF[] correctAnswers();

	/**
	 * 
	 * @return
	 */
	public double difficulty();
	
}
