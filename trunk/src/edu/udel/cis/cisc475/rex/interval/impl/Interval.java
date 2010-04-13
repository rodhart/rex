package edu.udel.cis.cisc475.rex.interval.impl;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/*
 * @author team 2
 *
 */

public class Interval implements IntervalIF
{
	private Double low;
	private boolean strictLow;
	private Double high;
	private boolean strictHigh;
		
	public Double low(){return low;}
	public boolean strictLow(){return strictLow;}
	public Double high(){return high;}
	public boolean strictHigh(){return strictHigh;}

	/* constructor */
	Interval(boolean strictLow, Double low,
			boolean strictHigh, Double high)
			{
				this.strictLow = strictLow;
				this.strictHigh = strictHigh;
				this.low = low;
				this.high = high;
			}
	
	/* when "high" is 5: 
	 * strictHigh: x < 5
	 * !strictHigh: x <= 5 */
	
	public boolean contains(double value)
	{
		/* tests for outright out of range */
		if((value > high) || (value < low)){return false;}
			
		/* if strictLow is on, x > low, so if x is low, false */
		if(strictLow && (x == low)){return false;}
		
		/* if strictHigh is on, x < high, so if x is high, false */
		if(strictHigh && (x == high)){return false;}
		
		/*otherwise, the value is inside the range and can be 
		 * equal to high and low so return true */
		return true;
	}

}