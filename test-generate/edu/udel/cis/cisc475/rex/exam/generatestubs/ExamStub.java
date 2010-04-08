package edu.udel.cis.cisc475.rex.exam.generatestubs;

import java.util.Collection;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class ExamStub implements ExamIF {

	@Override
	public void addElementIF(ExamElementIF element) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceIF frontMatter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMaster() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ProblemIF> problems() {
		// TODO Auto-generated method stub
		return null;
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

}
