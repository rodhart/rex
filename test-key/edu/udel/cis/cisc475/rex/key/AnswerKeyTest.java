package edu.udel.cis.cisc475.rex.key;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;

/**
 * Junit Test for AnswerKey
 * 
 * @author cardona
 *
 */
public class AnswerKeyTest {

	@Test
	// test getter
	public void test_version() {
		boolean result = a.equals(K.version());
		assertEquals (true, result);
	}

	@Test
	// test getter
	public void test_examName() {
		boolean result = b.equals(K.examName());
		assertEquals (true, result);
	}


	@Test
	// test getter
	public void test_date() {
		boolean result = c.equals(K.date());
		assertEquals (true, result);
	}



	@Test
	//test adding problems to collection
	//and test getting those problems back out
	public void test_collections() {
		boolean result; 
		//create temp containers
		Collection <String> Answer_a = new ArrayList <String>(); 
		Answer_a.add(new String("B"));
		//more than one correct answer
		Collection <String> Answer_b = new ArrayList <String>(); 
		Answer_b.add(new String("A"));
		Answer_b.add(new String("C"));
		Collection <String> Answer_c = new ArrayList <String>(); 
		Answer_c.add(new String("B"));

		//add problems to our class from the containers
		K.addProblem(Answer_a);	 	
		K.addProblem(Answer_b);	 	
		K.addProblem(Answer_c);	 	

		//test that problems are actually there	
		result = (3 == K.numProblems());
		assertEquals (true, result);

		result = Answer_a.equals(K.answers(0));
		assertEquals (true, result);

		result = Answer_b.equals(K.answers(1));
		assertEquals (true, result);

		result = Answer_c.equals(K.answers(2));
		assertEquals (true, result);
	
		//make an out of bounds call
		Collection <String> error_c = new ArrayList <String>();
		error_c.add(new String("error_out_of_bounds"));
		result = error_c.equals(K.answers(3));
		assertEquals (true, result);
	}//end of test_collections() 	

	
	
	
	
	
	
	

	//class variables
	private String a = "version string name";
	private String b = "exam string name ";
	private String c = "date string";
	private AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
	private AnswerKeyIF K = AKF.newAnswerKey(a, b, c);		
}//end of class
