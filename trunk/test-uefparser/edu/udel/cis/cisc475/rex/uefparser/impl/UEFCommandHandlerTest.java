package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import java.io.EOFException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
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
	 * @throws IOException
	 * @throws RexParseException
	 */
	@Test
	public void processAnswerTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator
							 + "processAnswerTestFile.tex");
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

		//check when no more command in queue
		try
		{
			answer = handler.processAnswer(5);
		}
		catch (RexParseException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void processAnswersTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator
							 + "processAnswersTestFile.tex");
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
	public void processProblemTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator
							 + "processProblemTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		ProblemIF problem = handler.processProblem();
		AnswerIF answer[] = problem.answers();

		//test the problems first answer
		assertEquals(true, answer[0].isCorrect());
		assertEquals("processProblemTestFile.tex", answer[0].source().filename());
		assertEquals(7, answer[0].source().startLine());
		assertEquals(9, answer[0].source().lastLine());
		assertEquals(1, answer[0].source().startColumn());
		assertEquals(9, answer[0].source().lastColumn());
		assertEquals("\\answer[correct] Text here \\ref{fig2}\n\n    test",
					 answer[0].source().text());

		//test the problems second answer
		assertEquals(true, answer[1].isCorrect());
		assertEquals("processProblemTestFile.tex", answer[1].source().filename());
		assertEquals(11, answer[1].source().startLine());
		assertEquals(12, answer[1].source().lastLine());
		assertEquals(1, answer[1].source().startColumn());
		assertEquals(18, answer[1].source().lastColumn());
		assertEquals("\\answer[correct] another test\n             test", answer[1].source().text());

		//test the problems topic
		assertEquals("topic", problem.topic());

		//test difficulty
		assertEquals(45.0, problem.difficulty(), 0.0);

		//test label correctness
		assertEquals("label 1", problem.label());

		//test the correctness of getting the problem's SourceIF
		assertEquals(2, problem.question().startLine());
		assertEquals(4, problem.question().lastLine());
		assertEquals(2, problem.question().startColumn());
		assertEquals(10, problem.question().lastColumn());
		assertEquals("Here is the problem question?\\ref{fig1}\n\\label{   label 1   }\nYes here.", problem.
				question().text());
	}

	@Test
	public void processBlockTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator
							 + "processBlockTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		BlockIF block = handler.processBlock();
		assertEquals("name of block", block.label());
		assertEquals("processBlockTestFile.tex", block.source().filename());
		assertEquals(1, block.source().startLine());
		assertEquals(7, block.source().lastLine());
		assertEquals(1, block.source().startColumn());
		assertEquals(12, block.source().lastColumn());
		assertEquals(
				"\\begin{block}{name of block}\n\\ref{somefig}\n\\begin{verbatim} some }{{} \\end{verbatim}\n\n\\verb!test this verb!\n\n\\end{block}",
				block.source().text());
	}

	@Test
	public void processFigureTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator
							 + "processFigureTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();
		FigureIF figure = handler.processFigure();
		assertEquals("fig:example topic", figure.label());
		assertEquals("processFigureTestFile.tex", figure.source().filename());
		assertEquals(2, figure.source().startLine());
		assertEquals(8, figure.source().lastLine());
		assertEquals(1, figure.source().startColumn());
		assertEquals(13, figure.source().lastColumn());
		assertEquals(
				"\\begin{figure}[placement h]\n  \\begin{center}\n      \\includegraphics[scale=0.50]{binary_tree.png}\n      \\caption{Example Figure \\ref{some reference}}\n      \\label{fig:example topic}\n   \\end{center}\n\\end{figure}",
				figure.source().text());
	}

	/**
	 * Tests that process() returns a correct ExamIF after reading in a valid UEF file.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void process() throws RexParseException, RexException, IOException
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

		FigureIF figure = (FigureIF) exam.elementWithLabel("fig:example topic");

		assertEquals("fig:example topic", figure.label());

		//ExamFactoryIF examFactory = new ExamFactory();
		//SourceFactoryIF sourceFactory = newSource

		//SourceIF source =

		//AnswerIF answer1 = new ExamFactory().newAnswer(true, null);

		//assertEquals(15,problem[0].difficulty(),0);
	}
}