/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Test Suite for Problem methods.
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author hboyd
 *
 */
public class ProblemTest {
	public final static boolean useStubs = false;
	
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
			sourceFactory = new SourceFactory();
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
	public void testNullLabel(){
		ProblemIF newProblem = examFactory.newProblem(testTopic, null, testQuestionSource, testAnswers);
		assertNull(newProblem.label());
	}

	@Test
	public void testGetRequiredBlock() throws RexException {
		ExamIF exam = examFactory.newGeneratedExam("test version");
		
		assertNull(problem.requiredBlock());
		
		// TODO: How do we mark a problem as being in a required block?
		
		// create required block
		SourceIF blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		
		BlockIF block = examFactory.newBlock("Test Block Label", blockSource);
		exam.addElement(block);
		exam.addElement(problem);
		
		// make problem part of required block
		//((Problem)problem).setRequiredBlock(block);
		
		exam.declareUse(problem, block);
		
		// test returned block is same as created
		BlockIF returned = problem.requiredBlock();
		
		assertNotNull(returned);
		assertEquals(block, returned);
	}
	
	@Test
	public void testGetReferencedFigures() throws RexException {
		ExamIF exam = examFactory.newGeneratedExam("test version");
		
		// I would rather have referenced figures be empty
		// so as to avoid null pointer exceptions...
		assertNotNull(problem.referencedFigures());
		assertTrue(problem.referencedFigures().isEmpty());
		
		// create figure for problem to reference
		SourceIF figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("TestFigureSource");
		
		FigureIF figure1 = examFactory.newFigure("testFigure1", figureSource);
		exam.addElement(figure1);
		exam.addElement(problem);
		
		// make problem reference figure
		//((Problem)problem).addReferencedFigure(figure1);
		exam.declareUse(problem, figure1);
		
		// test set returned contains referenced figure
		Collection<FigureIF> figures = problem.referencedFigures();
		
		assertNotNull(figures);
		assertEquals(1, figures.size());
		assertTrue(figures.contains(figure1));
		
		// create another figure
		figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("TestFigureSource");
		FigureIF figure2 = examFactory.newFigure("testFigure2", figureSource);
		exam.addElement(figure2);
		
		// add to problem the reference to figure
		//((Problem)problem).addReferencedFigure(figure2);
		exam.declareUse(problem, figure2);
		// test set returned contains reference figure
		figures = problem.referencedFigures();
		
		assertNotNull(figures);
		assertEquals(2, figures.size());
		assertTrue(figures.contains(figure1));
		assertTrue(figures.contains(figure2));
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
		assertTrue(problem.points() == 0);
	}
	
	@Test
	public void testGetQuestion() {
		assertNotNull(problem.question());
		assertEquals(testQuestionSource.text(), problem.question().text());
		assertEquals(testQuestionSource, problem.question());
	}
	
	@Test
	public void testGetAnswers() {
		AnswerIF[] answers = problem.answers();
		assertTrue(answers.length == 4);
		assertArrayEquals(testAnswers, answers);
	}
	
	@Test
	public void testGetCorrectAnswers() {
		AnswerIF[] answers = problem.correctAnswers();
		assertEquals(2, answers.length);
		assertTrue(Arrays.asList(answers).contains(testAnswer2));
		assertTrue(Arrays.asList(answers).contains(testAnswer4));
	}
	
	@Test
	public void testGetDifficulty() {
		problem.setDifficulty(0);
		
		double difficulty = problem.difficulty();
		
		assertEquals(0, difficulty, 0);
		
		problem.setDifficulty(23.75);
		
		difficulty = problem.difficulty();
		
		assertEquals(23.75, difficulty, 0);
		
		problem.setDifficulty(-20.1);
		
		difficulty = problem.difficulty();
		
		assertEquals(-20.1, difficulty, 0);
	}
}
