package edu.udel.cis.cisc475.rex.generate.IF;

import edu.udel.cis.cisc475.rex.config.IF.*;
import edu.udel.cis.cisc475.rex.exam.IF.*;

public interface GeneratorFactoryIF {
	
	/**
	 * @author Team 3
	 */

	GeneratorIF newGenerator(ExamIF master, ConfigIF config);
	
}