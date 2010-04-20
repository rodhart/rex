package edu.udel.cis.cisc475.rex.interval.generatestubs;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;


/**
 * @author krapf
 *
 */

public class IntervalStub implements IntervalIF {
	Double s_low = 0.0;
	boolean s_strictLow = true;
	Double s_high = 0.0;
	boolean s_strictHigh = true;
	boolean s_contains = false;
	
	public IntervalStub(boolean strictLow, Double low, boolean strictHigh,
			Double high) {
		this.s_strictLow = strictLow;
		this.s_low = low;
		this.s_strictHigh = strictHigh;
		this.s_high = high;
	}
	
	@Override
	public boolean strictLow() {
		// TODO Auto-generated method stub
		return s_strictLow;
	}
	
	@Override
	public Double low() {
		// TODO Auto-generated method stub
		return s_low;
	}
	
	@Override
	public boolean strictHigh() {
		// TODO Auto-generated method stub
		return s_strictHigh;
	}
	
	@Override
	public Double high() {
		// TODO Auto-generated method stub
		return s_high;
	}
	
	@Override
	public boolean contains(double value){
		// TODO Auto-generated method stub
		return s_contains;
	}
}