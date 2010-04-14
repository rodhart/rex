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
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author hboyd
 *
 */
public class ExamTest {
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
	public void testIsMaster() {
		ExamIF exam = examFactory.newMasterExam();
		
		assertTrue(exam.isMaster());
		exam = examFactory.newGeneratedExam();
		assertFalse(exam.isMaster());
	}

	@Test
	public void testGetPreamble() {
		ExamIF exam = examFactory.newMasterExam();
		
		SourceIF preambleSource = sourceFactory.newSource(testUEFfilename);
		preambleSource.addText("Test Preamble Source");
		
		exam.setPreamble(preambleSource);
		
		SourceIF result = exam.preamble();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(preambleSource.text(), result.text());
		assertEquals(preambleSource, result);
	}
	
	@Test
	public void testGetBadPreamble() {
		ExamIF exam = examFactory.newMasterExam();
		
		exam.setPreamble(null);
		
		assertNull(exam.preamble());
	}
	
	@Test
	public void testGetFrontMatter() {
		ExamIF exam = examFactory.newMasterExam();
		
		SourceIF frontMatterSource = sourceFactory.newSource(testUEFfilename);
		frontMatterSource.addText("Test Front Matter Source");
		
		exam.setFrontMatter(frontMatterSource);
		
		SourceIF result = exam.frontMatter();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(frontMatterSource.text(), result.text());
		assertEquals(frontMatterSource, result);
	}
	
	@Test
	public void testGetBadFrontMatter() {
		ExamIF exam = examFactory.newMasterExam();
		
		exam.setFrontMatter(null);
		
		assertNull(exam.frontMatter());
	}
	
	@Test
	public void testGetFinalBlock() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		SourceIF blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		BlockIF block = examFactory.newBlock("Test Block Topic", "Test Block Label", blockSource);
		
		exam.setFinalBlock(block);
		
		BlockIF result = exam.finalBlock();
		assertNotNull(result);
		assertNotNull(result.topic());
		assertNotNull(result.label());
		assertEquals("Test Block Topic", result.topic());
		assertEquals("Test Block Label", result.label());
		assertEquals(block, result);
	}
	
	@Test
	public void testGetBadFinalBlock() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		exam.setFinalBlock(null);
		
		assertNull(exam.finalBlock());
	}
	
	@Test
	public void testGetNumElements() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		assertEquals(0, exam.numElements());
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		assertEquals(3, exam.numElements());
	}
	
	@Test
	public void testGetElement() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		ExamElementIF element = exam.element(0);
		
		assertNull(element);
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		element = exam.element(0);
		
		assertNotNull(element);
	}
	
	@Test
	public void testGetElements() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<ExamElementIF> elements = exam.elements();
		
		assertNotNull(elements);
		assertEquals(0, elements.size());
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		elements = exam.elements();
		
		assertNotNull(elements);
		// size should be 2 because problem doesn't extend ExamElementIF
		assertEquals(2, elements.size());
	}
	
	@Test
	public void testGetElementWithLabel() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		ExamElementIF element = exam.elementWithLabel("Test Problem Label");
		assertNull(element);
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		element = exam.elementWithLabel("Test Problem Label");
		assertNotNull(element);
		assertEquals(problem, element);
		
		element = exam.elementWithLabel("Test Figure Label");
		assertNotNull(element);
		assertEquals(figure, element);
		
		element = exam.elementWithLabel("Test Block Label");
		assertNotNull(element);
		assertEquals(block, element);
	}
	
	@Test
	public void testGetElementsWithTopic() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem1 = createTestProblem();
		ProblemIF problem2 = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<ExamElementIF> elements = exam.elementsWithTopic("Test Problem Topic");
		assertNotNull(elements);
		assertEquals(0, elements.size());
		
		exam.addElementIF(problem1);
		exam.addElementIF(problem2);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		elements = exam.elementsWithTopic("Test Problem Topic");
		assertNotNull(elements);
		assertEquals(2, elements.size());
		
	}
	
	@Test
	public void testGetElementsUsingElement() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		FigureIF figure = createTestFigure();
		ProblemIF problem1 = createTestProblem();
		ProblemIF problem2 = createTestProblem();
		
		Collection<ExamElementIF> elements = exam.elementsUsingElement(figure);
		
		assertNotNull(elements);
		assertEquals(0, elements.size());
		
		exam.declareUse(problem1, figure);
		
		elements = exam.elementsUsingElement(figure);
		
		assertEquals(1, elements.size());
		assertTrue(elements.toArray()[0] instanceof ProblemIF);
		assertTrue(elements.contains(problem1));
		
		exam.declareUse(problem2, figure);
		
		elements = exam.elementsUsingElement(figure);
		
		assertEquals(2, elements.size());
		assertTrue(elements.contains(problem2));
	}
	
	@Test
	public void testGetFigures() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		
		FigureIF figure1 = createTestFigure();
		FigureIF figure2 = createTestFigure();
		
		Collection<FigureIF> figures = exam.figures();
		assertNotNull(figures);
		assertEquals(0, figures.size());
		
		exam.addElementIF(figure1);
		exam.addElementIF(figure2);
		
		figures = exam.figures();
		assertNotNull(figures);
		assertEquals(2, figures.size());
		assertTrue(figures.contains(figure1));
		assertTrue(figures.contains(figure2));
	}
	
	@Test
	public void testGetLabels() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<String> labels = exam.labels();
		assertNotNull(labels);
		assertEquals(0, labels.size());
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		labels = exam.labels();
		assertNotNull(labels);
		assertEquals(3, labels.size());
		assertTrue(labels.contains("Test Problem Label"));
		assertTrue(labels.contains("Test Figure Label"));
		assertTrue(labels.contains("Test Block Label"));
	}
	
	@Test
	public void testGetTopics() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<String> topics = exam.topics();
		assertNotNull(topics);
		assertEquals(0, topics.size());
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		topics = exam.labels();
		assertNotNull(topics);
		// should only return 2 topics since figure doesnt have a topic
		assertEquals(2, topics.size());
		assertTrue(topics.contains("Test Problem Topic"));
		assertTrue(topics.contains("Test Block Topic"));
	}
	
	@Test
	public void testGetProblems() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem1 = createTestProblem();
		ProblemIF problem2 = createTestProblem();
		
		Collection<ProblemIF> problems = exam.problems();
		assertNotNull(problems);
		assertEquals(0, problems.size());
		
		exam.addElementIF(problem1);
		exam.addElementIF(problem2);
		
		problems = exam.problems();
		assertNotNull(problems);
		assertEquals(2, problems.size());
		assertTrue(problems.contains(problem1));
		assertTrue(problems.contains(problem2));
	}
	
	@Test
	public void testGetProblemsWithTopic() {
		ExamIF exam = examFactory.newGeneratedExam();
		
		ProblemIF problem1 = createTestProblem();
		ProblemIF problem2 = createTestProblem();
		
		Collection<ProblemIF> problems = 
			exam.problemsWithTopic("Test Problem Topic");
		assertNotNull(problems);
		assertEquals(0, problems.size());
		
		exam.addElementIF(problem1);
		exam.addElementIF(problem2);
		
		problems = exam.problemsWithTopic("Test Problem Topic");
		assertNotNull(problems);
		assertEquals(2, problems.size());
		assertTrue(problems.contains(problem1));
		assertTrue(problems.contains(problem2));
	}
	
	@Test
	public void testAddElement() {
		ExamIF exam = examFactory.newMasterExam();
		
		exam = examFactory.newGeneratedExam();
		
		ProblemIF problem = createTestProblem();
		FigureIF figure = createTestFigure();
		BlockIF block = createTestBlock();
		
		Collection<ExamElementIF> elements = exam.elements();
		assertNotNull(elements);
		assertEquals(0, elements.size());
		
		exam.addElementIF(problem);
		exam.addElementIF(figure);
		exam.addElementIF(block);
		
		elements = exam.elements();
		assertNotNull(elements);
		assertEquals(3, elements.size());
		assertTrue(elements.contains(problem));
		assertTrue(elements.contains(figure));
		assertTrue(elements.contains(block));
	}
	
	@Test
	@Ignore
	public void testAddBadElement() {
		ExamIF exam = examFactory.newMasterExam();
		
		exam = examFactory.newGeneratedExam();
		
		exam.addElementIF(null);
		
		Collection<ExamElementIF> elements = exam.elements();
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
		return examFactory.newProblem("Test Problem Topic", "Test Problem Label", testQuestionSource, testAnswers);
	}
	
	private static FigureIF createTestFigure() {
		SourceIF figureSource;
		
		String testUEFfilename = "TestFileName.txt";
		String testLabel = "Test Figure Label";
		
		figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("Test Figure Source");
		return examFactory.newFigure(testLabel, figureSource);
	}
	
	private static BlockIF createTestBlock() {
		SourceIF blockSource;
		
		String testUEFfilename = "testFileName.txt";
		String testTopic = "Test Block Topic";
		String testLabel = "Test Block Label";
		
		blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		return examFactory.newBlock(testTopic, testLabel, blockSource);
	}
	
}
