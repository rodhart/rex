package edu.udel.cis.cisc475.rex.interval.generatestubs;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;

/**
 * @author krapf
 *
 */

public class IntervalFactoryStub implements IntervalFactoryIF {

	@Override
	public IntervalIF interval(boolean strictLow, Double low, boolean strictHigh,
			Double high) {
		// TODO Auto-generated method stub
		
		return new IntervalStub(strictLow, low, strictHigh, high);
	}

}