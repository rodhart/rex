package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * Provides a series of methods for creating objects within the Exam package.
 * 
 * 
 * @author Reed Martz (martz)
 *
 */
public class ExamFactory implements ExamFactoryIF {

	/**
	 * Creates a new instance of AnswerIF.
	 * 
	 * @param correct boolean: Determines if the AnswerIF is a correct answer
	 * @param text SourceIF: The source from the UEF that defines this answer
	 * @return new instance of AnswerIF
	 * @exception NullPointerException If text is null
	 */
	public AnswerIF newAnswer(boolean correct, SourceIF text) {
		if(text == null)
			throw new NullPointerException("The argument text cannot be null");
		return new Answer(correct, text);
	}

	/**
	 * Creates a new instance of BlockIF.
	 * 
	 * @param label String: The label given to this BlockIF
	 * @param text SourceIF: The source from the UEF that defines this BlockIF 
	 * @return new instance of BlockIF
	 * @exception NullPointerException If text is null
	 */
	public BlockIF newBlock(String label, SourceIF text)
	{
		if(text == null)
			throw new NullPointerException("The argument text cannot be null");
		return new Block(label, text);
	}
	
	/**
	 * Creates a new instance of FigureIF.
	 * 
	 * @param label String: The label given to this FigureIF
	 * @param text SourceIF: The source from the UEF that defines this FigureIF 
	 * @return new instance of FigureIF
	 * @exception NullPointerException If text is null
	 */
	public FigureIF newFigure(String label, SourceIF text) {
		if(text == null)
			throw new NullPointerException("The argument text cannot be null");
		return new Figure(label, text);
	}

	/**
	 * Creates a new instance of FixedAnswerIF.
	 * 	
	 * @param correct boolean: Determines if the FixedAnswerIF is a correct answer
	 * @param index int: The position that this FixedAnswerIF must be placed in.
	 * @param text SourceIF: The source from the UEF that defines this FixedAnswerIF 
	 * @return new instance of FixedAnswerIF
	 * @exception NullPointerException If text is null
	 */
	public FixedAnswerIF newFixedAnswer(boolean correct, int index,
			SourceIF text) {
		if(text == null)
			throw new NullPointerException("The argument text cannot be null");
		return new FixedAnswer(index, correct, text);
	}

	/**
	 * Creates a new instance of ExamIF for use as a output Exam.
	 * 
	 * @return new instance of ExamIF
	 * @deprecated Replaced by {@link #newGeneratedExam(String)}
	 */
	public ExamIF newGeneratedExam() {
		return new Exam(false);
	}

	/**
	 * Creates a new instance of ExamIF for use as a output Exam.
	 * 
	 * @param version String: represents the version identifier for this exam.
	 * @return new instance of ExamIF
	 */
	public ExamIF newGeneratedExam(String version) {
		return new Exam(false);
	}
	
	/**
	 * Creates a new instance ExamIF for use as the master repository.
	 * 
	 * @return new master instance of ExamIF
	 */
	public ExamIF newMasterExam() {
		return new Exam(true);
	}

	/**
	 * Creates a new instance of ProblemIF.
	 * 
	 * 
	 * @param topic String: The topic of this ProblemIF
	 * @param label String: The label given to this ProblemIF
	 * @param question SourceIF: The source from the UEF that defines this ProblemIF
	 * @param answers AnswerIF[]: An array of answers for this ProblemIF 
	 * @return new instance of ProblemIF
	 * @exception NullPointerException If topic, question, answers, or any element in answers is null.
	 */
	public ProblemIF newProblem(String topic, String label, SourceIF question, AnswerIF[] answers) {
		if(topic == null) 
				throw new NullPointerException("The argument topic cannot be null");
		if(question == null)   
		throw new NullPointerException("The argument question cannot be null");
		if(answers == null)
			throw new NullPointerException("The argument answers cannot be null");
		for(int i = 0; i < answers.length; i++)
			if(answers[i] == null)
			throw new NullPointerException("The argument answers cannot contain null values");
		return new Problem(topic, label, question, answers);
	}
}
