package edu.udel.cis.cisc475.rex.config;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;

public class Configs {

	public static ConfigFactoryIF newConfigFactory() {
		return new ConfigFactory();
	}
}
