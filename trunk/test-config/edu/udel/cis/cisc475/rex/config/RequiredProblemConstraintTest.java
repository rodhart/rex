package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.RequiredProblemConstraint;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
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
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("testing", 10, S);
		
		assertEquals(RequiredProblemConstraint.label(), "testing");
	
	}
	
	/**
	 * Second Test: Tests that label() returns the correct string from RequiredProblem.
	 */
	@Test
	public void testLabel2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("32423testing000", 10, S);
		
		assertEquals(RequiredProblemConstraint.label(), "32423testing000");
	
	}
	
	/**
	 * Tests that points() returns the correct integer from RequiredProblem.
	 */
	@Test
	public void testPoints() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("32423testing000", 10, S);
		
		assertEquals(RequiredProblemConstraint.points(), 10);
	
	}
	
	/**
	 * Second Test: Tests that points() returns the correct integer from RequiredProblem.
	 */
	@Test
	public void testPoints2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("32423testing000", 999310, S);
		
		assertEquals(RequiredProblemConstraint.points(), 999310);
	
	}
	
	/**
	 * Tests that source() returns the correct filename from RequiredProblem.
	 */
	@Test
	public void testSource() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("32423testing000", 999310, S);
		
		SourceIF F	= RequiredProblemConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt");
	
	}
	
	/**
	 * Second Test: Tests that source() returns the correct filename from RequiredProblem.
	 */
	@Test
	public void testSource2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		RequiredProblemConstraintIF RequiredProblemConstraint = new RequiredProblemConstraint("32423testing000", 999310, S);
		
		SourceIF F	= RequiredProblemConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt");
	
	}
	
	/**
	 * Tests that the factory method throws the correct exceptions given
	 * the desired incorrect input.
	 * @throws RexUnsatisfiableException
	 */
	@Test
	public void testExceptions() {
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		
		try{
			new RequiredProblemConstraint(null, 999310, S);
		}
		catch(Exception e){
			assertEquals("argument 'label' cannot be null", e.getMessage());
		}
		
		try{
			new RequiredProblemConstraint("32423testing000", 999310, null);
		}
		catch(Exception e){
			assertEquals("argument 'source' cannot be null", e.getMessage());
		}
		
	}
}