package edu.udel.cis.cisc475.rex.key;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;

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

//		note to self
// without using static, we must create instance of factory to use it
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey("version string name", "exam string name ", "date string");		
		
	 	System.out.printf("version is:   %s \n", K.version());			
	 	System.out.printf("exam name is:   %s \n", K.examName());			
	 	System.out.printf("date is:   %s \n", K.date());			
	 	System.out.printf("number of Answers in Answers Key is: %d \n", K.numProblems());			
	 	System.out.printf("\nadding some Answers \n");			
	 	
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

	 	System.out.printf("number of Answers in Answers Key is: %d \n", K.numProblems());			
	 	System.out.printf("Answer to question number 0 is %s \n", K.answers(0));			
	 	System.out.printf("Answer to question number 1 is %s \n", K.answers(1));			
	 	System.out.printf("Answer to question number 2 is %s \n", K.answers(2));			

	 	System.out.printf("Answer to question number 3 is %s \n", K.answers(3));			

	 	
	}//end of main
}//end of class source_test
