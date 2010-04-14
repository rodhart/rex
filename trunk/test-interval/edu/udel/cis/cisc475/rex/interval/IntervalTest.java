package edu.udel.cis.cisc475.rex.interval;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;

/**
 * 
 * @author aplatt
 *
 */

public class IntervalTest {

	/*
	 * Tests that low() returns the correct low value
	 */
	@Test
	public void testLow() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		boolean test = (I.low() == 5.0);
		assertTrue(test);
	}

	/*
	 * Tests that strictLow() returns the correct strictLow value
	 */
	@Test
	public void testStrictLow() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(false, 5.0, true, 10.0);
		
		boolean test = (I.strictLow() == true) && (I2.strictLow() == false);
		assertTrue(test);
	}

	/*
	 * Tests that high() returns the correct high value
	 */
	@Test
	public void testHigh() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		boolean test = (I.high() == 10.0);
		assertTrue(test);
	}

	/*
	 * Tests that strictHigh() returns the correct strictHigh value
	 */
	@Test
	public void testStrictHigh() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		boolean test = (I.strictHigh() == true) && (I2.strictHigh() == false);
		assertTrue(test);
	}

	/*
	 * Tests that the interval constructor sets the correct values
	 */
	@Test
	public void testInterval() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		assertTrue(I.strictLow() == false);
		assertTrue(I.strictHigh() == true);
		assertTrue(I.low() == 5.0);
		assertTrue(I.high() == 10.0);
	}

	/*
	 * Tests that contains() returns whether the value is inside the interval
	 * depending on the constraints set by the constructor.
	 */
	@Test
	public void testContains() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		assertFalse(I.contains(2.0));
		assertTrue(I.contains(5.0));
		assertTrue(I.contains(8.0));
		assertFalse(I.contains(10.0));
		
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		assertFalse(I.contains(11.0));
		assertFalse(I2.contains(5.0));
		assertTrue(I2.contains(8.0));
		assertTrue(I2.contains(10.0));
	}


}
