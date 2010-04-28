package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author Jesse Gledhill
 */


public class RequiredProblemConstraintTest {
	
	/**
	 * Tests that label() returns the correct string from RequiredProblem.
	 */
	@Test
	public void testLabel() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("testing", 10, S);
		
		assertEquals(RequiredProblemConstraint.label(), "testing");
	
	}
	
	/**
	 * Second Test: Tests that label() returns the correct string from RequiredProblem.
	 */
	@Test
	public void testLabel2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("32423testing000", 10, S);
		
		assertEquals(RequiredProblemConstraint.label(), "32423testing000");
	
	}
	
	/**
	 * Tests that points() returns the correct integer from RequiredProblem.
	 */
	@Test
	public void testPoints() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("32423testing000", 10, S);
		
		assertEquals(RequiredProblemConstraint.points(), 10);
	
	}
	
	/**
	 * Second Test: Tests that points() returns the correct integer from RequiredProblem.
	 */
	@Test
	public void testPoints2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("32423testing000", 999310, S);
		
		assertEquals(RequiredProblemConstraint.points(), 999310);
	
	}
	
	/**
	 * Tests that source() returns the correct filename from RequiredProblem.
	 */
	@Test
	public void testSource() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("32423testing000", 999310, S);
		
		SourceIF F	= RequiredProblemConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt");
	
	}
	
	/**
	 * Second Test: Tests that source() returns the correct filename from RequiredProblem.
	 */
	@Test
	public void testSource2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = configFactory.newRequiredProblemConstraint("32423testing000", 999310, S);
		
		SourceIF F	= RequiredProblemConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt");
	
	}
	
}