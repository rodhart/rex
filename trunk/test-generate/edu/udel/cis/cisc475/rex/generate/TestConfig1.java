package edu.udel.cis.cisc475.rex.generate;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class TestConfig1 {
	
	private static ConfigFactoryIF configFactory;
	private static ConfigIF config1;
	
	private static SourceFactoryIF sourceFactory;
	private static SourceIF source1, source2;
	
	private static IntervalFactoryIF intervalFactory;
	private static IntervalIF interval;
	
	
	public TestConfig1() throws Exception
	{
		configFactory = new ConfigFactory();
		sourceFactory = new SourceFactory();
		intervalFactory = new IntervalFactory();
		
		// Create sample Config
		config1 = configFactory.newConfig(true, 1);
		
		config1.setSeed(5);
		source1 = sourceFactory.newSource("testFile.txt");
		source1.addText("question1");
		source2 = sourceFactory.newSource("testFile.txt");
		source2.addText("question2");
		interval = intervalFactory.interval(true, 1.0, true, 5.0);
		config1.addRequiredProblemConstraint("label1", 4, source1);
		config1.addGroupConstraint("topic2", interval, 1, 3, source2);
		String[] versions = {"Version1"};
		config1.setVersionStrings(versions);
	}
	
	public ConfigIF getConfig()
	{
		return config1;
	}

}
