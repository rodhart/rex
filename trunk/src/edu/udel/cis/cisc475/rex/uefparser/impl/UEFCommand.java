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
public class UEFCommand
{

	/**
	 * The type of the command
	 */
	Types type;
	/**
	 * From the backslash, the starting position of the command.
	 */
	int startPosition;
	/**
	 * Following the last argument, the ending position of the command.
	 */
	int endPosition;
	/**
	 * The line number of the command.
	 */
	private int lineNumber;
	/**
	 * The column number of the command.
	 */
	private int columnNumber;
	/**
	 * The optional argument of the command.
	 */
	String optionalArgument;
	/**
	 * The arguments of the command
	 */
	List<String> Arguments;

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
	public UEFCommand()
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
	 * Sets the line number.
	 * @param lineNumber the line number to set.
	 */
	void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	/**
	 * Sets the column number.
	 * @param columnNumber the column number to set.
	 */
	void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
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
	 * Gets the line number of the command.
	 * @return the line number.
	 */
	int getLineNumber()
	{
		return this.lineNumber;
	}

	/**
	 * Gets the column number of the command.
	 * @return the column number.
	 */
	int getColumnNumber()
	{
		return this.columnNumber;
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
