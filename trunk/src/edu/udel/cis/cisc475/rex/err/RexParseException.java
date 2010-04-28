package edu.udel.cis.cisc475.rex.err;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class RexParseException extends RexException {

	/**
	 * @author Zach Hine
	 */

	private static final long serialVersionUID = 1L;

	private String message;

	private SourceIF source;

	public RexParseException(String message, SourceIF source) {
		super(message);
		this.message = message;
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
		String result = "A parsing error has occurred.\n" + message + ":\n";

		if (source != null) {
			result += source.filename() + " line " + source.startLine() + "("
					+ source.startColumn() + ")" + " through "
					+ source.lastLine() + "(" + source.lastColumn() + "):\n";
			result += source.text();
		}
		return result;
	}
}
