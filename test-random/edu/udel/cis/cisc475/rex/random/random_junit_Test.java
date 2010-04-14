package edu.udel.cis.cisc475.rex.random;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * @author cardona, justin
 *
 */
public class random_junit_Test {
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
		RandomizerFactoryIF RF_2 = new RandomizerFactory(); 
		RandomizerIF R1_2 = RF_2.newRandomizer(seed);
		// initialize several blank objects
		//these are where we will stuff our randomized string arrays
		Object[] b = new String[10];
		Object[] b_check = new String[10];
		b 		=  R1.choose (9, a);		
		b_check =  R1_2.choose (9, a);		

		boolean result;
		for(int i = 0; i < 9; i++) {
			//System.out.printf("call: %d,  %s,    %s\n",i, b[i], b_check[i]);
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
		}
	}//end of test_choose


	@Test
	public void test_choose_c() {
		//init a few different randomizers
		seed = 4236;
		RandomizerFactoryIF RF = new RandomizerFactory(); 
		RandomizerIF R1 = RF.newRandomizer(seed);
		RandomizerIF R1_2 = RF.newRandomizer(seed);
		// initialize several blank objects
		//these are where we will stuff our randomized string arrays
		Object[] c = new String[10];
		Object[] c_check = new String[10];
		c 		=  R1.choose (19, a);		
		c_check =  R1_2.choose (19, a);		
		boolean result = c.equals(c_check);
		for(int i = 0; i < 19; i++) {
			//System.out.printf("call: %d,  %s,    %s\n",i, b[i], b_check[i]);
			result = c[i].equals(c_check[i]);
			assertEquals (true, result);
		}		
	}//end of test_choose



	@Test
	public void test_choose_d() {
		//init a few different randomizers
		seed = 36;
		RandomizerFactoryIF RF = new RandomizerFactory(); 
		RandomizerIF R1 = RF.newRandomizer(seed);
		RandomizerFactoryIF RF_2 = new RandomizerFactory(); 
		RandomizerIF R1_2 = RF_2.newRandomizer(seed);
		// initialize several blank objects
		//these are where we will stuff our randomized string arrays
		Object[] d = new String[10];
		Object[] d_check = new String[10];
		d 		=  R1.choose (29, a);		
		d_check =  R1_2.choose (29, a);		
		boolean result;
		for(int i = 0; i < 29; i++) {
			result = d[i].equals(d_check[i]);
			assertEquals (true, result);
		}
	}//end of test_choose



	@Test
	public void test_choose_e() {
		//init a few different randomizers
		seed = 36;
		RandomizerFactoryIF RF = new RandomizerFactory(); 
		RandomizerIF R1 = RF.newRandomizer(seed);
		RandomizerFactoryIF RF_2 = new RandomizerFactory(); 
		RandomizerIF R1_2 = RF_2.newRandomizer(seed);
		// initialize several blank objects
		//these are where we will stuff our randomized string arrays
		Object[] e = new String[10];
		Object[] e_check = new String[10];
		e 		=  R1.choose (40, a);		
		e_check =  R1_2.choose (40, a);		

		boolean result;
		for(int i = 0; i < 40; i++) {
			result = e[i].equals(e_check[i]);
			assertEquals (true, result);
		}
	}//end of test_choose



}//end of class


