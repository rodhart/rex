package edu.udel.cis.cisc475.rex.ecfparser.IF

import edu.udel.cis.cisc475.rex.source.IF.*;
import edu.udel.cis.cisc475.rex.config.IF.*;

public interface EcfParser {
	void setPdfOption( boolean enable );
	void setSeed( long value );
	ConfigIF parse( File file );
}
