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
 * The ExamIF specifies a container for a single version of the exam. The ExamIF
 * allows setting/getting all of the problems, figures, sections, front matter,
 * etc for a specific version of the exam. The "master" version of the class
 * implementing ExamIF contains all of the data held in the UEF, and non-master
 * exams are a subset of this.
 * 
 * @author Kevin Schultz (schultz)
 * @author Tim McClory (tmcclory)
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
		this.labels = new HashSet<String>();
		this.topics = new HashSet<String>();
		this.uses = new LinkedHashMap<ExamElementIF, HashSet<ExamElementIF>>();
	}

	/**
	 * Adds an element to the exam. Duplicate elements will not be added.
	 * 
	 * @return the key for the element, or -1 if a duplicate
	 */
	public int addElementIF(ExamElementIF element) {
		// Even though we are using a map, we really want a set so should not
		// add duplicate entries in a single exam
		if (!elements.containsValue(element)) {
			int key = elements.size();
			// put into linked hash set
			elements.put(key,element);
			// also need to make some kind of record of whether it is
			// a problem, a block, or a figure.
			labels.add(element.label());
			// Allocate a new HashSet for the uses map
			HashSet<ExamElementIF> useesOfElement = new HashSet<ExamElementIF>();
			uses.put(element, useesOfElement);
			if (element instanceof FigureIF) {
				figures.add(key);
			} else if (element instanceof BlockIF) {
				blocks.add(key);
				topics.add(((BlockIF) element).topic());
			} else if (element instanceof ProblemIF) {
				problems.add(key);
				topics.add(((ProblemIF) element).topic());
			} else {
				// Do we need to do anything in this case? It definitely is not
				// good if it isn't one of the 3
			}
			return key;
		} else {
			return -1;
		}
		
	}

	/**
	 * Allow one element to use another element. For example a problem can
	 * depend on a figure, so the problem will be the user and the figure will
	 * be the usee. Multiple users can be associated with a usee. Both elements
	 * should be added to the exam BEFORE this method is called using the addElementIF
	 * method
	 * 
	 * 
	 * @param user
	 *            - one of several elements to depend on the usee
	 * @param usee
	 *            - singular element others are connected with
	 */
	public void declareUse(ExamElementIF user, ExamElementIF usee) {
		if (elements.containsValue(user) && elements.containsValue(usee)) {
			uses.get(user).add(usee);
		} else {
			System.err.println("error calling exam.DeclareUse(user, usee), must add elements to the exam before declaring a relationship");
		}
		
		
	}

	/**
	 * Returns the element specified by the key i, use elements().iterator() to
	 * get the key, the order of the elements in ExamIF is specified to be the
	 * same
	 * 
	 * @param i
	 * @return
	 */
	public ExamElementIF element(int i) {
		return elements.get(i);
	}

	/**
	 * Returns all elements that have a matching label to the argument
	 */
	public ExamElementIF elementWithLabel(String label) {
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while (i.hasNext()) {
			ExamElementIF element = i.next();
			if (element.label().equals(label)) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Returns a collection of all exam elements. The iterator from this
	 * collection will return elements in the order element(0), element(1),
	 */
	public Collection<ExamElementIF> elements() {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while (i.hasNext()) {
			ExamElementIF element = i.next();
			returnSet.add(element);
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	/**
	 * Returns all elements depending on the element specified in the argument.
	 * i.e. if several problems use a figure, and the figure is the argument of
	 * this call a collection of those problems will be returned. If no elements
	 * are found, an empty collection will be returned
	 */
	public Collection<ExamElementIF> elementsUsingElement(ExamElementIF element) {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while (i.hasNext()) {
			ExamElementIF newElement = i.next();
			if (uses.get(newElement).contains(element)) {
				returnSet.add(element);
			}
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	/**
	 * Returns all elements that have a matching topic to the argument
	 */	
	public Collection<ExamElementIF> elementsWithTopic(String topic) {
		HashSet<ExamElementIF> returnSet = new HashSet<ExamElementIF>();
		Collection<ExamElementIF> elementValues = elements.values();
		Iterator<ExamElementIF> i = elementValues.iterator();
		while (i.hasNext()) {
			ExamElementIF element = i.next();
			if (element instanceof ProblemIF) {
				if (((ProblemIF) element).topic().equals(topic))
					returnSet.add(element);
			} else if (element instanceof BlockIF) {
				if (((BlockIF) element).topic().equals(topic)) {
					returnSet.add(element);
				}
			}
		}
		return (Collection<ExamElementIF>) returnSet;
	}

	/**
	 * @return All figures stored in exam
	 */
	public Collection<FigureIF> figures() {
		HashSet<FigureIF> returnSet = new HashSet<FigureIF>();

		Iterator<Integer> i = figures.iterator();
		while (i.hasNext()) {
			returnSet.add((FigureIF) elements.get(i.next()));
		}

		return (Collection<FigureIF>) returnSet;
	}

	/**
	 * @return Closing block of the exam
	 */
	public BlockIF finalBlock() {
		return this.finalblock;
	}

	/**
	 * @return First page intro to exam
	 */
	public SourceIF frontMatter() {
		return this.frontmatter;
	}

	/**
	 * The master exam contains all problems loaded from the UEF, non-master
	 * exams contain only a sub-set of all problems in the UEF
	 * 
	 * @return true if master
	 */
	public boolean isMaster() {
		return this.isMaster;
	}

	/**
	 * A set of all the exam's elements' labels - no duplicates
	 */
	public Collection<String> labels() {
		return labels;
	}

	/**
	 * Number of elements stored in an exam, includes all elements such as
	 * problems, figures, blocks, preambles, but not front matter
	 */
	public int numElements() {
		return elements.size();
	}

	/**
	 * @return Intro to the exam as SourceIF object
	 */	
	public SourceIF preamble() {
		return this.preamble;
	}

	/**
	 * A collection of all problems stored in the exam
	 */
	public Collection<ProblemIF> problems() {
		HashSet<ProblemIF> returnSet = new HashSet<ProblemIF>();

		Iterator<Integer> i = problems.iterator();
		while (i.hasNext()) {
			returnSet.add((ProblemIF) elements.get(i.next()));
		}

		return (Collection<ProblemIF>) returnSet;
	}

	/**
	 * A collection of all problems in the exam with the specified topic
	 */
	public Collection<ProblemIF> problemsWithTopic(String topic) {
		HashSet<ProblemIF> returnSet = new HashSet<ProblemIF>();
		ProblemIF problem;

		Iterator<Integer> i = problems.iterator();
		while (i.hasNext()) {
			problem = (ProblemIF) elements.get(i.next());
			if (problem.topic().equals(topic)) {
				returnSet.add(problem);
			}
		}

		return (Collection<ProblemIF>) returnSet;
	}

	/**
	 * Set the final (non-problem) text in an exam
	 */
	public void setFinalBlock(BlockIF block) {
		this.finalblock = block;
	}

	/**
	 * Sets the exam front matter
	 */
	public void setFrontMatter(SourceIF frontMatter) {
		this.frontmatter = frontMatter;
	}

	/**
	 * Sets the exam preamble
	 */
	public void setPreamble(SourceIF preamble) {
		this.preamble = preamble;
	}

	/**
	 * A set of all of the exam's elements' topics - no duplicates
	 */
	public Collection<String> topics() {
		return (Collection<String>) topics;
	}

}
