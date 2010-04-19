/**
 * 
 */
package edu.udel.cis.cisc475.rex.source;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author Jack
 *
 */
public class SourceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetFilename() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);

		assertEquals(S.filename(), "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
	}

	@Test
	public void testGetStartLine() {

		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		assertEquals(S.startLine(), 0);
	}
	
	@Test
	public void testSetStartLine() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.setStartLine(15);
		
		assertEquals(S.startLine(),15);
	}
	
	@Test
	public void testGetLastLine() {

		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		assertEquals(S.lastLine(), 7);
	}
	
	@Test
	public void testSetLastLine() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.setLastLine(4);
		
		assertEquals(S.lastLine(),4);
	}
	
	@Test
	public void testGetStartColumn() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		assertEquals(S.startColumn(), 0);
	}
	
	@Test
	public void testSetStartColumn() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.setStartColumn(12);
		
		assertEquals(S.startColumn(), 12);
	}
	
	@Test
	public void testGetLastColumn() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		assertEquals(S.lastColumn(), 0);
	}
	
	@Test
	public void testSetLastColumn() {

		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.setLastColumn(8);
		
		assertEquals(S.lastColumn(), 8);
	}
	
	@Test
	public void testGetText() {

		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
//		String testString1 = "This is a sample text file for testing the source.\nLine 2\n";
//		String testString2 = "Line 3\n123456789012345678901234567890\nLine 4\n";
//		String testString3 = "Testing some more\nLine 5\n1234567890";
//		String testString = testString1 + testString2 + testString3;

		String testString = "1234567890"; // text() only returns last line that was read
		
		assertEquals(testString, S.text());
	}
	
	@Test
	public void testStartlineLessThanZero() {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.setStartLine(-5);
		
	//	assertFalse(S.write(new PrintWriter(System.out,true)));
	}
	
	@Test(expected= FileNotFoundException.class)
	public void testFileDoesNotExist() {
		String filename = "sillyfile.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		
	}
}
