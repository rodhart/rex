package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.SourceIF;

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
	boolean isCorrect();

	/**
	 * from \\\answer to end of answer
	 * 
	 * @return
	 */
	SourceIF source();

}
