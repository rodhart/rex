package edu.udel.cis.cisc475.rex.source.impl;

import java.io.IOException;
import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * This class holds a body of text, integer values for startLine, 
 * startColumn, lastLine, lastColumn, and the file name.  The class
 * is used to store a body of text and the parameters for the region of
 * text from the file it is from.
 * 
 * The class allows for the building of the body of text via the addText() method.
 * The other parameters have no bearing on any output and are simply there to hold 
 * the values.
 * 
 * @author cardona
 * @author justin johnson
 * @author jsong
 *
 */
public class Source implements SourceIF {

	//private variables
	private String fileName;
	private String Text;	
	private int startLine;
	private int startColumn;
	private int lastLine;
	private int lastColumn;	
	private StringBuffer myText;
	private static String newline = System.getProperty("line.separator");

	/**
	 * Basic constructor for the class, requires only a file name
	 * 
	 * @param filename The name of a file
	 */
	public Source(String filename) {
		//initialize private variables
		startLine = 0;
		startColumn = 0;
		lastLine = 0;
		lastColumn = 0;	
		Text = ""; 
		fileName = filename;	
		myText = new StringBuffer();
	}
	
	/** 
	 * Constructor for all fields
	 * 
	 * @param filename The name of a file
	 * @param startLine Starting line
	 * @param startColumn Starting column
	 * @param lastLine Ending line
	 * @param lastColumn Ending column
	 */
	 
	public Source(String filename, int startLine, int startColumn,
			int lastLine, int lastColumn) {
		this.startLine = startLine;
		this.startColumn = startColumn;
		this.lastLine = lastLine;
		this.lastColumn = lastColumn;
		fileName = filename;
		Text = "";
		myText = new StringBuffer();
	}
	
	/**
	 * Getter for the file name
	 * @return Name of the file
	 */
	public String filename() {
		return fileName;
	}
	
	/**
	 * Getter for the body of text
	 * @return The entire text 
	 */
	public String text() {
		Text = myText.toString();
		return Text;
	}
	
	/**
	 * Getter for the starting line
	 * @return An integer line to start on
	 */
	public int startLine() {
		return startLine;
	} 
	
	/**
	 * Getter for the starting column
	 * @return An integer column to start on
	 */
	public int startColumn() {
		return startColumn;
	}
	
	/** 
	 * Getter for the ending line
	 * @return An integer line to end on
	 */
	public int lastLine() {
		return lastLine;
	}
	
	/**
	 * Getter for the ending column
	 * @return An integer column to end on
	 */
	public int lastColumn()	{
		return lastColumn;
	} 

	/** 
	 * Setter for the starting line
	 * @param line Line to set startLine to
	 */
	public void setStartLine(int line) {
		startLine=line;
	} 
	
	/** 
	 * Setter for the starting column
	 * @param column Column to set startColumn to
	 */
	public void setStartColumn(int column) {
		startColumn=column;
	}
	
	/**
	 * Setter for the ending line
	 * @param line Line to set lastLine to
	 */
	public void setLastLine(int line) {
		lastLine=line;
	}
	
	/**
	 * Setter for the ending column
	 * @param column Column to set lastColumn to
	 */
	public void setLastColumn(int column) {
		lastColumn=column;
	}

	/**
	 * Writes Text to the PrintWriter object
	 * 
	 * @param out PrintWriter object to output to
	 * @throws IOException
	 */
	public void write(PrintWriter out) throws IOException {
		// Throw an IOException if out is null
		if(out == null)
			throw new IOException("Null PrintWriter");
		// Output the entire body of Text to out
		out.printf("%s\n", myText.toString());
	} 

	/**
	 * Adds a line of text to the data structure
	 * 
	 * @param text Text to be added
	 */
	public void addText(String text) {
		myText.append(text);
	}
}
