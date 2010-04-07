package edu.udel.cis.cisc475.rex.key;

import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;

public class Keys {

	public AnswerKeyFactoryIF newAnswerKeyFactory() {
		return new AnswerKeyFactory();
	}

}
