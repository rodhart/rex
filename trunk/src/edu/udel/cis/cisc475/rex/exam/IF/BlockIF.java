package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * The block interface!
 * 
 * @author fxfitz
 * 
 */
public interface BlockIF extends ExamElementIF {

	/**
	 * The topic of the block as inferred by what problems it is related to
	 */
	String topic();

	/**
	 * 
	 * Reference to where in the UEF the particular block is referenced.
	 */
	SourceIF source();
}
