/**
 * Test Suite of UEFCharHandler
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.EOFException;
import org.junit.Test;
import java.io.File;
import java.util.regex.Matcher;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Testing for UEFCharHandler
 * 
 * @author Ahmed El-Hassany
 * 
 * @author Aaron Myles Landwehr
 * 
 */
public class UEFCharHandlerTest
{

	/**
	 * Instance of UEFCharHandler to be tested.
	 */
	private UEFCharHandler uefCharHandler;

	/**
	 * Create an object of UEFCharHandler for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		// create the object.
		this.uefCharHandler = new UEFCharHandler();

		File file = new File("." + File.separator + "tests" + File.separator + "uefCharHandlerTestFile.txt");
		this.uefCharHandler.openFile(file);
	}

	/**
	 * Test for the read() method.
	 */
	@Test
	public void readTest() throws EOFException
	{
		char ch = this.uefCharHandler.read();
		assertEquals('L', ch);

		ch = this.uefCharHandler.read(5);
		assertEquals('0', ch);

		ch = this.uefCharHandler.read(7);
		assertEquals('\n', ch);

		ch = this.uefCharHandler.read(8);
		assertEquals('L', ch);

		//out of bound check
		try
		{
			this.uefCharHandler.setPosition(500);
			this.uefCharHandler.read();
		}
		catch (EOFException e)
		{
			assertTrue(true);
		}
	}

	/**
	 * Test for the move() method.
	 */
	@Test
	public void moveTest() throws EOFException
	{
		char ch = this.uefCharHandler.read();

		// move once
		this.uefCharHandler.move();
		ch = this.uefCharHandler.read();
		assertEquals('i', ch);

		// move twice
		this.uefCharHandler.move();
		ch = this.uefCharHandler.read();
		assertEquals('n', ch);

		// move thrice
		this.uefCharHandler.move();
		ch = this.uefCharHandler.read();
		assertEquals('e', ch);
	}

	/**
	 * Test for the getPosition() method.
	 */
	@Test
	public void getPositionTest() throws EOFException
	{
		int position = this.uefCharHandler.getPosition();
		assertEquals(0, position);

		// move once
		this.uefCharHandler.move();
		position = this.uefCharHandler.getPosition();
		assertEquals(1, position);

		// move twice
		this.uefCharHandler.move();
		position = this.uefCharHandler.getPosition();
		assertEquals(2, position);

		// move thrice
		this.uefCharHandler.move();
		position = this.uefCharHandler.getPosition();
		assertEquals(3, position);
	}

	/**
	 * Test for the setPosition() method.
	 */
	@Test
	public void setPositionTest()
	{
		int pos;
		this.uefCharHandler.setPosition(0);
		pos = this.uefCharHandler.getPosition();
		assertEquals(0, pos);

		this.uefCharHandler.setPosition(1);
		pos = this.uefCharHandler.getPosition();
		assertEquals(1, pos);

		this.uefCharHandler.setPosition(4);
		pos = this.uefCharHandler.getPosition();
		assertEquals(4, pos);

		this.uefCharHandler.setPosition(3);
		pos = this.uefCharHandler.getPosition();
		assertEquals(3, pos);
	}

	/**
	 * Test for the getContent() method.
	 */
	@Test
	public void getContentTest()
	{
		String buffer = this.uefCharHandler.getContent(0, 7);
		assertEquals("Line 0.", buffer);

		buffer = this.uefCharHandler.getContent(8, 15);
		assertEquals("Line 1.", buffer);

		buffer = this.uefCharHandler.getContent(16, 23);
		assertEquals("Line 2.", buffer);
	}

	@Test
	public void getLineNumberTest() throws EOFException
	{
		int lineNumber = this.uefCharHandler.getLineNumber();
		assertEquals(1, lineNumber);

		this.uefCharHandler.setPosition(7);
		lineNumber = this.uefCharHandler.getLineNumber();
		assertEquals(1, lineNumber);

		this.uefCharHandler.setPosition(8);
		lineNumber = this.uefCharHandler.getLineNumber();
		assertEquals(2, lineNumber);

		lineNumber = this.uefCharHandler.getLineNumber(23);
		assertEquals(3, lineNumber);

		//out of bound check
		try
		{
			this.uefCharHandler.setPosition(500);
			this.uefCharHandler.getLineNumber();
		}
		catch (EOFException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void getColumnNumberTest() throws EOFException
	{
		int columnNumber = this.uefCharHandler.getColumnNumber();
		assertEquals(1, columnNumber);

		this.uefCharHandler.setPosition(7);
		columnNumber = this.uefCharHandler.getColumnNumber();
		assertEquals(8, columnNumber);

		this.uefCharHandler.setPosition(8);
		columnNumber = this.uefCharHandler.getColumnNumber();
		assertEquals(1, columnNumber);

		columnNumber = this.uefCharHandler.getColumnNumber(22);
		assertEquals(7, columnNumber);

		//out of bound check
		try
		{
			this.uefCharHandler.setPosition(500);
			this.uefCharHandler.getColumnNumber();
		}
		catch (EOFException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void isLineBreakTest() throws EOFException
	{
		this.uefCharHandler.setPosition(7);
		assertEquals(true, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(8);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(9);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(10);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(11);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(12);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(13);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(14);
		assertEquals(false, this.uefCharHandler.isLineBreak());
		this.uefCharHandler.setPosition(15);
		assertEquals(true, this.uefCharHandler.isLineBreak());

		//out of bound check
		try
		{
			this.uefCharHandler.setPosition(500);
			this.uefCharHandler.isLineBreak();
		}
		catch (EOFException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void isWhiteSpaceTest() throws EOFException
	{
		this.uefCharHandler.setPosition(0);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(1);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(2);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(3);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(4);
		assertEquals(true, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(5);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(6);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(7);
		assertEquals(true, this.uefCharHandler.isWhiteSpace());
		this.uefCharHandler.setPosition(8);
		assertEquals(false, this.uefCharHandler.isWhiteSpace());

		//out of bound check
		try
		{
			this.uefCharHandler.setPosition(500);
			this.uefCharHandler.isWhiteSpace();
		}
		catch (EOFException e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void regexTest()
	{
		Matcher matcher = this.uefCharHandler.regex("Line 0");
		assertEquals(0, matcher.start());
		assertEquals(6, matcher.end());

		matcher = this.uefCharHandler.regex("ne 1");
		assertEquals(10, matcher.start());
		assertEquals(14, matcher.end());

		matcher = this.uefCharHandler.regex("NOT IN FILE");
		assertNull(matcher);
	}

	/**
	 * making sure the UEFCharHandler is null
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		this.uefCharHandler = null;
	}
}
