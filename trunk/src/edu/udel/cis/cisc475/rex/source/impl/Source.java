package edu.udel.cis.cisc475.rex.source.impl;

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
 * @author justinjo
 * @author jsong
 * @version 1.0, 19 Apr 2010
 *
 */
public class Source implements SourceIF {

	public static String newline = System.getProperty("line.separator");

	//private vars
	private String fileName;
	private String Text;	
	private int startline;
	private int startcolumn;
	private int lastline;
	private int lastcolumn;	
	private StringBuffer myText;

	/**
	 * Basic constructor for the class, requires only a file name
	 * 
	 * @param filename The name of a file
	 */
	public Source(String filename) {
		//init private vars
		startline = startcolumn = lastline = lastcolumn = 0;	
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
		startline = startLine;
		startcolumn = startColumn;
		lastline = lastLine;
		lastcolumn = lastColumn;
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
		return startline;
	} 
	
	/**
	 * Getter for the starting column
	 * @return An integer column to start on
	 */
	public int startColumn() {
		return startcolumn;
	}
	
	/** 
	 * Getter for the ending line
	 * @return An integer line to end on
	 */
	public int lastLine() {
		return lastline;
	}
	
	/**
	 * Getter for the ending column
	 * @return An integer column to end on
	 */
	public int lastColumn()	{
		return lastcolumn;
	} 

	/** 
	 * Setter for the starting line
	 * @param line Line to set startLine to
	 */
	public void setStartLine(int line) {
		startline=line;
	} 
	
	/** 
	 * Setter for the starting column
	 * @param column Column to set startColumn to
	 */
	public void setStartColumn(int column) {
		startcolumn=column;
	}
	
	/**
	 * Setter for the ending line
	 * @param line Line to set lastLine to
	 */
	public void setLastLine(int line) {
		lastline=line;
	}
	
	/**
	 * Setter for the ending column
	 * @param column Column to set lastColumn to
	 */
	public void setLastColumn(int column) {
		lastcolumn=column;
	}

	/**
	 * Writes Text to the PrintWriter object
	 * 
	 * @param out PrintWriter object to output to
	 */
	public void write(PrintWriter out) {
		out.printf("%s\n", myText.toString());
		return;
	} 

	/**
	 * Adds a line of text to the data structure
	 * 
	 * @param text Text to be added
	 */
	public void addText(String text) {
		myText.append(text);
		return;
	}
}
