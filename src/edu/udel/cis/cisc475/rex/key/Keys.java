package edu.udel.cis.cisc475.rex.key;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;

/**	
 * this class uses a static method used to create the factory which creates 
 * a new instance of an answer key
 * 
 * this is an alternate way to do factories with interfaces
 * instead of creating an instance of the factory (saves memory)
 *   @author siegel
 *   @author cardona
 * 
 */
public class Keys {

	/**	
	 * static method used to create the factory which creates 
	 * a new instance of an answer key
	 * 
	 * this is an alternate way to do factories with interfaces
	 * instead of creating an instance of the factory (saves memory)
	 * 
	 */
	public static AnswerKeyFactoryIF newAnswerKeyFactory() {
		return new AnswerKeyFactory();
	}

}//end of class
