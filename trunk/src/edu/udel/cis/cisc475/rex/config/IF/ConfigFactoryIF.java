package edu.udel.cis.cisc475.rex.config.IF;

/**
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 * ConfigFactoryIF is the interface for the ConfigFactory class. 
 * It provides a list of the class's public methods.
 * 
 */

public interface ConfigFactoryIF {
	
	ConfigIF newConfig(boolean pdf, int numVersions);

}