package edu.udel.cis.cisc475.rex.config;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/*
 * @author Jesse Gledhill
 */


public class GroupConstraintTest {
	
	/*
	 * Tests that difficultyInterval() returns the correct interval from groupConstraint.
	 */
	@Test
	public void testDifficultyInterval() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		IntervalIF J = GroupConstraint.difficultyInterval();
		
		assertTrue(J.strictLow() == false);
		assertTrue(J.strictHigh() == true);
		assertTrue(J.low() == 5.0);
		assertTrue(J.high() == 10.0);
	}
	
	/*
	 * Second Test: Tests that difficultyInterval() returns the correct interval from groupConstraint.
	 */
	@Test
	public void testDifficultyInterval2() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(true, 50.5, false, 100.2);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		IntervalIF J = GroupConstraint.difficultyInterval();
		
		assertTrue(J.strictLow() == true);
		assertTrue(J.strictHigh() == false);
		assertTrue(J.low() == 50.5);
		assertTrue(J.high() == 100.2);
	}
	
	/*
	 * Tests that numProblems() returns the correct integer from groupConstraint.
	 */
	@Test
	public void testNumProblems() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		assertEquals(GroupConstraint.numProblems(), 10);
	
	}
	
	/*
	 * Second Test: Tests that numProblems() returns the correct integer from groupConstraint.
	 */
	@Test
	public void testNumProblems2() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 5555, 20, "testing", S);
		
		assertEquals(GroupConstraint.numProblems(), 5555);
	
	}
	
	/*
	 * Tests that source() returns the correct source from groupConstraint.
	 */
	@Test
	public void testSource() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		SourceIF F = GroupConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt");
	
	}
	
	/*
	 * Second Test: Tests that source() returns the correct source from groupConstraint.
	 */
	@Test
	public void testSource2() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		SourceIF F = GroupConstraint.source();
		
		assertEquals(F.filename(), "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText2.txt");
	
	}
	
	/*
	 * Tests that points() returns the correct integer from groupConstraint.
	 */
	@Test
	public void testPoints() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		assertEquals(GroupConstraint.points(), 20);
	
	}
	
	/*
	 * Second Test: Tests that points() returns the correct integer from groupConstraint.
	 */
	@Test
	public void testPoints2() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20222, "testing", S);
		
		assertEquals(GroupConstraint.points(), 20222);
	
	}
	
	/*
	 * Tests that topic() returns the correct string from groupConstraint.
	 */
	@Test
	public void testTopic() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "testing", S);
		
		assertEquals(GroupConstraint.topic(), "testing");
	
	}
	
	/*
	 * Second Test: Tests that topic() returns the correct string from groupConstraint.
	 */
	@Test
	public void testTopic2() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		ConfigFactoryIF configFactory = new ConfigFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		GroupConstraintIF GroupConstraint = configFactory.newGroupConstraint(I, 10, 20, "-8test33ing2", S);
		
		assertEquals(GroupConstraint.topic(), "-8test33ing2");
	
	}
	
	/**
	 * Tests that the factory method throws the correct exceptions given
	 * the desired incorrect input.
	 * @throws RexUnsatisfiableException
	 */
	@Test
	public void testExceptions() throws RexUnsatisfiableException {
		String filename = "./trunk/test-config/edu/udel/cis/cisc475/rex/config/ExampleText.txt";
		
		SourceFactoryIF sourceFactory = new SourceFactory();
		SourceIF S = sourceFactory.newSource(filename);
		
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		ConfigFactoryIF configFactory = new ConfigFactory();
		try{
			configFactory.newGroupConstraint(null, 10, 20, "-8test33ing2", S);
		}
		catch(Exception e){
			assertEquals("argument 'difficulty' cannot be null", e.getMessage());
		}
		
		try{
			configFactory.newGroupConstraint(I, 10, 20, null, S);
		}
		catch(Exception e){
			assertEquals("argument 'topic' cannot be null", e.getMessage());
		}
		
		try{
			configFactory.newGroupConstraint(I, 10, 20, "-8test33ing2", null);
		}
		catch(Exception e){
			assertEquals("argument 'source' cannot be null", e.getMessage());
		}
	}
}
