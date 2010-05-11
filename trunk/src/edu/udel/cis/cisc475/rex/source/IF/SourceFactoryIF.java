package edu.udel.cis.cisc475.rex.source.IF;

/**
 * Factory class for creating objects of type SourceIF
 * 
 * @author cardona
 *
 */
public interface SourceFactoryIF {

	/** Creates a new instance of a SourceIF with the filename initialized
	 * 
	 * @param filename Filename to initialize the SourceIF with
	 * @return A new instance of SourceIF
	 */
	SourceIF newSource(String filename);
	
	/** Creates a new instance of a SourceIF with all fields initialized
	 * 
	 * @param filename Filename 
	 * @param startLine Starting line
	 * @param startColumn Starting column
	 * @param lastLine Ending line
	 * @param lastColumn Ending column
	 * @return
	 */
	SourceIF newSource(String filename,
								int startLine, 
								int startColumn,
								int lastLine,
								int lastColumn);



}