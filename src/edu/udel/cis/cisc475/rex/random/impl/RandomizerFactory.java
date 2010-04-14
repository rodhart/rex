package edu.udel.cis.cisc475.rex.random.impl;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**	
 * this class is a factory which creates a new instance of 
 * a random object chooser
 * 
 *   @author CARDONA
 * 
 */
public class RandomizerFactory implements RandomizerFactoryIF 
{

	/**
	 * do nothing constructor	
	 */
	public 	RandomizerFactory () {
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


	//note to self
	// we could do this much easier more cleanly with
	// a static method, but since interfaces do not
	//support static as I understand
	//then we cannot do it this way unless we kill the interface
	/*public class RandomizerFactory {

public static RandomizerIF newRandomizer(long seed){
	return new Randomizer (seed);	
	}
	 */


}//END OF CLASS
