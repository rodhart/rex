package edu.udel.cis.cisc475.rex.exam.IF;

/**
 * 
 * @author Team 1
 * 
 */
public interface AnswerIF {

	/**
	 * 
	 * @return
	 */
	public boolean isCorrect();

	/**
	 * from \\\answer to end of answer
	 * 
	 * @return
	 */
	public SourceIF source();

}
