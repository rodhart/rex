package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.output.ExamWriter;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.Source;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class ExamWriterTest {
	
	ExamFactoryIF examFactory;
	OutputFactoryIF examWriterFactory;
	SourceFactoryIF sourceFactory;
	
	@Before
	public void setUp() {
		examFactory = new ExamFactory();
	    sourceFactory = new SourceFactory();
	    examWriterFactory = new OutputFactory();
	}
	
    @Test
    public void testExamWriterNull() {
		Exam e = examFactory.newGeneratedExam();
		ExamWriter ew = examWriterFactory.newExamWriter(e);
		assertNotNull(ew);	
	}
    
    @Test
    public void testPrintBlockIF(){
    	Exam e = examFactory.newGeneratedExam();	
    	Source testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Block blockTest = examFactory.newBlock("test topic", "test label", testSource );
    	e.addElementIF(blockTest);
    	ExamWriter ew = examWriterFactory.newExamWriter(e);
    }
    
    @Test
    public void testPrintFigureIF(){
    	Exam e = examFactory.newGeneratedExam();	
    	Source testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Figure figureTest = examFactory.newFigure("test label", testSource );
    	e.addElementIF(figureTest);
    	ExamWriter ew = examWriterFactory.newExamWriter(e);
    }
    
    @Test
    public void testPrintProblem(){
    	Exam e = examFactory.newGeneratedExam();	
    	Source testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	Answer answerTest = examFactory.newAnswer(true, testSource);
    	Answer answerTest2 = examFactory.newAnswer(false, testSource);
    	Answer[] answerArrayTest = {answerTest, answerTest2};
    	
    	//Problem problemTest = new Problem("test topic", "test label", testSource, answerArrayTest );
    	//e.addElementIF(problemTest);
    	ExamWriter ew = examWriterFactory.newExamWriter(e);    
    }
    
}
