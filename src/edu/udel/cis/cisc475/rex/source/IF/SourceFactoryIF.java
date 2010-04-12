package edu.udel.cis.cisc475.rex.source.IF;


public interface SourceFactoryIF {

	public SourceIF newSource(String filename);
}