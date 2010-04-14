package edu.udel.cis.cisc475.rex.generate.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;

public class GeneratorFactory implements GeneratorFactoryIF {

	public GeneratorIF newGenerator(ExamIF master, ConfigIF config){
		try {
			return new Generator(master, config);
		} catch (RexUnsatisfiableException e) {
			e.printStackTrace();
		}
		return null;
	}

}
