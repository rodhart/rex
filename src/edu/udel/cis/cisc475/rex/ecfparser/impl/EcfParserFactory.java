package edu.udel.cis.cisc475.rex.ecfparser.impl;

import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserFactoryIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserIF;
import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParser;

/**
 * 
 * @author mcloughl
 * added stubs to make main compile
 */
public class EcfParserFactory implements EcfParserFactoryIF {

	@Override
	public EcfParserIF newParser(int numGeneratedExams) {
		// TODO Auto-generated method stub
		return new EcfParser(numGeneratedExams);
	}

}
