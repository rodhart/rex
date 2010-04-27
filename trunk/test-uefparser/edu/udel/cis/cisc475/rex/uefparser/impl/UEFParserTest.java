/**
 * Test Suite for UEFParser
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URL;
import org.junit.Test;

/**
 * Testing for UEFParser
 *
 * @author Ahmed El-Hassany
 *
 * @author Aaron Myles Landwehr
 *
 */
public class UEFParserTest
{

	/**
	 * Tests different argument combinations for the parseForArgument() method. The file read
	 * in attempts to identify different scenerios that could cause parsing failure.
	 * 
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void parseForArgumentTest() throws Exception
	{
		UEFParser parser = new UEFParser();
		
		//Open the file to parse.
		File file = new File(".", "examples" + File.separator + "parseForArgumentTestFile.tex");
		System.err.println(file.getAbsolutePath());
		parser.openFile(file);
		
		String argument1 = parser.parseForArgument();
		assertEquals("arg1", argument1);

		String argument2 = parser.parseForArgument();
		assertEquals("arg2", argument2);

		String argument3 = parser.parseForArgument();
		assertEquals("arg3", argument3);

		String argument4 = parser.parseForArgument();
		assertEquals("arg4", argument4);

		String argument5 = parser.parseForArgument();
		assertEquals("arg5", argument5);

		String argument6 = parser.parseForArgument();
		assertEquals("arg6", argument6);

		String argument7 = parser.parseForArgument();
		assertEquals("arg7", argument7);

		//Check for argument after optional argument- should ignore.
		String argument8 = parser.parseForArgument();
		assertEquals("arg8", argument8);

		//Check for argument following a command.
		String argument9 = parser.parseForArgument();
		assertEquals(null, argument9);
	}

	/**
	 * Tests different optional argument and argument combinations for the
	 * parseForOptionalArgument() method. The file read in attempts to identify different
	 * scenerios that could cause parsing failure.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void parseForOptionalArgumentTest() throws Exception
	{
		File file = new File("." + File.separator + "examples" + File.separator + "parseForOptionalArgumentTestFile.tex");
		System.err.println(file.getAbsolutePath());
		//Open the file to parse.
		UEFParser parser = new UEFParser();
		parser.openFile(file);

		//Check different optional argument possiblities.
		String optionalArgument1 = parser.parseForOptionalArgument();
		assertEquals("oarg1", optionalArgument1);

		//Check different optional argument possiblities.
		String optionalArgument2 = parser.parseForOptionalArgument();
		assertEquals("oarg2", optionalArgument2);

		//Check different optional argument possiblities.
		String optionalArgument3 = parser.parseForOptionalArgument();
		assertEquals("oarg3", optionalArgument3);

		//Check different optional argument possiblities.
		String optionalArgument4 = parser.parseForOptionalArgument();
		assertEquals("oarg4", optionalArgument4);

		//Check different optional argument possiblities.
		String optionalArgument5 = parser.parseForOptionalArgument();
		assertEquals("oarg5", optionalArgument5);

		//Check different optional argument possiblities.
		String optionalArgument6 = parser.parseForOptionalArgument();
		assertEquals("oarg6", optionalArgument6);

		//Check different optional argument possiblities.
		String optionalArgument7 = parser.parseForOptionalArgument();
		assertEquals("oarg7", optionalArgument7);

		//Check for optional argument when the there is an argument before an optional argument.
		String optionalArgument8 = parser.parseForOptionalArgument();
		assertNull(optionalArgument8);

		//Try again to make sure we didn't move forward.
		optionalArgument8 = parser.parseForOptionalArgument();
		assertNull(optionalArgument8);

		//Move past regular argument then check it for prosperity.
		String Argument1 = parser.parseForArgument();
		assertEquals("arg1", Argument1);

		//Check the optional argument one more time. Should be valid now.
		optionalArgument8 = parser.parseForOptionalArgument();
		assertEquals("oarg8", optionalArgument8);

		//One more check.
		String argument9 = parser.parseForOptionalArgument();
		assertEquals("oarg9", argument9);

		//Check for optional argument behind command
		String argument10 = parser.parseForOptionalArgument();
		assertEquals(null, argument10);
	}

	/**
	 * Test of parsing a complete file using the parseForCommand() method. The file read in
	 * attempts to identify likely parsing problems by trying to trick the parser with complicated
	 * scenerios of commands that are actually plain text because they are within escape sequences,
	 * verbatim, verb, and comments. It also attempts to verify the correctness of every possible
	 * command supported by the method.
	 *
	 * @throws Exception If the test somehow throws some kind of exception.
	 * The likely scenerio is that the needed resource file isn't being found for some reason.
	 */
	@Test
	public void parseForCommandTest() throws Exception
	{
		UEFParser parser = new UEFParser();

		//Open the file to parse.
		File file = new File("." + File.separator + "examples" + File.separator + "parseForCommandTestFile.tex");
		parser.openFile(file);

		//Check \documentclass command
		UEFCommand documentclass = parser.parseForCommand();
		assertEquals(UEFCommand.Types.documentclass, documentclass.getType());
		assertEquals(0, documentclass.getStartPosition());
		assertEquals(20, documentclass.getEndPosition());
		assertEquals(20, documentclass.getSize());
		assertEquals("exam", documentclass.getArgument(0));
		assertEquals(null, documentclass.getOptionalArgument());

		//Check \label command
		UEFCommand label = parser.parseForCommand();
		assertEquals(UEFCommand.Types.label, label.getType());
		assertEquals(20, label.getStartPosition());
		assertEquals(31, label.getEndPosition());
		assertEquals(11, label.getSize());
		assertEquals("lbl", label.getArgument(0));
		assertEquals(null, label.getOptionalArgument());

		//Check \answer command
		UEFCommand answer = parser.parseForCommand();
		assertEquals(UEFCommand.Types.answer, answer.getType());
		assertEquals(160, answer.getStartPosition());
		assertEquals(177, answer.getEndPosition());
		assertEquals(17, answer.getSize());
		assertEquals("optional", answer.getOptionalArgument());

		//Check \answer command
		answer = parser.parseForCommand();
		assertEquals(UEFCommand.Types.answer, answer.getType());
		assertEquals(178, answer.getStartPosition());
		assertEquals(199, answer.getEndPosition());
		assertEquals(21, answer.getSize());
		assertEquals("optional2", answer.getOptionalArgument());

		//Check \begin{document} command
		UEFCommand beginDocument = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginDocument, beginDocument.getType());
		assertEquals(237, beginDocument.getStartPosition());
		assertEquals(253, beginDocument.getEndPosition());
		assertEquals(16, beginDocument.getSize());
		assertEquals(null, beginDocument.getOptionalArgument());

		//Check \end{document} command
		UEFCommand endDocument = parser.parseForCommand();
		assertEquals(UEFCommand.Types.endDocument, endDocument.getType());
		assertEquals(253, endDocument.getStartPosition());
		assertEquals(267, endDocument.getEndPosition());
		assertEquals(14, endDocument.getSize());
		assertEquals(null, endDocument.getOptionalArgument());

		//Check \begin{problem} command
		UEFCommand beginProblem = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginProblem, beginProblem.getType());
		assertEquals(268, beginProblem.getStartPosition());
		assertEquals(302, beginProblem.getEndPosition());
		assertEquals(34, beginProblem.getSize());
		assertEquals("Topic", beginProblem.getArgument(0));
		assertEquals("difficulty", beginProblem.getArgument(1));
		assertEquals(null, beginProblem.getOptionalArgument());

		//Check \begin{problem} command with optional argument
		beginProblem = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginProblem, beginProblem.getType());
		assertEquals(302, beginProblem.getStartPosition());
		assertEquals(403, beginProblem.getEndPosition());
		assertEquals(101, beginProblem.getSize());
		assertEquals("Topic2", beginProblem.getArgument(0));
		assertEquals("difficulty2", beginProblem.getArgument(1));
		assertEquals("optional argument", beginProblem.getOptionalArgument());

		//Check \end{problem} command
		UEFCommand endProblem = parser.parseForCommand();
		assertEquals(UEFCommand.Types.endProblem, endProblem.getType());
		assertEquals(404, endProblem.getStartPosition());
		assertEquals(417, endProblem.getEndPosition());
		assertEquals(13, endProblem.getSize());
		assertEquals(null, endProblem.getOptionalArgument());

		//Check \begin{answers} command
		UEFCommand beginAnswers = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginAnswers, beginAnswers.getType());
		assertEquals(418, beginAnswers.getStartPosition());
		assertEquals(433, beginAnswers.getEndPosition());
		assertEquals(15, beginAnswers.getSize());
		assertEquals(null, beginAnswers.getOptionalArgument());

		//Check \end{answers} command
		UEFCommand endAnswers = parser.parseForCommand();
		assertEquals(UEFCommand.Types.endAnswers, endAnswers.getType());
		assertEquals(451, endAnswers.getStartPosition());
		assertEquals(464, endAnswers.getEndPosition());
		assertEquals(13, endAnswers.getSize());
		assertEquals(null, endAnswers.getOptionalArgument());

		//Check \begin{block} command
		UEFCommand beginBlock = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginBlock, beginBlock.getType());
		assertEquals(464, beginBlock.getStartPosition());
		assertEquals(491, beginBlock.getEndPosition());
		assertEquals(27, beginBlock.getSize());
		assertEquals("some Name", beginBlock.getArgument(0));
		assertEquals(null, beginBlock.getOptionalArgument());

		//Check \end{block} command
		UEFCommand endBlock = parser.parseForCommand();
		assertEquals(UEFCommand.Types.endBlock, endBlock.getType());
		assertEquals(619, endBlock.getStartPosition());
		assertEquals(630, endBlock.getEndPosition());
		assertEquals(11, endBlock.getSize());
		assertEquals(null, endBlock.getOptionalArgument());

		//Check \ref command
		UEFCommand ref = parser.parseForCommand();
		assertEquals(UEFCommand.Types.ref, ref.getType());
		assertEquals(631, ref.getStartPosition());
		assertEquals(651, ref.getEndPosition());
		assertEquals(20, ref.getSize());
		assertEquals("some reference", ref.getArgument(0));
		assertEquals(null, ref.getOptionalArgument());

		//Check \begin{figure} command
		UEFCommand beginFigure = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginFigure, beginFigure.getType());
		assertEquals(652, beginFigure.getStartPosition());
		assertEquals(666, beginFigure.getEndPosition());
		assertEquals(14, beginFigure.getSize());
		assertEquals(null, beginFigure.getOptionalArgument());

		//Check \end{figure} command
		UEFCommand endFigure = parser.parseForCommand();
		assertEquals(UEFCommand.Types.endFigure, endFigure.getType());
		assertEquals(692, endFigure.getStartPosition());
		assertEquals(704, endFigure.getEndPosition());
		assertEquals(12, endFigure.getSize());
		assertEquals(null, endFigure.getOptionalArgument());

		//Check \begin{answers} command when there isn't an optional argument
		beginAnswers = parser.parseForCommand();
		assertEquals(UEFCommand.Types.beginAnswers, beginAnswers.getType());
		assertEquals(789, beginAnswers.getStartPosition());
		assertEquals(804, beginAnswers.getEndPosition());
		assertEquals(15, beginAnswers.getSize());
		assertEquals(null, beginAnswers.getOptionalArgument());
	}
}
