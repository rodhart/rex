/**
 * Test Suite of UEFParserFactory
 */
package edu.udel.cis.cisc475.rex.uefparser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParserFactory;

/**
 * Testing for UEFParserFactory
 * 
 * @author Ahmed El-Hassany
 * 
 */
public class UEFParserFactoryTest {

	/**
	 * Instance of UEFParserFactory to be tested
	 */
	private UEFParserFactoryIF uefFactory;

	/**
	 * Create an object of UEFParserFactory for testing
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.uefFactory = new UEFParserFactory();
	}

	/**
	 * Testing creating UEFPaser using the factory method
	 */
	@Test
	public void set() {
		UEFParserIF p = this.uefFactory.newUEFParser();
		assertTrue(p instanceof UEFParserIF);
	}

	/**
	 * making sure the UEFParserFactory is null
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.uefFactory = null;
	}

}
