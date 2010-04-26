package edu.udel.cis.cisc475.rex.output.impl;

import java.io.File;
import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.Key;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;


/**
 * The AnswerKeyWriter class outputs the answer key
 * data which corresponds to an exam object
 * 
 * The file is written to a PrintWriter specified by the caller
 * 
 * @author cardona
 * @author iburns
 *
 */
public class AnswerKeyWriter implements AnswerKeyWriterIF {

	/**
	 * 
	 * constructor, establishes a pointer to the answer key object
	 * 
	 * @param K pointer to answer key object
	 */
	public AnswerKeyWriter (AnswerKeyIF K){
		this.K = K;
	}//end of constructor



	/**
	 * writes the answer key
	 * data is written to whichever output is specified by the caller
	 * 
	 * @param out a pointer to where the user wants the output to go
	 */
	public void write(PrintWriter out){
		out.printf("Exam version :   %s\n", K.version() ); 
		out.printf("Exam Name :      %s\n", K.examName() );
		out.printf("Date :           %s\n", K.date() ); 
		out.printf("\n"); 
		out.printf("This Exam contains %d problems.\n", K.numProblems() ); 
		out.printf("\n"); 
		out.printf("        Answer Key\n"); 

		for (int i = 0; i < K.numProblems(); i ++) {
			out.printf("problem %d :   %s\n", i+1, K.answers(i) ); 
		}
	}//end of write(PrintWriter out)


	/**
	 * writes the answer key data to a scantron
	 * 
	 * @param file pointer to a file
	 */
	public void writeScantron(File file){
	}//end of writeScantron(File file)

	//private vars
	private AnswerKeyIF K;
}//end of class
