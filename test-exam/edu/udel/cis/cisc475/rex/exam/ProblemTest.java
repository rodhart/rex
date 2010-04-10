/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;

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
	private static FixedAnswerIF testAnswer3;
	private static AnswerIF testAnswer4;
	
	private static String testUEFfilename = "testFileName.txt";
	private static String testTopic = "test Topic";
	private static String testLabel = "test Label";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			sourceFactory = new SourceFactoryStub();
		}
		else{
			// TODO Uncomment when entry point is available
			//sourceFactory = Sources.newSourceFactory();
			sourceFactory = null;
		}
		// TODO Uncomment when entry point is available
		//examFactory = Exams.newExamFactory();
		examFactory = new ExamFactory();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//create test question source
		testQuestionSource = sourceFactory.newSource(testUEFfilename);
		testQuestionSource.addText("Test Question Text?");
		
		//create test answers
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Test Answer 1");
		testAnswer1 = examFactory.newAnswer(false, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Test Answer 2");
		testAnswer2 = examFactory.newAnswer(true, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Test Fixed Answer 3");
		testAnswer3 = examFactory.newFixedAnswer(false, 3, testAnswerSource);
		
		testAnswerSource = sourceFactory.newSource(testUEFfilename);
		testAnswerSource.addText("Test Answer 4");
		testAnswer4 = examFactory.newAnswer(true, testAnswerSource);
		
		// fill array with answers
		testAnswers[0] = testAnswer1;
		testAnswers[1] = testAnswer2;
		testAnswers[2] = testAnswer3;
		testAnswers[3] = testAnswer4;
		
		// create test problem
		problem = examFactory.newProblem(testTopic, testLabel, testQuestionSource, testAnswers);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testGetRequiredBlock() {
		
		assertNull(problem.requiredBlock());
		
		// TODO: How do we mark a problem as being in a required block?
		
		// create required block
		
		// make problem part of required block
		
		// test returned block is same as created
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetReferencedFigures() {
		
		assertNotNull(problem.referencedFigures());
		assertTrue(problem.referencedFigures().isEmpty());
		
		// TODO: How do we mark that a problem references a figure? 
		
		// create figure for problem to reference
		SourceIF figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("TestFigureSource");
		
		FigureIF figure1 = examFactory.newFigure("testFigure1", figureSource);
		
		// make problem reference figure
		
		// test set returned contains referenced figure
		
		// create another figure
		figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("TestFigureSource");
		FigureIF figure2 = examFactory.newFigure("testFigure2", figureSource);
		// add to problem the reference to figure
		
		// test set returned contains reference figure
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetTopic() {
		assertNotNull(problem.topic());
		assertEquals(testTopic, problem.topic());
	}
	
	@Test
	public void testGetLabel() {
		assertNotNull(problem.label());
		assertEquals(testLabel, problem.label());
	}
	
	@Test
	public void testGetPoints() {
		assertNull(problem.points());
		
		problem.setPoints(5);
		Integer result = problem.points();
		
		assertNotNull(result);
		assertTrue(result == 5);
	}
	
	@Test
	public void testGetPointsBadInput(){
		problem.setPoints(-20);
		// TODO: Does set points throw an error or return null on bad input?
		assertNull(problem.points());
		
		problem.setPoints(0);
		assertNull(problem.points());
	}
	
	@Test
	public void testGetQuestion() {
		assertNotNull(problem.question());
		assertEquals(testQuestionSource, problem.question());
		assertEquals(testQuestionSource.text(), problem.question().text());
	}
	
	@Test
	public void testGetAnswers() {
		AnswerIF[] answers = problem.answers();
		assertTrue(answers.length == 4);
		assertArrayEquals(testAnswers, answers);
	}
	
	@Test
	public void testGetCorrectAnswers() {
		AnswerIF[] answers = problem.answers();
		assertTrue(answers.length == 2);
		assertTrue(Arrays.asList(answers).contains(testAnswer2));
		assertTrue(Arrays.asList(answers).contains(testAnswer4));
	}
	
	@Test
	@Ignore
	public void testGetDifficulty() {
		// TODO: How do we set a problems difficulty?
		fail("Not yet implemented");
	}
}
