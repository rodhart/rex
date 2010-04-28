package edu.udel.cis.cisc475.rex.generate;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.generatestubs.ConfigFactoryStub;
import edu.udel.cis.cisc475.rex.config.impl.Config;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.config.impl.Constraint;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.generatestubs.ExamFactoryStub;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.ExamElement;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.generatestubs.IntervalFactoryStub;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.key.Keys;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.generatestubs.AnswerKeyFactoryStub;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.Randomizer;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.generatestubs.SourceFactoryStub;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class GeneratorTest {

	public final static boolean useStubs = false;
	
	public int numexams;
	
	public static ExamIF generatedexam;
	public static AnswerKeyIF generatedkey;

	private static AnswerKeyFactoryIF keyFactory;
	
	private static ConfigFactoryIF configFactory;

	private static GeneratorFactoryIF generatorFactory;
	
	private static AnswerKeyFactoryIF answerKeyFactory;
	
	private static ExamFactoryIF masterFactory;
	private static ExamFactoryIF examFactory;
	
	private static IntervalFactoryIF intervalFactory;
	
	private static SourceFactoryIF sourceFactory;

	private static GeneratorIF generator1;

	private static ConfigIF config1;
	
	private static ExamIF master1;
	
	private static SourceIF question1, question2, question3, question4, question5, question6, question7;
	private static AnswerIF[] answers1, answers2, answers3, answers4, answers5, answers6, answers7;
	private static ProblemIF prob1, prob2, prob3, prob4, prob5, prob6, prob7;
	private static SourceIF source1;
	private static SourceIF source2;
	private static SourceIF source3;
	private static IntervalIF interval;
	private static Collection<String> answerProb1;
	private static Collection<String> answerProb2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			configFactory = new ConfigFactoryStub();
			masterFactory = new ExamFactoryStub();
			answerKeyFactory = new AnswerKeyFactoryStub();
			intervalFactory = new IntervalFactoryStub();
			sourceFactory = new SourceFactoryStub();
		} else {
			configFactory = new ConfigFactory();
			masterFactory = new ExamFactory();
			answerKeyFactory = Keys.newAnswerKeyFactory();
			intervalFactory = new IntervalFactory();
			sourceFactory = new SourceFactory();
			keyFactory = new AnswerKeyFactory();
		}
		
		generatorFactory = new GeneratorFactory();
		
		
		// Create sample master exam
		master1 = masterFactory.newMasterExam();
		
		
		question1 = sourceFactory.newSource("testFile.txt");		
		question1.addText("question1");		
		answers1 = new Answer[7];
		answers1[0] = new Answer(true, sourceFactory.newSource("option1"));
		answers1[1] = new Answer(true, sourceFactory.newSource("option2"));
		answers1[2] = new Answer(true, sourceFactory.newSource("option3"));
		answers1[3] = new Answer(true, sourceFactory.newSource("option4"));
		answers1[4] = new Answer(true, sourceFactory.newSource("option5"));
		answers1[5] = new Answer(true, sourceFactory.newSource("option6"));
		answers1[6] = new Answer(true, sourceFactory.newSource("option7"));
		prob1 = masterFactory.newProblem("topic1", "label1", question1, answers1);
		prob1.setDifficulty(3.0);
		
		question2 = sourceFactory.newSource("testFile.txt");
		question2.addText("question2");		
		answers2 = new Answer[7];
		answers2[0] = new Answer(false, sourceFactory.newSource("option8"));
		answers2[1] = new Answer(true, sourceFactory.newSource("option9"));
		answers2[2] = new Answer(false, sourceFactory.newSource("option10"));
		answers2[3] = new Answer(false, sourceFactory.newSource("option11"));
		answers2[4] = new Answer(false, sourceFactory.newSource("option12"));
		answers2[5] = new Answer(false, sourceFactory.newSource("option13"));
		answers2[6] = new Answer(false, sourceFactory.newSource("option14"));
		prob2 = masterFactory.newProblem("topic2", "label2", question2, answers2);
		prob2.setDifficulty(3.0);
		
		
		
		master1.addElement(prob1);
		master1.addElement(prob2);

		
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
		answerProb2.add("E");
		generatedkey.addProblem(answerProb2);

		
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
	
	@Test(expected = RexUnsatisfiableException.class)
	public void testNotEnoughProblemsToMeetGroupConstraints() throws Exception
	{
		config1.addRequiredProblemConstraint("label2", 3, source2);
		
		GeneratorIF generator2 = generatorFactory.newGenerator(master1, config1);	
	}
	
	@Test(expected=Exception.class)
	public void testRequireNonExistantProblem() throws Exception
	{
		config1.addRequiredProblemConstraint("label3", 3, source1);
		source3 = sourceFactory.newSource("testFile.txt");
		source3.addText("question3");
		
		GeneratorIF generator2 = generatorFactory.newGenerator(master1, config1);
	}
	
	@Test
	public void testNumGeneratedExams() {
		numexams = generator1.numGeneratedExams();
		assertEquals(config1.numVersions(),numexams);
	}
	
	@Test
	// This just tests getting generated exam, does not test if correct
	public void testGetGeneratedExam(){
		ExamIF exam = generator1.getGeneratedExam(0);
		//in this case all of the master is being used, so use it as a generatedExam
		ExamIF exam2 = master1;
		// Currently in my test I added two ProblemIFs and nothing else, subject to change
		// after being randomized exam1.elements(0) corresponds to exam2.elements(1)
		// after being randomized exam2.elements(0) corresponds to exam1.elements(0)
		assertEquals(2,exam.numElements());
		assertEquals(exam2.element(1).label(), exam.element(0).label());
		assertEquals(exam2.element(0).label(), exam.element(1).label());
	}

	@Test
	//This just tests getting answer key, does not test if answer key is correct
	public void testGetAnswerKey(){
		AnswerKeyIF key = generator1.getAnswerKey(0);
		
		assertEquals(generatedkey.version(), key.version());
		assertEquals(generatedkey.examName(), key.examName());
		assertEquals(generatedkey.date(), key.date());
		
		assertEquals(answerProb1, key.answers(1));
		assertEquals(answerProb2, key.answers(0));
	}
}