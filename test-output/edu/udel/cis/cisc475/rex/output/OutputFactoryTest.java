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
	public void testNewExamWriter() {
		
		OutputFactory OF = new OutputFactory();
		
		ExamIF exam = examFactory.newGeneratedExam();
		
		ExamWriterIF ewIF = OF.newExamWriter(exam);
		
		assertNotNull(ewIF);
	}

	/**
	 * Test method for newAnswerKeyWriter
	 */
	@Test
	public void testNewAnswerKeyWriter() {
		
		OutputFactory OF = new OutputFactory();

		AnswerKeyIF answerKey = answerKeyFactory.newAnswerKey("version", "examName", "date");
		
		AnswerKeyWriterIF akf = OF.newAnswerKeyWriter(answerKey);
		
		assertNotNull(akf);
	}

}
