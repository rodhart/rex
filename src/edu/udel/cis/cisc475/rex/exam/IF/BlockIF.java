package edu.udel.cis.cisc475.rex.exam.IF;

/**
 * 
 * @author Team 1
 *
 */
public interface BlockIF extends ExamElementIF {
	
	/**
	 * 
	 * @return
	 */
	String topic();
	
	/**
	 * 
	 * @return
	 */
	SourceIF source();  /* from \\\begin\{block\} to \\\end\{block\} */
}
