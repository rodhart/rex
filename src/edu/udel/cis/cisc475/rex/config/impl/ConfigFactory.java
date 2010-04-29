package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

/**
 * ConfigFactory creates a new instance of the various classes
 * contained in the config package.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */
public class ConfigFactory implements ConfigFactoryIF {

	/**
	 * Creates a new instance of Config
	 * @return new instance of Config
	 */
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		return new Config(pdf, numVersions);
	}
}
