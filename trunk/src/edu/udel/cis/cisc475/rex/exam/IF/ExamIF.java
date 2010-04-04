package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.SourceIF;

import java.util.Collection;

/**
 * 
 * @author Team 1
 *
 */
public interface ExamIF {
	
	/**
	 * 
	 * @return
	 */
	boolean isMaster();
	
	/**
	 * 
	 * @return
	 */
	SourceIF preamble();
	
	/**
	 * 
	 * @return
	 */
	SourceIF frontMatter();
	
	/**
	 * 
	 * @return
	 */
	BlockIF finalBlock();
	
	/**
	 * 
	 * @return
	 */
	int numElements();
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	ExamElementIF element(int i);
	
	/**
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
