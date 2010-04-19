package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.output.ExamWriter;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
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
		ExamIF e = examFactory.newGeneratedExam();
		ExamWriterIF ew = examWriterFactory.newExamWriter(e);
		assertNotNull(ew);	
	}
    
    @Test
    public void testPrintBlockIF(){
    	ExamIF e = examFactory.newGeneratedExam();	
    	SourceIF testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	BlockIF blockTest = examFactory.newBlock("test topic", "test label", testSource );
    	e.addElementIF(blockTest);
    	ExamWriterIF ew = examWriterFactory.newExamWriter(e);
    }
    
    @Test
    public void testPrintFigureIF(){
    	ExamIF e = examFactory.newGeneratedExam();	
    	SourceIF testSource = sourceFactory.newSource("./trunk/test-source/edu/udel/cis/cisc475/rex/source/SampleText.txt");
    	FigureIF figureTest = examFactory.newFigure("test label", testSource );
    	e.addElementIF(figureTest);
    	ExamWriterIF ew = examWriterFactory.newExamWriter(e);
    }
    
    @Test
    public void testPrintProblem(){
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
