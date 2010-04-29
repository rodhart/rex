package edu.udel.cis.cisc475.rex.source.IF;

/**
 * Factory class for creating objects of type SourceIF
 * 
 * @author cardona
 *
 */
public interface SourceFactoryIF {

	public SourceIF newSource(String filename);
	public SourceIF newSource(String filename,
								int startLine, 
								int startColumn,
								int lastLine,
								int lastColumn);



}