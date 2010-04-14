package edu.udel.cis.cisc475.rex.key.impl;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

/**	
 * this class is a factory which creates a new instance of 
 * an answer key
 * 
 *   @author CARDONA
 * 
 */
public class AnswerKeyFactory implements AnswerKeyFactoryIF {

	/**
	 * do nothing constructor	
	 */	public 	AnswerKeyFactory () {
	 }//end of constructor



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
	 public AnswerKeyIF newAnswerKey(String version, String examName, String date) {
		 return new Key(version, examName, date);
	 }

}//end of class
