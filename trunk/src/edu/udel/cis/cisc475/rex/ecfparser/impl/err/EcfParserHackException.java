package edu.udel.cis.cisc475.rex.ecfparser.impl.err;

import edu.udel.cis.cisc475.rex.err.RexException;
import org.antlr.runtime.RecognitionException;

/**
 * Exception to get non RecognitionExceptions out of the parser.
 * @author Burke Cates (cates)
 */
public class EcfParserHackException extends RecognitionException {
	private static final long serialVersionUID = 1L;
	private RexException storedException;

	/** Encapsulate a RexException to get it out of the parser.
	 * @param e RexException that wants to get out of the parser
	 * 	alive.
	 */
	public EcfParserHackException(RexException e) {
		this.storedException = e;
	}

	/** Free the RexException trapped inside this hack of
	 * 	an exception.
	 * @return RexException stored within.
	 */
	public RexException releaseTheRex() {
		return this.storedException;
	}
}
