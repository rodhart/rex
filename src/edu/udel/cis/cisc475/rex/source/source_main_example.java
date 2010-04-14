package edu.udel.cis.cisc475.rex.source;

import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author team 5
 *
 */

public class source_main_example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "./trunk/src/edu/udel/cis/cisc475/rex/source/DrHarvey_source_test.tex";
		
		// Creates a new SourceFactory
		SourceFactoryIF ourFactory = new SourceFactory();
		// Gets a new instance of Source
		SourceIF S = ourFactory.newSource(filename);
		
		//declare a pointer to an interface and
		//create an object of the interface type
		
	 	System.out.printf("filename is %s \n", S.filename());			
	 	System.out.printf("read in file\n");			

	 	System.out.printf("now display what is in our array \n");
		PrintWriter pw = new PrintWriter(System.out,true); 
		S.write(pw); 

//	 	System.out.printf("now add some text to our array \n\n");
//		S.addText("this is test line 1");
//		S.addText("      this is the second test line");

		System.out.printf("now display what is in our array \n");
		S.write(pw); 

		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("lastLine is %d \n", S.lastLine());
		
		System.out.printf("setting startline to 0\n");
		S.setStartLine(0);			
		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("setting lastline to 3\n");
		S.setLastLine(3);			
		System.out.printf("lastline is %d \n", S.lastLine());			
		System.out.printf("now display between bounds lines 0 to 3 - will show lines 0, 1, 2\n");
		S.write(pw); 
		
		System.out.printf("setting startline to 7\n");
		S.setStartLine(655);			
		System.out.printf("startline is %d \n", S.startLine());			
		System.out.printf("setting lastline to 7\n");
		S.setLastLine(1000);			
		System.out.printf("lastline is %d \n", S.startLine());			
		System.out.printf("now display between bounds lines 6 to 7 - will show line 6\n");
		S.write(pw); 

		S.setStartColumn(3);
		S.setLastColumn(82);
		S.write(pw);
	
	
	}//end of main
}//end of class source_main_example
