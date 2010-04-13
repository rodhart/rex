package edu.udel.cis.cisc475.rex.config.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Constraint implements ConstraintIF {
	private SourceIF source;
	
	public Constraint(SourceIF source){
		this.source = source;
	}
	
	@Override
	public SourceIF source() {
		return this.source;
	}

}
