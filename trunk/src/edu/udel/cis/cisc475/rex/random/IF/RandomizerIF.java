package edu.udel.cis.cisc475.rex.random.IF;

/**
 * Interface for Randomizer class, outlining accessible functions 
 * from the class.

 * This class creates an instance of a random object chooser.  It creates
 * a seeded random number generator and is given a number of elements to pick,
 * along with a range of elements to pick from. It returns the chosen objects.
 * 
 * This class will yield repeatable results given a repeated seed.
 * 
 * @author cardona
 * @author jsong
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
	 * @param items the arrays of objects to choose from
	 * @return an array of chosen objects
	 * @throws Exception 
	 */
	Object[] choose(int numItems, Object[] items);

	/**
	 *  General purpose random chooser: 
	 * can be used to permute answers 
	 * (let numItems=items.length) 
	 * permute topics, select problems 
	 * satisfying constraints (first filter then select) 
	 * 
	 * @param numItems number of items to return
	 * @param items the arrays of type T to choose from
	 * @return an array of type T
	 * @throws Exception 
	 */
//	<T> T[] chooseT(int numItems, T[] items);
}// end of interface