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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

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

/**
 * @author hboyd
 *
 */
public class ExamTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static ExamIF masterExam;
	private static ExamIF generatedExam;
	
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
		
		masterExam = examFactory.newMasterExam();
		generatedExam = examFactory.newGeneratedExam();
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
	public void testIsMaster() {
		assertTrue(masterExam.isMaster());
		assertFalse(generatedExam.isMaster());
	}

	@Test
	public void testGetPreamble() {
		SourceIF preambleSource = sourceFactory.newSource(testUEFfilename);
		preambleSource.addText("Test Preamble Source");
		
		masterExam.setPreamble(preambleSource);
		
		SourceIF result = masterExam.preamble();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(preambleSource.text(), result.text());
		assertEquals(preambleSource, result);
	}
	
	@Test
	public void testGetBadPreamble() {
		masterExam.setPreamble(null);
		
		assertNull(masterExam.preamble());
	}
	
	@Test
	public void testGetFrontMatter() {
		SourceIF frontMatterSource = sourceFactory.newSource(testUEFfilename);
		frontMatterSource.addText("Test Front Matter Source");
		
		masterExam.setFrontMatter(frontMatterSource);
		
		SourceIF result = masterExam.frontMatter();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(frontMatterSource.text(), result.text());
		assertEquals(frontMatterSource, result);
	}
	
	@Test
	public void testGetBadFrontMatter() {
		masterExam.setFrontMatter(null);
		
		assertNull(masterExam.frontMatter());
	}
	
	@Test
	public void testGetFinalBlock() {
		SourceIF blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		BlockIF block = examFactory.newBlock("Test Block Topic", "Test Block Label", blockSource);
		
		masterExam.setFinalBlock(block);
		
		BlockIF result = masterExam.finalBlock();
		assertNotNull(result);
		assertNotNull(result.topic());
		assertNotNull(result.label());
		assertEquals("Test Block Topic", result.topic());
		assertEquals("Test Block Label", result.label());
		assertEquals(block, result);
	}
	
	@Test
	public void testGetBadFinalBlock() {
		masterExam.setFinalBlock(null);
		
		assertNull(masterExam.finalBlock());
	}
	
	@Test
	@Ignore
	public void testGetNumElements() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetElement() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetElements() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetElementWithLabel() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetElementsWithTopic() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetElementsUsingElement() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetFigures() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetLabels() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetTopics() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetProblems() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testGetProblemsWithTopic() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testSetPreamble() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testSetFrontMatter() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testSetFinalBlock() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddElement() {
		generatedExam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<ExamElementIF> elements = generatedExam.elements();
		assertNotNull(elements);
		assertEquals(0, elements.size());
		
		generatedExam.addElementIF(problem);
		generatedExam.addElementIF(figure);
		generatedExam.addElementIF(block);
		
		elements = generatedExam.elements();
		assertNotNull(elements);
		assertEquals(3, elements.size());
		assertTrue(elements.contains(problem));
		assertTrue(elements.contains(figure));
		assertTrue(elements.contains(block));
	}
	
	@Test
	@Ignore
	public void testAddBadElement() {
		generatedExam = examFactory.newGeneratedExam();
		
		generatedExam.addElementIF(null);
		
		Collection<ExamElementIF> elements = generatedExam.elements();
		assertNotNull(elements);
		assertEquals(0, elements.size());
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
		return examFactory.newProblem("Test Topic", "Test Problem Label", testQuestionSource, testAnswers);
	}
	
	private static FigureIF createTestFigure() {
		SourceIF figureSource;
		
		String testUEFfilename = "testFileName.txt";
		String testLabel = "test Label";
		
		figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("Test Figure Source");
		return examFactory.newFigure(testLabel, figureSource);
	}
	
	private static BlockIF createTestBlock() {
		SourceIF blockSource;
		
		String testUEFfilename = "testFileName.txt";
		String testTopic = "test Topic";
		String testLabel = "test Label";
		
		blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		return examFactory.newBlock(testTopic, testLabel, blockSource);
	}
}
