package edu.udel.cis.cisc475.rex.uefparser.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;

/**
 * Tests the uefPdflatex() method in UEFParser, i.e. running the UEF through pdflatex
 * 
 * @author Tim Armstrong
 */
public class UEFParserPdflatexTest
{
	/**
	 * Tests that a valid Latex UEF file will pass through pdflatex without any problems 
	 */
	@Test
	public void testParseValid() throws RexException, IOException
	{
		File uef = new File("test-integration" + File.separator + "armstron" + File.separator + "exam.tex");
		
		UEFParser parser = new UEFParser();		
		
		try
		{
			parser.parse(uef);
		}
		catch (RexParseException e)
		{
			assertTrue(false);
			return;
		}
		
		assertTrue(true);
	}
	
	/**
	 * Tests that a UEF file will fail that contains invalid Latex in terms of our custom commands and environments
	 */
	@Test
	public void testParseInvalidRex() throws RexException, IOException
	{
		UEFParser parser = new UEFParser();

		File file = new File("test-integration" + File.separator + "armstron" + File.separator + "examInvalidLatexRex.tex");
		
		try
		{
			parser.parse(file);
		}
		catch (RexParseException e)
		{
			if (e.getMessage().contains("is not valid Latex"))
			{
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}	

	/**
	 * Tests that a UEF file will fail that contains invalid Latex in terms of standard Latex
	 */
	@Test
	public void testParseInvalidUser() throws RexException, IOException
	{
		UEFParser parser = new UEFParser();

		File file = new File("test-integration" + File.separator + "armstron" + File.separator + "examInvalidLatexUser.tex");

		System.out.println(file.getName());
		
		try
		{
			parser.parse(file);
		}
		catch (RexParseException e)
		{
			if (e.getMessage().contains("is not valid Latex"))
			{
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}
}
