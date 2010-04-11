package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

import java.util.Collection;

/**
 * 
 * @author Kevin Schultz (schultz)
 * @author Tim McClory (tmcclory) 
 */
public interface ExamIF {
	
	/**
	 * The master exam contains all problems loaded from the UEF, 
	 * non-master exams contain only a sub-set of all problems in the UEF
	 * 
	 * @return true if master
	 */
	boolean isMaster();
	
	/**
	 * Intro to a section
	 * 
	 * @return
	 */
	SourceIF preamble();
	
	/**
	 * First page intro to exam  
	 * 
	 * @return 
	 */
	SourceIF frontMatter();
	
	/**
	 * Last 
	 * 
	 * @return
	 */
	BlockIF finalBlock();
	
	/**
	 * Number of elements stored in an exam, includes all elements such 
	 * as problems, figures, blocks, preambles, but not front matter
	 * 
	 * @return
	 */
	int numElements();
	
	/**
	 * 
	 * 
	 * @param i
	 * @return
	 */
	ExamElementIF element(int i);
	
	/**
	 * 
	 * 
	 * @return
	 */
	Collection<ExamElementIF> elements();
	
	/**
	 * Returns the set of all exam elements.  The iterator from this 
	 * collection will return elements in the order element(0), element(1), 
	 * @param label
	 * @return
	 */
	ExamElementIF elementWithLabel(String label);
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	Collection<ExamElementIF> elementsWithTopic(String topic);
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	Collection<ExamElementIF> elementsUsingElement(ExamElementIF element);
	
	/**
	 * All figures stored in exam 
	 * 
	 * @return
	 */
	Collection<FigureIF> figures();
	
	/**
	 * 
	 * @return
	 */
	Collection<String> labels();
	
	/**
	 * 
	 *
	 * 
	 * @return 
	 */
	Collection<String> topics();
	
	/**
	 * 
	 * @return
	 */
	Collection<ProblemIF> problems();
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	Collection<ProblemIF> problemsWithTopic(String topic);
	
	/**
	 * 
	 * @param preamble
	 */
	void setPreamble(SourceIF preamble);
	
	/**
	 * 
	 * @param frontMatter
	 */
	void setFrontMatter(SourceIF frontMatter);
	
	/**
	 * 
	 * @param block
	 */
	void setFinalBlock(BlockIF block);
	
	/**
	 * 
	 * @param element
	 */
	void addElementIF(ExamElementIF element);
	
	/**
	 * 
	 * @param user
	 * @param usee
	 */
	void declareUse(ExamElementIF user, ExamElementIF usee);
}
