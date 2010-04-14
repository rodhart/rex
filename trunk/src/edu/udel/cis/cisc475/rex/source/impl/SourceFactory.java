package edu.udel.cis.cisc475.rex.source.impl;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * A class implementing the factory method for Source
 * 
 * @author Jack Song
 *
 */
public class SourceFactory implements SourceFactoryIF {

	/**
	 *  Creates a new instance of Source 
	 *  
	 *  @param filename File to be used
	 *  @return Returns a new Source object with the file parsed
	 *  
	 */
	public SourceIF newSource(String filename) {
		return new Source(filename);
	}


	/**
	 *  Creates a new instance of Source 
	 *  
	 *  @param filename File to be used
	 *  @param startLine  startLine to be used
	 *  @param startColumn startColumn to be used
	 *  @param lastLine lastLine to be used
	 *  @param lastColumn lastColumn File to be used
	 *  @return Returns a new Source object with the file parsed
	 *  
	 */
	public SourceIF newSource(String filename, int startLine, int startColumn,
								int lastLine, int lastColumn){

		return new Source(filename,	startLine, startColumn, lastLine, lastColumn);
	}//end of method
}//end of class
