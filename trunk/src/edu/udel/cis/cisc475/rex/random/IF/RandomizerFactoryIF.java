package edu.udel.cis.cisc475.rex.random.IF;

/**	
* this class is a factory which creates a new instance of 
* a random object chooser
* 
*   @author CARDONA
* 
*/
public interface RandomizerFactoryIF {

	
	/**
	* this method is a factory which creates a new instance of 
	* a random object chooser
	* 
	* @param seed used to create the random numbers 
	* @return a pointer to an instance of a random object chooser
	*/ 
	RandomizerIF newRandomizer(long seed);

}//end of interface
