package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles the underlying UEF file character by character.
 * Internally uses unix line breaks for internal representation.
 * This is important to note because the position may differ
 * from the actual byte position in the underlying file. For instance-
 * in the case of windows line breaks. This would be true.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-Hassany
 */
class UEFCharHandler
{

	/**
	 * The current position in the file. This will count the new lines as just
	 * another charcte
	 */
	private int position;
	/**
	 * Mapping of file position to actual file lines.
	 */
	NavigableMap<Integer, Integer> positionToLineNumberMap;
	/**
	 * The complete contents of the file
	 */
	private String fileContents;
	/**
	 * the name of the file being handled
	 */
	private String fileName;

	/**
	 * Opens a file and completely reads it into a StringBuffer for easy
	 * parsing. Set's the position to the first character of the file.
	 * 
	 * @param file
	 *            The file to read from.
	 * @throws IOException
	 * 
	 */
	void openFile(File file) throws IOException
	{
		//initialize
		this.position = 0;
		positionToLineNumberMap = new TreeMap<Integer, Integer>();

		//work
		FileInputStream stream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		StringBuffer buffer = new StringBuffer();
		int lineNumber = 1;
		String line = reader.readLine();
		while (line != null)
		{
			positionToLineNumberMap.put(buffer.length(), lineNumber);
			buffer.append(line);
			buffer.append('\n');
			lineNumber++;
			line = reader.readLine();
		}
		reader.close();

		fileContents = buffer.toString();
		this.fileName = file.getName();
	}

	boolean isWhiteSpace() throws EOFException
	{


		if (position < fileContents.length())
		{
			char ch = fileContents.charAt(position);
			if (ch == ' ' || ch == '\t' || ch == '\r'
				|| ch == '\n' || ch == '\f' || ch == '\b')
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new EOFException();
		}
	}

	boolean isLineBreak() throws EOFException
	{
		if (position < fileContents.length())
		{
			char ch = fileContents.charAt(position);
			if (ch == '\r'
				|| ch == '\n' || ch == '\f')
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new EOFException();
		}
	}

	/**
	 * Moves the current position ahead one byte in the file.
	 */
	void move() throws EOFException
	{
		position++;
	}

	/**
	 * Reads one character from the underlying file and returns it.
	 * 
	 * @return The character read or null if at the end of the file.
	 * 
	 */
	Character read() throws EOFException
	{
		if (position < fileContents.length())
		{
			return fileContents.charAt(position);
		}
		else
		{
			throw new EOFException();
		}
	}

	boolean eof()
	{
		if (position < fileContents.length())
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Returns a range of characters from the underlying file.
	 * 
	 * @param start
	 *            The beginning index inclusive.
	 * @param end
	 *            The ending index exclusive.
	 * @return A string containing the characters read.
	 */
	String getContent(int start, int end)
	{
		return fileContents.substring(start, end);
	}

	/**
	 * Returns the current position in the file. This return linear position
	 * including counting the newlines chars
	 * 
	 * @return The current position in the file.
	 */
	int getPosition()
	{
		return position;
	}

	/**
	 * Sets the current position in the file.
	 * 
	 * @param position
	 *            The position to set the file to.
	 */
	void setPosition(int position)
	{
		this.position = position;
	}

	/**
	 * Get the file name of the file being handled
	 * 
	 * @return
	 */
	String getFileName()
	{
		return this.fileName;
	}

	/**
	 * Get the line number at the current position in the file.
	 */
	int getLineNumber() throws EOFException
	{
		return getLineNumber(this.position);
	}

	/**
	 * Get the line number at the specified position in the file.
	 *
	 * @param position the position at which to get the line number.
	 */
	int getLineNumber(int position) throws EOFException
	{
		if (position < fileContents.length())
		{
			return positionToLineNumberMap.floorEntry(position).getValue();
		}
		else
		{
			throw new EOFException();
		}
	}

	/**
	 * Get the column number at the current position in the file.
	 */
	int getColumnNumber()
	{
		return getColumnNumber(this.position);
	}

	/**
	 * Get the column number at the specified position in the file.
	 *
	 * @param position the position to get the column number at.
	 */
	int getColumnNumber(int position)
	{
		position = position - 1;
		int columnNumber = 1;
		while (position > -1 && fileContents.charAt(position) != '\n')
		{
			columnNumber++;
			position--;
		}
		return columnNumber;
	}

	/**
	 * Returns a java.util.regex.Matcher consisting of the information about any matching text
	 * found. Returns null if the match isn't found.
	 * @param pattern
	 * @return
	 */
	Matcher regex(String pattern)
	{
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(this.fileContents);
		boolean wasFound = matcher.find(this.getPosition());
		if (wasFound)
		{
			return matcher;
		}
		else
		{
			return null;
		}
	}
}
