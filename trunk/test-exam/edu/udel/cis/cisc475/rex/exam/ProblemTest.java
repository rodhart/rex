/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * @author hboyd
 *
 */
public class ProblemTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static ProblemIF problem;
	private static SourceIF	testQuestionSource;
	private static SourceIF testAnswerSource;
	private static AnswerIF[] testAnswers = new AnswerIF[4];
	private static AnswerIF testAnswer1;
	private static AnswerIF testAnswer2;
	private static AnswerIF testAnswer3;
	private static AnswerIF testAnswer4;
	
	private static String testUEFfilename = "testFileName.txt";
	private static String testTopic = "test Topic";
	private static String testLabel = "test Label";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			
		}
		else{
			
		}
		
		//create test question source
		testQuestionSource = sourceFactory.newSource(testUEFfilename);
		testQuestionSource.addText("Test Question Text?");
		
		//create test answers
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Foo Bar");
		testAnswer1 = examFactory.newAnswer(true, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Foo Bar");
		testAnswer2 = examFactory.newAnswer(true, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Foo Bar");
		testAnswer3 = examFactory.newAnswer(true, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Foo Bar");
		testAnswer4 = examFactory.newAnswer(true, testAnswerSource);
		
		//create test problem
		problem = examFactory.newProblem(testTopic, testLabel, testQuestionSource, testAnswers);
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
	@Ignore
	public void testGetRequiredBlock() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetReferencedFigure() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetTopic() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetPoints() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetQuestion() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetAnswers() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetCorrectAnswers() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetDifficulty() {
		fail("Not yet implemented");
	}
}
