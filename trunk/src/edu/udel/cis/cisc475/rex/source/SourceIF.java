package edu.udel.cis.cisc475.rex.source.IF;

import java.util.*;
import java.io.*;

/**
 * 


/**
 * @author team 5
 *
 */
interface SourceIF {
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
	void write(PrintWriter out); 
	void addText(String text);
}//end of interface SourceIF 
 
