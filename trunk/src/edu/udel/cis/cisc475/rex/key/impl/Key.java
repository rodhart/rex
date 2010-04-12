package edu.udel.cis.cisc475.rex.key.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**
 * @author cardona
 *
 */
public class Key implements AnswerKeyIF {

//constructor
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

//getters
public	String 	version () 	{return _version;} 
public  String 	examName()	{return _examName;} 
public  String 	date()		{return _date;} 
public  int 	numProblems() {return _numAnswers;} 

public Collection <String> answers(int index) {	
	//since _numAnswers is 1 based and index is 0 based use less than
	if (index<_numAnswers)
	return _Answers.get(index);
		//else
 		return error_out_of_bounds;
}

//setters
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
