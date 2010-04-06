package edu.udel.cis.cisc475.rex.config;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Config implements ConfigIF {
	
	/**
	 * @author zhine
	 */

	@Override
	public GroupConstraintIF addGroupConstraintIF(String topic,
			IntervalIF difficulty, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequiredProblemConstraintIF addRequiredProblemConstraint(
			String label, int points, SourceIF source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConstraintIF> constraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finalBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numVersions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean pdfOption() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long seed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFinalBlock(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSeed(long value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVersionStrings(String[] names) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] versionStrings() {
		// TODO Auto-generated method stub
		return null;
	}

}
