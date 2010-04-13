package edu.udel.cis.cisc475.rex.source.IF;

/**
 * Interface for SourceFactory class
 * 
 * @author Jim Cardona
 *
 */
public interface SourceFactoryIF {

	public SourceIF newSource(String filename);
}