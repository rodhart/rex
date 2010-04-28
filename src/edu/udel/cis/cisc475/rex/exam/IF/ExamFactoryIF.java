package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * Provides a series of methods for creating objects within the Exam package.
 * 
 * 
 * @author Reed Martz (martz)
 *
 */
public interface ExamFactoryIF {
	
	/**
	 * Creates a new instance ExamIF for use as the master repository.
	 * 
	 * @return new master instance of ExamIF
	 */
    ExamIF newMasterExam();
    
	/**
	 * Creates a new instance of ExamIF for use as a output Exam.
	 * 
	 * @return new instance of ExamIF
	 */
    ExamIF newGeneratedExam();
        
	/**
	 * Creates a new instance of BlockIF.
	 * 
	 * @param label String: The label given to this BlockIF
	 * @param text SourceIF: The source from the UEF that defines this BlockIF 
	 * @return new instance of BlockIF
	 * @exception NullPointerException If text is null
	 */
	 BlockIF newBlock(String label, SourceIF text);
    
		/**
		 * Creates a new instance of FigureIF.
		 * 
		 * @param label String: The label given to this FigureIF
		 * @param text SourceIF: The source from the UEF that defines this FigureIF 
		 * @return new instance of FigureIF
		 * @exception NullPointerException If text is null
		 */
    FigureIF newFigure(String label, SourceIF text);
    
    /**
	 * Creates a new instance of AnswerIF.
	 * 
	 * @param correct boolean: Determines if the AnswerIF is a correct answer
	 * @param text SourceIF: The source from the UEF that defines this answer
	 * @return new instance of AnswerIF
	 * @exception NullPointerException If text is null
	 */
	  AnswerIF newAnswer(boolean correct, SourceIF text);
    
		/**
		 * Creates a new instance of FixedAnswerIF.
		 * 	
		 * @param correct boolean: Determines if the FixedAnswerIF is a correct answer
		 * @param index int: The position that this FixedAnswerIF must be placed in.
		 * @param text SourceIF: The source from the UEF that defines this FixedAnswerIF 
		 * @return new instance of FixedAnswerIF
		 * @exception NullPointerException If text is null
		 */
    FixedAnswerIF newFixedAnswer(boolean correct, int index, SourceIF text);
    
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
  ProblemIF newProblem(String topic, String label, SourceIF question, AnswerIF[] answers);
    
}
