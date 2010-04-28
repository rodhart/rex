package edu.udel.cis.cisc475.rex.interval.IF;

import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;

/**
 * 
 * Interface for IntervalFactory.
 * 
 * @author Kyle Bouchard (bouchard)
 *
 */
public interface IntervalFactoryIF 
{
	IntervalIF interval(
			boolean strictLow, 
			Double low, 
			boolean strictHigh, 
			Double high) 
	throws RexUnsatisfiableException;
}

