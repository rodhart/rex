package edu.udel.cis.cisc475.rex.interval.impl;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

public class IntervalFactory implements IntervalFactoryIF
{
	
	/**
	 * The IntervalFactory class handles the creation of an
	 * Interval object.
	 * 
	 *  @author Kyle Bouchard (bouchard)
	 */

	/**
	 * This function is a wrapper for the Interval constructor
	 * 
	 * If low is greater than high (error condition), throw
	 * RuntimeExction, as this should never happen
	 */
	@Override
	public IntervalIF interval(boolean strictLow, Double low,
			boolean strictHigh, Double high){

		if(low > high)
		{
			throw new RuntimeException();
		}
		else
		{
			return new Interval(strictLow, low, strictHigh, high);
		}

		//return null;

		
	}

}
