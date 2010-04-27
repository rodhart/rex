package edu.udel.cis.cisc475.rex.uefparser.IF;

/**
 * Factory class for creating objects of type UEFParserIF.
 * 
 * @author Ahmed El-Hassany
 * 
 */
public interface UEFParserFactoryIF {
	/**
	 * Create new object that implements UEFParserIF.
	 * 
	 * @return object of type UEFParserIF.
	 */
	UEFParserIF newUEFParser();
}

