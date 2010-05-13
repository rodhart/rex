package edu.udel.cis.cisc475.rex.generate;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class GeneratorTest {

	public final static boolean useStubs = false;
	
	public int numexams;

	
	private static GeneratorFactoryIF generatorFactory;
	public static AnswerKeyIF generatedkey;
	private static AnswerKeyFactoryIF keyFactory;
	private static SourceFactoryIF sourceFactory;
	/*
	public static ExamIF generatedexam;
	

	
	
	private static ConfigFactoryIF configFactory;

	
	
	private static AnswerKeyFactoryIF answerKeyFactory;
	
	private static ExamFactoryIF masterFactory;
	private static ExamFactoryIF examFactory;
	
	private static IntervalFactoryIF intervalFactory;
	
	
*/
	//private static GeneratorIF generator1;

	//private static ConfigIF config1;
	
	//private static ExamIF master1;
	/*
	private static SourceIF question1, question2, question3;
	private static AnswerIF[] answers1, answers2, answers3;
	private static ProblemIF prob1, prob2, prob3;
	private static SourceIF source1;
	private static SourceIF source2;
	private static IntervalIF interval;
	
	*/
	private static Collection<String> answerProb1;
	private static Collection<String> answerProb2, answerProb3;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		if (useStubs) {
			/*configFactory = new ConfigFactoryStub();
			masterFactory = new ExamFactoryStub();
			answerKeyFactory = new AnswerKeyFactoryStub();
			intervalFactory = new IntervalFactoryStub();
			sourceFactory = new SourceFactoryStub();*/
		} else {
			//configFactory = new ConfigFactory();
			//masterFactory = new ExamFactory();
			//answerKeyFactory = Keys.newAnswerKeyFactory();
			//intervalFactory = new IntervalFactory();
			sourceFactory = new SourceFactory();
			keyFactory = new AnswerKeyFactory();
		}
	
		generatorFactory = new GeneratorFactory();
		
		
		

		
		// Create sample AnswerKey
		generatedkey = keyFactory.newAnswerKey("Version1", "examName", "date");
		answerProb1 = new ArrayList<String>();
		answerProb1.add("A");
		answerProb1.add("B");
		answerProb1.add("C");
		answerProb1.add("D");
		answerProb1.add("E");
		answerProb1.add("F");
		answerProb1.add("G");
		generatedkey.addProblem(answerProb1);
		answerProb2 = new ArrayList<String>();
		answerProb2.add("B");
		generatedkey.addProblem(answerProb2);
		// All answers are correct so ? should appear in answer sheet as of now 
		answerProb3 = new ArrayList<String>();
		answerProb3.add("A");
		answerProb3.add("B");
		answerProb3.add("C");
		answerProb3.add("D");
		answerProb3.add("E");
		answerProb3.add("F");
		answerProb3.add("G");
		answerProb3.add("?");
		generatedkey.addProblem(answerProb3);

		
		//generator1 = generatorFactory.newGenerator(master1, config1);
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
	public void testGetMaster() throws RexUnsatisfiableException, Exception {
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
		ExamIF master = generator1.getMaster();
		assertEquals(master1, master);
	}
	
	@Test
	public void testGetConfig() throws RexUnsatisfiableException, Exception {
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
		ConfigIF config = generator1.getConfig();
		assertEquals(config1, config);
	}	
	
	@Test(expected = RexUnsatisfiableException.class)
	public void testNotEnoughProblemsToMeetGroupConstraints() throws Exception
	{
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		SourceIF source2 = sourceFactory.newSource("testFile.txt");
		source2.addText("question2");
		
		config1.addRequiredProblemConstraint("label2", 3, source2);
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);	
	}
	
	@Test(expected=Exception.class)
	public void testRequireNonExistantProblem() throws Exception
	{
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		SourceIF source4 = sourceFactory.newSource("testFile.txt");
		source4.addText("question4");
		config1.addRequiredProblemConstraint("label4", 3, source4);	
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
	}
	
	@Test
	public void testNumGeneratedExams() throws RexUnsatisfiableException, Exception {
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
		
		numexams = generator1.numGeneratedExams();
		assertEquals(config1.numVersions(),numexams);
	}
	
	@Test
	// This just tests getting generated exam, does not test if correct
	public void testGetGeneratedExam() throws RexUnsatisfiableException, Exception{
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
		
		ExamIF exam = generator1.getGeneratedExam(0);
		//in this case all of the master is being used, so use it as a generatedExam
		ExamIF exam2 = master1;
		// Currently in my test I added two ProblemIFs and nothing else, subject to change
		// after being randomized exam1.elements(0) corresponds to exam2.elements(1)
		// after being randomized exam2.elements(0) corresponds to exam1.elements(0)
		assertEquals(2,exam.numElements());
		assertEquals(exam2.element(0).label(), exam.element(0).label());
		assertEquals(exam2.element(1).label(), exam.element(1).label());
	}

	@Test
	//This just tests getting answer key, does not test if answer key is correct
	public void testGetAnswerKey() throws RexUnsatisfiableException, Exception{
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
		
		AnswerKeyIF key = generator1.getAnswerKey(0);
		
		assertEquals(generatedkey.version(), key.version());
		assertEquals(generatedkey.examName(), key.examName());
		assertEquals(generatedkey.date(), key.date());
		
		assertEquals(answerProb1, key.answers(0));
		assertEquals(answerProb2, key.answers(1));
	}
	
	
	
	@Test @Ignore //(expected = RexUnsatisfiableException.class)
	// I want to test what happens if default is returned inside public String answerChar(int input)
	// but it is not working
	public void testDefaultAnswerChar() throws Exception{
		TestExam1 exam1 = new TestExam1();
		ExamIF master1 = exam1.getMaster();
		
		TestConfig1 con1 = new TestConfig1();
		ConfigIF config1 = con1.getConfig();
		
		SourceIF source3 = sourceFactory.newSource("testFile.txt");
		source3.addText("question3");
		
		//config1.addGroupConstraint("topic3", intervalFactory.interval(true, 2.0, true, 4.0), 1, 3, source3);
		config1.addRequiredProblemConstraint("label3", 3, source3);
		
		GeneratorIF generator1 = generatorFactory.newGenerator(master1, config1);
	}
	
	//public void test
}