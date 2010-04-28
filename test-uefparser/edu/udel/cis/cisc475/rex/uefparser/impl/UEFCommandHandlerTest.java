package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.Collection;
import org.junit.Test;

/**
 *
 * @author Aaron Myles Landwehr
 *
 */
public class UEFCommandHandlerTest
{

	/**
	 * Tests that processAnswer() returns correct AnswerIF objects after reading in a number of
	 * \answer commands from a file.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void processAnswerTest() throws Exception
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "processAnswerTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		AnswerIF answer = handler.processAnswer(0);
		assertEquals(true, answer.isCorrect());
		assertEquals("processAnswerTestFile.tex", answer.source().filename());
		assertEquals(1, answer.source().startLine());
		assertEquals(1, answer.source().lastLine());
		assertEquals(1, answer.source().startColumn());
		assertEquals(26, answer.source().lastColumn());
		assertEquals("\\answer[correct] $2^{-1}$", answer.source().text());

		answer = handler.processAnswer(1);
		assertEquals(true, answer.isCorrect());
		assertEquals("processAnswerTestFile.tex", answer.source().filename());
		assertEquals(2, answer.source().startLine());
		assertEquals(2, answer.source().lastLine());
		assertEquals(1, answer.source().startColumn());
		assertEquals(23, answer.source().lastColumn());
		assertEquals("\\answer[correct] $2^0$", answer.source().text());

		answer = handler.processAnswer(2);
		assertEquals(false, answer.isCorrect());
		assertEquals("processAnswerTestFile.tex", answer.source().filename());
		assertEquals(2, answer.source().startLine());
		assertEquals(2, answer.source().lastLine());
		assertEquals(23, answer.source().startColumn());
		assertEquals(36, answer.source().lastColumn());
		assertEquals("\\answer $2^1$", answer.source().text());

		answer = handler.processAnswer(3);
		assertEquals(false, answer.isCorrect());
		assertEquals("processAnswerTestFile.tex", answer.source().filename());
		assertEquals(3, answer.source().startLine());
		assertEquals(4, answer.source().lastLine());
		assertEquals(8, answer.source().startColumn());
		assertEquals(26, answer.source().lastColumn());
		assertEquals("\\answer $3^1$\n       ends on this line.", answer.source().text());

		answer = handler.processAnswer(4);
		assertTrue(answer instanceof FixedAnswerIF);
		assertEquals(false, answer.isCorrect());
		assertEquals(4, ((FixedAnswerIF) answer).index());
		assertEquals("processAnswerTestFile.tex", answer.source().filename());
		assertEquals(5, answer.source().startLine());
		assertEquals(5, answer.source().lastLine());
		assertEquals(1, answer.source().startColumn());
		assertEquals(33, answer.source().lastColumn());
		assertEquals("\\answer[fixed] none of the above", answer.source().text());
	}

	@Test
	public void processAnswersTest() throws Exception
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "processAnswersTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		AnswerIF answer[] = handler.processAnswers();

		assertEquals(true, answer[0].isCorrect());
		assertEquals("processAnswersTestFile.tex", answer[0].source().filename());
		assertEquals(2, answer[0].source().startLine());
		assertEquals(2, answer[0].source().lastLine());
		assertEquals(1, answer[0].source().startColumn());
		assertEquals(26, answer[0].source().lastColumn());
		assertEquals("\\answer[correct] $2^{-1}$", answer[0].source().text());

		assertEquals(true, answer[1].isCorrect());
		assertEquals("processAnswersTestFile.tex", answer[1].source().filename());
		assertEquals(3, answer[1].source().startLine());
		assertEquals(3, answer[1].source().lastLine());
		assertEquals(1, answer[1].source().startColumn());
		assertEquals(23, answer[1].source().lastColumn());
		assertEquals("\\answer[correct] $2^0$", answer[1].source().text());

		assertEquals(2, answer.length);
	}

	@Test
	public void processProblemTest() throws Exception
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "processProblemTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		ProblemIF problem = handler.processProblem();
		AnswerIF answer[] = problem.answers();

		assertEquals(true, answer[0].isCorrect());
		assertEquals("processProblemTestFile.tex", answer[0].source().filename());
		assertEquals(7, answer[0].source().startLine());
		assertEquals(9, answer[0].source().lastLine());
		assertEquals(1, answer[0].source().startColumn());
		assertEquals(9, answer[0].source().lastColumn());
		assertEquals("\\answer[correct] Text here\n\n    test", answer[0].source().text());

		assertEquals(true, answer[1].isCorrect());
		assertEquals("processProblemTestFile.tex", answer[1].source().filename());
		assertEquals(11, answer[1].source().startLine());
		assertEquals(12, answer[1].source().lastLine());
		assertEquals(1, answer[1].source().startColumn());
		assertEquals(18, answer[1].source().lastColumn());
		assertEquals("\\answer[correct] another test\n             test", answer[1].source().text());
	}

	/**
	 * Tests that process() returns a correct ExamIF after reading in a valid UEF file.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void process() throws Exception
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "exam.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		ExamIF exam = handler.process();

		Collection<String> topics = exam.topics();

		assertTrue(topics.contains("logical"));
		assertTrue(topics.contains("example topic"));
		assertTrue(topics.contains("matrix matlab"));
		assertTrue(topics.contains("function call"));

		assertTrue(exam.isMaster());

		Collection<ProblemIF> problemCollection = exam.problems();
		ProblemIF problem[] = problemCollection.toArray(new ProblemIF[0]);

		//ExamFactoryIF examFactory = new ExamFactory();
		//SourceFactoryIF sourceFactory = newSource

		//SourceIF source =

		//AnswerIF answer1 = new ExamFactory().newAnswer(true, null);

		//assertEquals(15,problem[0].difficulty(),0);
	}
}
