package edu.udel.cis.cisc475.rex.err;

public class RexException extends Exception {

	/**
	 * @author Zach Hine
	 * 
	 * Generic Rex Exception that serves as a base
	 * for other Rex specific Exceptions. Can take a String,
	 * and will print the String back to the user.
	 */

	private static final long serialVersionUID = 1L;

	public RexException() {
		super("A REX error has occurred.");
	}

	public RexException(String message) {
		super(message);
	}

}
