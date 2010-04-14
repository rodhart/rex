package edu.udel.cis.cisc475.rex.generate;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;

public class GeneratorFactoryStub implements GeneratorFactoryIF {

	@Override
	public GeneratorIF newGenerator(ExamIF master, ConfigIF config) {
		// TODO Auto-generated method stub
		return new GeneratorStub(master, config);
	}
}
