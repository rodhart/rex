package edu.udel.cis.cisc475.rex.random;



import java.util.*;
import java.io.*;
import edu.udel.cis.cisc475.rex.random.IF.*;
/**
 * 
 */

/**
 * @author  team 5
 *
 */
public class random_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//create some objects
		String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		for(int i = 0; i < 3; i++) {
			System.out.printf("in random test main before call %s\n", a[i]);
			}

		Object[] b = new String[10];
		b =  Randomizer.choose (3, a);		

		//diff way to do the call
		//String[] b = new String[10];
		//b =  (String[]) randomizer.choose (3, a);		
		
		
		for(int i = 0; i < 3; i++) {
		System.out.printf("in random test main after return from call %s\n", b[i]);
		}

	}//end of main
}//end of class
