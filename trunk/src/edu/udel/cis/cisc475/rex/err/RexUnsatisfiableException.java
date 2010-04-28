package edu.udel.cis.cisc475.rex.err;

public class RexUnsatisfiableException extends RexException {

	/**
	 * @author Zach Hine
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RexUnsatisfiableException()
	{
		super("The operation you have requested is unsatisfiable.");
	}
	
	public RexUnsatisfiableException(String message)
	{
		super(message);
	}
	
	
}
