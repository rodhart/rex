package edu.udel.cis.cisc475.rex.generate.IF;

import edu.udel.cis.cisc475.rex.config.IF.*;
import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.*;

public interface GeneratorFactoryIF {
	
	/**
	 * @author Team 3
	 * @throws Exception 
	 * @throws RexUnsatisfiableException 
	 * 
	 */

	GeneratorIF newGenerator(ExamIF master, ConfigIF config) throws RexException;
	
}