package edu.udel.cis.cisc475.rex.random.impl;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**	
 * This class implements RandomizerFactory and is used to create a new instance of Randomizer.
 * 
 *   @author cardona
 * 
 */
public class RandomizerFactory implements RandomizerFactoryIF {

	/**
	 * Empty constructor
	 */
	public 	RandomizerFactory() {
	}//end of constructor

	/**
	 * this method is a factory which creates a new instance of 
	 * a random object chooser
	 * 
	 * @param seed used to create the random numbers 
	 * @return a pointer to an instance of a random object chooser
	 */ 
	public RandomizerIF newRandomizer(long seed){
		return new Randomizer (seed);	
	}

}//END OF CLASS
