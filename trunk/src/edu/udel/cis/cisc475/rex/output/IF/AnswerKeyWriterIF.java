package edu.udel.cis.cisc475.rex.output.IF;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The AnswerKeyWriterIF is an interface.  Implementing this interface will create 
 * a class used to output an answerKey object using a print writer object 
 * and the public write() function.
 * 
 * The AnswerKeyWriter class outputs a formatted output of the answer key data 
 * which corresponds to an exam object.
 * 
 * There is also consideration for writing a scantron formatted file.
 * 
 * @author cardona
 * @author jsong
 *
 */
public interface AnswerKeyWriterIF {
	
	/**
	 * Writes the Key object to a file using a PrintWriter object
	 * 	
	 * @param out any PrintWriter object used to output data
	 * @throws IOException 
	 */
	void write(PrintWriter out);
	/**
	 * Writes a scantron formatted file using the answer Key object.
	 * 
	 * @param file
	 */
	void writeScantron(File file);
}//end of interface 
