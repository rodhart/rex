package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;

import static org.junit.Assert.*;


public class ExamWriterTest {

	public void testExamWriterNull {
		
		Exam e = new Exam(true);
		
		ExamWriter ew = new ExamWriter(e);
		
		assertNotNull(ew);	
	}
	
}
