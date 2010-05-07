package edu.udel.cis.cisc475.rex.exam.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import java.util.ArrayList;

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
	
	private String versionID;

	/**
	 * Stores all elements (problems, figures, blocks) in one collection
	 * accessible both as a map (by key)
	 */
	private Map<Integer, ExamElementIF> elements;

	/**
	 * A list storing the keys of all the figures in the Map elements
	 */
	private List<Integer> figures;

	/**
	 * A list storing the keys of all the blocks in the Map elements
	 */
	private List<Integer> blocks;

	/**
	 * A list storing the keys of all the problems in the Map elements
	 */
	private List<Integer> problems;

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
	private Set<String> topics;

	/**
	 * Set of labels throughout all ExamElements
	 */
	private Set<String> labels;

	/**
	 *  Set representing the uses relationship between elements.
	 *  uses[user] = { the set of usees}
	 *  If Element A uses soley Element B, then
	 *  uses[A] = {B}
	 */
	private Map<ExamElementIF, HashSet<ExamElementIF>> uses;
	
	private Map<ExamElementIF, HashSet<ExamElementIF>> usedBy;
	
	/**
	 * Mapping from label (String) to the Exam element that uses this label
	 */
	private Map<String, ExamElementIF> labelToElement;
	
	/**
	 * Mapping from topic (String) to all Exam elements described by this topic.
	 * Only Blocks and Problems have topics.
	 */
	private Map<String, HashSet<ExamElementIF>> topicToElements;
	
	/**
	 * Mapping from topic (String) to problems that are described by this topic.
	 */
	private Map<String, HashSet<ProblemIF>> topicToProblems;

	/**
	 * Deprecated, do not use
	 * @param isMaster
	 * @deprecated
	 */
	public Exam(boolean isMaster) {
		this(isMaster, "null");
	}
	
	/**
	 * Default constructor
	 */
	public Exam(boolean isMaster, String versionID) {
		super();
		this.isMaster = isMaster;
		this.versionID = versionID;
		this.elements = new LinkedHashMap<Integer, ExamElementIF>();
		this.figures = new LinkedList<Integer>();
		this.blocks = new LinkedList<Integer>();
		this.problems = new LinkedList<Integer>();
		this.labels = new HashSet<String>();
		this.topics = new HashSet<String>();
		this.uses = new LinkedHashMap<ExamElementIF, HashSet<ExamElementIF>>();
		this.usedBy = new LinkedHashMap<ExamElementIF, HashSet<ExamElementIF>>();
		this.labelToElement = new LinkedHashMap<String, ExamElementIF>();
		this.topicToElements = new LinkedHashMap<String, HashSet<ExamElementIF>>();
		this.topicToProblems = new LinkedHashMap<String, HashSet<ProblemIF>>();
	}

	/**
	 * Adds an element to the exam. Duplicate elements will not be added.
	 * 
	 * @return the key for the element, or -1 if a duplicate
	 */
	public int addElement(ExamElementIF element) {
		// Even though we are using a map, we really want a set so should not
		// add duplicate entries in a single exam
		if (!elements.containsValue(element) && element != null) {
			int key = elements.size();
			if (element instanceof FigureIF) {
				figures.add(key);
			} else if (element instanceof BlockIF) {
				blocks.add(key);
				if(topicToElements.containsKey(((BlockIF) element).topic())) {
					topicToElements.get(((BlockIF) element).topic()).add(element);
				}
				else {
					HashSet<ExamElementIF> newSet = new HashSet<ExamElementIF>();
					newSet.add(element);
					topicToElements.put(((BlockIF) element).topic(), newSet);
				}
			} else if (element instanceof ProblemIF) {
				problems.add(key);
				topics.add(((ProblemIF) element).topic());
				if(topicToProblems.containsKey(((ProblemIF) element).topic())) {
					topicToProblems.get(((ProblemIF) element).topic()).add((ProblemIF) element);
				}
				else {
					HashSet<ProblemIF> newSet = new HashSet<ProblemIF>();
					newSet.add((ProblemIF) element);
					topicToProblems.put(((ProblemIF) element).topic(), newSet);
				}
				if(topicToElements.containsKey(((ProblemIF) element).topic())) {
					topicToElements.get(((ProblemIF) element).topic()).add(element);
				}
				else {
					HashSet<ExamElementIF> newSet = new HashSet<ExamElementIF>();
					newSet.add(element);
					topicToElements.put(((ProblemIF) element).topic(), newSet);
				}
		
			} 
		
			// put into linked hash set
			elements.put(key,element);
			// also need to make some kind of record of whether it is
			// a problem, a block, or a figure.
			labels.add(element.label());
			labelToElement.put(element.label(), element);
			
			// Allocate a new HashSet for the uses map
			HashSet<ExamElementIF> useesOfElement = new HashSet<ExamElementIF>();
			HashSet<ExamElementIF> usersOfElement = new HashSet<ExamElementIF>();
			uses.put(element, useesOfElement);
			usedBy.put(element, usersOfElement);
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
	public void declareUse(ExamElementIF user, ExamElementIF usee) throws RexException{
		if (elements.containsValue(user) && elements.containsValue(usee)) {
			uses.get(user).add(usee);
			usedBy.get(usee).add(user);
			if(user instanceof ProblemIF && usee instanceof FigureIF) {
				((Problem) user).addReferencedFigure((FigureIF) usee);
			}
			if(user instanceof ProblemIF && usee instanceof BlockIF){
				((Problem) user).setRequiredBlock((BlockIF) usee);
				
				if ( ((BlockIF) usee).topic() == null || 
						((BlockIF) usee).topic().equals(((ProblemIF) user).topic())){
					((Block) usee).setTopic( ((ProblemIF) user).topic());
				} else {
					throw new RexException("Cannot change a blocks topic once it is set!");
				}
			}
		} else {
			throw new RexException("error calling exam.DeclareUse(user, usee), must add elements to the exam before declaring a relationship");
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
	 * Returns the element with a matching label to the argument, there should be only one element
	 * with the given label specified in the UEF but this is not guaranteed.
	 */
	public ExamElementIF elementWithLabel(String label) {
		return labelToElement.get(label);
	}

	/**
	 * Returns a collection of all exam elements. The iterator from this
	 * collection will return elements in the order element(0), element(1),
	 */
	public Collection<ExamElementIF> elements() {
		Set<ExamElementIF> returnSet = new HashSet<ExamElementIF>();
		Iterator<ExamElementIF> i = elements.values().iterator();
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
		Set<ExamElementIF> emptySet = new HashSet<ExamElementIF>();
		if (usedBy.get(element)==null) {
			return emptySet;
		}
		return usedBy.get(element);
	}

	/**
	 * Returns all elements that have a matching topic to the argument
	 */	
	public Collection<ExamElementIF> elementsWithTopic(String topic) {
		Set<ExamElementIF> emptySet = new HashSet<ExamElementIF>();
		if (topicToElements.get(topic) == null) {
			return emptySet;
		}
		return topicToElements.get(topic);
	}

	/**
	 * @return All figures stored in exam
	 */
	public Collection<FigureIF> figures() {
		Set<FigureIF> returnSet = new HashSet<FigureIF>();

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
		List<ProblemIF> returnSet = new ArrayList<ProblemIF>();

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
		Set<ProblemIF> emptySet = new HashSet<ProblemIF>();
		if (topicToElements.get(topic) == null) {
			return emptySet;
		}
		return topicToProblems.get(topic);
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

	/**
	 * Get the Version ID string for an exam. The master exam will return
	 * "master" while a generated exam will return whatever version was set by
	 * the ExamFactory (as specified in the ECF)
	 * 
	 * @return Version ID string
	 */
	public String version() {
		return versionID;
	}
}
