/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Test Suite for testing ExamFactory.
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
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
public class ExamFactoryTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static String testUEFfilename = "testFileName.txt";
	
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewMasterExam() {
		ExamIF exam = examFactory.newMasterExam();
		assertNotNull(exam);
		assertTrue(exam.isMaster());
	}

	@Test
	public void testNewGeneratedExam() {
		ExamIF exam = examFactory.newGeneratedExam();
		assertNotNull(exam);
		assertFalse(exam.isMaster());
	}
	
	@Test
	public void testNewBlock() {
		SourceIF blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("test Block Source");
		ExamElementIF block = examFactory.newBlock("test Label", blockSource);
		assertNotNull(block);
		assertTrue(block instanceof BlockIF);
	}
	
	@Test
	public void testNewBlockBadInput() {
		SourceIF blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("test Block Source");
		// TODO: Remove commented test.
//		try{
//			ExamElementIF block = examFactory.newBlock(null, "test Label", blockSource);
//			fail("Expected Exceptions not Thrown.");
//		} catch (Exception e) {
//			
//		}
//		try{
//			ExamElementIF block = examFactory.newBlock("test topic", null, blockSource);
//			fail("Expected Exceptions not Thrown.");
//		} catch (Exception e) {
//			
//		}
		try{
			examFactory.newBlock("test label", null);
			fail("Expected Exceptions not Thrown.");
		} catch (Exception e) {
			
		}
		
	}
	
	@Test
	public void testNewFigure() {
		SourceIF figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("test Figure Source");
		ExamElementIF figure = examFactory.newFigure("test figure label", figureSource);
		assertNotNull(figure);
		assertTrue(figure instanceof FigureIF);
	}
	
	@Test
	public void testNewFigureBadInput() {
		SourceIF figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("test Figure Source");
		// TODO: Remove commented test.
//		try{
//			ExamElementIF figure = examFactory.newFigure(null, figureSource);
//			fail("Expected Exceptions not Thrown.");
//		} catch(Exception e) {
//			
//		}
		try{
			examFactory.newFigure("test figure label", null);
			fail("Expected Exceptions not Thrown.");
		} catch(Exception e) {
			
		}
	}
	
	@Test
	public void testNewAnswer() {
		SourceIF answerSource = sourceFactory.newSource(testUEFfilename);
		answerSource.addText("test Answer Source");
		AnswerIF answer1 = examFactory.newAnswer(true, answerSource);
		assertNotNull(answer1);
		AnswerIF answer2 = examFactory.newAnswer(false, answerSource);
		assertNotNull(answer2);
		assertNotSame(answer1, answer2);
	}
	
	@Test(expected = Exception.class)
	public void testNewAnswerBadInput() {
		examFactory.newAnswer(true, null);
	}
	
	@Test
	public void testNewFixedAnswer() {
		SourceIF answerSource = sourceFactory.newSource(testUEFfilename);
		answerSource.addText("test Answer Source");
		AnswerIF answer1 = examFactory.newFixedAnswer(true, 0, answerSource);
		assertNotNull(answer1);
		assertTrue(answer1 instanceof FixedAnswerIF);
		AnswerIF answer2 = examFactory.newFixedAnswer(false, 0, answerSource);
		assertNotNull(answer2);
		assertTrue(answer1 instanceof FixedAnswerIF);
		assertNotSame(answer1, answer2);
	}
	
	@Test(expected = Exception.class)
	public void testNewFixedAnswerBadInput() {
		examFactory.newFixedAnswer(false, 0, null);
	}
	
	@Test
	public void testNewProblem() {
		SourceIF answerSource = sourceFactory.newSource(testUEFfilename);
		answerSource.addText("test Answer Source");
		AnswerIF answer1 = examFactory.newAnswer(true, answerSource);
		AnswerIF answer2 = examFactory.newFixedAnswer(false, 0, answerSource);
		AnswerIF[] answers = new AnswerIF[2];
		answers[0] = answer1;
		answers[1] = answer2;
		
		SourceIF problemSource = sourceFactory.newSource(testUEFfilename);
		problemSource.addText("test Problem Source?");
		
		ExamElementIF problem = examFactory.newProblem("test Problem Topic", "test Label", problemSource, answers);
		assertNotNull(problem);
		assertTrue(problem instanceof ProblemIF);
	}
	
	@Test
	public void testNewProblemBadInput() {
		SourceIF answerSource = sourceFactory.newSource(testUEFfilename);
		answerSource.addText("test Answer Source");
		AnswerIF answer1 = examFactory.newAnswer(true, answerSource);
		AnswerIF answer2 = examFactory.newFixedAnswer(false, 0, answerSource);
		AnswerIF[] answers = new AnswerIF[2];
		SourceIF problemSource = sourceFactory.newSource(testUEFfilename);
		problemSource.addText("test Problem Source?");
		
		try{
			// answers array is empty
			examFactory.newProblem("test Problem Topic", "test Label", problemSource, answers);
			fail("Expected Exceptions not Thrown.");
		} catch (Exception e) {
			
		}
		answers[0] = answer1;
		answers[1] = answer2;
		try{
			examFactory.newProblem(null, "test Label", problemSource, answers);
			fail("Expected Exceptions not Thrown.");
		} catch (Exception e) {
			
		}
		// TODO: Remove commented test.
//		try{
//			ExamElementIF problem = examFactory.newProblem("test Problem Topic", null, problemSource, answers);
//			fail("Expected Exceptions not Thrown.");
//		} catch (Exception e) {
//			
//		}
		try{
			examFactory.newProblem("test Problem Topic", "test Label", null, answers);
			fail("Expected Exceptions not Thrown.");
		} catch (Exception e) {
			
		}
		try{
			examFactory.newProblem("test Problem Topic", "test Label", problemSource, null);
			fail("Expected Exceptions not Thrown.");
		} catch (Exception e) {
			
		}
	}
	
}
