package edu.udel.cis.cisc475.rex.source;

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

		
//		newSource(filename);
		
		//declare a pointer to an interface and
		//create an object of the interface type
		SourceIF S = new Source(filename);
		
		
		
	 	System.out.printf("filename is %s \n", S.filename());			
	 	System.out.printf("read in file\n");			

	 	System.out.printf("now display what is in our array \n");
		PrintWriter pw = new PrintWriter(System.out,true); 
		S.write(pw); 

	 	System.out.printf("now add some text to our array \n\n");
		S.addText("this is test line 1");
		S.addText("      this is the second test line");

		System.out.printf("now display what is in our array \n");
		S.write(pw); 

		
		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("lastLine is %d \n", S.lastLine());
		
		System.out.printf("setting startline to 0\n");
		S.setStartLine(0);			
		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("setting lastline to 3\n");
		S.setLastLine(3);			
		System.out.printf("lastline is %d \n", S.startLine());			
		System.out.printf("now display between bounds lines 0 to 3 - will show lines 0, 1, 2\n");
		S.write(pw); 
		
		System.out.printf("setting startline to 6\n");
		S.setStartLine(6);			
		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("setting lastline to 7\n");
		S.setLastLine(7);			
		System.out.printf("lastline is %d \n", S.startLine());			
		System.out.printf("now display between bounds lines 6 to 7 - will show line 6\n");
		S.write(pw); 

	
	
	}//end of main
}//end of class source_test
