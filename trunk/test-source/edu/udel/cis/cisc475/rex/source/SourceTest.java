package edu.udel.cis.cisc475.rex.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * @author jsong
 *
 */
public class SourceTest {

	public static String newline = System.getProperty("line.separator");

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
		
		assertEquals(S.lastLine(), 0);
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

		String filename = "some name";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.addText("mvemjsnup" + newline);
		S.addText("JUnit is kinda painful" + newline);
		
		
		String testString = "mvemjsnup" + newline + "JUnit is kinda painful" + newline;
		
		assertEquals(testString, S.text());
	}	
	
	@Test
	public void testWrite() throws IOException {
		String filename = "./trunk/test-source/edu/udel/cis/cisc475/rex/source/test.txt";
		
		SourceFactoryIF myFactory = new SourceFactory();
		SourceIF S = myFactory.newSource(filename);
		
		S.addText("This is my first line of text" + newline);
		S.addText("Second line of text this is" + newline);
		
		PrintWriter pw = null;
		pw = new PrintWriter(new FileWriter(filename));
		
		S.write(pw);
		pw.flush();
		
		StringBuffer contents = new StringBuffer();
		
		File aFile = new File(filename);
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			String line = null;
			
			try {
				while((line = input.readLine()) != null){
					contents.append(line);
					contents.append(newline);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// There is an extra newline appended by reading in until null 
		// Line 1 and 2 are read and a newline is appended after each
		// Line 3 is just a newline itself and the above loop appends a newline
		// This is why an extra newline is added at the end of testString
		
		String testString = "This is my first line of text" + newline + "Second line of text this is" + newline + newline;
		assertTrue(testString.equals(contents.toString()));
	}
	
	@Test
	public void testAddNullText() {
		String filename = "blah";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.addText(null);
		
		boolean result = S.text().equals("null");
		
		assertTrue(result);
	}
	
	@Test
	public void testAddNullTextToExistingText() {
		String filename = "whatever name";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		S.addText("Line of text\n");
		S.addText("Nother line of text\n\n");
		
		S.addText(null);
		
		String testString = "Line of text\n" + "Nother line of text\n\n" + "null";
		
		System.out.print(S.text());
		boolean result = S.text().equals(testString);
		assertTrue(result);
	}
	
	@Test
	public void testAddLotsOfNewlines() {
		String filename = "some name";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		for(int i = 0; i < 5000; i++)
			S.addText(newline);
		
		String testString = "";
		
		for(int i = 0; i < 5000; i++)
			testString += newline;
		
		boolean result = S.text().equals(testString);
		
		assertTrue(result);
	}
	
	@Test
	public void testAddThreeMillionChars() {
		String filename = "moo";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		StringBuffer testString = new StringBuffer();
		
		for(int i = 0; i < 3000000; i++) {
			S.addText("t");
			testString.append("t");
		}
		
		boolean result = S.text().equals(testString.toString());
		
		assertTrue(result);
	}
	
	@Test
	public void testAdditionalConstructor() {
		String filename = "moo";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename, 1, 2, 3, 4);
		
		assertEquals(S.filename(), filename);
		assertEquals(S.startLine(), 1);
		assertEquals(S.startColumn(), 2);
		assertEquals(S.lastLine(), 3);
		assertEquals(S.lastColumn(), 4);
	}
	
	
	
	
	
	@Test(expected=IOException.class)
	public void testNullPrintWriter() throws IOException {
		String filename = "moo";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		PrintWriter pw = null;
		
		S.addText("meow" + newline);
		
		S.write(pw);
	}
}
