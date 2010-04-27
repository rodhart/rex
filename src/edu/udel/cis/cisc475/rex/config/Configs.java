package edu.udel.cis.cisc475.rex.config;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;

public class Configs {

	public static ConfigFactoryIF newConfigFactory() {
//		return new ConfigFactoryStub();
// this class should not use stubs
		return null;
	}
}
