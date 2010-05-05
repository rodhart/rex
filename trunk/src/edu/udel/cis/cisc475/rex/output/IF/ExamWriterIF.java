package edu.udel.cis.cisc475.rex.output.IF;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The ExamWriterIF is an interface.  Implementing this interface will create 
 * a class used to output a Exam object using a print writer object 
 * and the public write() function.
 * 
 * The ExamWriter class outputs a formatted output of the newly generated exams 
 * created in the generator class.
 * The files are written to a PrintWriter specified by the caller.
 * 
 * @author cardona
 * @author jsong
 *
 */
public interface ExamWriterIF {
	
	/**
	 * Writes the exam object to a file using a PrintWriter object
	 * 	
	 * @param out any PrintWriter object used to output data
	 * @throws IOException 
	 * 
	 */
	void write(PrintWriter out) throws IOException;
}//end of interface 
