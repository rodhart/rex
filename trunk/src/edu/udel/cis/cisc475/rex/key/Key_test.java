package edu.udel.cis.cisc475.rex.key;

import java.util.ArrayList;
import java.util.Collection;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.key.impl.Key;

/**
 * @author cardona
 *
 */

public class Key_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		note to self
//		without factory since constructor is public
//		Key K = new Key ("version string name", "exam string name ", "date string");		
		

// without using static, we must create instance of factory to use it
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey("version string name", "exam string name ", "date string");		
		
		
	 	System.out.printf("version is:   %s \n", K.version());			
	 	System.out.printf("exam name is:   %s \n", K.examName());			
	 	System.out.printf("date is:   %s \n", K.date());			
	 	System.out.printf("number of Problems is: %d \n", K.numProblems());			
	 	
	 	System.out.printf("\nadding some problems \n");			
		//create temp containers
	 	String a[] = {"one", "two", "three", "four", "five"};
		String b[] = {"six", "seven", "eight", "nine", "ten"};
	 	Collection <String> newProblem = new ArrayList<String>();

	 	for (String c: a)
	 		newProblem.add(c); 
	 	//add problems to our class
	 	K.addProblem(newProblem);	 	

	 	System.out.printf("number of Problems is %s \n", K.numProblems());			
	 	System.out.printf("Problems are %s \n", K.answers(0));			

	 	System.out.printf("\nadding some more problems \n");			
	 	Collection <String> newProblem2 = new ArrayList<String>();
	 	for (String c: b)
	 		newProblem2.add(c); 
	 	//add problems to our class
	 	K.addProblem(newProblem2);	 	

	 	System.out.printf("number of Problems is %s \n", K.numProblems());			
	 	System.out.printf("Problems are %s \n", K.answers(0));			
	 	
	}//end of main
}//end of class source_test
