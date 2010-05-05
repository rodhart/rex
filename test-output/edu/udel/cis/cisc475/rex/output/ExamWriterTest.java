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
    	String testString2 = "\\documentclass[master]{exam}\n" +
    			"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    			"\\begin{document}\n" +
    			"test string\n" +
    			"\\end{document}\n";
		
    	//adds the block element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
		Block blockTest = new Block("test topic", testSource );
		e.addElement(blockTest);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}
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
    	
    	String line = "";
		StringBuffer testData = new StringBuffer();
		while((line = dataInput.readLine()) != null){
			testData.append(line);
			testData.append("\n");
			}
		
		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
		
		
    }
    
    @Test
    public void testPrintFigureIF() throws IOException {
    	Exam e = new Exam(true);	
    	String testString = "test string";
		String testString2 = "\\documentclass[master]{exam}\n" +
				"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    			"\\begin{document}\n" +
    			"\\newpage\n" +
    			"test string\n" +
    			"\\label{fig:test topic}\n" +
    			"\\end{document}\n";

		
		//adds the figure element   
		Source testSource = new Source("test file");
    	testSource.addText(testString);
		Figure figureTest = new Figure("test topic", testSource );
		e.addElement(figureTest);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}
    	
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
    	
    	String line = "";
		StringBuffer testData = new StringBuffer();
		while((line = dataInput.readLine()) != null){
			testData.append(line);
			testData.append("\n");
			}

		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
        }
      
    @Test
    public void testFrontMatter(){

    	Exam e = new Exam(true);	
    	String testString = "test string";    	
    	String testString2 = "\\documentclass[master]{exam}\n" +
				"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    			"\\begin{document}\n" +
    			"test string\n" +
    			"\\end{document}\n";
		
    	//sets the  front matter
    	Source frontMatter = new Source("frontmatter.txt");
    	frontMatter.addText(testString);
    	e.setFrontMatter(frontMatter);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}		
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
    	
    	String line = "";
		StringBuffer testData = new StringBuffer();
		try {
			while((line = dataInput.readLine()) != null){
				testData.append(line);
				testData.append("\n");
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
    }
    
    @Test
    public void testPreamble(){
    	

    	Exam e = new Exam(true);	
    	String testString = "test string";    	
    	String testString2 = "\\documentclass[master]{exam}\n" +
    			"test string\n" +
    			"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    			"\\begin{document}\n" +
    			"\\end{document}\n";
		
    	//adds the block element   
    	//sets the  front matter
    	Source preamble = new Source("preamble");
    	preamble.addText(testString);
    	e.setPreamble(preamble);
    	
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}
    	
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
    	
    	String line = "";
		StringBuffer testData = new StringBuffer();
		try {
			while((line = dataInput.readLine()) != null){
				testData.append(line);
				testData.append("\n");
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
    }
    
    @Test 
    public void testFinalBlock(){
    	

		Exam e = new Exam(true);	
    	String testString = "test string";    	
    	String testString2 = "\\documentclass[master]{exam}\n" +
				"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    			"\\begin{document}\n" +
    			"test string\n" +
    			"\\end{document}\n";
		
    	//sets the  final block
    	Source finalSource = new Source("finalBlock.txt");
		finalSource.addText(testString);
    	Block finalBlock = new Block("Final topic", finalSource);
		e.setFinalBlock(finalBlock);
		
		//creates an exam writer
    	ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}
    	
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
    	
    	String line = "";
		StringBuffer testData = new StringBuffer();
		try {
			while((line = dataInput.readLine()) != null){
				testData.append(line);
				testData.append("\n");
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
       }

    /* tested block output if block is multiple lines
     * and is master is false
     */
	@Test
	public void testPrintBlockIF12() throws IOException {
		Exam e = new Exam(false);	
    	String testString = "inside the block";
    	
    	String testString2 = "\\documentclass{exam}" + "\n" +
		"\\newcommand{\\examversion}{" + e.version() + "}\n" +
    	"\\begin{document}" + "\n" +    	
    	//"\\begin{block}" + "\n" +
    	"inside the block" + "\n" +
    	//"\\end{block}" + "\n" +
    	"\\end{document}" + "\n";

		//adds the block element   
		Source testSource = new Source("test file");
		testSource.addText(testString);
		Block blockTest = new Block("test topic", testSource );
		e.addElement(blockTest);

		//creates an exam writer
		ExamWriter ew = new ExamWriter(e);
    	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
    	File theDir = new File(dirName);
    	String filename = "";
    	if (theDir.exists()) {
    		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	} else { // assume only trunk was checked out -- fix this to add
				 // another check & throw exception if everything is messed up!
    		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
    	}
    	
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
//		System.out.println(testData);

	//	System.out.println(testString2);

		//checks if the saved testData is the same as the 
		//testString that was stored in a block
		assertEquals(testData.toString().compareTo(testString2), 0);
	}    

/* tested block output if block is multiple lines
 * and test figure
 * and is master is false
 */
@Test
public void testPrintBlockIF13() throws IOException {
	Exam e = new Exam(false);	
	String testString3 = "inside the figure";
	String testString2 = "\\documentclass{exam}" + "\n" +
	"\\newcommand{\\examversion}{" + e.version() + "}\n" +
	"\\begin{document}" + "\n" +    	
	"\\newpage" + "\n" +
	//"\\begin{figure}" + "\n" +
	"inside the figure" + "\n" +
	"\\label{fig:" + "test topic" + "}" + "\n" +
	//"\\end{figure}" + "\n" +
	"\\end{document}" + "\n";
	
	//adds the figure element   
	Source testSource2 = new Source("test file");
	testSource2.addText(testString3);
	Figure figureTest = new Figure("test topic", testSource2 );
	e.addElement(figureTest);

	//creates an exam writer
	ExamWriter ew = new ExamWriter(e);
	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
	File theDir = new File(dirName);
	String filename = "";
	if (theDir.exists()) {
		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
	} else { // assume only trunk was checked out -- fix this to add
			 // another check & throw exception if everything is messed up!
		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
	}
	
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
//	System.out.println(testData);
//	System.out.println(testString2);
	//checks if the saved testData is the same as the 
	//testString that was stored in a block
	assertEquals(testData.toString().compareTo(testString2), 0);
}    

/* tested problem output if multiple lines
 * and is master is false
 */
@Test
public void testPrintBlockIF15() throws IOException {
	Exam e = new Exam(false);	
	String testString = "inside the problem";
	
	String testString2 = "\\documentclass{exam}" + "\n" +
	"\\newcommand{\\examversion}{" + e.version() + "}\n" +
	"\\begin{document}" + "\n" +    	
	"\\begin{problem}{topic}{0.0}" + "\n" + "\n" +
	"\\begin{answers}" + "\n" +
	"this is the answer 1" + "\n" +
	"this is the answer 2" + "\n" +
	"\\end{answers}" + "\n" +
	"\\end{problem}" + "\n" +
	"\\end{document}" + "\n";
	
	//adds the block element   
	Source testSource = new Source("test file");
//	testSource.addText(testString);

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
	String dirName = "./trunk/test-output/edu/udel/cis/cisc475/rex/output";
	File theDir = new File(dirName);
	String filename = "";
	if (theDir.exists()) {
		filename = "./trunk/test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
	} else { // assume only trunk was checked out -- fix this to add
			 // another check & throw exception if everything is messed up!
		filename = "./test-output/edu/udel/cis/cisc475/rex/output/test2.txt";
	}
	
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
//	System.out.println(testData);
//	System.out.println(testString2);
	//checks if the saved testData is the same as the 
	//testString that was stored in a block
	assertEquals(testData.toString().compareTo(testString2), 0);
}    

}//end of class

