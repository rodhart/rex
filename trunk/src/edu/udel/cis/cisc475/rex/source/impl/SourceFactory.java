/**
 * 
 */
package edu.udel.cis.cisc475.rex.source.impl;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * @author Jack
 *
 */
public class SourceFactory implements SourceFactoryIF {

	public SourceIF newSource(String filename) {
		return new Source(filename);
	}

	
	

}
