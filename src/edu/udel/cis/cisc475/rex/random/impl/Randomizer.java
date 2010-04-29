package edu.udel.cis.cisc475.rex.random.impl;

import java.util.ArrayList;
import java.util.Random;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**
 * This class creates an instance of a random object chooser.  It creates
 * a seeded random number generator and is given a number of elements to pick,
 * along with a range of elements to pick from. It returns the chosen objects.
 * 
 * This class will yield repeatable results given a repeated seed.
 * 
 * @author justin
 * @author cardona
 *
 */
public class Randomizer implements RandomizerIF {

	// Private variables
	private Random randnum;

	/**
	 * class creates an instance of a random object chooser
	 * when called it first creates the seed randomizer
	 * @param seed used to create the random numbers 
	 */
	public	Randomizer (long seed){
		randnum = new Random();
		randnum.setSeed(seed);
	}// End of constructor



	/**
	 *  General purpose random chooser: can be used to permute answers 
	 * (let numItems=items.length) permute topics, select problems 
	 * satisfying constraints (first filter then select) 
	 * 
	 * @param numItems  number of items to return
	 * @param items  the arrays of objects to choose from
	 * @return an array of chosen objects
	 */
	public Object[] choose(int numItems, Object[] items) {

		int index;
		// Holds the original list
		ArrayList itemsArray = new ArrayList();
		// Holds the shuffled list
		ArrayList shuffledItems  = new ArrayList();

		// If numItems == 0, return an empty array
		if(numItems == 0){
			Object emptyArray[] = {};
			return emptyArray;
		}
		
		// Adds all the items to the arraylist
		for(int i=0; i< items.length; i++){
			itemsArray.add(items[i]);	
		}
		
		// If numItems > number of items in the array or negative numItems, throw exception
		if((numItems > items.length + 1) || (numItems < 0)){	
			throw new IllegalArgumentException("Number of items: " + numItems + "\n" + 
					"Number of of items in the array: " + (items.length + 1));
		}

		
		
		while(shuffledItems.size()!=numItems ){
			// Gets a random number in the array's range
			index = randnum.nextInt(itemsArray.size());
			// Adds that item to the shuffled list
			shuffledItems.add(itemsArray.get(index));
			// Removes that item from the items array
			itemsArray.remove(index);
		}

		index = 0;
		
		// Returns the shuffled list as an array
		return shuffledItems.toArray();
	}//end of public Object[] choose(int numItems, Object[] items) {


	
	
	/**
	 *  General purpose random chooser: can be used to permute answers 
	 * (let numItems=items.length) permute topics, select problems 
	 * satisfying constraints (first filter then select) 
	 * 
	 * @param numItems  number of items to return
	 * @param items  the type T array to choose from
	 * @return an array of chosen objects
	 */
public	<T> T[] chooseT(int numItems, T[] items){	
/*
	int index;
	//holds the original list
//	ArrayList itemsArray = new ArrayList();
	//holds the shuffled list
	ArrayList shuffledItems  = new ArrayList();

	//adds all the items to the arraylist
	for(int i=0; i< items.length; i++){
		itemsArray.add(items[i]);	
	}

	//if picked items is less then zero return null
	if(numItems < 0){
		// Should be changed to RexException when it's implemented
		//throw new Exception("Number of items: " + numItems + "\n numItems <= 0");
		return null;
	}
	//if they request more objects then in the list
	if(numItems > items.length + 1){	
		//throw new Exception("Number of items: " + numItems + "\n"
			//	+ "Length of array: " + (items.length+1));
		return null;
	}
	
	while(shuffledItems.size()!=numItems ){
		//gets a random number in the array's range
		index = randnum.nextInt(itemsArray.size());
		//adds that item to the shuffled list
		shuffledItems.add(itemsArray.get(index));
		//removes that item from the items array
		itemsArray.remove(index);
	}

	index = 0;
	
	//returns the shuffled list as an array
	*/
//	return shuffledItems.toArray();
	return items;	
	}//end of public Object[] choose(int numItems, Object[] items) {
	
}//end of class 

