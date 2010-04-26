package edu.udel.cis.cisc475.rex.source.impl;

import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * A class that reads in a text-based input and writes it 
 * with a PrintWriter.  A section can be selected with starting
 * line, ending line, starting column, and ending column.
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
	 * Constructor for the class
	 * 
	 * @param filename The name of the file to be read
	 */
	public Source(String filename) {
		//init private vars
		startline = startcolumn = lastline = lastcolumn = 0;	
		Text = ""; 
		fileName = filename;	
		myText = new StringBuffer();
	}

	//getters
	public String filename() {return fileName;}
	public String text()	 {return Text;}
	public int startLine() 	 {return startline;} 
	public int startColumn() {return startcolumn;}
	public int lastLine() 	 {return lastline;}
	public int lastColumn()	 {return lastcolumn;} 

	//setters
	public void setStartLine(int line) 		{startline=line;} 
	public void setStartColumn(int column)  {startcolumn=column;}
	public void setLastLine(int line) 		{lastline=line;}
	public void setLastColumn(int column) 	{lastcolumn=column;}

	//output of our array list string object
	/**
	 * Writes selected lines and columns to a PrintWriter object.
	 * 
	 * @param out PrintWriter object to output to
	 */
	public void write(PrintWriter out) {
		out.printf("%s\n", Text);
		return;
	} 

	/**
	 * Adds a line of text to the bottom of the data structure
	 * 
	 * @param text Text to be added
	 */
	public void addText(String text) {
		myText.append(text);
		myText.append(newline);
		Text = myText.toString();
		return;
	}
}//end of class SourceFactoryIF 
