package edu.udel.cis.cisc475.rex.source.generatestubs;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;


/**
 * @author krapf
 *
 */
public class SourceFactoryStub implements SourceFactoryIF {

	@Override
	public SourceIF newSource(String filename) {
		// TODO Auto-generated method stub
		
		return new SourceStub(filename);
	}

}
