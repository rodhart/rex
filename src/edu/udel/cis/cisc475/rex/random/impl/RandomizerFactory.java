package edu.udel.cis.cisc475.rex.random.impl;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**
 * 
 * @author team3
 * We added this stub so that our methods would work.
 *
 */

public class RandomizerFactory implements RandomizerFactoryIF {


	public RandomizerIF newRandomizer(long seed) {
		return new Randomizer(seed);
	}
	
}
