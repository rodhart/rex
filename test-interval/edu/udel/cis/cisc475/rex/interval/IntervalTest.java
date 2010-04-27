package edu.udel.cis.cisc475.rex.interval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;

/**
 * Tests the interval class to ensure we get the desired 
 * results given different values.
 * 
 * @author Anthony Platt (aplatt)
 *
 */

public class IntervalTest {

	/**
	 * Tests that low() returns the correct low value
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testLow() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		assertTrue(I.low().equals(5.0));
	}

	/**
	 * Tests that strictLow() returns the correct strictLow value
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testStrictLow() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(false, 5.0, true, 10.0);
		
		assertEquals(true, I.strictLow());
		assertEquals(false, I2.strictLow());
	}

	/**
	 * Tests that high() returns the correct high value
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testHigh() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		assertTrue(I.high().equals(10.0));
	}

	/**
	 * Tests that strictHigh() returns the correct strictHigh value
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testStrictHigh() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		assertEquals(true, I.strictHigh());
		assertEquals(false, I2.strictHigh());
	}

	/**
	 * Tests that the interval constructor sets the correct values
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testInterval() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		assertEquals(false, I.strictLow());
		assertEquals(true, I.strictHigh());
		assertTrue(I.low().equals(5.0));
		assertTrue(I.high().equals(10.0));
	}

	/**
	 * Tests that contains() returns whether the value is inside the interval
	 * depending on the constraints set by the constructor.
	 * @throws RexUnsatisfiableException 
	 */
	@Test
	public void testContains() throws RexUnsatisfiableException {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		assertFalse(I.contains(2.0));
		assertTrue(I.contains(5.0));
		assertTrue(I.contains(8.0));
		assertFalse(I.contains(10.0));
		
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		assertFalse(I2.contains(11.0));
		assertFalse(I2.contains(5.0));
		assertTrue(I2.contains(8.0));
		assertTrue(I2.contains(10.0));
		
		IntervalIF I3 = intervalFactory.interval(true, 10.0, false, 5.0);
		
		assertFalse(I3.contains(11.0));
		assertFalse(I3.contains(5.0));
		assertTrue(I3.contains(8.0));
		assertTrue(I3.contains(10.0));
	}


}
