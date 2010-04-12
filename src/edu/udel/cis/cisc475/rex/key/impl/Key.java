package edu.udel.cis.cisc475.rex.key.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * @author cardona
 *
 */
public class Key implements AnswerKeyIF {


//constructor
public Key(String version, String examName, String date) {
	//init private vars
	_numProblems = 0;
	_version = version;
	_examName = examName;	
	_date = date;	
	_Problem = new ArrayList<String>();
}//end of constructor

//getters
public	String 	version () 	{return _version;} 
public  String 	examName()	{return _examName;} 
public  String 	date()		{return _date;} 
public  int 	numProblems() {return _numProblems;} 


public Collection <String> answers(int i) { 
	//given an index to a problem
	//this function is supposed to return
	//the correct answer?


	//return one at index?
	//
	//_Problem.get(i);

	//return all?
	return _Problem;

} 

//setters
public void addProblem( Collection <String> Problem) {
	_Problem.addAll(Problem);
	_numProblems += Problem.size();
}
	
//private vars
private String _version;
private String _examName;	
private String _date;	
private int _numProblems;
private Collection<String> _Problem;
}//end of class 
