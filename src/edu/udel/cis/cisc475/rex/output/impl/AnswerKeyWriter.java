package edu.udel.cis.cisc475.rex.output.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;


/**
 * The AnswerKeyWriter class outputs a formatted output of the answer key data 
 * which corresponds to an exam object.
 *
 * The AnswerKeyWriter will output an answerKey object using a print writer object 
 * and the public write() function.
 * 
 * 
 * There is also consideration for writing a scantron formatted file.
 * 
 * @author cardona
 *
 */
public class AnswerKeyWriter implements AnswerKeyWriterIF {

	//private vars
	private static String newline = System.getProperty("line.separator");
	private AnswerKeyIF K;

	/**
	 * 
	 * Constructor that establishes a pointer to the answer key object.
	 * 
	 * @param K pointer to answer key object
	 */
	public AnswerKeyWriter (AnswerKeyIF K){
		this.K = K;
	}//end of constructor

	/**
	 * Writes the answer key. Data is written to whichever printWriter is specified by the caller.
	 * 
	 * @param out a pointer to where the user wants the output to go
	 */
	public void write(PrintWriter out){
		
		TimeZone zone = TimeZone.getDefault();
		Calendar now = Calendar.getInstance(zone);
		Date day = new Date(now.getTimeInMillis());
		
		out.print("Answer Key for " + K.examName() + " version '" +
				K.version() + "' generated on " + day.toString() + newline);
		out.print("Total points: " + "<integer>" + newline);
		for (int i = 0; i < K.numProblems(); i++) {
			out.printf("%d. %s%s", i+1, K.answers(i), newline );
			
		}
	}//end of write(PrintWriter out)


	/**
	 * Writes the answer key data to a Scantron
	 * 
	 * Not yet implemented
	 * 
	 * @param file File to output Scantron to
	 */
	public void writeScantron(File file){
	}//end of writeScantron(File file)

}//end of class
