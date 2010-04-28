package edu.udel.cis.cisc475.rex.err;

public class RexParseException extends RexException {

	/**
	 * @author Zach Hine
	 */
	
	private static final long serialVersionUID = 1L;
	
	public RexParseException()
	{
		super("The parsing operation you have attempted could not be completed.");
	}
	
	public RexParseException(String message)
	{
		super(message);
	}
	
}
