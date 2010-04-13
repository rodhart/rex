package edu.udel.cis.cisc475.rex.output;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.output.ExamWriter;

public class ExamWriterTest2 {
	
    @Test
    public void testExamWriterNull() {
		
		Exam e = new Exam(true);
		
		ExamWriter ew = new ExamWriter(e);
		
		assertNotNull(ew);	
	}
	
}
