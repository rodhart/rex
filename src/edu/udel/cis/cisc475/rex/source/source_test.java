//package edu.udel.cis.cisc475.rex.source.IF;

import java.util.*;
import java.io.*;
/**
 * @version .001 
 * @author team 5
 *
 */

public class source_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String filename = "DrHarvey_source_test.tex";
		Source S = new Source(filename);
		
	 	System.out.printf("filename is %s \n", S.filename());			
	 	System.out.printf("read in file\n");			

	 	//
	 	System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("startColumn is %d \n", S.startColumn());
		System.out.printf("lastLine is %d \n", S.lastLine());
		System.out.printf("lastColumn is %d \n\n", S.lastColumn()); 

		//
		System.out.printf("setting startline to 5\n");
		S.setStartLine(5);			
		System.out.printf("startline is %d \n", S.startLine());			

		//
		System.out.printf("now add some text to our array \n\n");
		S.addText("this is test line 1");
		S.addText("      this is the second test line");

		//
		System.out.printf("now display what is in our array \n");
		PrintWriter pw = new PrintWriter(System.out,true); 
		S.write(pw); 

		
		
	}//end of main
}//end of class source_test
