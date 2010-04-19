package edu.udel.cis.cisc475.rex.source.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * A class that reads in a text-based input and writes it 
 * with a PrintWriter.  A section can be selected with starting
 * line, ending line, starting column, and ending column.
 * 
 * @author Jim Cardona
 * @author Justin Johnson
 * @author Jack Song
 * @version 1.0, 19 Apr 2010
 *
 */
public class Source implements SourceIF {

	public static String newline = System.getProperty("line.separator");
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
	// name = new File( fileName );
	
	 /*	check if the filename is an existing file (need try - catch statements) 
	  * 
	 	this instance of source is being used to read in
	 	an ECF or UEF file and we will 
	 	read in the whole file 
	 	into our 
	 	array list 
	 	and 
	 	set the markers along the way	 	
	 */
     /*if ( name.exists() ) // check if exists
     { if ( name.isFile() ) // check if is file and not directory
     { 

    	 try
         {
            input = new Scanner( name );
         } // end try
         catch ( FileNotFoundException fileNotFoundException )
         {
            System.err.println( "Error opening file." );
            System.exit( 1 );
         } // end catch
	 
	 
         while ( input.hasNext() )
         {
        	 Text = input.nextLine(); // read line
        	 addText(Text); //add line to our string array
        	 lastline++; //increment # of lines

         } // end while
         lastline--;
	 
	 
     }//end of if ( name.isFile() )
     else {}
     	// exception is a directory, not file
     
     
     }//end of if ( name.exists() ) 
     	else {}
     	// exception file not found
     */
}//end of constructor



/**
 * alternate Constructor for the class
 * 
 * @param filename The name of the file to be read
 */
/*
public Source(String filename,
		int startLine, 
		int startColumn,
		int lastLine,
		int lastColumn){

	 //init private vars
    startline = startLine;
    startcolumn = startColumn;
    lastline = lastLine;
    lastcolumn = lastColumn;
    					
	 Text = " "; //using this as temp string buffer only

   // create File object based on user input
	 fileName = filename;	
	 name = new File( fileName );
	
	 //array list object that will contain all of our lines of imported text
	 text_lines = new ArrayList<String>();
	 
	 
	 /*	check if the filename is an existing file (need try - catch statements) 
	  * 
	 	this instance of source is being used to read in
	 	an ECF or UEF file and we will 
	 	read in the whole file 
	 	into our 
	 	array list 
	 	and 
	 	set the markers along the way	 	
	 *//*
    if ( name.exists() ) // check if exists
    { if ( name.isFile() ) // check if is file and not directory
    { 

   	 try
        {
           input = new Scanner( name );
        } // end try
        catch ( FileNotFoundException fileNotFoundException )
        {
           System.err.println( "Error opening file." );
           System.exit( 1 );
        } // end catch
	 
	 
        while ( input.hasNext() )
        {
       	 Text = input.nextLine(); // read line
       	 addText(Text); //add line to our string array
       	 lastline++; //increment # of lines

        } // end while
        lastline--;
	 
	 
    }//end of if ( name.isFile() )
    else {}
    	// exception is a directory, not file
    
    
    }//end of if ( name.exists() ) 
    	else {}
    	// exception file not found
    
}//end of constructor
*/











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
	// loop through the array list and dump
	//	the contents to out
	// use delimiters set by requesting program of
	//start and end rows
	out.printf("%s\n", Text);
/*	
	String tempString = "";
	
	// Checks startline, lastline < 0 and startline > lastline cases
	if(startline < 0 || lastline < 0 || startline > lastline)
	{
		System.err.println("Startline = " + startline);
		System.err.println("Lastline = " + lastline);
		System.err.println("Boundary error");
	}
	// Checks startline > length of file case, gives an error
	else if(startline > size)
	{
		System.err.println("Startline is past the end of file");
	}
	// Checks lastline > lenth of file case, sets lastline to the end of file
	else if(lastline > size)
	{
		lastline = size-1;
	}
	// startline = lastline case, prints that one line
	else if(startline == lastline)
	{
		for(int i = startcolumn; i < text_lines.get(startline).length(); i++)
		{
			tempString += text_lines.get(startline).charAt(i);
		}
		System.out.printf("%s\n", tempString);
	}
	// normal conditions
	else
	{
		// prints startline from startcolumn to endline
		if(startcolumn < text_lines.get(startline).length())
		{
			for(int i = startcolumn; i<text_lines.get(startline).length(); i++)
				tempString += text_lines.get(startline).charAt(i);
			out.printf("%s\n", tempString);
		}
		// prints startline+1 to lastline-1
		for (int i = startline + 1; (i < size) && (i < lastline - 1); i++)
			out.printf("%s\n", text_lines.get(i)); 
		tempString = "";
		// no lastcolumn set, prints lastline completely
		if(lastcolumn == 0)
			out.printf("%s\n", text_lines.get(lastline));
		// prints lastline from beginning to lastcolumn
		else
		{
			for(int i = 0; (i<text_lines.get(lastline).length() && i < lastcolumn); i++)
				tempString += text_lines.get(lastline).charAt(i);
			out.printf("%s\n", tempString);
		}
	}
*/} 

// add text to our structure

/**
 * Adds a line of text to the bottom of the data structure
 * 
 * @param text Text to be added
 */
public void addText(String text) {
	myText.append(text);
	myText.append(newline);
	Text = myText.toString();
}

	
//private vars
private String fileName;
private String Text;	
private int startline;
private int startcolumn;
private int lastline;
private int lastcolumn;	
private StringBuffer myText;
//		List<String> text_lines; //our string array
}//end of class SourceFactoryIF 
