package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.output.impl.ExamWriter;
import edu.udel.cis.cisc475.rex.output.impl.OutputFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.Source;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

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
	
	@Before
	public void setUp() {
		//examFactory = new ExamFactory();
	    //sourceFactory = new SourceFactory();
	    //examWriterFactory = new OutputFactory();
	}
	
    @Test
    public void testExamWriterNull() {
		//ExamIF e = examFactory.newGeneratedExam();
		//ExamWriterIF ew = examWriterFactory.newExamWriter(e);
		//assertNotNull(ew);	
	}
    
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
    	ew.write(pw);
    	
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
    	ew.write(pw);
    	
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
    	ew.write(pw);
    	
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
    public void testPrintProblem2(){
    	ExamIF e = examFactory.newGeneratedExam();	
    	SourceIF testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	AnswerIF answerTest = examFactory.newAnswer(true, testSource);
    	AnswerIF answerTest2 = examFactory.newAnswer(false, testSource);
    	AnswerIF[] answerArrayTest = {answerTest, answerTest2};
    	
    	
    	//Problem problemTest = new Problem("test topic", "test label", testSource, answerArrayTest );
    	//e.addElementIF(problemTest);
    	ExamWriterIF ew = examWriterFactory.newExamWriter(e);    
    }
}
