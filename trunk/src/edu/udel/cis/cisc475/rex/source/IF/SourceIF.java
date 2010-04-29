package edu.udel.cis.cisc475.rex.source.IF;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Interface for Source class, outlining publicly accessible functions 
 * from the class.
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
 
