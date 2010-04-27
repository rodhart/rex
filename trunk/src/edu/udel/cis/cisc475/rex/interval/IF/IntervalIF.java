package edu.udel.cis.cisc475.rex.interval.IF;

/**
 * Interface for Interval
 * 
 * @author Kyle Bouchard (bouchard)
 *
 */
public interface IntervalIF {

	Double low();
	boolean strictLow();
	Double high();
	boolean strictHigh();

	boolean contains(double value);
}
