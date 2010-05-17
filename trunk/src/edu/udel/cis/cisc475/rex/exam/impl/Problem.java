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
	private HashSet<FigureIF> referencedFigures;
	private BlockIF requiredBlock;

	protected Problem(String topic, String label, SourceIF question,
			AnswerIF[] answers) {
		this.topic = topic;
		this.label = label;
		this.question = question;
		this.answers = answers;
		this.referencedFigures = new HashSet<FigureIF>();

	}

	/**
	 * array of answers (correct and incorrect) to the question
	 */
	public AnswerIF[] answers() {
		return this.answers;
	}


	/**
	 * 
	 * @return array of correct answers to the question
	 */
	public AnswerIF[] correctAnswers() {
		Collection<AnswerIF> correct = new HashSet<AnswerIF>();
		int i = 0;

		for (i = 0; i < this.answers.length; i++) {
			if (this.answers[i].isCorrect() == true) {
				correct.add(this.answers[i]);
			}
		}

		return (AnswerIF[]) correct.toArray(new AnswerIF[correct.size()]);
	}

	/**
	 * difficulty of the problem
	 */
	public double difficulty() {
		return this.difficulty;
	}

	/**
	 * the number of points a problem is worth
	 */
	public Integer points() {
		return this.points;
	}

	/**
	 * reference to question in the source UEF file
	 */
	public SourceIF question() {
		return this.question;
	}

	/**
	 * Collection of FigureIF.
	 */
	public Collection<FigureIF> referencedFigures() {
		return (Collection<FigureIF>) this.referencedFigures;
	}
	
	/**
	 * If the problem referrences a block, this will return that block,
	 * otherwise it will be null
	 */
	public BlockIF requiredBlock() {
		return this.requiredBlock;
	}

	/**
	 * points set amount of points the problem is worth
	 */
	public void setPoints(int points) {
		if (points < 0) {
			this.points = 0;
		} else {
			this.points = points;
		}
	}

	/**
	 * sets the difficulty of the problems
	 */
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * problem topic
	 */
	public String topic() {
		return this.topic;
	}
	
	void addReferencedFigure(FigureIF fig) {
		referencedFigures.add(fig);
	}
	public String label() {
		return this.label;
	}
	void setRequiredBlock(BlockIF block) {
		this.requiredBlock = block;
	}
}
