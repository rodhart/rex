package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * The figure interface.
 * @author fxfitz
 * 
 */
public interface FigureIF extends ExamElementIF {
	
	/**
	 * Reference to where in the UEF the particular figure
	 * is referenced.
	 * from \\\begin\{figure\} to \\\end\{figure\}
	 * @return 
	 */
	SourceIF source(); 
}
