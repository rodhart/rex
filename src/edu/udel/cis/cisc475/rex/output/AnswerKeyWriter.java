package edu.udel.cis.cisc475.rex.output;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import edu.udel.cis.cisc475.rex.key.impl.Key;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;


/**
 * @author team 5
 *
 */
public class AnswerKeyWriter implements AnswerKeyWriterIF {

	//constructor
	public AnswerKeyWriter (Key K){
	this.K = K;
	numProblems = K.numProblems();
//	C = new ArrayList<String> ();

	for (int i = 0; i < numProblems; i++) {
//	C.add(K.answers(i));	
	}

	C = new ArrayList<String> (K.answers(0));

	}//end of constructor
	
	public void write(PrintWriter out){
		out.printf("Exam version : \t %s\n", K.version() ); 
		out.printf("Exam Name : \t %s\n", K.examName() );
		out.printf("Date :      \t %s\n", K.date() ); 
		out.printf("\nThis Exam contains %d problems. \n", K.numProblems() ); 

		int count = 1;
		out.printf("\n\t  Answer Key  \n"); 

		for (String c: C) {
	 		out.printf("problem %d :   %s\n", count, c); 
	 		count++;
	 	}
}//end of write(PrintWriter out)

	
	
public void writeScantron(File file){


}//end of writeScantron(File file)

//private vars
private Key K;
private ArrayList <String> C;	
private int numProblems;
}//end of class
