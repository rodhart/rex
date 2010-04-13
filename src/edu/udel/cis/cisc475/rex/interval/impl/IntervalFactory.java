package edu.udel.cis.cisc475.rex.interval.impl;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

public class IntervalFactory implements IntervalFactoryIF
{
	
	/**
	 * Added by Team 3 
	 * 7:47 4/12/10
	 * Needed a stub for Generator's generate() method
	 */

	@Override
	public IntervalIF interval(boolean strictLow, Double low,
			boolean strictHigh, Double high) {
		// TODO Auto-generated method stub
		return new Interval(strictLow, low, strictHigh, high);
	}

}
