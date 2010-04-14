

package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.*;

import org.junit.Test;
import junit.framework.TestCase;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.config.impl.Config;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;

/*
 * @author Jesse Gledhill
 */


public class ConfigTest {
	/*
	 * Tests that pdfOption() returns the correct boolean when pdfOption is true
	 */
	@Test
	public void testPdfOptionTrue() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		ConfigIF C = configFactory.newConfig(true, 5);
		
		assertTrue(C.pdfOption());
	}

	/*
	 * Tests that pdfOption() returns the correct boolean when pdfOption is false
	 */
	@Test
	public void testPdfOptionFalse() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		ConfigIF C = configFactory.newConfig(false, 10);
		
		assertFalse(C.pdfOption());
	}
	
	/*
	 * Tests that numVersions() returns the correct integer when the number of versions is a single
	 * digit number.
	 */
	@Test
	public void testNumVersionsSingleD() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		ConfigIF C = configFactory.newConfig(false, 1);
		
		assertEquals(C.numVersions(), 1);
	}
	
	/*
	 * Tests that numVersions() returns the correct integer when the number of versions is a 
	 * negative number.
	 */
	@Test
	public void testNumVersionsNegative() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		ConfigIF C = configFactory.newConfig(false, -123);
		
		assertEquals(C.numVersions(), -123);
	}
	
	/*
	 * Tests that numVersions() returns the correct integer when the number of versions is a 
	 * 4 digit number.
	 */
	@Test
	public void testNumVersionsFourDigit() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		ConfigIF C = configFactory.newConfig(false, 9999);
		
		assertEquals(C.numVersions(), 9999);
	}
	
	
	
}
