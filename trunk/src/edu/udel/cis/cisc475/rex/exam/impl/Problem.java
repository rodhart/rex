package edu.udel.cis.cisc475.rex.exam.impl;

import java.util.Collection;
import java.util.HashSet;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 *
 * @author fxfitz
 *
 */
public class Problem implements ProblemIF {
	private Integer points;
	private double difficulty;
	private String topic;
	private String label;
	private SourceIF question;
	private AnswerIF[] answers;
	
	protected Problem(String topic, String label, SourceIF question,
			AnswerIF[] answers) {
		this.topic = topic;
		this.label = label;
		this.question = question;
		this.answers = answers;
	}
	@Override
	public AnswerIF[] answers() {
		return this.answers;
	}

	@Override
	public AnswerIF[] correctAnswers() {
		// TODO Take out .correctAnswers, and have it loop through
		// all of the answers and see which ones are correct.	
		
		Collection<AnswerIF> correct = new HashSet<AnswerIF>();
		int i = 0;
		
		for(i=0;i<this.answers.length;i++){
			if (this.answers[i].isCorrect() == true){
				correct.add(this.answers[i]);
			}
		}

		return (AnswerIF[]) correct.toArray(new AnswerIF[correct.size()]);
	}

	@Override
	public double difficulty() {
		return this.difficulty;
	}

	@Override
	public Integer points() {
		return this.points;
	}

	@Override
	public SourceIF question() {
		return this.question;
	}

	@Override
	public Collection<FigureIF> referencedFigures() {
		// TODO Figure out what to do here :-P
		return null;
	}

	@Override
	public BlockIF requiredBlock() {
		// TODO Figure out what to do here :-P
		return null;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String topic() {
		return this.topic;
	}

	@Override
	public String label() {
		return this.label;
	}

}
