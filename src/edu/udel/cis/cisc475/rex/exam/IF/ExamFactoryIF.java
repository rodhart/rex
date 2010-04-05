package edu.udel.cis.cisc475.rex.exam.IF;

import edu.udel.cis.cisc475.rex.source.IF.*;

/**
 * 
 * @author Team 1
 *
 */
public interface ExamFactoryIF {
	
	/**
	 * 
	 * @return
	 */
    ExamIF newMasterExam();
    
    /**
     * 
     * @return
     */
    ExamIF newGeneratedExam();
    
    /**
     * 
     * @param topic
     * @param label
     * @param text
     * @return
     */
    BlockIF newBlock(String topic, String label, SourceIF text);
    
    /**
     * 
     * @param label
     * @param text
     * @return
     */
    FigureIF newFigure(String label, SourceIF text);
    
    /**
     * 
     * @param correct
     * @param text
     * @return
     */
    AnswerIF newAnswer(boolean correct, SourceIF text);
    
    /**
     * 
     * @param correct
     * @param index
     * @param text
     * @return
     */
    FixedAnswerIF newFixedAnswer(boolean correct, int index, SourceIF text);
    
    /**
     * 
     * @param topic
     * @param label
     * @param question
     * @param answers
     * @return
     */
    ProblemIF newProblem(String topic, String label, SourceIF question, AnswerIF[] answers);
    
}
