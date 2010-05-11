package edu.udel.cis.cisc475.rex.key.impl;

import java.util.ArrayList;
import java.util.Collection;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * 
* Class which builds a representation of an answer key in addition to the
 * indexed answers themselves.
 * ~~
 * These answers can take the form of individual letters or
 * strings of text.
 * ~~
 * Additionally, a specific problem can have more than one correct answer
 * so the representation of a given problem will be an array of answers
 * (which most of the time will contain only one element).
  * 
 * @author cardona
 */
public class Key implements AnswerKeyIF {

	//private variables
	private String Version;
	private String ExamName;	
	private String OurDate;	
	private int NumAnswers;
	private ArrayList < Collection<String> > Answers;
	
	/**
	 * Constructor initializes the answer key
	 * and sets all the initial variables.
	 * There are no answers loaded at this time
	 * so the collection is initialized, but empty.
	 * 
	 * @param version describes the exam version that is set by user
	 * @param examName describes the exam name that is set by user
	 * @param date describes the exam date that is set by user, may not be true date
	 */
	public Key(String version, String examName, String date) {
		//init private vars
		NumAnswers = 0;
		Version = version;
		ExamName = examName;	
		OurDate = date;	
		Answers = new ArrayList< Collection<String>  >();
	}//end of constructor


	/**
	 * Simple getter that returns the version of the answer key
	 * @return the exam version that is set by user
	 */
	public String version()	{
		return Version;
	} 

	/**
	 * Simple getter that returns the name of the exam that the answer key goes to
	 * @return the exam name that is set by user
	 */
	public String examName() {
		return ExamName;
	} 

	/**
	 * Simple getter that returns the date that the answer key was generated
	 * @return the exam date that is set by user, may not be true date
	 */
	public String date() {
		return OurDate;
	} 

	/**
	 * Simple getter that returns the number of problems in the answer key
	 * @return the number of problems that have been inputed 
	 */
	public int numProblems() {
		return NumAnswers;
	} 

	/**
	 * Getter that returns answer(s) given a problem index
	 * @param index the problem number that we want answers for
	 * @return a collection of correct answers (maybe just one)
	 * @throws IndexOutOfBoundsException Throws exception when index > number of answers 
	 */
	public Collection <String> answers(int index) {	
		//since NumAnswers is 1 based and index is 0 based use less than
		if (index<NumAnswers)
			return Answers.get(index);
		else
			throw new IndexOutOfBoundsException("index: " + index + " NumAnswers: " + NumAnswers);
	}

	//setters
	/**
	 * Setter that adds answer(s) 
	 * It is assumed that the problem answers will be given in order
	 * so the index is merely incremented
	 * 
	 * @param nextAnswer collection of answers (maybe just one) 
	 */
	public void addProblem( Collection <String> nextAnswer) {
		Answers.add(nextAnswer);
		NumAnswers ++;
	}

}//end of class 
