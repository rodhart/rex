package edu.udel.cis.cisc475.rex.output.IF;

import java.io.File;
import java.io.PrintWriter;

/**
 * @author team 5
 *
 */
public interface AnswerKeyWriterIF {
	void write(PrintWriter out);
	void writeScantron(File file);
}//end of interface 
