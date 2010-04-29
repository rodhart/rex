package edu.udel.cis.cisc475.rex.random.IF;

/**	
 * Factory class for creating an instance of RandomizerIF.
 * 
 *   @author cardona
 *   @author jsong
 * 
 */
public interface RandomizerFactoryIF {


	/**
	 * Method for creating a new instance of RandomizerIF.
	 * 
	 * @param Seed used to create the random numbers
	 * @return A pointer to an instance of a random object chooser
	 */ 
	RandomizerIF newRandomizer(long seed);

}//end of interface
