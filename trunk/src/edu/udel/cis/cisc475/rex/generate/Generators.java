package edu.udel.cis.cisc475.rex.generate;

import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;

public class Generators {

	public static GeneratorFactoryIF newGeneratorFactory() {
		return new GeneratorFactory();
	}

}
