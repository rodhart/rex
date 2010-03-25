package edu.udel.cis.cisc475.rex.exam.IF;

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
    public ExamIF newMasterExam();
    
    /**
     * 
     * @return
     */
    public ExamIF newGeneratedExam();
    
    /**
     * 
     * @param topic
     * @param label
     * @param text
     * @return
     */
    public BlockIF newBlock(String topic, String label, SourceIF text);
    
    /**
     * 
     * @param label
     * @param text
     * @return
     */
    public FigureIF newFigure(String label, SourceIF text);
    
    /**
     * 
     * @param correct
     * @param text
     * @return
     */
    public AnswerIF newAnswer(boolean correct, SourceIF text);
    
    /**
     * 
     * @param correct
     * @param index
     * @param text
     * @return
     */
    public FixedAnswerIF newFixedAnswer(boolean correct, int index, SourceIF text);
    
    /**
     * 
     * @param topic
     * @param label
     * @param question
     * @param answers
     * @return
     */
    public ProblemIF newProblem(String topic, String label, SourceIF question, AnswerIF[] answers);
    
}
