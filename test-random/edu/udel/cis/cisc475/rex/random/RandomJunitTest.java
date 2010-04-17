package edu.udel.cis.cisc475.rex.random;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * @author jim cardona
 * @author justin johnson
 *
 */
public class RandomJunitTest {


	@Test
	public void testChoose() {
		int i = 0;
		seed = 3;
		numToPick = 9;
		R1 = RF.newRandomizer(seed);
		R1_2 = RF_2.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		b_check =  R1_2.choose (numToPick, a);		
		for(i = 0; i < numToPick; i++) {
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
		}
	}//end of method
	
		@Test
		//test that random numbers picked are
		//same with same seed
		public void testChooseA() {
		int i = 0;
		seed = 4236;
		numToPick = 19;
		R1 = RF.newRandomizer(seed);
		R1_2 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		b_check =  R1_2.choose (numToPick, a);		
		for(i = 0; i < numToPick; i++) {
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
			}
		}//end of method
		
		//test that random numbers picked are
		//same with same seed
		@Test
		public void testChooseB() {
		int i = 0;
		seed = 36;
		numToPick = 29;
		R1 = RF.newRandomizer(seed);
		R1_2 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		b_check =  R1_2.choose (numToPick, a);		
		for(i = 0; i < numToPick; i++) {
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
			}
		}//end of method
		
		@Test
		//test that random numbers picked are
		//same with same seed
		public void testChooseC() {
			int i = 0;
		seed = 4236;
		numToPick = 39;
		R1 = RF.newRandomizer(seed);
		R1_2 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		b_check =  R1_2.choose (numToPick, a);		
		for(i = 0; i < numToPick; i++) {
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
		}
	}//end of method


		
		@Test
		//test if number of objects to pick is <1
		public void testChooseD() {
		seed = 4236;
		numToPick = 0;
		R1 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		if (b == null) 	result = true;	
			assertEquals (true, result);
		}//end of method
		
		
		
		@Test
		//test if number of objects to pick is <1
		public void testChooseE() {
		seed = 4236;
		numToPick = -10;
		R1 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		if (b == null) 	result = true;	
			assertEquals (true, result);
		}//end of method
		
		
		
		
		
		
		@Test
		//test if number of objects is > avail objects
		public void testChooseF() {
		seed = 4236;
		numToPick = 42;//we have only 40 objects cannot pick 42
		R1 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		if (b == null) 	result = true;	
			assertEquals (true, result);
		}//end of method
		
		
		
	//class variables		
	private String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"two_one", "two_two", "two_three", "two_four", "two_five", "two_six", "two_seven", "two_eight", "two_nine", "two_ten",
			"three_one", "three_two", "three_three", "three_four", "three_five", "three_six", "three_seven", "three_eight", "three_nine", "three_ten",
			"four_one", "four_two", "four_three", "four_four", "four_five", "four_six", "four_seven", "four_eight", "four_nine", "four_ten"};
	private long seed;
	private Object[] b = new String[10];
	private Object[] b_check = new String[10];
	private RandomizerFactoryIF RF = new RandomizerFactory(); 
	private RandomizerFactoryIF RF_2 = new RandomizerFactory(); 
	private boolean result;
	private int numToPick = 0;
	private RandomizerIF R1;
	private RandomizerIF R1_2;
	//System.out.printf("call: %d,  %s,    %s\n",i, b[i], b_check[i]);
}//end of class


