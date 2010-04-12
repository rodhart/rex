package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * The interface to the answer class. 
 * @author fxfitz
 * 
 */
public interface AnswerIF {

	/**
	 * @return true if answer is true; false if false
	 */
	boolean isCorrect();

	/**
	 * Reference to where in the UEF the particular answer
	 * is referenced.
	 * from \\\begin\{figure\} to \\\end\{figure\}
	 * @return 
	 */
	SourceIF source();

}
