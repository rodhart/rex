package edu.udel.cis.cisc475.rex.key.impl;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public class AnswerKeyFactoryTest {

	AnswerKeyFactory AnswerKeyFactory;
	
	@Before
	public void setUp() throws Exception {
		
		AnswerKeyFactory = new AnswerKeyFactory();
		
	}

	
	
	@Test
	public void testAnswerKeyFactory() {
		
		AnswerKeyFactory akf = new AnswerKeyFactory();
		
		assertNotNull(akf);
	}
	
	
	
	@Test
	public void testCompareAnswerKeyFactory() {
		
		AnswerKeyFactory akf1 =  AnswerKeyFactory;
		
		AnswerKeyFactory akf2 =  AnswerKeyFactory;
		
		boolean result = akf1.equals(akf2);
		
		assertTrue(result);
	}

	
	
	@Test
	public void testNewAnswerKey() {
		
		AnswerKeyFactory akf =  AnswerKeyFactory;
		
		AnswerKeyIF akfIF = akf.newAnswerKey("version", "examName", "date");
		
		assertNotNull(akfIF);
	}



}//end of class
