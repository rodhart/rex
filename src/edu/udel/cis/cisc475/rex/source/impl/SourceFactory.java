package edu.udel.cis.cisc475.rex.source.impl;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * A class implementing the factory method for SourceFactoryIF and is
 * used to create an instance of SourceFactory.  Allows for the instantiation 
 * of a SourceIF object.
 * 
 * @author jsong
 *
 */
public class SourceFactory implements SourceFactoryIF {

	/**
	 *  Creates a new instance of Source 
	 *  
	 *  @param filename Name of file
	 *  @return Returns a new Source object with the given file name
	 *  
	 */
	public SourceIF newSource(String filename) {
		return new Source(filename);
	}

	/**
	 * Creates a new instance of Source
	 * 
	 * @param filename Name of file
	 * @param startLine Starting line
	 * @param startColumn Starting column
	 * @param lastLine Ending line
	 * @param lastColumn Ending column
	 * @return Returns a new Source object initialized with given parameters
	 */
	public SourceIF newSource(String filename, int startLine, 
			int startColumn, int lastLine, int lastColumn) {
		return new Source(filename, startLine, startColumn, lastLine, lastColumn);
	}
	
}
