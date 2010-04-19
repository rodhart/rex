package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.output.ExamWriter;
import edu.udel.cis.cisc475.rex.source.impl.Source;

public class ExamWriterTest {
	
    @Test
    public void testExamWriterNull() {
		
		Exam e = new Exam(true);
		
		ExamWriter ew = new ExamWriter(e);
		
		assertNotNull(ew);	
	}
    
    @Test
    public void testPrintBlockIF(){
    	Exam e = new Exam(true);	
    	Source testSource = new Source("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Block blockTest = new Block("test topic", "test label", testSource );
    	e.addElementIF(blockTest);
    	ExamWriter ew = new ExamWriter(e);
    }
    
    @Test
    public void testPrintFigureIF(){
    	Exam e = new Exam(true);	
    	Source testSource = new Source("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Figure figureTest= new Figure("test label", testSource );
    	e.addElementIF(figureTest);
    	ExamWriter ew = new ExamWriter(e);
    }
    
    @Test
    public void testPrintProblem(){
    	Exam e = new Exam(true);	
    	Source testSource = new Source("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Answer answerTest = new Answer(true, testSource);
    	Answer answerTest2 = new Answer(false, testSource);
    	Answer[] answerArrayTest = {answerTest, answerTest2};
    	
    	//Problem problemTest = new Problem("test topic", "test label", testSource, answerArrayTest );
    	//e.addElementIF(problemTest);
    	ExamWriter ew = new ExamWriter(e);
    
    }
	
}
