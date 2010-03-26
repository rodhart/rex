package edu.udel.cis.cisc475.rex.source.IF;

import java.util.*;
import java.io.*;

/**
 * 


/**
 * @version .001 
 * @author team 5
 *
 */
public class Source implements SourceIF {

//constructor
public Source(String filename) {
	 fileName = filename;	
	 startline = startcolumn = lastline = lastcolumn = 0;	
	 Text = " ";
}//end of constructor



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



public void write(PrintWriter out) {} 
public void addText(String text) {}

	
//private vars
private String fileName;
private String Text;
private int startline;
private int startcolumn;
private int lastline;
private int lastcolumn;	
}//end of class SourceFactoryIF 
