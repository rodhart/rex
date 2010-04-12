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
}//end of constructor

//getters
public	String 	version () 	{return _version;} 
public  String 	examName()	{return _examName;} 
public  String 	date()		{return _date;} 
public  int 	numProblems() {return _numAnswers;} 
public Collection <String> answers(int index) {	return _Answers.get(index);} 

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
}//end of class 
