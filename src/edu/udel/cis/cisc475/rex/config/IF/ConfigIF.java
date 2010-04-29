package edu.udel.cis.cisc475.rex.config.IF;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * ConfigIF is the interface for the Config class. 
 * It provides a list of the class's public methods.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 *
 */

public interface ConfigIF {
	/**
	 * Returns true or false whether or not a pdf should be generated
	 */
	boolean pdfOption();

	/**
	 * Returns exam seed
	 */
	long seed();

	/**
	 * Returns the Group and Problem Constraints that have been stored in the 
	 * constraints collection.
	 */
	Collection<ConstraintIF> constraints();

	/**
	 * Returns the final block
	 */
	String finalBlock();

	/**
	 * Returns the version strings for exam version differentiation
	 */
	String[] versionStrings();

	/**
	 * Returns the number of versions to be generated
	 */
	int numVersions();

	/**
	 * Sets the exam seed number
	 * @param value - the exam seed value
	 */
	void setSeed(long value);

	/**
	 * sets the version strings for exam version differentiation
	 * @param names - the list of version strings.
	 * @throws RexParseException 
	 */
	void setVersionStrings(String[] names) throws RexParseException;

	/**
	 * Sets the final block
	 * @param label  - the label of the final block
	 */
	void setFinalBlock(String label);

	/**
	 * Constructor creates a new GroupConstraint object with the passed parameters and adds it to
	 * the constraints collection.  
	 * 
	 * @param topic - the required topic
	 * @param difficulty - the range of problem difficulties to be included in the topic
	 * @param numProblems - the number of problems to include within the topic
	 * @param points - the point value of the problem
	 * @param source - the location in the ECF file of the group constraint
	 * 
	 */
	GroupConstraintIF addGroupConstraint(String topic, IntervalIF difficulty,
			int numProblems, int points, SourceIF source);

	/**
	 * Creates a new RequiredProblemConstraint object with the passed parameters and adds 
	 * it to the constraints collection.  
	 * 
	 * @param label - the required problem's label
	 * @param points - the point value of the problem
	 * @param source - the location in the ECF file of the problem constraint
	 * 
	 */
	RequiredProblemConstraintIF addRequiredProblemConstraint(String label,
			int points, SourceIF source);

}// end of interface