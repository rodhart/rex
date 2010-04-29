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
	 * The simple constructor for creating a Command.
	 * Creates a list to store arguments.
	 */
	UEFCommand()
	{
		//create a list for the arguments.
		this.Arguments = new ArrayList<String>();
		this.type = null;
		this.startPosition = -1;
		this.endPosition = -1;
		this.optionalArgument = null;
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
	 * Sets the type of command.
	 * @param type the type of command to set.
	 */
	void setType(Types type)
	{
		//set the type of command.
		this.type = type;
	}

	/**
	 * Sets the start position.
	 * @param position the position to set.
	 */
	void setStartPosition(int position)
	{
		//set the start position.
		this.startPosition = position;
	}

	/**
	 * Sets the end position.
	 * @param position the position to set.
	 */
	void setEndPosition(int position)
	{
		//set the end position.
		this.endPosition = position;
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
	 * @return the type of the command or null if the type hasn't been set.
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
	 * @return the start position. or -1 if it hasn't been set.
	 */
	int getStartPosition()
	{
		//return the start position of the command.
		return this.startPosition;
	}

	/**
	 * Gets the end position of the command.
	 * @return the end position or -1 if it hasn't been set.
	 */
	int getEndPosition()
	{
		//return the end position of the command.
		return this.endPosition;
	}

	/**
	 * Gets the size of the command.
	 * @return the size or -1 if either start or end position is not positive.
	 */
	int getSize()
	{
		//make sure command positions are positive before calculating size.
		if (this.endPosition > -1 && this.startPosition > -1)
		{
			//return the size of the command based upon the start and end position.
			return this.endPosition - this.startPosition;
		}
		else
		{
			//command positions aren't positive so return -1.
			return -1;
		}
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
