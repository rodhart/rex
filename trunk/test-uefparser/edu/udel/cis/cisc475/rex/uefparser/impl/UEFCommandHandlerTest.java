/**
 * Test Suite of UEFCommandHandler
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
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
		File file = new File("." + File.separator + "tests" + File.separator
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
		File file = new File("." + File.separator + "tests" + File.separator
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
		File file = new File("." + File.separator + "tests" + File.separator
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
		assertEquals("processProblemTestFile.tex", problem.question().filename());
		assertEquals(2, problem.question().startLine());
		assertEquals(4, problem.question().lastLine());
		assertEquals(2, problem.question().startColumn());
		assertEquals(10, problem.question().lastColumn());
		assertEquals("Here is the problem question?\\ref{fig1}\n\\label{   label 1   }\nYes here.", problem.question().text());
	}

	@Test
	public void processBlockTest() throws RexParseException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "tests" + File.separator
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
		File file = new File("." + File.separator + "tests" + File.separator
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

	@Test
	public void negativeTest() throws RexParseException, RexException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "exam.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		try
		{
			handler.processDocument();
		}
		catch (RexParseException e)
		{
			assertTrue(true);
		}
		try
		{
			handler.processAnswers();
		}
		catch (RexParseException e)
		{
			assertTrue(true);
		}
	}

	/**
	 * Tests that process() returns a correct ExamIF after reading in a valid UEF file.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void processTest() throws RexParseException, RexException, IOException
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

		//Test retrieving a figure using it's label
		FigureIF figure = (FigureIF) exam.elementWithLabel("fig:example topic");

		//check the correctness of the figure
		assertEquals("fig:example topic", figure.label());
		assertEquals("exam.tex", figure.source().filename());
		assertEquals(19, figure.source().startLine());
		assertEquals(25, figure.source().lastLine());
		assertEquals(1, figure.source().startColumn());
		assertEquals(13, figure.source().lastColumn());
		assertEquals(
				"\\begin{figure}[placement h]\n  \\begin{center}\n      \\includegraphics[scale=0.50]{binary_tree.png}\n      \\caption{Example Figure}\n      \\label{fig:example topic}\n   \\end{center}\n\\end{figure}", figure.
				source().text());



		//Get the problem requiring the figure
		Collection<ExamElementIF> elementCollection = exam.elementsUsingElement(figure);

		//Convert the problem list to an array
		ProblemIF problems[] = elementCollection.toArray(new ProblemIF[0]);
		assertEquals(1, problems.length);
		assertNotNull(problems);

		//get the only problem
		ProblemIF problem = problems[0];
		assertNotNull(problem);

		//get the answer list
		AnswerIF answer[] = problem.answers();
		assertNotNull(answer);

		//test the problems first answer
		assertEquals(true, answer[0].isCorrect());
		assertEquals("exam.tex", answer[0].source().filename());
		assertEquals(33, answer[0].source().startLine());
		assertEquals(33, answer[0].source().lastLine());
		assertEquals(5, answer[0].source().startColumn());
		assertEquals(38, answer[0].source().lastColumn());
		assertEquals("\\answer[correct] correct answer 1",
					 answer[0].source().text());
		assertFalse(answer[0] instanceof FixedAnswerIF);

		//test the problems second answer
		assertEquals(false, answer[1].isCorrect());
		assertEquals("exam.tex", answer[1].source().filename());
		assertEquals(34, answer[1].source().startLine());
		assertEquals(34, answer[1].source().lastLine());
		assertEquals(5, answer[1].source().startColumn());
		assertEquals(21, answer[1].source().lastColumn());
		assertEquals("\\answer answer 2", answer[1].source().text());
		assertFalse(answer[1] instanceof FixedAnswerIF);

		//test the problems third answer
		assertEquals(false, answer[2].isCorrect());
		assertEquals("exam.tex", answer[2].source().filename());
		assertEquals(35, answer[2].source().startLine());
		assertEquals(35, answer[2].source().lastLine());
		assertEquals(5, answer[2].source().startColumn());
		assertEquals(34, answer[2].source().lastColumn());
		assertEquals("\\answer[fixed] fixed answer 3", answer[2].source().text());
		assertTrue(answer[2] instanceof FixedAnswerIF);

		//test the problems fourth answer
		assertEquals(true, answer[3].isCorrect());
		assertEquals("exam.tex", answer[3].source().filename());
		assertEquals(36, answer[3].source().startLine());
		assertEquals(36, answer[3].source().lastLine());
		assertEquals(5, answer[3].source().startColumn());
		assertEquals(50, answer[3].source().lastColumn());
		assertEquals("\\answer[correct,fixed] correct fixed answer 4", answer[3].source().text());
		assertTrue(answer[3] instanceof FixedAnswerIF);

		//assume rest of problems are correct.

		//test the problems topic
		assertEquals("example topic", problem.topic());

		//test difficulty
		assertEquals(15.0, problem.difficulty(), 0.0);

		//test label correctness
		assertEquals(null, problem.label());

		//test the correctness of getting the problem's SourceIF
		assertEquals("exam.tex", problem.question().filename());
		assertEquals(30, problem.question().startLine());
		assertEquals(31, problem.question().lastLine());
		assertEquals(3, problem.question().startColumn());
		assertEquals(26, problem.question().lastColumn());
		assertEquals("This is a problem\n  \\ref{fig:example topic}", problem.question().text());


		//retrieve the required block and test it.
		BlockIF block = problem.requiredBlock();

		assertNotNull(block);
		assertEquals("example topic", block.label());
		assertEquals("exam.tex", block.source().filename());
		assertEquals(14, block.source().startLine());
		assertEquals(16, block.source().lastLine());
		assertEquals(1, block.source().startColumn());
		assertEquals(12, block.source().lastColumn());
		assertEquals(
				"\\begin{block}{example topic}\n  This is an example of a block\n\\end{block}",
				block.source().text());

		//check for problems requiring the following block
		//We'd check the correctness of all the problems, but their order is random. :'-(
		block = (BlockIF) exam.elementWithLabel("logical");
		elementCollection = exam.elementsUsingElement(block);
		problems = elementCollection.toArray(new ProblemIF[0]);
		assertEquals(3, problems.length);
		if (problems[0].difficulty() == 8.0)
		{
			assertEquals("In MATLAB the value 1 is true, and true is:\n%:type logical", problems[0].question().text());
		}
		else if (problems[1].difficulty() == 8.0)
		{
			assertEquals("In MATLAB the value 1 is true, and true is:\n%:type logical", problems[1].question().text());
		}
		else if (problems[2].difficulty() == 8.0)
		{
			assertEquals("In MATLAB the value 1 is true, and true is:\n%:type logical", problems[2].question().text());
		}
		else
		{
			fail("No matching difficulty found for problem.");
		}

		//check for problems requiring the following block
		//We'd check the correctness of all the problems, but their order is random. :'-(
		block = (BlockIF) exam.elementWithLabel("matrix matlab");
		elementCollection = exam.elementsUsingElement(block);
		problems = elementCollection.toArray(new ProblemIF[0]);
		assertEquals(4, problems.length);

		if (problems[0].difficulty() == 4.0)
		{
			assertEquals(
					"Which of the following will extract \\texttt{[5 6 7]} from the matrix m\n  shown?\\\\\n\\texttt{>> m = [3 4 5 6 7 8];\n}\n%\\myans{A}{B}{E}", problems[0].
					question().text());
		}
		else if (problems[1].difficulty() == 4.0)
		{
			assertEquals(
					"Which of the following will extract \\texttt{[5 6 7]} from the matrix m\n  shown?\\\\\n\\texttt{>> m = [3 4 5 6 7 8];\n}\n%\\myans{A}{B}{E}", problems[1].
					question().text());
		}
		else if (problems[2].difficulty() == 4.0)
		{
			assertEquals(
					"Which of the following will extract \\texttt{[5 6 7]} from the matrix m\n  shown?\\\\\n\\texttt{>> m = [3 4 5 6 7 8];\n}\n%\\myans{A}{B}{E}", problems[2].
					question().text());
		}
		else if (problems[3].difficulty() == 4.0)
		{
			assertEquals(
					"Which of the following will extract \\texttt{[5 6 7]} from the matrix m\n  shown?\\\\\n\\texttt{>> m = [3 4 5 6 7 8];\n}\n%\\myans{A}{B}{E}", problems[3].
					question().text());
		}
		else
		{
			fail("No matching difficulty found for problem.");
		}

		//test problems with topic 'function call'
		elementCollection = exam.elementsWithTopic("function call");
		problems = elementCollection.toArray(new ProblemIF[0]);
		assertEquals(2, problems.length);
		ProblemIF problem2;
		if (problems[0].difficulty() == 5.0)
		{
			problem = problems[0];
			problem2 = problems[1];
		}
		else
		{
			problem = problems[1];
			problem2 = problems[0];
		}
		assertEquals(
				"Consider the function f:\n  \\begin{verbatim}\n    function [] = f(x)\n    output = 7;\n    end\n  \\end{verbatim}\n  What will be in x after the command:\n  \\begin{verbatim}>> x = f(3);\\end{verbatim}\n%:type function call", problem.
				question().text());
		assertEquals(
				"Consider the function f:\n  \\begin{verbatim}\n    function answer = f(a)\n    answer = a+2;\n    end\n  \\end{verbatim}\n  What will be in x after the command:\n  \\begin{verbatim}>> x = f(4);\n  \\end{verbatim}\n%:type function call", problem2.
				question().text());
	}

	/**
	 * Tests that references have the correct USES relationships in an ExamIF after reading in a UEF file.
	 *
	 * @throws RexParseException
	 * @throws RexException
	 * @throws IOException
	 */
	@Test
	public void referenceTest() throws RexParseException, RexException, IOException
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "tests" + File.separator + "referenceTestFile.tex");
		parser.parseForAllCommands(file);

		//get a reference to the handler to allow direct calls to the process methods of the handler
		UEFCommandHandler handler = parser.getUEFCommandHandler();

		ExamIF exam = handler.process();

		//get the only problem in the exam.
		Collection<ProblemIF> problemCollection = exam.problemsWithTopic("topic");
		ProblemIF problem = problemCollection.toArray(new ProblemIF[0])[0];

		//get figures referencing that problem.
		Collection<FigureIF> figureCollection = problem.referencedFigures();
		FigureIF figure[] = figureCollection.toArray(new FigureIF[0]);

		//there should be 3 figures.
		assertEquals(3, figure.length);

		//check each figure to make sure they are correct.
		boolean foundFig1 = false;
		boolean foundFig2 = false;
		boolean foundFig3 = false;
		for (int n = 0; n < figure.length; n++)
		{
			//go through each figure and check them off if they are found.
			//make sure there are no duplicates.
			if (figure[n].label().equals("fig1"))
			{
				if (foundFig1 == false)
				{
					foundFig1 = true;
					assertTrue(true);
				}
				else
				{
					fail();
				}
			}
			else if (figure[n].label().equals("fig2"))
			{
				if (foundFig2 == false)
				{
					foundFig2 = true;
					assertTrue(true);
				}
				else
				{
					fail();
				}
			}
			else if (figure[n].label().equals("fig3"))
			{
				if (foundFig3 == false)
				{
					foundFig3 = true;
					assertTrue(true);
				}
				else
				{
					fail();
				}
			}
			else
			{
				fail();
			}
		}

		//get all the figures in the exam
		figureCollection = exam.figures();
		figure = figureCollection.toArray(new FigureIF[0]);

		//check to make sure there are four figures.
		assertEquals(4, figure.length);

		//find fig4 because we want to make sure fig3 is USING it.
		for (int n = 0; n < figure.length; n++)
		{
			if(figure[n].label().equals("fig4"))
			{
				//get the figure from the collection
				Collection<ExamElementIF> examElementCollection = exam.elementsUsingElement(figure[n]);
				FigureIF element[] = examElementCollection.toArray(new FigureIF[0]);

				//check to make sure there is only one figure using fig4
				assertEquals(1,element.length);

				//make sure the figure is fig3.
				assertEquals("fig3",element[0].label());
			}
		}
	}
}
