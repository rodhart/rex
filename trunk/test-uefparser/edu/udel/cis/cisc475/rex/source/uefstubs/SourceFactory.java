package edu.udel.cis.cisc475.rex.source.uefstubs;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class SourceFactory implements SourceFactoryIF {

	@Override
	public SourceIF newSource(String filename) {
		return new Source(filename);
	}

	@Override
	public SourceIF newSource(String filename, int startLine, int startColumn,
			int lastLine, int lastColumn) {
		// TODO Auto-generated method stub
		return null;
	}

}
