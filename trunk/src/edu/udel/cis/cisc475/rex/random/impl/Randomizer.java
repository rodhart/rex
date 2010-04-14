package edu.udel.cis.cisc475.rex.random.impl;



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
 * @author  CARDONA, justin
 *
 */
public class Randomizer implements RandomizerIF {

// note to self
// this constructor is public, so we are not forced into using
//the factory
//if we put the factory call inside the class then
//we could make the default constructor private which would
//force the use of the factory

	
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
public Object[] choose (int numItems, Object[] items) {

	int numObjects = items.length; //total number of array elements
	if (numObjects <= 0) {
		System.err.printf("	in method " +
				"public Object[] choose (int numItems, Object[] items)   " +
				"number of Objects <= 0");
		return null;		
	}//end of    if (numObjects == 0) 

	if (numObjects < numItems) {
		System.err.printf("	in method " +
				"public Object[] choose (int numItems, Object[] items)   " +
				"numItems larger than number of Objects");
		return null;		
	}//end of    	if (numObjects < numItems) 


	//pickedNums will store the indexes to the final random set
	int pickedNums[] = new int[numItems];
	int numofPickedNums = 0; //how many numbers have been already picked
	//these are vars used in loop
	int nextNum = 0;
	boolean done = false; 
	boolean alreadyPicked = false;
		
	//do the first one outside of the loop
	//this assumes that user wants at least
	//one object back (we checked for tht above already)
	pickedNums[0] = randnum.nextInt(numObjects); 

	//now loop through and pick the rest of them
	while (!done) {
	nextNum = randnum.nextInt(numObjects); //pick next random int 
	alreadyPicked = false;					//reset the loop checker
	
	//loop through and verify num has not already been picked
	//since we do not want duplicate questions or duplicate
	//answers on a multiple choice test
	for(int i = 0; i<=numofPickedNums; i++) { 
			if (pickedNums[i] == nextNum )
				alreadyPicked = true;
	}//end of for loop

	//if our num hasn't been picked yet 
		//then assign it into the array	
		if (!alreadyPicked){	
			numofPickedNums++; 
			pickedNums[numofPickedNums] = nextNum;
			}//end of if (!alreadyPicked)
	
	//if the required amount of numbers has been picked then we are done
	//with number picking
	if (numofPickedNums == (numItems-1)	)
		done = true;
	}//end of while (!done) 	
	
	//now create an array of objects consisting
	//and ordered by the indexes of our picked numbers
	Object[] newItems = new Object[numItems]; 
	for(int i = 0; i<numItems; i++) { 
		newItems[i] = items[pickedNums[i]];
	}//end of for loop

	//send our sorted object array on its way to its new home
	//bye bye baby! have a nice life!
	return newItems;
}//end of choose method


//private vars
private Random randnum;
}//end of class 

