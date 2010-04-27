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
	 * this function is a wrapper for the Interval constructor
	 */
	@Override
	public IntervalIF interval(boolean strictLow, Double low,
			boolean strictHigh, Double high) {
		// TODO Auto-generated method stub
		return new Interval(strictLow, low, strictHigh, high);
	}

}
