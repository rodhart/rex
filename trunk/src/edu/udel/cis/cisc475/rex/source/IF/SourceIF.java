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
	String filename();
	String text();
	int startLine(); 
	int startColumn();
	int lastLine();
	int lastColumn(); 
	void setStartLine(int line); 
	void setStartColumn(int column);
	void setLastLine(int line);
	void setLastColumn(int column);
	void write(PrintWriter out) throws IOException; 
	void addText(String text);
	
}//end of interface SourceIF 
 
