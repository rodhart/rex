package edu.udel.cis.cisc475.rex.generate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.Configs;
import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;

public class GeneratorTest {

	private static ConfigFactoryIF configFactory;

	private static GeneratorFactoryIF generatorFactory;

	private static GeneratorIF generator1;

	private static ConfigIF config1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		configFactory = Configs.newConfigFactory();
		generatorFactory = Generators.newGeneratorFactory();
		config1 = configFactory.newConfig();
		generator1 = generatorFactory.newGenerator(null, config1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConfig() {
		ConfigIF config = generator1.getConfig();

		assertEquals(config1, config);
	}

	@Test
	@Ignore
	public void testGetMaster() {
		fail("Not yet implemented");
	}

}
