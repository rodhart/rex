/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author hboyd
 *
 */
public class BlockTest {
	public final static boolean useStubs = true;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			
		}
		else{
			
		}
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
	@Ignore
	public void testGetTopic() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetSource() {
		fail("Not yet implemented");
	}
}