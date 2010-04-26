package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;
import edu.udel.cis.cisc475.rex.output.impl.OutputFactory;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * Junit Test of OutputFactory
 * 
 * @author iburns
 *
 */
public class OutputFactoryTest {
	
	ExamFactoryIF examFactory;
	OutputFactory outputFactory;
	AnswerKeyFactoryIF answerKeyFactory; 
	
	@Before
	public void setUp() {
		examFactory = new ExamFactory();
		outputFactory = new OutputFactory();
		answerKeyFactory = new AnswerKeyFactory();
		
	}

	/**
	 * Test method for newExamWriter.
	 */
	@Test
	public void testOutputFactory() {
		
		OutputFactory OF1 = outputFactory;
		
		OutputFactory OF2 = outputFactory;
		
		boolean OFCompare = OF1.equals(OF2);
		
		assertTrue(OFCompare);
	}
	
	/**
	 * Test method for newExamWriter.
	 */
	@Test
	public void testNewExamWriter() {
		
		OutputFactory OF = outputFactory;
		
		ExamIF exam = examFactory.newGeneratedExam();
		
		ExamWriterIF ewIF = OF.newExamWriter(exam);
		
		assertNotNull(ewIF);
	}
	
	
	/**
	 * Test method for comparing two newExamWriters
	 */
	@Test
	public void testCompareNewExamWriter() {
		
		OutputFactory OF = outputFactory;
		
		ExamIF exam = examFactory.newGeneratedExam();
				
		ExamWriterIF ewIF1 = OF.newExamWriter(exam);
		
		ExamWriterIF ewIF2 = OF.newExamWriter(exam);
		
		boolean ewCompare = ewIF1.equals(ewIF2);
		
		assertTrue(ewCompare);
	}
	

	/**
	 * Test method for newAnswerKeyWriter
	 */
	@Test
	public void testNewAnswerKeyWriter() {
		
		OutputFactory OF = outputFactory;

		AnswerKeyIF answerKey = answerKeyFactory.newAnswerKey("version", "examName", "date");
		
		AnswerKeyWriterIF akf = OF.newAnswerKeyWriter(answerKey);
		
		assertNotNull(akf);
	}

	/**
	 * Test method for comparing newAnswerKeyWriter
	 */
	@Test
	public void testCompareNewAnswerKeyWriter() {
		
		OutputFactory OF = outputFactory;

		AnswerKeyIF answerKey = answerKeyFactory.newAnswerKey("version", "examName", "date");
		
		AnswerKeyWriterIF akf1 = OF.newAnswerKeyWriter(answerKey);
		
		AnswerKeyWriterIF akf2 = OF.newAnswerKeyWriter(answerKey);
		
		boolean akfCompare = akf1.equals(akf2);
		
		assertTrue(akfCompare);
	}
	
}
