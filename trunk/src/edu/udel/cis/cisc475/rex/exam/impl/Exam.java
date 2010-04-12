package edu.udel.cis.cisc475.rex.exam.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * 
 * 
 * @author Kevin Schultz (schultz)
 * @author Tim McClory (tmcclory)
 *
 */
public class Exam implements ExamIF {
	
	private boolean isMaster;

	/**
	 * Stores all elements (problems, figures, blocks) in one collection
	 * accessible both as a map (by key) and as a linked list using an iterator
	 */
	private LinkedHashMap<Integer, ExamElementIF> elements;
	
	/**
	 * A list storing the keys of all the figures in the Map elements
	 */
	private LinkedList<Integer> figures;
	
	/**
	 * A list storing the keys of all the blocks in the Map elements
	 */
	private LinkedList<Integer> blocks;
	
	/**
	 * A list storing the keys of all the problems in the Map elements
	 */
	private LinkedList<Integer> problems;
	
	/**
	 * TODO: What is the difference between the preamble and front matter?
	 */
	private SourceIF preamble; 
	
	/**
	 * The first page(s) of an exam 
	 */
	private SourceIF frontmatter;
	
	/**
	 * The last page(s) of an exam
	 */
	private BlockIF finalblock;
	
	/**
	 * Set of topics throughout all ExamElements
	 */
	private HashSet<String> topics;
	
	/**
	 * Set of labels throughout all ExamElements
	 */
	private HashSet<String> labels;
	
	/**
	 * 
	 */
	private LinkedHashMap<ExamElementIF, HashSet<ExamElementIF>> uses;
	/**
	 * Default constructor
	 */
	public Exam(boolean isMaster) {
		super();
		this.isMaster = isMaster;
		this.elements = new LinkedHashMap<Integer, ExamElementIF>();
		this.figures = new LinkedList<Integer>();
		this.blocks = new LinkedList<Integer>();
		this.problems = new LinkedList<Integer>();
		
	}


	/**
	 *
	 */
	public void addElementIF(ExamElementIF element) {
		int key = elements.size() + 1;
		//put into linked hash set
		//also need to make some kind of record of whether it is
		//a problem, a block, or a figure.
		labels.add(element.label());
		//Allocate a new HashSet for the uses map
		HashSet<ExamElementIF> useesOfElement = new HashSet();
		uses.put(element, useesOfElement);
		if (element.getClass().isInstance(FigureIF.class)) {
			figures.add(key);
		} else if (element.getClass().isInstance(BlockIF.class)) {
			blocks.add(key);
			topics.add(((ProblemIF) element).topic());
		} else if (element.getClass().isInstance(ProblemIF.class)) {
			problems.add(key);
			topics.add(((ProblemIF) element).topic());
		} else {
			//Do we need to do anything in this case? It definitely is not good if it isn't one of the 3
		}
		
		

	}

	/**
	 * 
	 */
	public void declareUse(ExamElementIF user, ExamElementIF usee) {
		uses.get(user).add(usee);
	}

	public ExamElementIF element(int i) {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();	
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> itr = elementValues.iterator();
		int count = 0;
		ExamElementIF element=itr.next();
		while(itr.hasNext() && count<i)	{
			element = itr.next();
			returnSet.add(element);
		}
		if(count==i) {
			return element;
		}
		return null;
	}

	public ExamElementIF elementWithLabel(String label) {		
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while(i.hasNext())	{
			ExamElementIF element = i.next();
			if(element.label().equals(label))
			{
				return element;
			}
		}
		return null;
	}

	public Collection<ExamElementIF> elements() {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();	
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while(i.hasNext())	{
			ExamElementIF element = i.next();
			returnSet.add(element);
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	public Collection<ExamElementIF> elementsUsingElement(ExamElementIF element) {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();	
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while(i.hasNext())	{
			ExamElementIF newElement = i.next();
			if(uses.get(newElement).contains(element)) {
				returnSet.add(element);
			}
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	public Collection<ExamElementIF> elementsWithTopic(String topic) {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();	
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while(i.hasNext())	{
			ExamElementIF element = i.next();
			if (element.getClass().isInstance(ProblemIF.class)) {
				if(((ProblemIF) element).topic().equals(topic))
					returnSet.add(element);
			}
			else if (element.getClass().isInstance(BlockIF.class)) {
				if(((BlockIF) element).topic().equals(topic)) {
					returnSet.add(element);
				}
			}
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	public Collection<FigureIF> figures() {
		HashSet<FigureIF> returnSet = new HashSet<FigureIF>();
		
		Iterator<Integer> i = figures.iterator();
		while(i.hasNext()) {
			returnSet.add((FigureIF) elements.get(i.next()));
		}
		
		return (Collection<FigureIF>) returnSet;
	}

	public BlockIF finalBlock() {
		return this.finalblock;
	}

	public SourceIF frontMatter() {
		return this.frontmatter;
	}

	public boolean isMaster() {
		return this.isMaster;
	}

	public Collection<String> labels() {
		return labels;
	}

	public int numElements() {
		return elements.size();
	}

	public SourceIF preamble() {
		return this.preamble;
	}

	/**
	 * 
	 */
	public Collection<ProblemIF> problems() {
		HashSet<ProblemIF> returnSet = new HashSet<ProblemIF>();
		
		Iterator<Integer> i = problems.iterator();
		while(i.hasNext()) {
			returnSet.add((ProblemIF) elements.get(i.next()));
		}
		
		return (Collection<ProblemIF>) returnSet;
	}

	
	public Collection<ProblemIF> problemsWithTopic(String topic) {
		HashSet<ProblemIF> returnSet = new HashSet<ProblemIF>();
		ProblemIF problem;
		
		Iterator<Integer> i = problems.iterator();
		while(i.hasNext()) {
			problem = (ProblemIF) elements.get(i.next());
			if(problem.topic().equals(topic)) {
				returnSet.add(problem);
			}
		}
		
		return (Collection<ProblemIF>) returnSet;
	}

	public void setFinalBlock(BlockIF block) {
		this.finalblock = block;
	}

	public void setFrontMatter(SourceIF frontMatter) {
		this.frontmatter = frontMatter;
	}

	public void setPreamble(SourceIF preamble) {
		this.preamble = preamble;
	}

	//@Override
	public Collection<String> topics() {
		return (Collection<String>) topics;
	}

}
