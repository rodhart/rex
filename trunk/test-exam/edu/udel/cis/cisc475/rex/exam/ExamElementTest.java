/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Test Suite for testing Exam Element.
 * Apr 9, 2010
 * 
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.impl.ExamElement;


/**
 * @author fxfitz
 *
 */
public class ExamElementTest {
	
	static ExamElement ee = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ee = new ExamElement("physics");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExamElement(){
		// Check to make sure it was created.
		assertNotNull(ee);
		
		// Make sure it was set to physics
		assertEquals("physics", ee.label());
	}
}
