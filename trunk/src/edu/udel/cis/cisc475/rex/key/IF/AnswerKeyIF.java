package edu.udel.cis.cisc475.rex.key.IF;

import java.util.Collection;

/**
 * 
 * class which builds a representation of an answer key
 * store information about the answer key in addition to the
 * indexed answers themselves
 * 
 * these answers can take the form of individual letters or
 * strings of text
 * 
 * additionally, a specific problem can have more than one correct answer
 * so the representation of a given problem will be an array of answers
 * which most of the time will contain only one element
 * 
 * 
 * @author cardona
 */
public interface AnswerKeyIF {

	/**
	 * simple getter that returns data about the answer key
	 * 
	 * @return the exam version that is set by user
	 */
	String version(); 
	
	/**
	 * simple getter that returns data about the answer key
	 * 
	 * @return the exam name that is set by user
	 */
	String examName(); 
	
	/**
	 * simple getter that returns data about the answer key
	 * 
	 * @return the exam date that is set by user, may not be true date
	 */
	String date(); 
	
	/**
	 * simple getter that returns data about the answer key
	 * 
	 * @return the number of problems that have been inputted 
	 */
	int numProblems(); 
	
	/**
	 * getter that returns answer(s) given a problem index
	 * 
	 * @param i index of the problem number that we want answers for
	 * @return a collection of correct answers (maybe just one) 
	 */
	Collection <String> answers(int i); 
	

	/**
	 * setter that adds answer(s) 
	 * it is assumed that the problem answers will be given in order
	 * so the index is merely incremented
	 * 
	 * @param problem collection of answers (maybe just one) 
	 */
	void addProblem( Collection <String> Problem);
}//end of interface 
 
