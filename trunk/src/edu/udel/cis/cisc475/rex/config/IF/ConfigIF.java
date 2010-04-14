package edu.udel.cis.cisc475.rex.config.IF;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/*
 * @author team 2
 *
 */

public interface ConfigIF {
	boolean pdfOption();

	long seed();

	Collection<ConstraintIF> constraints();

	String finalBlock();

	String[] versionStrings();

	int numVersions();

	void setSeed(long value);

	void setVersionStrings(String[] names);

	void setFinalBlock(String label);

	GroupConstraintIF addGroupConstraintIF(String topic, IntervalIF difficulty,
			int numProblems, int points, SourceIF source);

	RequiredProblemConstraintIF addRequiredProblemConstraint(String label,
			int points, SourceIF source);

}// end of interface