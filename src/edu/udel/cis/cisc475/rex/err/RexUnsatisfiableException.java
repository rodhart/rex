package edu.udel.cis.cisc475.rex.err;

import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

public class RexUnsatisfiableException extends RexException {

	/**
	 * @author Zach Hine
	 * 
	 * A Rex Exception signifying that a constraint cannot
	 * be fulfilled. In addition to a String, a Source can be
	 * taken as input to better pinpoint the unsatisfiable
	 * constraint.
	 */

	private static final long serialVersionUID = 1L;

	private SourceIF source;

	public RexUnsatisfiableException(String message, SourceIF source) {
		super(message);		
		this.source = source;
	}

	public RexUnsatisfiableException(String message) {
		this(message, null);
	}

	public RexUnsatisfiableException() {
		this("The request is not satisfiable");
	}

	public SourceIF source() {
		return source;
	}

	public String toString() {
		String result = "Request is not satisfiable.\n" + this.getMessage() + ":\n";

		if (source != null) {
			result += source.filename() + " line " + source.startLine() + "("
					+ source.startColumn() + ")" + " through "
					+ source.lastLine() + "(" + source.lastColumn() + "):\n";
			result += source.text();
		}
		return result;
	}

}
