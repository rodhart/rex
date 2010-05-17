package edu.udel.cis.cisc475.rex.ecfparser.IF;

import java.io.File;
import java.io.IOException;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.err.RexParseException;

/**
 * Interface to the ECF parser, which will parse an ECF file into a ConfigIF object.
 * A class implementing this interface must be able to create a ConfigIF object from
 * some input ECF file.
 *
 * @author Burke Cates
 *
 */
public interface EcfParserIF {

	/**
	 * Sets the flag to enable or disable the pdf generation option.
	 * @param enable boolean stating whether or not to enable pdf generation.
	 */
	void setPdfOption(boolean enable);

	/**
	 * Set the seed for the ConfigIF object to be generated.
	 * @param value the primitive long seed to be inserted into the ConfigIF object.
	 */
	void setSeed(long value);

	/**
	 * Parse the given file into a ConfigIF object.
	 * @param file file to open and parse.
	 * @return ConfigIF object containing configuration parameters found in the parsed file.
	 */
	ConfigIF parse(File file) throws IOException, RexUnsatisfiableException, RexParseException;
}
