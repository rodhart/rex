package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
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
	}
}
