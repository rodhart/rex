package edu.udel.cis.cisc475.rex.random.impl;

import java.util.ArrayList;
import java.util.Random;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**
 * class creates an instance of a random object chooser
 * when called it first creates the seed randomizer
 * then when given a set of objects and a certain number to choose
 * will return the chosen objects
 * the choosing of these objects will be repeatable if given the
 * same seed value
 * 
 * @author justin
 * @author cardona
 *
 */
public class Randomizer implements RandomizerIF {

	// note to self
	// this constructor is public, so we are not forced into using
	//the factory
	//if we put the factory call inside the class then
	//we could make the default constructor private which would
	//force the use of the factory

	//private vars
	private Random randnum;

	/**
	 * class creates an instance of a random object chooser
	 * when called it first creates the seed randomizer
	 * @param seed used to create the random numbers 
	 */
	public	Randomizer (long seed){
		randnum = new Random();
		randnum.setSeed(seed);
	}// end of constructor



	/**
	 *  General purpose random chooser: can be used to permute answers 
	 * (let numItems=items.length) permute topics, select problems 
	 * satisfying constraints (first filter then select) 
	 * 
	 * @param numItems  number of items to return
	 * @param items  the arrays of objects to choose from
	 * @return an array of chosen objects
	 */
	public Object[] choose(int numItems, Object[] items) throws Exception {

		int index;
		//holds the original list
		ArrayList itemsArray = new ArrayList();
		//holds the shuffled list
		ArrayList shuffledItems  = new ArrayList();

		//adds all the items to the arraylist
		for(int i=0; i< items.length; i++){
			itemsArray.add(items[i]);	
		}

		//if picked items is less then zero return null
		if(numItems <= 0){
			// Should be changed to RexException when it's implemented
			throw new Exception("Number of items: " + numItems + "\n numItems <= 0");
		}
		//if they request more objects then in the list
		if(numItems > items.length + 1){	
			throw new Exception("Number of items: " + numItems + "\n"
					+ "Length of array: " + (items.length+1));
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
		return shuffledItems.toArray();
	}

}//end of class 

