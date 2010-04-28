package edu.udel.cis.cisc475.rex.generate;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class TestExam1 {
	
	private static ExamFactoryIF masterFactory;
	private static ExamIF master1;
	
	private static SourceFactoryIF sourceFactory;
	private static SourceIF question1, question2, question3;
	
	private static AnswerIF[] answers1, answers2, answers3;
	
	private static ProblemIF prob1, prob2, prob3;
	
	public TestExam1()
	{
		masterFactory = new ExamFactory();
		sourceFactory = new SourceFactory();
		
		master1 = masterFactory.newMasterExam();
		
		
		question1 = sourceFactory.newSource("testFile.txt");		
		question1.addText("question1");		
		answers1 = new Answer[7];
		answers1[0] = new Answer(true, sourceFactory.newSource("option1"));
		answers1[1] = new Answer(true, sourceFactory.newSource("option2"));
		answers1[2] = new Answer(true, sourceFactory.newSource("option3"));
		answers1[3] = new Answer(true, sourceFactory.newSource("option4"));
		answers1[4] = new Answer(true, sourceFactory.newSource("option5"));
		answers1[5] = new Answer(true, sourceFactory.newSource("option6"));
		answers1[6] = new Answer(true, sourceFactory.newSource("option7"));
		prob1 = masterFactory.newProblem("topic1", "label1", question1, answers1);
		prob1.setDifficulty(3.0);
		
		question2 = sourceFactory.newSource("testFile.txt");
		question2.addText("question2");		
		answers2 = new Answer[2];
		answers2[0] = new Answer(false, sourceFactory.newSource("option8"));
		answers2[1] = new Answer(true, sourceFactory.newSource("option9"));
		prob2 = masterFactory.newProblem("topic2", "label2", question2, answers2);
		prob2.setDifficulty(3.0);
		
		question3 = sourceFactory.newSource("testFile.txt");		
		question3.addText("question3");		

		// create new problem with an 8th answer
		answers3 = new Answer[8];
		answers3[0] = new Answer(true, sourceFactory.newSource("option15"));
		answers3[1] = new Answer(true, sourceFactory.newSource("option16"));
		answers3[2] = new Answer(true, sourceFactory.newSource("option17"));
		answers3[3] = new Answer(true, sourceFactory.newSource("option18"));
		answers3[4] = new Answer(true, sourceFactory.newSource("option19"));
		answers3[5] = new Answer(true, sourceFactory.newSource("option20"));
		answers3[6] = new Answer(true, sourceFactory.newSource("option21"));
		answers3[7] = new Answer(true, sourceFactory.newSource("option22"));
		prob3 = masterFactory.newProblem("topic3", "label3", question3, answers3);
		prob3.setDifficulty(3.0);
		
		master1.addElement(prob1);
		master1.addElement(prob2);
		master1.addElement(prob3);
	}

	public ExamIF getMaster()
	{
		return master1;
	}
	
}
