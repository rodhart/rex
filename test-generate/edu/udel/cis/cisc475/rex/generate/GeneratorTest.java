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
import edu.udel.cis.cisc475.rex.config.generatestubs.ConfigFactoryStub;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.generatestubs.ExamFactoryStub;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.key.Keys;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.generatestubs.AnswerKeyFactoryStub;

public class GeneratorTest {

	public final static boolean useStubs = true;
	
	public int numexams;

	private static ConfigFactoryIF configFactory;

	private static GeneratorFactoryIF generatorFactory;
	
	private static AnswerKeyFactoryIF answerKeyFactory;
	
	private static ExamFactoryIF masterFactory;

	private static GeneratorIF generator1;

	private static ConfigIF config1;
	
	private static ExamIF master1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			configFactory = new ConfigFactoryStub();
			masterFactory = new ExamFactoryStub();
			answerKeyFactory = new AnswerKeyFactoryStub();
			masterFactory = new ExamFactoryStub();
			generatorFactory = new GeneratorFactoryStub();
		} else {
			configFactory = Configs.newConfigFactory();
			answerKeyFactory = Keys.newAnswerKeyFactory();
		}
		//generatorFactory = Generators.newGeneratorFactory();
		//generatorFactory = new GeneratorFactory();
		
		config1 = configFactory.newConfig(true, 1);
		master1 = masterFactory.newMasterExam();
		generator1 = generatorFactory.newGenerator(master1, config1);
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
	public void testGetMaster() {
		ExamIF master = generator1.getMaster();
		assertEquals(master1, master);
	}
	
	@Test
	public void testGetConfig() {
		ConfigIF config = generator1.getConfig();

		assertEquals(config1, config);
	}	
	
	@Test
	public void testNumGeneratedExams() {
		numexams = generator1.numGeneratedExams();
		assertEquals(config1.numVersions(),numexams);
	}
}