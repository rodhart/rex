package edu.udel.cis.cisc475.rex.uefparser.IF;

/**
 * Factory class for creating objects of type UEFParserIF.
 * 
 * @author Team 4
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

