package edu.udel.cis.cisc475.rex.exam.generatestubs;



import java.util.Collection;
import java.util.HashSet;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 *
 *
 */
public class ProblemStub implements ProblemIF {
	private Integer points;
	private double difficulty;
	private String topic;
	private String label;
	private SourceIF question;
	private AnswerIF[] answers;
	private HashSet<FigureIF> referencedFigures;
	private BlockIF requiredBlock;
	
	protected ProblemStub(String topic, String label, SourceIF question,
			AnswerIF[] answers) {
		this.topic = topic;
		this.label = label;
		this.question = question;
		this.answers = answers;
		this.referencedFigures = new HashSet<FigureIF>();
	}

	public AnswerIF[] answers() {
		return this.answers;
	}

	public AnswerIF[] correctAnswers() {
		Collection<AnswerIF> correct = new HashSet<AnswerIF>();
		int i = 0;
		
		for(i=0;i<this.answers.length;i++){
			if (this.answers[i].isCorrect() == true){
				correct.add(this.answers[i]);
			}
		}

		return (AnswerIF[]) correct.toArray(new AnswerIF[correct.size()]);
	}

	public double difficulty() {
		return this.difficulty;
	}

	public Integer points() {
		return this.points;
	}

	public SourceIF question() {
		return this.question;
	}

	public Collection<FigureIF> referencedFigures() {
		return (Collection<FigureIF>)this.referencedFigures;
	}
	
	void addReferencedFigure(FigureIF fig){
		referencedFigures.add(fig);
	}

	public BlockIF requiredBlock() {
		return this.requiredBlock;
	}
	
	void setRequiredBlock(BlockIF block){
		this.requiredBlock = block;
	}

	public void setPoints(int points) {
		// TODO: not sure if we should throw exception here
		if(points < 0) {
			this.points = 0;
		}
		else {
			this.points = points;
		}
	}
	
	public void setDifficulty(double difficulty){
		this.difficulty = difficulty;
	}

	public String topic() {
		return this.topic;
	}

	public String label() {
		return this.label;
	}

}
