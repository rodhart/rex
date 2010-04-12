package edu.udel.cis.cisc475.rex.key.impl;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public class AnswerKeyFactory implements AnswerKeyFactoryIF {


	//do nothing constructor	
	public 	AnswerKeyFactory () {
	}//end of constructor


	
	public AnswerKeyIF newAnswerKey(String version, String examName, String date) {
		return new Key(version, examName, date);
	}

}//end of class
