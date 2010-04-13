package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;

/**
 * This class implments UEFParserFactoryIF and is used to create instances of
 * UEFParser
 * 
 * @author Ahmed El-Hassany
 * 
 */
public class UEFParserFactory implements UEFParserFactoryIF {

	@Override
	public UEFParserIF newUEFParser() {
		UEFParser p = new UEFParser();
		return p;
	}

}
