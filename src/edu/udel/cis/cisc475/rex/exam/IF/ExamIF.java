package edu.udel.cis.cisc475.rex.exam.IF;

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
	public boolean isMaster();
	
	/**
	 * 
	 * @return
	 */
	public SourceIF preamble();
	
	/**
	 * 
	 * @return
	 */
	public SourceIF frontMatter();
	
	/**
	 * 
	 * @return
	 */
	public BlockIF finalBlock();
	
	/**
	 * 
	 * @return
	 */
	public int numElements();
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public ExamElementIF element(int i);
	
	/**
	 * 
	 * @return
	 */
	public Collection<ExamElementIF> elements();
	
	/**
	 * Returns the set of all exam elements.  The iterator from this 
	 * collection will return elements in the order element(0), element(1), 
	 * @param label
	 * @return
	 */
	public ExamElementIF elementWithLabel(String label);
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	public Collection<ExamElementIF> elementsWithTopic(String topic);
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	public Collection<ExamElementIF> elementsUsingElement(ExamElementIF element);
	
	/**
	 * 
	 * @return
	 */
	public Collection<FigureIF> figures();
	
	/**
	 * 
	 * @return
	 */
	public Collection<String> labels();
	
	/**
	 * 
	 * @return
	 */
	public Collection<String> topics();
	
	/**
	 * 
	 * @return
	 */
	public Collection<ProblemIF> problems();
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	public Collection<ProblemIF> problemsWithTopic(String topic);
	
	/**
	 * 
	 * @param preamble
	 */
	public void setPreamble(SourceIF preamble);
	
	/**
	 * 
	 * @param frontMatter
	 */
	public void setFrontMatter(SourceIF frontMatter);
	
	/**
	 * 
	 * @param block
	 */
	public void setFinalBlock(BlockIF block);
	
	/**
	 * 
	 * @param element
	 */
	public void addElementIF(ExamElementIF element);
	
	/**
	 * 
	 * @param user
	 * @param usee
	 */
	public void declareUse(ExamElementIF user, ExamElementIF usee);
}
