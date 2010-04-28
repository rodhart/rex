package edu.udel.cis.cisc475.rex.interval.IF;

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
			Double high);
}

