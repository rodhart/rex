package edu.udel.cis.cisc475.rex.output.IF;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The AnswerKeyWriterIF is used to output an answerKey object
 * with a print writer object using the public write() function.
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
