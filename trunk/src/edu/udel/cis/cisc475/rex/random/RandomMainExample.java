package edu.udel.cis.cisc475.rex.random;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * general purpose tester of the random method
 * this does not replace junit but complements it
 * 
 * @author  CARDONA
 *
 */
public class RandomMainExample {

	/**
	 * general purpose tester of the random method
	 * this does not replace junit but complements it
	 * @param args - not accepting any arguments
	 */
	public static void main(String[] args) {

		//create some objects
		String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
				"two_one", "two_two", "two_three", "two_four", "two_five", "two_six", "two_seven", "two_eight", "two_nine", "two_ten",
				"three_one", "three_two", "three_three", "three_four", "three_five", "three_six", "three_seven", "three_eight", "three_nine", "three_ten",
				"four_one", "four_two", "four_three", "four_four", "four_five", "four_six", "four_seven", "four_eight", "four_nine", "four_ten"};

		long seed = 3;

		//	note to self
		//	without factory since constructor is public
		//	RandomizerIF R = new Randomizer (seed);


		//	note to self
		//	with static factory method
		//	-note that since interfaces cannot have
		//	factory methods, this example
		//	will only work with classes that are not implementing
		//	interfaces
		//	RandomizerIF R = RandomizerFactory.newRandomizer (seed);


		// without using static, we must create instance 
		// of factory to use it
		RandomizerFactoryIF RF = new RandomizerFactory(); 
		RandomizerIF R = RF.newRandomizer(seed);

		Object[] b = new String[10];
		b =  R.choose (10, a);		


		for(int i = 0; i < 10; i++) {
			System.out.printf("in random test main after return from call %s\n", b[i]);
		}

	}//end of main
}//end of class
