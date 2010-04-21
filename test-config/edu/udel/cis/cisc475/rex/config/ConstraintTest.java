package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.Constraint;
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
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		ConstraintIF Constraint = new Constraint(S);
		
		SourceIF F	= Constraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt");
	
	}
	
	/*
	 * Second Test: Tests that source() returns the correct filename from Constraint.
	 */
	@Test
	public void testSource2() {
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		ConstraintIF Constraint = new Constraint(S);
		
		SourceIF F	= Constraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt");
	
	}
	
}