package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * @author fxfitz
 * 
 */
public class Figure implements FigureIF {

	private String label;
	private SourceIF source;

	public Figure(String label, SourceIF source) {
		this.label = label;
		this.source = source;
	}

	/**
	 * Reference to where in the UEF the particular figure is referenced. from
	 * \\\begin\{figure\} to \\\end\{figure\}
	 * 
	 */
	public SourceIF source() {
		return source;
	}

	public String label() {
		return label;
	}

}
