package edu.udel.cis.cisc475.rex.err;

public class RexException extends Exception {

	/**
	 * @author Zach Hine
	 */

	private static final long serialVersionUID = 1L;

	public RexException() {
		super("A REX error has occurred.");
	}

	public RexException(String message) {
		super(message);
	}

}
