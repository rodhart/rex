/**
 * Test Suite of UEFCmmand
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParser.States;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.CommandTypes;

import org.junit.Test;
import java.io.File;
import java.net.URL;
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

/**
 * Testing for UEFCommand
 *
 * @author Ahmed El-Hassany
 *
 * @author Aaron Myles Landwehr
 *
 */
public class UEFCommandTest
{

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
	 * Initialize the pieces we need to setup testing and open the file
	 * we need help with our testing.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		//create the object.
		this.uefCharHandler = new UEFCharHandler();
		this.state = new Stack<States>();
		this.state.push(States.top);

		//find our test file.
		URL url = this.getClass().getResource("./uefCommandTestFile.tex");

		File file = new File(url.getFile().replace("%20", " "));
		this.uefCharHandler.openFile(file);
		this.uefCommand = new UEFCommand(this.uefCharHandler, state);
	}

	/**
	 * Test the process() method. Makes sure CommandTypes are returned correctly and the
	 * position in the file is updated correctly.
	 */
	@Test
	public void processTest()
	{
		CommandTypes command = this.uefCommand.process();
		assertEquals(CommandTypes.none, command);
		assertEquals(2, uefCharHandler.getPosition());//check that position was set correctly.

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.beginProblem);
		assertEquals(20, uefCharHandler.getPosition());//check that position was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.endProblem);
		assertEquals(36, uefCharHandler.getPosition());//check that position was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.beginAnswers);
		assertEquals(57, uefCharHandler.getPosition());//check that position was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.endAnswers);
		assertEquals(75, uefCharHandler.getPosition());//check that position was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.answer);
		assertEquals(83, uefCharHandler.getPosition());//check that position was set correctly

		command = this.uefCommand.process();
		assertEquals(command, CommandTypes.documentclass);
		assertEquals(98, uefCharHandler.getPosition());//check that position was set correctly
	}

	/**
	 * Clean up our used resources after a test
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		this.uefCharHandler = null;
		this.state = null;
		this.uefCommand = null;
	}
}
