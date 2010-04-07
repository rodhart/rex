package edu.udel.cis.cisc475.rex.key;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public class AnswerKeyFactory implements AnswerKeyFactoryIF {

	@Override
	public AnswerKeyIF newAnswerKey(String version, String examName, String date) {
		return new AnswerKey();
	}

}
