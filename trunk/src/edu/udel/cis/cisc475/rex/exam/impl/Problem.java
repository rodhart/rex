package edu.udel.cis.cisc475.rex.exam.impl;

import java.util.Collection;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class Problem implements ProblemIF {
	private Integer points;
	private double difficulty;
	private String topic;
	private String label;
	private AnswerIF[] answers;
	private AnswerIF[] correctAnswers;
	
	@Override
	public AnswerIF[] answers() {
		return answers;
	}

	@Override
	public AnswerIF[] correctAnswers() {
		return correctAnswers;
	}

	@Override
	public double difficulty() {
		// TODO Auto-generated method stub
		return difficulty;
	}

	@Override
	public Integer points() {
		// TODO Auto-generated method stub
		return this.points;
	}

	@Override
	public SourceIF question() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FigureIF> referencedFigures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockIF requiredBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String topic() {
		return topic;
	}

}
