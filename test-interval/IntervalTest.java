import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;


public class IntervalTest {

	@Test
	public void testLow() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		boolean test = (I.low() == 5.0);
		Assert.assertTrue(test);
	}

	@Test
	public void testStrictLow() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(false, 5.0, true, 10.0);
		
		boolean test = (I.strictLow() == true) && (I2.strictLow() == false);
		Assert.assertTrue(test);
	}

	@Test
	public void testHigh() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(true, 5.0, true, 10.0);
		
		boolean test = (I.high() == 10.0);
		Assert.assertTrue(test);
	}

	@Test
	public void testStrictHigh() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		boolean test = (I.strictHigh() == true) && (I2.strictHigh() == false);
		Assert.assertTrue(test);
	}

	@Test
	public void testInterval() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		Assert.assertTrue(I.strictLow() == false);
		Assert.assertTrue(I.strictHigh() == true);
		Assert.assertTrue(I.low() == 5.0);
		Assert.assertTrue(I.high() == 10.0);
	}

	@Test
	public void testContains() {
		IntervalFactoryIF intervalFactory = new IntervalFactory();
		IntervalIF I = intervalFactory.interval(false, 5.0, true, 10.0);
		
		Assert.assertTrue(I.contains(5.0));
		Assert.assertTrue(I.contains(8.0));
		Assert.assertFalse(I.contains(10.0));
		
		IntervalIF I2 = intervalFactory.interval(true, 5.0, false, 10.0);
		
		Assert.assertFalse(I2.contains(5.0));
		Assert.assertTrue(I2.contains(8.0));
		Assert.assertTrue(I2.contains(10.0));
	}

}
