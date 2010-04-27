

package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author Jesse Gledhill
 * added a test for addGroupConstraint to test a nullpointer exception -cates
 */


public class ConfigTest {
  
  ConfigFactoryIF configFactory;
  IntervalFactoryIF intervalFactory;
  SourceFactoryIF sourceFactory;
  
  @Before
  public void setUp() {
    configFactory = new ConfigFactory();
    intervalFactory = new IntervalFactory();
    sourceFactory = new SourceFactory();
  }
  
	/**
	 * Tests that pdfOption() returns the correct boolean when pdfOption is true
	 */
	@Test
	public void testPdfOptionTrue() {
		ConfigIF C = configFactory.newConfig(true, 5);
		
		assertTrue(C.pdfOption());
	}

	/**
	 * Tests that pdfOption() returns the correct boolean when pdfOption is false
	 */
	@Test
	public void testPdfOptionFalse() {
		ConfigIF C = configFactory.newConfig(false, 10);
		
		assertFalse(C.pdfOption());
	}
	
	/**
	 * Tests that numVersions() returns the correct integer when the number of versions is a single
	 * digit number.
	 */
	@Test
	public void testNumVersionsSingleD() {
		ConfigIF C = configFactory.newConfig(false, 1);
		
		assertEquals(1, C.numVersions());
	}
	
	/**
	 * Tests that numVersions() returns the correct integer when the number of versions is a 
	 * negative number.
	 */
	@Test
	public void testNumVersionsNegative() {
		ConfigIF C = configFactory.newConfig(false, -123);
		
		assertEquals(-123, C.numVersions());
	}
	
	/**
	 * Tests that numVersions() returns the correct integer when the number of versions is a 
	 * 4 digit number.
	 */
	@Test
	public void testNumVersionsFourDigit() {
		ConfigIF C = configFactory.newConfig(false, 9999);

		assertEquals(9999, C.numVersions());
	}
	
	/**
	 * Tests the addition of group constraints 
	 * @throws RexUnsatisfiableException 
	 * 
	 */
	
	public void testAddGroupConstraint() throws RexUnsatisfiableException {
		ConfigIF C = configFactory.newConfig(false, 9999);
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		SourceIF S = sourceFactory.newSource("test");
		
		GroupConstraintIF constraint = C.addGroupConstraint("topic", I, 5, 5, S);
		
		Collection<ConstraintIF> Cs = C.constraints();
		assertEquals(constraint, Cs.toArray()[0]);
	}
	
	/**
	 * Tests the addition of problem constraints 
	 * 
	 */
	@Test
	public void testAddProblemConstraint() {
		ConfigIF C = configFactory.newConfig(false, 9999);
		SourceIF S = sourceFactory.newSource("test");
		
		RequiredProblemConstraintIF constraint = C.addRequiredProblemConstraint("problem1", 5, S);
		
		Collection<ConstraintIF> Cs = C.constraints();
		
		assertEquals(constraint, Cs.toArray()[0]);
	}
	
	/**
	 * tests the exam seed setting and getting 
	 */
	@Test
	public void testSeed() {
		ConfigIF C = configFactory.newConfig(false, -123);
		C.setSeed(1290);
		assertEquals(1290, C.seed());
	}
	
	/**
	 * tests the exam final block setting and getting 
	 */
	@Test
	public void testFinalBlock() {
		ConfigIF C = configFactory.newConfig(false, -123);
		C.setFinalBlock("This is my final block");
		assertEquals("This is my final block", C.finalBlock());
	}
	
	/**
	 * tests the exam version strings setting and getting 
	 */
	@Test
	public void testVersionStrings() {
		ConfigIF C = configFactory.newConfig(false, -123);
		String versions[] = {"test1", "test2", "test3"};
		C.setVersionStrings(versions);
		Assert.assertArrayEquals(versions, C.versionStrings());
	}
	
}
