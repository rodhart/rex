package edu.udel.cis.cisc475.rex.config.generatestubs;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;

public class ConfigFactoryStub implements ConfigFactoryIF {

	@Override
	public ConfigIF newConfig(boolean pdf, int numVersions) {
		// TODO Auto-generated method stub
		return new ConfigStub(pdf, numVersions);
	}

}
