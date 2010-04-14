/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author hboyd
 *
 */
public class FixedAnswerTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static SourceIF answerSource1;
	private static SourceIF answerSource2;
	private static FixedAnswerIF answer1;
	private static FixedAnswerIF answer2;
	
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
		
		answerSource1 = sourceFactory.newSource(testUEFfilename);
		answerSource1.addText("Foo Bar Answer Text.");
		
		answer1 = examFactory.newFixedAnswer(true, 0, answerSource1);
		
		answerSource2 = sourceFactory.newSource(testUEFfilename);
		answerSource2.addText("Bar Foo Answer Text.");
		
		answer2 = examFactory.newFixedAnswer(false, 1, answerSource2);
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
	public void testGetIndex() {
		assertTrue(answer1.index() == 0);
		assertTrue(answer2.index() == 1);
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
		assertNotNull(result.text());
		assertEquals(answerSource1.text(), result.text());
		assertEquals(answerSource1, result);
		assertNotSame(answerSource2, result);
	}
}
