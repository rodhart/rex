package edu.udel.cis.cisc475.rex.source.IF;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Interface for Source class, outlining publicly accessible functions 
 * from the class.
 * 
 * The class that implements this interface will hold a body of text, integer values for startLine, 
 * startColumn, lastLine, lastColumn, and the file name.  The class
 * is used to store a body of text and the parameters for the region of
 * text from the file it is from.
 * 
 * The class allows for the building of the body of text via the addText() method.
 * The other parameters have no bearing on any output and are simply there to hold 
 * the values.
 * 

 * @author cardona
 * @author jsong
 *
 */
public interface SourceIF {
	
	/** Getter for filename.
	 * 
	 * @return Name of file
	 */
	String filename();
	
	/** Getter for the text inside the data structure.
	 * 
	 * @return The entire body of text in the data structure
	 */
	String text();
	
	/** Getter for the starting line.
	 * 
	 * @return The starting line
	 */
	int startLine();
	
	/** Getter for the starting column.
	 * 
	 * @return The starting column
	 */
	int startColumn();
	
	/** Getter for the ending line.
	 * 
	 * @return The ending line
	 */
	int lastLine();
	
	/** Getter for the ending column.
	 * 
	 * @return The ending line
	 */
	int lastColumn(); 
	
	/** Setter for the starting line.
	 * 
	 * @param line Line to start on
	 */
	void setStartLine(int line);
	
	/** Setter for the starting column.
	 * 
	 * @param column Column to start on
	 */
	void setStartColumn(int column);
	
	/** Setter for the ending line.
	 * 
	 * @param line Line to end on
	 */
	void setLastLine(int line);
	
	/** Setter for the ending column.
	 * 
	 * @param column Column to end on
	 */
	void setLastColumn(int column);
	
	/** Method to output the contents of text to a PrintWriter object.
	 * 
	 * @param out PrintWriter object to output to
	 * @throws IOException
	 */
	void write(PrintWriter out) throws IOException;
	
	/** Add text to the data structure.  Adds the text as is.
	 * 
	 * @param text Text to add
	 */
	void addText(String text);
	
} 
 
