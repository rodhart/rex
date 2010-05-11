package edu.udel.cis.cisc475.rex.key.impl;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**	
 * This class is a factory which creates a new instance of 
 * an answer key.
 * 
 *   @author cardona
 * 
 */
public class AnswerKeyFactory implements AnswerKeyFactoryIF {

	/**
	 * Empty constructor
	 */	
	public 	AnswerKeyFactory () {
	}//end of constructor



	/**
	 * Constructor initializes the answer key
	 * and sets all the initial variables.
	 * There are no answers loaded at this time
	 * so the collection is initialized, but empty.
	 * 
	 * @param version The exam version that is set by user
	 * @param examName The exam name that is set by user
	 * @param date The date that the exam was generated
	 */
	public AnswerKeyIF newAnswerKey(String version, String examName, String date) {
		return new Key(version, examName, date);
	}

}//end of class
