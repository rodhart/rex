/**
 * Test Suite of UEFCharHandler
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.EOFException;
import org.junit.Test;
import java.io.File;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

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

		// find our test file.
		URL url = this.getClass().getResource("./uefCharHandlerTestFile.txt");

		File file = new File(url.getFile().replace("%20", " "));
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
