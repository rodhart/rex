package edu.udel.cis.cisc475.rex.ecfparser.IF;

import java.io.File;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

public interface EcfParserIF {
	void setPdfOption(boolean enable);

	void setSeed(long value);

	ConfigIF parse(File file);
}
