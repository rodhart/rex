package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a command read from a file.
 * This includes such things as the start position,
 * end position, line number, column number, type,
 * arguments, optional argument, and size.
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
	 */
	UEFCommand()
	{
		this.Arguments = new ArrayList<String>();
	}

	/**
	 * Sets the type of command.
	 * @param type the type of command to set.
	 */
	void setType(Types type)
	{
		this.type = type;
	}

	/**
	 * Sets the start position.
	 * @param position the position to set.
	 */
	void setStartPosition(int position)
	{
		this.startPosition = position;
	}

	/**
	 * Sets the end position.
	 * @param position the position to set.
	 */
	void setEndPosition(int position)
	{
		this.endPosition = position;
	}

	/**
	 * Sets the optional argument.
	 * @param optionalArgument the optional argument to set.
	 */
	void setOptionalArgument(String optionalArgument)
	{
		this.optionalArgument = optionalArgument;
	}

	/**
	 * Adds an argument to the command.
	 * @param argument the argument to add to the command.
	 */
	void addArgument(String argument)
	{
		this.Arguments.add(argument);
	}

	/**
	 * Returns the type of command.
	 * @return the type to return.
	 */
	Types getType()
	{
		return this.type;
	}

	/**
	 * Gets the start position of the command.
	 * @return the start position.
	 */
	int getStartPosition()
	{
		return this.startPosition;
	}

	/**
	 * Gets the end position of the command.
	 * @return the end position.
	 */
	int getEndPosition()
	{
		return this.endPosition;
	}

	/**
	 * Gets the size of the command.
	 * @return the size.
	 */
	int getSize()
	{
		return this.endPosition - this.startPosition;
	}

	/**
	 * Gets the optional argument.
	 * @return the optional argument as a String.
	 */
	String getOptionalArgument()
	{
		return this.optionalArgument;
	}

	/**
	 * Gets an argument from the command using the specified index.
	 * @param index the index of the argument to retrieve.
	 * @return the argument.
	 */
	String getArgument(int index)
	{
		return this.Arguments.get(index);
	}
}
