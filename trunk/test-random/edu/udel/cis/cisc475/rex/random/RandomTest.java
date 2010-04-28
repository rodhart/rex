package edu.udel.cis.cisc475.rex.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.random.IF.RandomizerFactoryIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import edu.udel.cis.cisc475.rex.random.impl.RandomizerFactory;

/**
 * Junit tests for Random module
 * @author cardona
 *
 */
public class RandomTest {

	@Test
	//test that random numbers picked are
	//same with same seed
	public void testChooseA1()  {
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
	public void testChooseA2()  {
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
	public void testChooseA3()  {
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
	public void testChooseA4()  {
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
	//test that random numbers picked are
	//same with same seed
	//test that all 40 diff elements are used
	public void testChooseA5()  {
		int i = 0;
		seed = 4236;
		numToPick = 40;
		R1 = RF.newRandomizer(seed);
		R1_2 = RF.newRandomizer(seed);
		b 		=  R1.choose (numToPick, a);		
		b_check =  R1_2.choose (numToPick, a);		
		for(i = 0; i < numToPick; i++) {
			result = b[i].equals(b_check[i]);
			assertEquals (true, result);
		}
	}//end of method

	@SuppressWarnings("deprecation")
	@Test
	//test if number of objects to pick is == 0
	public void testChooseB1()  {
		seed = 4236;
		numToPick = 0;
		R1 = RF.newRandomizer(seed);
		b = R1.choose (numToPick, a);
		Object testObject[] = {};
		
		assertEquals(testObject, b);
	}//end of method

	@Test(expected=IllegalArgumentException.class)
	//test if number of objects to pick is <1
	public void testChooseB2()  {
		seed = 4236;
		numToPick = -10;
		R1 = RF.newRandomizer(seed);
		b = R1.choose(numToPick, a);
	}//end of method

	@Test(expected=IllegalArgumentException.class)
	//test if number of objects is > avail objects
	public void testChooseB3()  {
		seed = 4236;
		numToPick = 42;//we have only 40 objects cannot pick 42
		R1 = RF.newRandomizer(seed);
		b = R1.choose (numToPick, a);
	}//end of method

	@Test
	//test if result is random to 5%
	public void testChooseMillion1()  {
		seed = 426;
		numToPick = 1;
		R1 = RF.newRandomizer(seed);
		int[] counter = new int[11];

		for (int x=0; x<1000000; x++){
			b =  R1.choose (numToPick, ten);		
			temp = b[0].toString();

			//since java switch does not handle strings, I just used if then else :(
			//and yes I know its ugly
			if (temp =="one") counter[1]++;
			if (temp =="two") counter[2]++;
			if (temp =="three") counter[3]++;
			if (temp =="four") counter[4]++;
			if (temp =="five") counter[5]++;
			if (temp =="six") counter[6]++;
			if (temp =="seven") counter[7]++;
			if (temp =="eight") counter[8]++;
			if (temp =="nine") counter[9]++;
			if (temp =="ten") counter[10]++;
		}//end of while loop

		for (int x = 1; x<11; x++){
			assertTrue ( (counter[x] < (100000*1.05)) && (counter[1] > (100000*.95)) );
		}	
	}//end of method

	@Test
	//test if result is random to 1%
	public void testChooseMillion2()  {
		seed = 426;
		numToPick = 1;//we have only 40 objects cannot pick 42
		R1 = RF.newRandomizer(seed);
		int[] counter = new int[11];

		for (int x=0; x<1000000; x++){
			b =  R1.choose (numToPick, ten);		
			temp = b[0].toString();

			//since java switch does not handle strings, I just used if then else :(
			//and yes I know its ugly
			if (temp =="one") counter[1]++;
			if (temp =="two") counter[2]++;
			if (temp =="three") counter[3]++;
			if (temp =="four") counter[4]++;
			if (temp =="five") counter[5]++;
			if (temp =="six") counter[6]++;
			if (temp =="seven") counter[7]++;
			if (temp =="eight") counter[8]++;
			if (temp =="nine") counter[9]++;
			if (temp =="ten") counter[10]++;
		}//end of while loop

		for (int x = 1; x<11; x++){
			assertTrue ( (counter[x] < (100000*1.01)) && (counter[1] > (100000*.99)) );
		}	
	}//end of method

	@Test
	//test if result is random to .5%
	public void testChooseMillion3()  {
		seed = 426;
		numToPick = 1;//we have only 40 objects cannot pick 42
		R1 = RF.newRandomizer(seed);
		int[] counter = new int[11];

		for (int x=0; x<1000000; x++){
			b =  R1.choose (numToPick, ten);		
			temp = b[0].toString();

			//since java switch does not handle strings, I just used if then else :(
			//and yes I know its ugly
			if (temp =="one") counter[1]++;
			if (temp =="two") counter[2]++;
			if (temp =="three") counter[3]++;
			if (temp =="four") counter[4]++;
			if (temp =="five") counter[5]++;
			if (temp =="six") counter[6]++;
			if (temp =="seven") counter[7]++;
			if (temp =="eight") counter[8]++;
			if (temp =="nine") counter[9]++;
			if (temp =="ten") counter[10]++;
		}//end of while loop

		for (int x = 1; x<11; x++){
			assertTrue ( (counter[x] < (100000*1.005)) && (counter[1] > (100000*.995)) );
		}	
	}//end of method

	//class variables		
	private String a[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"two_one", "two_two", "two_three", "two_four", "two_five", "two_six", "two_seven", "two_eight", "two_nine", "two_ten",
			"three_one", "three_two", "three_three", "three_four", "three_five", "three_six", "three_seven", "three_eight", "three_nine", "three_ten",
			"four_one", "four_two", "four_three", "four_four", "four_five", "four_six", "four_seven", "four_eight", "four_nine", "four_ten"};
	private String ten[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
	String temp;
	private long seed;
	private Object[] b = new String[10];
	private Object[] b_check = new String[10];
	private RandomizerFactoryIF RF = new RandomizerFactory(); 
	private RandomizerFactoryIF RF_2 = new RandomizerFactory(); 
	private boolean result;
	private int numToPick = 0;
	private RandomizerIF R1;
	private RandomizerIF R1_2;
}//end of class
