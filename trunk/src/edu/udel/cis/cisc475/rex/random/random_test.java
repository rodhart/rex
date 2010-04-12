package edu.udel.cis.cisc475.rex.random;




import java.util.Random;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.RandomizerFactory;

/**
 * @author  CARDONA
 *
 */
public class random_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//create some objects
		String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		for(int i = 0; i < 3; i++) {
			System.out.printf("in random test main before call %s\n", a[i]);
			}

		long seed = 3;
		
//		note to self
//		without factory since constructor is public
//		RandomizerIF R = new Randomizer (seed);

		
//		note to self
//		with static factory method
//		-note that since interfaces cannot have
//		factory methods, this example
//		will only work with classes that are not implementing
//		interfaces
//		RandomizerIF R = RandomizerFactory.newRandomizer (seed);

		
// without using static, we must create instance 
// of factory to use it
		RandomizerFactoryIF RF = new RandomizerFactory(); 
		RandomizerIF R = RF.newRandomizer(seed);
		
		Object[] b = new String[10];
		b =  R.choose (3, a);		
	
		
		for(int i = 0; i < 3; i++) {
		System.out.printf("in random test main after return from call %s\n", b[i]);
		}

	}//end of main



}//end of class
