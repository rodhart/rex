package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.output.impl.ExamWriter;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.Source;

/**
 * Junit Testing for ExamWriter
 * 
 * @author kiernan
 * @author justin
 */
public class ExamWriterTest {
	ExamFactoryIF examFactory;
	OutputFactoryIF examWriterFactory;
	SourceFactoryIF sourceFactory;
	
   
    
    
    
    
    
    
    
    
    
    @Test
    public void testPrintBlockIF() throws IOException {
    	Exam e = new Exam(true);	
    	String testString = "test string";
		
    	//adds the block element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
		Block blockTest = new Block("test topic", testSource );
		e.addElement(blockTest);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	String testData = "";
    	try {
			testData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.compareTo(testString), 0);
    }
    
    
    
    
    
    
    @Test
    public void testPrintFigureIF() throws IOException {
    	Exam e = new Exam(true);	
    	String testString = "test string";
		
    	//adds the figure element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
		Figure figureTest = new Figure("test topic", testSource );
		e.addElement(figureTest);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	String testData = "";
    	try {
			testData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a figure
		assertEquals(testData.compareTo(testString), 0);
    }
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testPrintProblem() throws IOException {
    	Exam e = new Exam(true);	
    	String testString = "test string";
		
    	//adds the block element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
    	
    	//creates an answer array
    	Source ans1Source = new Source("ans1");
		ans1Source.addText("this is the answer 1");
		Answer ans1 = new Answer(true,ans1Source);
		Source ans2Source = new Source("ans2");
		ans2Source.addText("this is the answer 2");
		Answer ans2 = new Answer(true,ans2Source);
		Answer[] answerArray = {ans1, ans2};
		
		ProblemIF problemTest = new ProblemStub("topic", "label" ,testSource, answerArray);
		e.addElement(problemTest);
    
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	//checks the question
    	String questionData = "";
    	try {
			questionData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	assertEquals(questionData.compareTo(testString), 0);
   
    	//checks ans1 data is correct
		String ans1Data = "";
    	try {
			ans1Data = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	assertEquals(ans1Data.compareTo("this is the answer 1"), 0);
	    
	 	//checks ans1 data is correct
		String ans2Data = "";
    	try {
			ans2Data = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	assertEquals(ans2Data.compareTo("this is the answer 2"), 0);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testPrintProblemWithBlock(){
    	Exam e = new Exam(true);	
    	String testString = "test string";
		
    	//adds the block element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
    	
    	//creates an answer array
    	Source ans1Source = new Source("ans1");
		ans1Source.addText("this is the answer 1");
		Answer ans1 = new Answer(true,ans1Source);
		Source ans2Source = new Source("ans2");
		ans2Source.addText("this is the answer 2");
		Answer ans2 = new Answer(true,ans2Source);
		Answer[] answerArray = {ans1, ans2};
		
		ProblemIF problemTest = new ProblemStub("topic", "label" ,testSource, answerArray);
		e.addElement(problemTest);
    
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	//checks the question
    	String questionData = "";
    	try {
			questionData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	assertEquals(questionData.compareTo(testString), 0);
   
    	//checks ans1 data is correct
		String ans1Data = "";
    	try {
			ans1Data = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	assertEquals(ans1Data.compareTo("this is the answer 1"), 0);
	    
	 	//checks ans1 data is correct
		String ans2Data = "";
    	try {
			ans2Data = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	assertEquals(ans2Data.compareTo("this is the answer 2"), 0);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testFrontMatter(){
    	Exam e = new Exam(true);	
    	String testString = "test string";
    	
    	//sets the  front matter
    	Source frontMatter = new Source("frontmatter.txt");
    	frontMatter.addText(testString);
    	e.setFrontMatter(frontMatter);
    
    	//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	String testData = "";
    	try {
			testData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored the front matter
		assertEquals(testData.compareTo(testString), 0);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testPreamble(){
    	Exam e = new Exam(true);	
    	String testString = "test string";
    	
    	//sets the  front matter
    	Source preamble = new Source("preamble");
    	preamble.addText(testString);
    	e.setPreamble(preamble);

    	//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	String testData = "";
    	try {
			testData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in preamble
		assertEquals(testData.compareTo(testString), 0);
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test 
    public void testFinalBlock(){
    	Exam e = new Exam(true);	
    	String testString = "test string";
    	
    	//sets the  final block
    	Source finalSource = new Source("finalBlock.txt");
		finalSource.addText(testString);
    	Block finalBlock = new Block("Final topic", finalSource);
		e.setFinalBlock(finalBlock);
		

    	//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
    	//the examWriter writes out to the printwriter
    	try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	//read in the data 
    	FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	DataInputStream dataInput = new DataInputStream(fileInput);
    	
    	String testData = "";
    	try {
			testData = dataInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a final matter
		assertEquals(testData.compareTo(testString), 0);
    }

    
    
    
    
    
    
    
    
    
    
    
    /* tested block output if block is multiple lines
     */
	@Test
	public void testPrintBlockIF2() throws IOException {
		Exam e = new Exam(true);	
		String testString = "\\being{verbatim}" + "\n" + 
		"%Description: " + "\n" + 
		"%Tests the circleArea function that takes a radius and returns an area." + "\n" +
		"%Example (circleArea): " + "\n";

		//adds the block element   
		Source testSource = new Source("test file");
		testSource.addText(testString);
		Block blockTest = new Block("test topic", testSource );
		e.addElement(blockTest);

		//creates an exam writer
		ExamWriter ew = new ExamWriter(e);
		String filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test.txt";
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		//the examWriter writes out to the printwriter
		try {
			ew.write(pw);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		File aFile = new File(filename);
		BufferedReader input = new BufferedReader(new FileReader(aFile));
		String line = "";
		StringBuffer testData = new StringBuffer();
		while((line = input.readLine()) != null){
			testData.append(line);
			testData.append("\n");
			}
		testString += "\n";

		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString), 0);
	}    
}//end of class
