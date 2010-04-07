package edu.udel.cis.cisc475.rex.key;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;

public class Keys {

	public AnswerKeyFactoryIF newAnswerKeyFactory() {
		return new AnswerKeyFactory();
	}

}
