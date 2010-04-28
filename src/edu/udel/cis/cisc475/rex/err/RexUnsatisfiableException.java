package edu.udel.cis.cisc475.rex.err;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class RexUnsatisfiableException extends RexException {

	/**
	 * @author Zach Hine
	 */

	private static final long serialVersionUID = 1L;

	private String message;

	private SourceIF source;

	public RexUnsatisfiableException(String message, SourceIF source) {
		super(message);
		this.message = message;
		this.source = source;
	}

	public RexUnsatisfiableException() {
		this("The request is not satisfiable", null);
	}

	public SourceIF source() {
		return source;
	}

	public String toString() {
		String result = "Request is not satisfiable.\n" + message + ":\n";

		if (source != null) {
			result += source.filename() + " line " + source.startLine() + "("
					+ source.startColumn() + ")" + " through "
					+ source.lastLine() + "(" + source.lastColumn() + "):\n";
			result += source.text();
		}
		return result;
	}

}
