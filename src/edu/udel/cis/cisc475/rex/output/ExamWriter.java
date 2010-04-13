package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
/**
 * @author team 5
<<<<<<< .mine
 * hello
=======
 * Outputs a randomized, completed exam file
>>>>>>> .r212
 */
public class ExamWriter implements ExamWriterIF {


	// Notes for creation:
	// loop with exam.numElements through a hash(?) to find 
	// out what to print
	// Does not include front matter.

	//constructor	
	public ExamWriter(Exam E) {

		this.E = E;
		numElements = E.numElements();

	}//end of constructor


	public void write(PrintWriter out){
		// output begining of exam file
		out.printf("%s\n", E.preamble());
		out.printf("%s\n", E.frontMatter());
		
		// output problems with respective answers
		
		
		// output the end block for exam
		out.printf("%s\n", E.finalBlock());
		
	}//end of write(PrintWriter out)

	
	//private vars
	private Exam E;
	private Collection <ExamElementIF> C;	
	private int numElements;
}//end of class 
