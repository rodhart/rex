package edu.udel.cis.cisc475.rex.key.generatestubs;

import java.util.Collection;
import java.util.LinkedList;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public class AnswerKeyStub implements AnswerKeyIF {

	private String[][] answers = { { "answer1" }, { "answer2a", "answer2b" } };

	@Override
	public void addProblem(Collection<String> Problem) {
	}

	@Override
	public Collection<String> answers(int i) {
		Collection<String> collection = new LinkedList<String>();

		for (String answer : answers[i])
			collection.add(answer);
		return collection;
	}

	@Override
	public String date() {
		return "21-Jan-1977";
	}

	@Override
	public String examName() {
		return "CISC475 Final Exam";
	}

	@Override
	public int numProblems() {
		return 2;
	}

	@Override
	public String version() {
		return "A";
	}

}
