package edu.udel.cis.cisc475.rex.config;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.generatestubs.ConfigFactoryStub;

public class Configs {

	public static ConfigFactoryIF newConfigFactory() {
		return new ConfigFactoryStub();
	}
}
