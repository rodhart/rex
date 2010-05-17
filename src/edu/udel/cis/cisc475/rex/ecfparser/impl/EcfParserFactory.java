package edu.udel.cis.cisc475.rex.ecfparser.impl;

import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserFactoryIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserIF;
import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParser;

/**
 * Factory class used to create EcfParserIF objects from our implemented EcfParser.
 * @author mcloughl
 */
public class EcfParserFactory implements EcfParserFactoryIF {

	/**
	 * Create a new EcfParser with some number of exams specified.
	 * @param numGeneratedExams
	 * @return a new EcfParser
	 */
	@Override
	public EcfParserIF newParser(int numGeneratedExams) {
		return new EcfParser(numGeneratedExams);
	}

}
