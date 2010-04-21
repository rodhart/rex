package edu.udel.cis.cisc475.rex.config.impl;

import java.util.Collection;
import java.util.HashSet;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * The Config class handles the storage of the configuration options.
 * It stores the command line arguments such as whether it needs pdfs and 
 * also how many versions to generate. It can also store the group
 * constraints and the problem constraints.
 * 
 * @author Anthony Platt (aplatt)
 * @author Jeremy Verchick (verchick)
 * 
 */

public class Config implements ConfigIF {
	private long seed;
	private String[] versionStrings;
	private String finalBlock;
	private boolean pdfOption;
	private int numVersions;
	private HashSet<ConstraintIF> constraints;
	
	public Config(boolean pdf, int numVersions){
		this.numVersions = numVersions;
		this.pdfOption = pdf;
		this.constraints = new HashSet<ConstraintIF>();
	}
	
	/**
	 * Creates a new GroupConstraint object with the passed parameters and adds it to
	 * the constraints collection.  
	 */
	public GroupConstraintIF addGroupConstraint(String topic,
			IntervalIF difficulty, int numProblems, int points, SourceIF source) {

		GroupConstraintIF constraint = new GroupConstraint(difficulty, numProblems, points, topic, source);
		this.constraints.add(constraint);
		
		return constraint;
	}

	/**
	 * Creates a new RequiredProblemConstraint object with the passed parameters and adds 
	 * it to the constraints collection.  
	 */
	public RequiredProblemConstraintIF addRequiredProblemConstraint(
			String label, int points, SourceIF source) {
		
		RequiredProblemConstraintIF constraint = new RequiredProblemConstraint(label, points, source);
		constraints.add(constraint);
		
		return constraint;
	}

	/**
	 * Sets the exam seed number
	 */
	public void setSeed(long value) {
		this.seed = value;

	}

	/**
	 * sets the version strings for exam version differentiation
	 */
	public void setVersionStrings(String[] names) {
		this.versionStrings = names;
	}

	/**
	 * Returns the Group and Problem Constraints that have been stored in the 
	 * constraints collection.
	 */
	public Collection<ConstraintIF> constraints() {
		return (Collection<ConstraintIF>)constraints;
	}

	/**
	 * Returns the final block
	 */
	public String finalBlock() {
		return this.finalBlock;
	}

	/**
	 * Returns the number of versions to be generated
	 */
	public int numVersions() {
		return this.numVersions;
	}

	/**
	 * Returns true or false whether or not a pdf should be generated
	 */
	public boolean pdfOption() {
		return this.pdfOption;
	}

	/**
	 * Returns exam seed
	 */
	public long seed() {
		return this.seed;
	}

	/**
	 * Returns the final block
	 */
	public void setFinalBlock(String label) {
		this.finalBlock = label;
	}

	/**
	 * Returns the version strings for exam version differentiation
	 */
	public String[] versionStrings() {
		return this.versionStrings;
	}

}
