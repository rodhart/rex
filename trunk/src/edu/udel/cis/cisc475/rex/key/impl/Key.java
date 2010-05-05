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

	/**
	 * constructor initializes the answer key
	 * and sets all the initial variable
	 * there are no answers loaded at this time
	 * so the collection is initialized but is empty
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
	 * simple getter that returns data about the answer key
	 * @return the exam version that is set by user
	 */
	public String version()	{
		return Version;
	} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the exam name that is set by user
	 */
	public String examName() {
		return ExamName;
	} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the exam date that is set by user, may not be true date
	 */
	public String date() {
		return OurDate;
	} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the number of problems that have been inputted 
	 */
	public int numProblems() {
		return NumAnswers;
	} 

	/**
	 * getter that returns answer(s) given a problem index
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
	 * setter that adds answer(s) 
	 * it is assumed that the problem answers will be given in order
	 * so the index is merely incremented
	 * 
	 * @param nextAnswer collection of answers (maybe just one) 
	 */
	public void addProblem( Collection <String> nextAnswer) {
		Answers.add(nextAnswer);
		NumAnswers ++;
	}

	//private vars
	private String Version;
	private String ExamName;	
	private String OurDate;	
	private int NumAnswers;
	private ArrayList < Collection<String> > Answers;
}//end of class 
