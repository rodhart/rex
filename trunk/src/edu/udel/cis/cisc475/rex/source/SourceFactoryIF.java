package edu.udel.cis.cisc475.rex.source.IF;

/**
 * 


/**
 * @version .001 
 * @author team 5
 *
 */
//public interface SourceFactoryIF {

//}


public class SourceFactoryIF {
/**
 * factory method returns an object that implements SourceIF interface.
 */
	public SourceIF newSource(String filename){
		Source S = new Source(filename);  
		return S;
	
	}//end of newSource
}//end of class SourceFactoryIF 


