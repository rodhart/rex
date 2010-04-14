/**
 * Test Suite of UEFCmmand
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.CommandTypes;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParser.States;

/**
 * Testing for UEFCommand
 * 
 * @author Ahmed El-Hassany
 * 
 * @author Aaron Myles Landwehr
 * 
 */
public class UEFCommandTest {

	/**
	 * Instance of UEFCharHandler to be used to help in testing.
	 */
	private UEFCharHandler uefCharHandler;
	/**
	 * Instance of UEFCommand to be test.
	 */
	private UEFCommand uefCommand;
	/**
	 * Instance of the Stack of states to be used to help in testing.
	 */
	private Stack<States> state;

	/**
	 * Instance of UEFCharHandler to be used to help in testing.
	 */
	private UEFCharHandler uefCharHandler2;
	/**
	 * Instance of UEFCommand to be test.
	 */
	private UEFCommand uefCommand2;
	/**
	 * Instance of the Stack of states to be used to help in testing.
	 */
	private Stack<States> state2;

	/**
	 * Initialize the pieces we need to setup testing and open the file we need
	 * help with our testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// create the object.
		this.uefCharHandler = new UEFCharHandler();
		this.state = new Stack<States>();
		this.state.push(States.top);

		// create the object.
		this.uefCharHandler2 = new UEFCharHandler();
		this.state2 = new Stack<States>();
		this.state2.push(States.top);

		// find our test file.
		URL url = this.getClass().getResource("./uefCommandTestFile.tex");

		File file = new File(url.getFile().replace("%20", " "));
		this.uefCharHandler.openFile(file);
		this.uefCommand = new UEFCommand(this.uefCharHandler, state);

		// find our test file.
		URL url2 = this.getClass().getResource("./uefCommandTestFile2.tex");

		File file2 = new File(url2.getFile().replace("%20", " "));
		this.uefCharHandler2.openFile(file2);
		this.uefCommand2 = new UEFCommand(this.uefCharHandler2, state2);
	}

	/**
	 * Test the process() method. Makes sure CommandTypes are returned correctly
	 * and the position in the file is updated correctly.
	 */
	@Test
	public void processTest() {
		CommandTypes command = this.uefCommand.process();
		assertEquals(CommandTypes.none, command);
		assertEquals(2, uefCharHandler.getPosition());// check that position was
		// set correctly.

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.beginProblem);
		assertEquals(20, uefCharHandler.getPosition());// check that position
		// was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.endProblem);
		assertEquals(36, uefCharHandler.getPosition());// check that position
		// was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.beginAnswers);
		assertEquals(57, uefCharHandler.getPosition());// check that position
		// was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.endAnswers);
		assertEquals(75, uefCharHandler.getPosition());// check that position
		// was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.answer);
		assertEquals(83, uefCharHandler.getPosition());// check that position
		// was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.documentclass);
		assertEquals(98, uefCharHandler.getPosition());// check that position
		// was set correctly
	}

	/**
	 * Test the getArguments() method. Makes sure arguments are returned
	 * correctly and the position in the file is updated correctly after each
	 * call.
	 */
	@Test
	public void getArgumentsTest() {
		uefCharHandler.setPosition(98);
		String[] arguments = this.uefCommand.getArguments(1);
		assertEquals("argument1", arguments[0]);
		assertEquals(110, uefCharHandler.getPosition());

		arguments = this.uefCommand.getArguments(2);
		assertEquals("argument2", arguments[0]);
		assertEquals("argument3", arguments[1]);
		assertEquals(137, uefCharHandler.getPosition());

		arguments = this.uefCommand.getArguments(3);
		assertEquals("argument 4", arguments[0]);
		assertEquals("argument5", arguments[1]);
		assertEquals("argument6", arguments[2]);
		assertEquals(185, uefCharHandler.getPosition());

	}

	/**
	 * Test the getOptionalArgument() method. Makes sure the argument is
	 * returned correctly and the position in the file is updated correctly
	 * after each call.
	 */
	@Test
	public void getOptionalArgumentTest() {
		// should return null, because it will hit an argument before an
		// optional argument.
		// Should also reset the position of UEFCharHandler.
		String argument = this.uefCommand.getOptionalArgument();
		assertEquals(null, argument);
		assertEquals(0, this.uefCharHandler.getPosition());

		// testing with actual optional arguments now.
		this.uefCharHandler.setPosition(185);
		argument = this.uefCommand.getOptionalArgument();
		assertEquals("oargument 1", argument);
		assertEquals(199, this.uefCharHandler.getPosition());

		argument = this.uefCommand.getOptionalArgument();
		assertEquals("oargument2", argument);
		assertEquals(211, this.uefCharHandler.getPosition());

		argument = this.uefCommand.getOptionalArgument();
		assertEquals("oargument3", argument);
		assertEquals(224, this.uefCharHandler.getPosition());
	}

	/**
	 * Test the peakUntil() method.
	 */
	@Test
	public void peekUntilTest() {
		this.uefCharHandler.setPosition(224);
		String buffer = this.uefCommand.peekUntil();
		assertEquals(
				"\n\n  some text that should be peeked at. Peekaboo!\nAnother line that should be peaked at.\n ",
				buffer);

	}

	/**
	 * Test the processOptionalArguments() method.
	 */
	@Test
	public void processOptionalArgumentsTest() {
		String arguments[] = this.uefCommand
				.processOptionalArguments(" test, test2 ,test3 ");
		assertEquals("test", arguments[0]);
		assertEquals("test2", arguments[1]);
		assertEquals("test3", arguments[2]);
	}

	/**
	 * Test processDocumentClass TODO: More testing is needed once the method
	 * implementation is completed
	 */
	@Test
	public void processDocumentClassTest() {
		UEFCommand.CommandTypes command;
		command = this.uefCommand2.process();
		// After processing the document class we get back to state none
		assertEquals(CommandTypes.none, command);
	}

	/**
	 * Test begging problem
	 */
	@Test
	public void processBeginProblemTest() {
		UEFCommand.CommandTypes command;
		command = this.uefCommand2.process();
		command = this.uefCommand2.process();
		// After processing begin problem we should be in state beginproblem
		assertEquals(CommandTypes.beginProblem, command);

		ProblemIF problem = this.uefCommand2.processBeginProblem();
		assertNotNull(problem);
	}

	/**
	 * Test Ending problem
	 */
	@Test
	public void processEndProblemTest() {
		UEFCommand.CommandTypes command;
		command = this.uefCommand2.process();
		command = this.uefCommand2.process();
		command = this.uefCommand2.process();
		// After processing begin problem we should be in state beginproblem
		assertEquals(CommandTypes.endProblem, command);
	}

	/**
	 * Clean up our used resources after a test
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.uefCharHandler = null;
		this.state = null;
		this.uefCommand = null;
	}
}
