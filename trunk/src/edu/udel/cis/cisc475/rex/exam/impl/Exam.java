package edu.udel.cis.cisc475.rex.exam.impl;

import java.util.Collection;
import java.util.LinkedHashSet;

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
 * @author Haley Boyd (boyd)
 * @author Tim McClory (tmcclory)
 *
 */
public class Exam implements ExamIF {
	
	private boolean isMaster;

	private LinkedHashSet elements;

	private SourceIF preamble; 
	
	private SourceIF frontmatter;
	
	private BlockIF finalblock;
	

	/**
	 * 
	 */
	public Exam(boolean isMaster) {
		super();
		this.isMaster = isMaster;
	}


	//@Override
	public void addElementIF(ExamElementIF element) {
		// TODO Auto-generated method stub

	}

	//@Override
	public void declareUse(ExamElementIF user, ExamElementIF usee) {
		// TODO Auto-generated method stub

	}

	//@Override
	public ExamElementIF element(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public ExamElementIF elementWithLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<ExamElementIF> elements() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<ExamElementIF> elementsUsingElement(ExamElementIF element) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<ExamElementIF> elementsWithTopic(String topic) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<FigureIF> figures() {
		// TODO Auto-generated method stub
		return null;
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

	//@Override
	public Collection<String> labels() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public int numElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	public SourceIF preamble() {
		return this.preamble;
	}

	//@Override
	public Collection<ProblemIF> problems() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<ProblemIF> problemsWithTopic(String topic) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
