package edu.udel.cis.cisc475.rex.source.IF;

/**
 * Interface for SourceFactory class
 * 
 * @author cardona
 * @author jsong
 *
 */
public interface SourceFactoryIF {

	public SourceIF newSource(String filename);

/*	public SourceIF newSource(String filename,
								int startLine, 
								int startColumn,
								int lastLine,
								int lastColumn);


*/
}