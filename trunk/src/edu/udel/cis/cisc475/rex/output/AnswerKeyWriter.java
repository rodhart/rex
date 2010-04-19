package edu.udel.cis.cisc475.rex.output;

import java.io.File;
import java.io.PrintWriter;
import edu.udel.cis.cisc475.rex.key.impl.Key;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;


/**
 * 
 * class which writes the answer key
 * data is written to whichever output is specified by the caller
 * 
 * @author cardona
 *
 */
public class AnswerKeyWriter implements AnswerKeyWriterIF {

	/**
	 * 
	 * constructor, establishes a pointer to the answer key representation stored in 
	 * our program
	 * 
	 * @param K a pointer to answer key representation stored in our program
	 */
	public AnswerKeyWriter (Key K){
		this.K = K;
	}//end of constructor



	/**
	 * 
	 * writes the answer key
	 * data is written to whichever output is specified by the caller
	 * 
	 * @param out a pointer to where the user wants the output to go
	 */
	public void write(PrintWriter out){
		out.printf("Exam version : \t %s\n", K.version() ); 
		out.printf("Exam Name : \t %s\n", K.examName() );
		out.printf("Date :      \t %s\n", K.date() ); 
		out.printf("\nThis Exam contains %d problems. \n", K.numProblems() ); 

		out.printf("\n\t  Answer Key  \n"); 

		for (int i = 0; i < K.numProblems(); i ++) {
			out.printf("problem %d :   %s\n", i+1, K.answers(i) ); 
		}
	}//end of write(PrintWriter out)


	/**
	 * 
	 * writes the answer key data to a scantron
	 * 
	 * @param file pointer to a file
	 */
	public void writeScantron(File file){
	}//end of writeScantron(File file)

	//private vars
	private Key K;
}//end of class
