package edu.udel.cis.cisc475.rex.main;

import org.junit.Test;
import edu.udel.cis.cisc475.rex.main.Rex;

public class MainTest {
	@Test
	public void RunCompletionTest(){
	String [] args = {"examples/exam_cisc106.tex","examples/exam_cisc106.ecf"};

	Rex.main(args);	
	}
	
	
}
