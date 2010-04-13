package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

public class ConfigFactory implements ConfigFactoryIF {

	@Override
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		return new Config(pdf, numVersions);
	}

}
