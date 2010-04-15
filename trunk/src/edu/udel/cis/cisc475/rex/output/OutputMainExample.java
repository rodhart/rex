package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import edu.udel.cis.cisc475.rex.key.impl.Key;

/**
 * @author cardona
 * 
 */

public class OutputMainExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//declare a pointer to an interface and
		//create an object of the interface type
		Key K = new Key ("version string name", "exam string name ", "date string");		

		//create temp containers
		Collection <String> Answer_a = new ArrayList <String>(); 
		Answer_a.add(new String("B"));
		//more than one correct answer
		Collection <String> Answer_b = new ArrayList <String>(); 
		Answer_b.add(new String("A"));
		Answer_b.add(new String("C"));
		Collection <String> Answer_c = new ArrayList <String>(); 
		Answer_c.add(new String("B"));

		//add problems to our class from the containers
		K.addProblem(Answer_a);	 	
		K.addProblem(Answer_b);	 	
		K.addProblem(Answer_c);	 	

		//create the writer only once the key is built??
		AnswerKeyWriter AKW = new AnswerKeyWriter (K);		

		//create output stream
		PrintWriter pw = new PrintWriter(System.out,true); 

		//make call to write out the answer key
		AKW.write(pw);

	}//end of main
}//end of class 
