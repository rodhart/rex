package edu.udel.cis.cisc475.rex.key;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;

/**
 * @author cardona
 *
 */
public class AnswerKeyJunitTest {



	@Test
	public void test_version() {
		String a = "version string name";
		String b = "exam string name ";
		String c = "date string";
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey(a, b, c);		
		boolean result = a.equals(K.version());
		assertEquals (true, result);
	}

	@Test
	public void test_examName() {
		String a = "version string name";
		String b = "exam string name ";
		String c = "date string";
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey(a, b, c);		
		boolean result = b.equals(K.examName());
		assertEquals (true, result);
	}


	@Test
	public void test_date() {
		String a = "version string name";
		String b = "exam string name ";
		String c = "date string";
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey(a, b, c);		
		boolean result = c.equals(K.date());
		assertEquals (true, result);
	}



	@Test
	public void test_collections() {
		String a = "version string name";
		String b = "exam string name ";
		String c = "date string";
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey(a, b, c);		
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

		boolean result = (3 == K.numProblems());
		assertEquals (true, result);

		result = Answer_a.equals(K.answers(0));
		assertEquals (true, result);

		result = Answer_b.equals(K.answers(1));
		assertEquals (true, result);

		result = Answer_c.equals(K.answers(2));
		assertEquals (true, result);
	}//end of test_collections() 	


}//end of class
