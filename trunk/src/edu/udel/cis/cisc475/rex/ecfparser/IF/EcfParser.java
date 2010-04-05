package edu.udel.cis.cisc475.rex.ecfparser.IF

public interface EcfParser {
	void setPdfOption( boolean enable );
	void setSeed( long value );
	ConfigIF parse( File file );
}
