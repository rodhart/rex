package edu.udel.cis.cisc475.rex.output;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class ExamFactoryStub implements ExamFactoryIF {

	
	@Override
	public BlockIF newBlock(String topic, SourceIF text) {
		return null;
	}
	
	
	
	
	@Override
	public AnswerIF newAnswer(boolean correct, SourceIF text) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public FigureIF newFigure(String label, SourceIF text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FixedAnswerIF newFixedAnswer(boolean correct, int index,
			SourceIF text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamIF newGeneratedExam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExamIF newMasterExam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProblemIF newProblem(String topic, String label, SourceIF question,
			AnswerIF[] answers) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ExamIF newGeneratedExam(String version) {
		// TODO Auto-generated method stub
		return null;
	}

}
