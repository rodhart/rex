package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.output.ExamStub;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.output.impl.OutputFactory;

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
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey("version string name", "exam string name ", "date string");		

		//create the writer only once the key is built
		OutputFactoryIF OF = new OutputFactory(); 
		AnswerKeyWriterIF AKW = OF.newAnswerKeyWriter (K);		

		
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


		//create output stream
		PrintWriter pw = new PrintWriter(System.out,true); 

		//make call to write out the answer key
		AKW.write(pw);

		
		/* now we are done fooling around with answer keys
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * now lets do some exam stuff
		 * 
		 */
		ExamIF EM = new ExamStub();		
		ExamWriterIF EW = OF.newExamWriter (EM);		
		
		EW.write(pw);
		
	}//end of main
}//end of class 
