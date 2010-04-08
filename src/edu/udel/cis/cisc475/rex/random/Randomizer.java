package edu.udel.cis.cisc475.rex.random;



import java.util.Random;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

/**
 * @author  team 5
 *
 */
public class Randomizer implements RandomizerIF {

	
	Randomizer (long seed){
		randnum = new Random();
		randnum.setSeed(seed);
	}// end of constructor
	
	/* General purpose random chooser: 
	 * can be used to permute answers 
	 * (let numItems=items.length) 
	 * permute topics, select problems 
	 * satisfying constraints (first filter then select) */
public Object[] choose (int numItems, Object[] items) {

	int numObjects = items.length; //total number of array elements
	int pickedNums[] = new int[numItems];
	int nextNum = 0;
	int numofPickedNums = 0; //how many numbers have been already picked
	
	boolean done = false; 
	boolean alreadyPicked = false;
	
	//do the first one outside of the loop
	//this assumes that user wants at least
	//one object back
	pickedNums[0] = randnum.nextInt(numObjects); 

	while (!done) {
	nextNum = randnum.nextInt(numObjects); //pick next random int 
	alreadyPicked = false;					//reset the loop checker
	
	//loop through and verify num is not repeat	
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

	return newItems;
}//end of choose method


//private vars
private Random randnum;

}//end of class 
	   


