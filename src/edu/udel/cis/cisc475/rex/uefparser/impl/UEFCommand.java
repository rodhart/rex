package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a command read from a file.
 * This includes such things as the start position,
 * end position, type, arguments, optional argument, and size.
 *
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 */
class UEFCommand
{

	/**
	 * The type of the command
	 */
	private Types type;
	/**
	 * From the backslash, the starting position of the command.
	 */
	private int startPosition;
	/**
	 * Following the last argument, the ending position of the command.
	 */
	private int endPosition;
	/**
	 * The optional argument of the command.
	 */
	private String optionalArgument;
	/**
	 * The arguments of the command
	 */
	private List<String> Arguments;

	/**
	 * All of the supported commands.
	 */
	enum Types
	{

		documentclass, label, ref, answer, beginDocument, endDocument, beginBlock, endBlock,
		beginProblem, endProblem, beginAnswers, endAnswers, beginFigure, endFigure
	}

	/**
	 * A constructor for creating a Command.
	 * Creates a list to store arguments.
	 * And allows the type, startPosition,
	 * and endPosition to be set.
	 */
	UEFCommand(Types type, int startPosition, int endPosition)
	{
		//create a list for the arguments.
		this.Arguments = new ArrayList<String>();
		this.type = type;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.optionalArgument = null;
	}

	/**
	 * Sets the optional argument.
	 * @param optionalArgument the optional argument to set.
	 */
	void setOptionalArgument(String optionalArgument)
	{
		//set the optional argument.
		this.optionalArgument = optionalArgument;
	}

	/**
	 * Adds an argument to the command.
	 * @param argument the argument to add to the command.
	 */
	void addArgument(String argument)
	{
		//add a an argument to the argument list.
		this.Arguments.add(argument);
	}

	/**
	 * Returns the type of command.
	 * @return the type of the command.
	 *
	 * @see Types
	 */
	Types getType()
	{
		//set the type of command.
		return this.type;
	}

	/**
	 * Gets the start position of the command.
	 * @return the start position.
	 */
	int getStartPosition()
	{
		//return the start position of the command.
		return this.startPosition;
	}

	/**
	 * Gets the end position of the command.
	 * @return the end position.
	 */
	int getEndPosition()
	{
		//return the end position of the command.
		return this.endPosition;
	}

	/**
	 * Gets the size of the command.
	 * @return the size.
	 */
	int getSize()
	{
		//return the size of the command based upon the start and end position.
		return this.endPosition - this.startPosition;
	}

	/**
	 * Gets the optional argument.
	 *
	 * @return the optional argument as a String
	 * or null if there is no optional argument.
	 */
	String getOptionalArgument()
	{
		//return the optional argument of the command.
		return this.optionalArgument;
	}

	/**
	 * Gets an argument from the command using the specified index.
	 *
	 * @param index the index of the argument to retrieve.
	 *
	 * @return the argument or null if there is no argument at the
	 * specified index.
	 */
	String getArgument(int index)
	{
		//check to make sure we are not attempting to access
		//an argument that doesn't exist.
		if (index < this.Arguments.size())
		{
			//return the argument because we are within bounds.
			return this.Arguments.get(index);
		}
		else
		{
			//return null because we are outside of argument bounds.
			return null;
		}
	}
}
