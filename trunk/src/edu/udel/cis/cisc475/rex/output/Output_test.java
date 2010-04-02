package IF;

import java.util.*;
import java.io.*;

/**
 * @version .001 
 * @author team 5
 *
 */

public class Output_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//declare a pointer to an interface and
		//create an object of the interface type
		Key K = new Key ("version string name", "exam string name ", "date string");		
		
		//create temp containers
	 	String a[] = {"one", "two", "three", "four", "five"};
	 	Collection <String> newProblem = new ArrayList<String>();
	 	for (String c: a)
	 		newProblem.add(c); 
	 	//add problems to our class
	 	K.addProblem(newProblem);	 	

	 	//create the writer only once the key is built??
	 	AnswerKeyWriter AKW = new AnswerKeyWriter (K);		

	 	//create output stream
	 	PrintWriter pw = new PrintWriter(System.out,true); 
		 
	 	//make call to write out the answer key
	 	AKW.write(pw);
	 	
	}//end of main
}//end of class 
