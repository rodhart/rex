package edu.udel.cis.cisc475.rex.err;

public class RexException extends Exception {
	
	/**
	 * @author Zach Hine
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RexException() {
		super("The operation you have requested cannot be completed.");
	}

	public RexException(String message) {
		super(message);
	}
	

}
