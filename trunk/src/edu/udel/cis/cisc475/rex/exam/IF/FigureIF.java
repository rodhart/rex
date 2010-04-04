package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.SourceIF;

/**
 * 
 * @author Team 1
 *
 */
public interface FigureIF extends ExamElementIF {
	
	/**
	 * from \\\begin\{figure\} to \\\end\{figure\}
	 * @return
	 */
	SourceIF source(); 
}
