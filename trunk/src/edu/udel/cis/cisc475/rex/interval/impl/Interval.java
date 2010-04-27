package edu.udel.cis.cisc475.rex.interval.impl;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/**
 * The Interval class handles an Interval object. An Interval
 * object contains an interval used for various REX functionality.
 * 
 * Interval stores the upper and lower ranges of the defined 
 * interval, as well as whether the upper or lower bound is
 * exclusive or not.
 * 
 * @author Kyle Bouchard (bouchard)
 *
 */

public class Interval implements IntervalIF
{
	private Double low;
	private boolean strictLow;
	private Double high;
	private boolean strictHigh;
		
	/**
	 * Returns the lower bound
	 * @return double low
	 */
	public Double low()
	{
		return low;
	}
	
	/**
	 * Returns true if the lower bound is exclusive
	 * @return boolean strictLow
	 */
	public boolean strictLow()
	{
		return strictLow;
	}
	
	/**
	 * Returns the upper bound
	 * @return double high
	 */
	public Double high()
	{
		return high;
	}
	
	/**
	 * Returns true if the upper bound is exclusive.
	 * @return boolean strictHigh
	 */
	public boolean strictHigh()
	{
		return strictHigh;
	}
	
	/** 
	 * The interval constructor takes in two doubles,
	 * corresponding to the upper and lower bounds of 
	 * the interval, and two booleans.
	 * 
	 *  The booleans define whether the upper or lower 
	 *  bound are exclusive.
	 */
	Interval(boolean strictLow, Double low,
			boolean strictHigh, Double high)
			{
				if(low > high)
					System.err.print("Warning: Interval low was greater than high");
				
				this.strictLow = strictLow;
				this.strictHigh = strictHigh;
				this.low = low;
				this.high = high;
			}
		
	/**
	 * The contains(double) function takes in a double
	 * value and tests the private variables against it.
	 * 
	 * The function returns true if the value passed in
	 * is within the interval defined by the Interval 
	 * object.
	 * 
	 * If either the upper or lower bound's "strict" value
	 * is true, then the interval's upper or lower bound is 
	 * exclusive.
	 * 
	 * That is, if, say the interval is 1,10 and only strictLow
	 * is true, the interval can be best seen as: (1,10], or
	 * 2-10, inclusive.
	 * 
	 * @param	value	The value to test for existence within the range of the interval.
	 * @return	True or false, depending on the value's existence within the range of the interval.
	 * 
	 */
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