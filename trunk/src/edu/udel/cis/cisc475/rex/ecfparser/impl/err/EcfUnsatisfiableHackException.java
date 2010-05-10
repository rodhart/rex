package edu.udel.cis.cisc475.rex.ecfparser.impl.err;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.IntStream;

/**
 * Exception to get non RecognitionExceptions out of the parser.
 * @author Burke Cates (cates)
 */
public class EcfUnsatisfiableHackException extends RecognitionException {
	private static final long serialVersionUID = 1L;

	private String message;

	/** Encapsulate a RexException to get it out of the parser.
	 * @param message reasoning for the exception.
	 * @param badInput the offending string of characters.
	 * @param input input stream of chars. the RecognitionException likes this.
	 * 	alive.
	 */
	public EcfUnsatisfiableHackException(String message, String badInput, IntStream input) {
		super(input);
		this.message = "line " + super.line + ":" + super.charPositionInLine + " unsatisfiable input '" + badInput + "' " + message;
	}

	/**
	 * Get the message out of the exception.
	 */
	public String getMessage() {
		return this.message;
	}
}
