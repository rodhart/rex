package edu.udel.cis.cisc475.rex.interval.IF;

/*
 * @author team 2
 *
 */

public interface IntervalIF {
	Double low();

	boolean strictLow();

	Double high();

	boolean strictHigh();

	boolean contains(double value);
}// end of interface
