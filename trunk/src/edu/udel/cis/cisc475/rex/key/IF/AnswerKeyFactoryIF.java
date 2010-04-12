package edu.udel.cis.cisc475.rex.key.IF;

/**	
* this class is a factory which creates a new instance of 
* an answer key
* 
*   @author CARDONA
* 
*/
public interface AnswerKeyFactoryIF {

	/**
	 * constructor initiallizes the answer key
	 * and sets all the initial variable
	 * there are no answers loaded at this time
	 * so the collection is initialized but is empty
	 * 
	 * @param version describes the exam version that is set by user
	 * @param examName describes the exam name that is set by user
	 * @param date describes the exam date that is set by user, may not be true date
	 */
AnswerKeyIF newAnswerKey(String version, String examName, String date);
}//end of interface

