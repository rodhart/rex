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
		
		String filename = "a file name";
		Source S = new Source(filename);
		
	 	System.out.printf("filename is %s \n", S.filename());			

		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("startColumn is %d \n", S.startColumn());
		System.out.printf("lastLine is %d \n", S.lastLine());
		System.out.printf("lastColumn is %d \n", S.lastColumn()); 

		System.out.printf("setting startline to 5\n");
		S.setStartLine(5);			
		System.out.printf("setting startColumn to 3\n");
		S.setStartColumn(3);
		System.out.printf("setting lastLine to 10\n");
		S.setLastLine(10);
		System.out.printf("setting lastColumn to 4\n");
		S.setLastColumn(4); 

		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("startColumn is %d \n", S.startColumn());
		System.out.printf("lastLine is %d \n", S.lastLine());
		System.out.printf("lastColumn is %d \n", S.lastColumn()); 

		//		String text();
		System.out.printf("now add some text to our array \n");
		S.addText(" this is test line 1");
		S.addText(" this is the second test line");
		S.addText(" this is test line 3");
		S.addText(" this is the fourth test line and there are no more");

		
		PrintWriter pw = new PrintWriter(System.out,true); 
		S.write(pw); 

		
		
	}//end of main
}//end of class source_test
