package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;

import edu.udel.cis.cisc475.rex.output.ExamWriter;




public class ExamWriterTest {

	public void testExamWriterNull {
		
		Exam e = new Exam(true);
		
		ExamWriter ew = new ExamWriter(e);
		
		assertNotNull(ew);	
	}
	
}
