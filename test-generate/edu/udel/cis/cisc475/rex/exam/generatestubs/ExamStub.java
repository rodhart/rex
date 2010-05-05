package edu.udel.cis.cisc475.rex.exam.generatestubs;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.generatestubs.SourceFactoryStub;

public class ExamStub implements ExamIF {

	private boolean isMaster = true;
	//private HashSet<String> labels = new HashSet<String>();
	//labels.add("A");
	SourceFactoryIF sourceFactory = new SourceFactoryStub();
	ExamFactoryIF examFactory = new ExamFactoryStub();
	
	
	@Override
	public int addElement(ExamElementIF element) {
		return 0;
	}

	@Override
	public void declareUse(ExamElementIF user, ExamElementIF usee) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExamElementIF element(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamElementIF elementWithLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ExamElementIF> elements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ExamElementIF> elementsUsingElement(ExamElementIF element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ExamElementIF> elementsWithTopic(String topic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FigureIF> figures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockIF finalBlock() {
		SourceIF finalSource = sourceFactory.newSource("testFile.txt");
		finalSource.addText("This is the text for the final block");
		
		BlockIF finalBlock = examFactory.newBlock("Final label", finalSource);
		
		return finalBlock;
	}

	@Override
	public SourceIF frontMatter() {
		SourceIF frontMatter = sourceFactory.newSource("testFile.txt");
		frontMatter.addText("First page of exam");
		
		return frontMatter;
	}

	@Override
	public boolean isMaster() {
		// TODO Auto-generated method stub
		return isMaster;
	}

	@Override
	public Collection<String> labels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SourceIF preamble() {
		SourceIF preamble = sourceFactory.newSource("testFile.txt");
		preamble.addText("Intro to exam");
		
		return preamble;
	}

	@Override
	public Collection<ProblemIF> problems() {
		SourceIF question1 = sourceFactory.newSource("testFile.txt");
		question1.addText("question1");
		
		AnswerIF[] answers1 = new Answer[2];
		//answers1[0] = examFactory.newAnswer(true, sourceFactory.newSource("option1"));
		//answers1[1] = examFactory.newAnswer(false, sourceFactory.newSource("option2"));
		
		answers1[0] = new Answer(true, sourceFactory.newSource("option1"));
		answers1[1] = new Answer(false, sourceFactory.newSource("option2"));
		
		ProblemIF prob1 = examFactory.newProblem("topic1", "label1", question1, answers1);
		
		
		SourceIF question2 = sourceFactory.newSource("testFile.txt");
		question1.addText("question2");
		
		AnswerIF[] answers2 = new AnswerStub[2];
		answers2[0] = examFactory.newAnswer(false, sourceFactory.newSource("option3"));
		answers2[1] = examFactory.newAnswer(true, sourceFactory.newSource("option4"));
		
		ProblemIF prob2 = examFactory.newProblem("topic1", "label1", question2, answers2);
		
		ArrayList<ProblemIF> list = new ArrayList<ProblemIF>();
		list.add(prob1);
		list.add(prob2);
		
		return list;
	}

	@Override
	public Collection<ProblemIF> problemsWithTopic(String topic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFinalBlock(BlockIF block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFrontMatter(SourceIF frontMatter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPreamble(SourceIF preamble) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> topics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

}
