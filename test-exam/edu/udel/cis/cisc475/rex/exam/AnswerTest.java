/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Test Suite for testing Answer methods.
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;


/**
 * @author hboyd
 *
 */
public class AnswerTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static SourceIF answerSource1;
	private static SourceIF answerSource2;
	private static AnswerIF answer1;
	private static AnswerIF answer2;
	
	private static String testUEFfilename = "testFileName.txt";
	
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
		
		answerSource1 = sourceFactory.newSource(testUEFfilename);
		answerSource1.addText("Foo Bar Answer Text.");
		
		answer1 = examFactory.newAnswer(true, answerSource1);
		
		answerSource2 = sourceFactory.newSource(testUEFfilename);
		answerSource2.addText("Bar Foo Answer Text.");
		
		answer2 = examFactory.newAnswer(false, answerSource2);
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
	public void testIsCorrect() {
		assertTrue(answer1.isCorrect());
		assertFalse(answer2.isCorrect());
	}

	@Test
	public void testGetSource() {
		SourceIF result = answer1.source();
		assertNotNull(result);
		assertEquals(answerSource1, result);
		assertNotSame(answerSource2, result);
		assertNotNull(result.text());
		assertEquals(answerSource1.text(), result.text());
	}
}
