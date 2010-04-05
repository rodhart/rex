package edu.udel.cis.cisc475.rex.output;

import java.util.*;
import java.io.*;

/**
 * 


/**
 * @author team 5
 *
 */
public class AnswerKeyWriter implements AnswerKeyWriterIF {

	//constructor
	public AnswerKeyWriter (Key K){
	this.K = K;
	}//end of constructor
	
	public void write(PrintWriter out){
		out.printf("Exam version : \t %s\n", K.version() ); 
		out.printf("Exam Name : \t %s\n", K.examName() );
		out.printf("Date :      \t %s\n", K.date() ); 
		out.printf("\nThis Exam contains %d problems. \n", K.numProblems() ); 

		C = new ArrayList<String> (K.answers(0) );
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
}//end of class
