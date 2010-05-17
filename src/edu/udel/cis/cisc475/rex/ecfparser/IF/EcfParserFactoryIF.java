package edu.udel.cis.cisc475.rex.ecfparser.IF;

/**
 * Interface specifying how to implement an EcfParserFactoryIF class.
 * A class implementing EcfParserFactoryIF must be able to instansiate a
 * class implementing EcfParserIF.
 * @author Kyle Bouchard
 * @author Burke Cates
 */
public interface EcfParserFactoryIF {
	/**
	 * Make a new EcfParserIF object for some number of exams.
	 * @param numGeneratedExams the number of exams we are generating.
	 * @return An EcfParserIF object with the number of exams to be generated specified.
	 */
	EcfParserIF newParser(int numGeneratedExams);
}
