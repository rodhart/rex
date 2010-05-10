package edu.udel.cis.cisc475.rex.err;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class RexParseException extends RexException {

	/**
	 * @author Zach Hine
	 * 
	 * A Rex Exception signifying faulty input to Rex. In addition
	 * to a String, can take a Source (to more accurately pinpoint
	 * the bad input).
	 */

	private static final long serialVersionUID = 1L;


	private SourceIF source;

	public RexParseException(String message, SourceIF source) {
		super(message);
		this.source = source;
	}

	public RexParseException() {
		this("The parsing operation you have attempted could not be completed",
				null);
	}

	public SourceIF source() {
		return source;
	}

	public String toString() {
		String result = "A parsing error has occurred.\n" + this.getMessage() + ":\n";

		if (source != null) {
			result += source.filename() + " line " + source.startLine() + "("
					+ source.startColumn() + ")" + " through "
					+ source.lastLine() + "(" + source.lastColumn() + "):\n";
			result += source.text();
		}
		return result;
	}
}
