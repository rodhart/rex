package edu.udel.cis.cisc475.rex.config.IF;


public interface ConfigFactoryIF {
	
	ConfigIF newConfig(boolean pdf, int numVersions);

}