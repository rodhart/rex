package edu.udel.cis.cisc475.rex.config.IF;


/**
 * ConfigFactoryIF is the interface for the ConfigFactory class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */

public interface ConfigFactoryIF {
	/**
	 * Creates a new instance of Config
	 * @return new instance of Config
	 */
	ConfigIF newConfig(boolean pdf, int numVersions);
}