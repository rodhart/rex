package edu.udel.cis.cisc475.rex.key.IF;

import java.util.Collection;


/**
 * @author team 5
 *
 */
public interface AnswerKeyIF {
	String version(); 
	String examName(); 
	String date(); 
	int numProblems(); 
	Collection <String> answers(int i); 
	void addProblem( Collection <String> Problem);
}//end of interface 
 
