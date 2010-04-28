package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/*
 * @author Jesse Gledhill
 */


public class ConstraintTest {
	
	/*
	 * Tests that source() returns the correct filename from Constraint.
	 */
	@Test
	public void testSource() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		ConstraintIF Constraint = configFactory.newConstraint(S);
		
		SourceIF F	= Constraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt");
	
	}
	
	/*
	 * Second Test: Tests that source() returns the correct filename from Constraint.
	 */
	@Test
	public void testSource2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt";
		ConfigFactoryIF configFactory = new ConfigFactory();
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		ConstraintIF Constraint = configFactory.newConstraint(S);
		
		SourceIF F	= Constraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt");
	
	}
	
	/**
	 * Tests that the factory method throws the correct exceptions given
	 * the desired incorrect input.
	 * @throws RexUnsatisfiableException
	 */
	@Test
	public void testExceptions() {
		ConfigFactoryIF configFactory = new ConfigFactory();
		try{
			configFactory.newConstraint(null);
		}
		catch(Exception e){
			assertEquals("argument 'label' cannot be null", e.getMessage());
		}
	}
}