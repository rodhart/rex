package edu.udel.cis.cisc475.rex.ecfparser.impl.err;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.IntStream;

/**
 * Exception to get non RecognitionExceptions out of the parser.
 * @author Burke Cates (cates)
 */
public class EcfParseHackException extends RecognitionException {
	private static final long serialVersionUID = 1L;

	private String message;
	private SourceIF source;

	/** Encapsulate a RexException to get it out of the parser.
	 * @param message reasoning for the exception.
	 * @param badInput the offending string of characters.
	 * @param input input stream of chars. the RecognitionException likes this.
	 * 	alive.
	 */
	public EcfParseHackException(String message, String badInput, IntStream input, SourceIF source) {
		super(input);
		this.source = source;
		this.message = "line " + super.line + ":" + super.charPositionInLine + " parser failure on input '" + badInput + "' " + message;
	}

	/**
	 * Get the message out of the exception.
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Get the source out of the exception.
	 */
	public SourceIF getSource() {
		return this.source;
	}
}
