/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
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
import org.junit.Ignore;
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
		ExamElementIF block = examFactory.newBlock("test Topic", "test Label", blockSource);
		assertNotNull(block);
		assertTrue(block instanceof BlockIF);
	}
	
	@Test
	@Ignore
	public void testNewBlockBadInput() {
		fail("Not yet implemented");
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
	@Ignore
	public void testNewFigureBadInput() {
		fail("Not yet implemented");
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
	
	@Test
	@Ignore
	public void testNewAnswerBadInput() {
		fail("Not yet implemented");
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
	
	@Test
	@Ignore
	public void testNewFixedAnswerBadInput() {
		fail("Not yet implemented");
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
	@Ignore
	public void testNewProblemBadInput() {
		fail("Not yet implemented");
	}
	
}
