package edu.udel.cis.cisc475.rex.generate.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;

public class GeneratorFactory implements GeneratorFactoryIF 
{
	public GeneratorIF newGenerator(ExamIF master, ConfigIF config) throws RexException
	{
			return new Generator(master, config);
	}
}
