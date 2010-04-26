/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
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
public class BlockTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static SourceIF blockSource;
	private static BlockIF block;
	private static ProblemIF problem;
	
	private static String testUEFfilename = "testFileName.txt";
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
		
		problem = createTestProblem();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		block = examFactory.newBlock(testLabel, blockSource);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLabel() {
		assertNotNull(block.label());
		assertEquals(testLabel, block.label());
	}
	
	@Test
	public void testGetSource() {
		SourceIF result = block.source();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(blockSource.text(), result.text());
		assertEquals(blockSource, result);
	}
	
	@Test
	public void testGetTopic(){
		ExamIF exam = examFactory.newGeneratedExam();
		
		exam.addElement(block);
		exam.addElement(problem);
		
		assertNull(block.topic());
		
		exam.declareUse(problem, block);
		assertEquals("Test Problem Topic", block.topic());
		
	}

	private static ProblemIF createTestProblem() {
		SourceIF	testQuestionSource;
		SourceIF testAnswerSource;
		AnswerIF[] testAnswers = new AnswerIF[4];
		AnswerIF testAnswer1;
		AnswerIF testAnswer2;
		FixedAnswerIF testAnswer3;
		AnswerIF testAnswer4;
		
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
		return examFactory.newProblem("Test Problem Topic", "Test Problem Label", testQuestionSource, testAnswers);
	}
}
