package edu.udel.cis.cisc475.rex.output.IF;

import java.io.PrintWriter;

/**
 * The ExamWriterIF is used to output a Exam object
 * with a print writer object using the public write() function.
 * 
 * @author Team 5
 *
 */
public interface ExamWriterIF {
	
	/**
	 * Writes the exam object to a file using a PrintWriter object
	 * 	
	 * @param out any PrintWriter object used to output data
	 */
	void write(PrintWriter out);
}//end of interface 
