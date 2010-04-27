package edu.udel.cis.cisc475.rex.random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * Junit Test for RandomizerFactory
 * 
 * @author iburns
 *
 */
public class RandomizerFactoryTest {

	RandomizerFactory RandomizerFactory;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		RandomizerFactory = new RandomizerFactory();
		
	}

	/**
	 * Test method for {@link edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory#RandomizerFactory()}.
	 */
	@Test
	public void testRandomizerFactory() {
		
		RandomizerFactory rf =  RandomizerFactory;
		
		assertNotNull(rf);
	}
	
	@Test
	public void testCompareRandomizerFactory() {
		
		RandomizerFactory rf1 =  RandomizerFactory;
		
		RandomizerFactory rf2 =  RandomizerFactory;
		
		boolean result = rf1.equals(rf2);
		
		assertTrue(result);
	}

	/**
	 * Test method for {@link edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory#newRandomizer(long)}.
	 */
	@Test
	public void testNewRandomizer() {
		
		RandomizerFactory rf =  RandomizerFactory;
		
		RandomizerIF rfIF = rf.newRandomizer(0);
		
		assertNotNull(rfIF);
		
	}

	@Test
	public void testCompareNewRandomizer() {
		
		RandomizerFactory rf =  RandomizerFactory;
		
		RandomizerIF rfIF1 = rf.newRandomizer(0);
		
		RandomizerIF rfIF2 = rf.newRandomizer(0);
		
		boolean result = rfIF1.equals(rfIF2);
		
		assertTrue(result);
	}
		
}
