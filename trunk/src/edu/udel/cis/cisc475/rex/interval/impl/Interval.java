package edu.udel.cis.cisc475.rex.interval.impl;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/**
 * @author bouchard
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

	/** constructor */
	Interval(boolean strictLow, Double low,
			boolean strictHigh, Double high)
			{
				this.strictLow = strictLow;
				this.strictHigh = strictHigh;
				this.low = low;
				this.high = high;
			}
		
	public boolean contains(double value)
	{
		/* tests for outright out of range */
		if((value > high) || (value < low))
		{
			return false;
		}
		else if(this.strictLow && (value == this.low))
		{
			// if strictLow is on, x > low, so if x is low, false
			return false;
		}
		else if(this.strictHigh && (value == this.high))
		{
			// if strictHigh is on, x < high, so if x is high, false
			return false;
		}
		else 
		{
			//otherwise, the value is inside the range and can be 
			//equal to high and low so return true
			return true;
		}
	}

}