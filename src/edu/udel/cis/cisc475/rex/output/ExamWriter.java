package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;

/**
 * @author team 5
 * Outputs a randomized, completed exam file
 */
public class ExamWriter implements ExamWriterIF {


	// Notes for creation:
	// loop with exam.numElements through a hash(?) to find 
	// out what to print
	// Does not include front matter.

	//constructor	
	public ExamWriter(Exam exam) {

		this.exam = exam;
		numElements = exam.numElements();
		

	}//end of constructor


	public void write(PrintWriter out){
	}//end of write(PrintWriter out)

}//end of class 
