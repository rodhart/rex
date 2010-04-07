package edu.udel.cis.cisc475.rex.config;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

public class ConfigFactory implements ConfigFactoryIF {

	@Override
	public ConfigIF newConfig() {
		return new ConfigStub();
	}

}
