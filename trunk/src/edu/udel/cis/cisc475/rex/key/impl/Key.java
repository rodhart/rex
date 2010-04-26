package edu.udel.cis.cisc475.rex.key.impl;

import java.util.ArrayList;
import java.util.Collection;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

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
		_numAnswers = 0;
		_version = version;
		_examName = examName;	
		_date = date;	
		_Answers = new ArrayList< Collection<String>  >();
		error_out_of_bounds = new ArrayList<String>();
		error_out_of_bounds.add(new String("error_out_of_bounds"));
	}//end of constructor


	/**
	 * simple getter that returns data about the answer key
	 * @return the exam version that is set by user
	 */
	public	String 	version () 	{return _version;} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the exam name that is set by user
	 */
	public  String 	examName()	{return _examName;} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the exam date that is set by user, may not be true date
	 */
	public  String 	date()		{return _date;} 

	/**
	 * simple getter that returns data about the answer key
	 * @return the number of problems that have been inputted 
	 */
	public  int 	numProblems() {return _numAnswers;} 



	/**
	 * getter that returns answer(s) given a problem index
	 * @param index the problem number that we want answers for
	 * @return a collection of correct answers (maybe just one) 
	 */
	public Collection <String> answers(int index) {	
		//since _numAnswers is 1 based and index is 0 based use less than
		if (index<_numAnswers)
			return _Answers.get(index);
		//else
		return error_out_of_bounds;
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
		_Answers.add(nextAnswer);
		_numAnswers ++;
	}

	//private vars
	private String _version;
	private String _examName;	
	private String _date;	
	private int _numAnswers;
	private ArrayList < Collection<String> > _Answers;
	private  Collection <String> error_out_of_bounds;
}//end of class 
