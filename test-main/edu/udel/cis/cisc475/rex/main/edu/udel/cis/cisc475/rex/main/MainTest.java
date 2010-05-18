package edu.udel.cis.cisc475.rex.main;

import org.junit.Test;
import edu.udel.cis.cisc475.rex.main.Rex;

public class MainTest {
	@Test
	public void RunCompletionTest(){
	String [] args = {"examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	@Test
	public void SeedRunCompletionTest(){
	String [] args = {"-seed","234","examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	@Test
	public void NRunCompletionTest(){
	String [] args = {"-n","3","examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	@Test
	public void PDFRunCompletionTest(){
	String [] args = {"-pdf","examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	@Test
	public void BadArgsTest(){
	String [] args = {"--h","examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	@Test
	public void doubleArgsTest(){
	String [] args = {"-seed","2","-seed","4","examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
}
