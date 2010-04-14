package edu.udel.cis.cisc475.rex.random.IF;

/**
 * class creates an instance of a random object chooser
 * when called it first creates the seed randomizer
 * then when given a set of objects and a certain number to choose
 * will return the chosen objects
 * the choosing of these objects will be repeatable if given the
 * same seed value
 * 
 * @author  CARDONA
 *
 */
public interface RandomizerIF {

	
	
	
	/**
	 *  General purpose random chooser: 
	 * can be used to permute answers 
	 * (let numItems=items.length) 
	 * permute topics, select problems 
	 * satisfying constraints (first filter then select) 
	 * 
	 * @param numItems number of items to return
	 * @param items the arrays of objects o choose from
	 * @return an array of chosen objects
	 */
	Object[] choose(int numItems, Object[] items);

}// end of interface