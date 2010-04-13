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
	 *  @param File to be used
	 *  @return Returns a new Source object with the file parsed
	 *  
	 */
	public SourceIF newSource(String filename) {
		return new Source(filename);
	}

}
