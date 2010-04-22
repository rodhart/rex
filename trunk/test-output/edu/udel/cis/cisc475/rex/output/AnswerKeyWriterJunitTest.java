package edu.udel.cis.cisc475.rex.output;
import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.key.impl.Key;

/**
 * @author cardona
 * 
 */
public class AnswerKeyWriterJunitTest {

	public static String newline = System.getProperty("line.separator");
	
	//test to see what is actually writer to output
	@Test
	public void test_answer_key_writer() {
		// declare a pointer to an interface and
		// create an object of the interface type
		Key K = new Key("version string name", "exam string name",
				"date string");

		// create temp containers
		Collection<String> Answer_a = new ArrayList<String>();
			Answer_a.add(new String("B"));
		// more than one correct answer
		Collection<String> Answer_b = new ArrayList<String>();
			Answer_b.add(new String("A"));
			Answer_b.add(new String("C"));
		Collection<String> Answer_c = new ArrayList<String>();
			Answer_c.add(new String("B"));

		// add problems to our class from the containers
		K.addProblem(Answer_a);
		K.addProblem(Answer_b);
		K.addProblem(Answer_c);

		
		
		//all that junk above was just setting up the answer key
		//now we can take that stuff and actually write it out
		//write method writes to a printWriter
		//so we must always pipe through that
		PrintWriter ScreenW = new PrintWriter(System.out,true); 
		AnswerKeyWriter AKW = new AnswerKeyWriter(K);
		PrintWriter pw = null;
		StringBuffer contents = new StringBuffer();
		try {
			//pipe from printWriter to file then from file to stringbuffer
			pw = new PrintWriter(new FileWriter("AnswerKeyWriterTester.txt"));
			//make call to write out the answer key to the file
			//this is the actual call that we want to test
			AKW.write(pw);
			pw.flush();
			} catch (IOException e) {e.printStackTrace();	}
		//now that it is written out to file
		//pipe it to a string buffer	
		File aFile = new File("AnswerKeyWriterTester.txt");
		String line = null;
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				while( (line = input.readLine() ) != null)
					contents.append(line + newline);
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		//now our entire writing that was done by AKW.write should be located
		//in our stringbuffer 'contents'
		
		// here is the text that we were expecting
		String testString = 
		"Exam version :   version string name" + newline +  
		"Exam Name :      exam string name" + newline + 
		"Date :           date string" + newline + 
		 newline + 
		"This Exam contains 3 problems." + newline +
		 newline +  
		"        Answer Key"+ newline + 
		"problem 1 :   [B]" + newline + 
		"problem 2 :   [A, C]" + newline + 
		"problem 3 :   [B]" + newline;		


		//now compare the two	
//		System.out.println(contents.toString());
//		System.out.println(testString);
//		AKW.write(ScreenW);
//		System.out.println( testString.compareTo( contents.toString() ) );

		assertEquals(0,(testString.compareTo( contents.toString() ) ));
	}// end test

}// end of class
