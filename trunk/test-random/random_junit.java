	import static org.junit.Assert.*;

import org.junit.BeforeClass;
	import org.junit.Test;
	import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
	import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
	import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;
import edu.udel.cis.cisc475.rex.random.impl.Randomizer;

	/**
	 * @author cardona
	 *
	 */
public class random_junit {
private String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
		"two_one", "two_two", "two_three", "two_four", "two_five", "two_six", "two_seven", "two_eight", "two_nine", "two_ten",
		"three_one", "three_two", "three_three", "three_four", "three_five", "three_six", "three_seven", "three_eight", "three_nine", "three_ten",
		"four_one", "four_two", "four_three", "four_four", "four_five", "four_six", "four_seven", "four_eight", "four_nine", "four_ten"};
 
long seed;
	
	
		@Test
		public void test_choose_b() {
			//init a few different randomizers
			seed = 3;
			RandomizerFactoryIF RF = new RandomizerFactory(); 
			RandomizerIF R1 = RF.newRandomizer(seed);
			// initialize several blank objects
			//these are where we will stuff our randomized string arrays
			Object[] b = new String[10];
			Object[] b_check = new String[10];
			b 		=  R1.choose (9, a);		
			b_check =  R1.choose (9, a);		

			boolean result = b.equals(b_check);
	    	assertEquals (true, result);
		}//end of test_choose
		
		
		@Test
		public void test_choose_c() {
			//init a few different randomizers
			seed = 4236;
			RandomizerFactoryIF RF = new RandomizerFactory(); 
			RandomizerIF R2 = RF.newRandomizer(seed);
			// initialize several blank objects
			//these are where we will stuff our randomized string arrays
			Object[] c = new String[10];
			Object[] c_check = new String[10];
			c 		=  R2.choose (9, a);		
			c_check =  R2.choose (9, a);		

			boolean result = c.equals(c_check);
	    	assertEquals (true, result);
		}//end of test_choose
		
		@Test
		public void test_choose_d() {
			//init a few different randomizers
			seed = 36;
			RandomizerFactoryIF RF = new RandomizerFactory(); 
			RandomizerIF R3 = RF.newRandomizer(seed);
			// initialize several blank objects
			//these are where we will stuff our randomized string arrays
			Object[] d = new String[10];
			Object[] d_check = new String[10];
			d 		=  R3.choose (9, a);		
			d_check =  R3.choose (9, a);		

			boolean result = d.equals(d_check);
	    	assertEquals (true, result);
		}//end of test_choose
		
}//end of class

