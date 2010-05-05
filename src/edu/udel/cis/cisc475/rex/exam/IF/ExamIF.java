package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.source.IF.*;

import java.util.Collection;

/**
 * The ExamIF specifies a container for a single version of the exam. The ExamIF
 * allows setting/getting all of the problems, figures, sections, front matter,
 * etc for a specific version of the exam. The "master" version of the class
 * implementing ExamIF contains all of the data held in the UEF, and non-master
 * exams are a subset of this.
 * 
 * @author Kevin Schultz (schultz)
 * @author Tim McClory (tmcclory)
 */
public interface ExamIF {

	/**
	 * The master exam contains all problems loaded from the UEF, non-master
	 * exams contain only a sub-set of all problems in the UEF
	 * 
	 * @return true if master
	 */
	boolean isMaster();

	/**
	 * @return Intro to the exam as SourceIF object
	 */
	SourceIF preamble();

	/**
	 * @return First page intro to exam as SourceIF object
	 */
	SourceIF frontMatter();

	/**
	 * @return Closing section of the exam as BlockIF object
	 */
	BlockIF finalBlock();

	/**
	 * Number of elements stored in an exam, includes all elements such as
	 * problems, figures, blocks, preambles, but not front matter
	 */
	int numElements();

	/**
	 * Returns the element specified by the key i, use elements().iterator() to
	 * get the key, the order of the elements in ExamIF is specified to be the
	 * same
	 * 
	 * @param int key i
	 */
	ExamElementIF element(int i);

	/**
	 * Returns a collection of all exam elements. The iterator from this
	 * collection will return elements in the order element(0), element(1),
	 */
	Collection<ExamElementIF> elements();

	/**
	 * Returns the element with a matching label to the argument, there should
	 * be only one element with the given label specified in the UEF but this is
	 * not guaranteed
	 */
	ExamElementIF elementWithLabel(String label);

	/**
	 * Returns all elements that have a matching topic to the argument
	 */
	Collection<ExamElementIF> elementsWithTopic(String topic);

	/**
	 * Returns all elements depending on the element specified in the argument.
	 * i.e. if several problems use a figure, and the figure is the argument of
	 * this call a collection of those problems will be returned. If no elements
	 * are found, an empty collection will be returned
	 */
	Collection<ExamElementIF> elementsUsingElement(ExamElementIF element);

	/**
	 * All figures stored in exam
	 */
	Collection<FigureIF> figures();

	/**
	 * A set of all the exam's elements' labels - no duplicates
	 */
	Collection<String> labels();

	/**
	 * A set of all of the exam's elements' topics - no duplicates
	 */
	Collection<String> topics();

	/**
	 * A collection of all problems stored in the exam
	 */
	Collection<ProblemIF> problems();

	/**
	 * A collection of all problems in the exam with the specified topic
	 */
	Collection<ProblemIF> problemsWithTopic(String topic);

	/**
	 * Sets the exam preamble
	 */
	void setPreamble(SourceIF preamble);

	/**
	 * Sets the exam front matter
	 */
	void setFrontMatter(SourceIF frontMatter);

	/**
	 * Set the final (non-problem) text in an exam
	 */
	void setFinalBlock(BlockIF block);

	/**
	 * Adds an element to the exam. Duplicate elements will not be added.
	 * 
	 * @return the key for the element, or -1 if a duplicate
	 */
	int addElement(ExamElementIF element);

	/**
	 * Allow one element to use another element. For example a problem can
	 * depend on a figure, so the problem will be the user and the figure will
	 * be the usee. Multiple users can be associated with a usee.
	 * 
	 * @param user
	 *            - one of several elements to depend on the usee
	 * @param usee
	 *            - singular element others are connected with
	 * @throws RexException
	 */
	void declareUse(ExamElementIF user, ExamElementIF usee) throws RexException;

	/**
	 * Get the Version ID string for an exam. The master exam will return
	 * "master" while a generated exam will return whatever version was set by
	 * the ExamFactory (as specified in the ECF)
	 * 
	 * @return Version ID string
	 */
	String version();
}
