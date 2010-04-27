package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

/**
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 * ConfigFactory creates a new instance of the Config class and
 * returns it.
 * 
 */
public class ConfigFactory implements ConfigFactoryIF {

	@Override
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		return new Config(pdf, numVersions);
	}

}
