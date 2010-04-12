package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * The block interface!
 * @author fxfitz
 *
 */
public interface BlockIF extends ExamElementIF {
	
	/**
	 * @return topic
	 */
	String topic();
	
	/**
	 * Reference to where in the UEF the particular block
	 * is referenced.
	 * from \\\begin\{figure\} to \\\end\{figure\}
	 * @return 
	 */
	SourceIF source();  /* from \\\begin\{block\} to \\\end\{block\} */
}
