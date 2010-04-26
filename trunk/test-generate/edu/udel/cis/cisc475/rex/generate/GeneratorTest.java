package edu.udel.cis.cisc475.rex.generate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.Configs;
import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.generatestubs.ConfigFactoryStub;
import edu.udel.cis.cisc475.rex.config.impl.Config;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.generatestubs.ExamFactoryStub;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.generatestubs.IntervalFactoryStub;
import edu.udel.cis.cisc475.rex.key.Keys;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.generatestubs.AnswerKeyFactoryStub;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.generatestubs.SourceFactoryStub;

public class GeneratorTest {

	public final static boolean useStubs = true;
	
	public int numexams;
	
	public static ExamIF generatedexam;

	private static ConfigFactoryIF configFactory;

	private static GeneratorFactoryIF generatorFactory;
	
	private static AnswerKeyFactoryIF answerKeyFactory;
	
	private static ExamFactoryIF masterFactory;
	
	private static IntervalFactoryIF intervalFactory;
	
	private static SourceFactoryIF sourceFactory;

	private static GeneratorIF generator1;

	private static ConfigIF config1;
	
	private static ExamIF master1;
	
	private static AnswerKeyIF key1;
	
	private static IntervalIF interval1;

	private static SourceIF source1;
	
	private static ProblemIF problem1;
	
	private static AnswerIF answer1;
	
	private static AnswerIF[] num1Answers = {answer1};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			configFactory = new ConfigFactoryStub();
			masterFactory = new ExamFactoryStub();
			answerKeyFactory = new AnswerKeyFactoryStub();
			intervalFactory = new IntervalFactoryStub();
			sourceFactory = new SourceFactoryStub();
		} else {
			configFactory = Configs.newConfigFactory();
			answerKeyFactory = Keys.newAnswerKeyFactory();
		}
		generatorFactory = new GeneratorFactory();
		
		master1 = masterFactory.newMasterExam();
		master1.problems();
		
		config1 = configFactory.newConfig(true, 1);
		
		Collection<ConstraintIF> theConstraints = config1.constraints();
		ArrayList<GroupConstraintIF> groupConstraints = new ArrayList<GroupConstraintIF>();
		ArrayList<RequiredProblemConstraintIF> reqProbConstraints = new ArrayList<RequiredProblemConstraintIF>();
		
		for (ConstraintIF c : theConstraints)
		{				
			if (c.getClass().isInstance(GroupConstraintIF.class))	
			{
				groupConstraints.add((GroupConstraintIF) c);
			}
			else if (c.getClass().isInstance(RequiredProblemConstraintIF.class))
			{
				reqProbConstraints.add((RequiredProblemConstraintIF) c);
			}
		}
		
		/* Dan's Work
		interval1 = intervalFactory.interval(false, 10.0, true, 25.0);
		source1 = sourceFactory.newSource("filename.java");
		
		//Creating example config
		config1 = configFactory.newConfig(false, 1);
		String[] versions = {"Version1"};
		config1.setSeed(29475092);
		config1.setVersionStrings(versions);
		config1.setFinalBlock("Final block label");
		config1.addGroupConstraint("Prime", interval1, 2, 10, source1);
		config1.addRequiredProblemConstraint("Required Problem", 5, source1);
		
		//creating example Master Exam
		master1 = masterFactory.newMasterExam();
		problem1 = masterFactory.newProblem("Prime", "Prime1", source1, num1Answers);
	
		generator1 = generatorFactory.newGenerator(master1, config1);
		 */
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

	@Test @Ignore
	public void testGetMaster() {
		ExamIF master = generator1.getMaster();
		assertEquals(master1, master);
	}
	
	@Test @Ignore
	public void testGetConfig() {
		ConfigIF config = generator1.getConfig();

		assertEquals(config1, config);
	}	
	
	@Test @Ignore
	public void testNumGeneratedExams() {
		numexams = generator1.numGeneratedExams();
		assertEquals(config1.numVersions(),numexams);
	}
	
	@Test @Ignore
	public void testGetGeneratedExam(){
		generatedexam = generator1.getGeneratedExam(1);
		assertEquals(masterFactory.newGeneratedExam(),generatedexam);
	}

	@Test @Ignore
	public void testGetAnswerKey(){
		assertEquals(key1,generator1.getAnswerKey(1));
		
	}
	
}