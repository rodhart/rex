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
 * Class that handles the underlying UEF file character by character. Internally
 * uses unix line breaks for internal representation. This is important to note
 * because the position may differ from the actual byte position in the
 * underlying file. For instance- in the case of windows line breaks. This would
 * be true.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-Hassany
 */
class UEFCharHandler
{

	/**
	 * The current position in the file. This will count the new line characters
	 * as bytes.
	 */
	private int position;
	/**
	 * Map that is used to map file positions to lines file lines.
	 * For instance, bytes 0 thru 25 may be mapped to line 1.
	 * And bytes 26-54 may be mapped to line 2.
	 */
	private NavigableMap<Integer, Integer> positionToLineNumberMap;
	/**
	 * The complete contents of the file stored in a string.
	 */
	private String fileContents;
	/**
	 * The name of the file being handled.
	 */
	private String fileName;

	/**
	 * Opens a file and completely reads it into a String for easy
	 * parsing. Sets the position to the first character of the file.
	 * Optimized to use a StringBuffer internally for storing intermediate
	 * lines.
	 * 
	 * @param file
	 *            The file to read into memory.
	 * @throws IOException if there is an issue opening or reading the file.
	 * 
	 */
	void openFile(File file) throws IOException
	{
		// initialize and setup variables
		this.position = 0;
		positionToLineNumberMap = new TreeMap<Integer, Integer>();

		//open the file
		FileInputStream stream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		//Read file into memory.
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

		//store file information into class fields.
		fileContents = buffer.toString();
		this.fileName = file.getName();
	}

	/**
	 * Checks whether the current position in the file is a whitespace.
	 *
	 * @return True if we are at a whitespace. False otherwise.
	 * @throws EOFException if we are at or past the end o the file.
	 */
	boolean isWhiteSpace() throws EOFException
	{
		//make sure we are not at the end of the file
		if (position < fileContents.length())
		{
			//check the current position
			char ch = fileContents.charAt(position);
			if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n'
				|| ch == '\f' || ch == '\b')
			{
				//at whitespace.
				return true;
			}
			else
			{
				//not at whitespace.
				return false;
			}
		}
		else
		{
			//at end of file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Checks whether the current position in the file is a letter.
	 *
	 * @return True if we are at a letter. False otherwise.
	 * @throws EOFException if we are at or past the end of the file.
	 */
	boolean isLetter() throws EOFException
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			char ch = fileContents.charAt(position);
			if (Character.isLetter(ch))
			{
				//at a letter.
				return true;
			}
			else
			{
				//not at letter.
				return false;
			}
		}
		else
		{
			//at end of file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Checks whether the current position in the file is a line break.
	 *
	 * @return True if we are at a line break. False otherwise.
	 * @throws EOFException if we are at or past the end of the file.
	 */
	boolean isLineBreak() throws EOFException
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			char ch = fileContents.charAt(position);
			if (ch == '\r' || ch == '\n' || ch == '\f')
			{
				//at a whitespace.
				return true;
			}
			else
			{
				//not at a whitespace.
				return false;
			}
		}
		else
		{
			//at end of file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Moves the current position ahead one byte in the file.
	 */
	void move()
	{
		//increment position
		position++;
	}

	/**
	 * Reads one character from the underlying file and returns it.
	 * 
	 * @return The character read or null if at the end of the file.
	 * 
	 */
	/**
	 *  Reads one character from the underlying file and returns it.
	 *
	 * @return the character read.
	 * @throws EOFException if we are at or past the end of the file.
	 */
	Character read() throws EOFException
	{
		//call read() with the current position in the file and return it.
		return read(this.position);
	}

	/**
	 * 	 * Reads one character from the underlying file at the specified position
	 * and returns it.
	 *
	 * @param position the position to read from within the file.
	 * @return the character read at the specified position.
	 *
	 * @throws EOFException if we are at or past the end of the file.
	 */
	Character read(int position) throws EOFException
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			//return the character at the specified position.
			return fileContents.charAt(position);
		}
		else
		{
			//at the end of the file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Checks whether we are at the end of the file. Returns true if we are and false otherwise.
	 *
	 * @return true if we are at the end of the file. False otherwise.
	 */
	boolean eof()
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			//not at the end of the file.
			return false;
		}
		else
		{
			//at the end of the file.
			return true;
		}
	}

	/**
	 * Returns a range of characters from the underlying file as a String.
	 * Operates in exactly the same manner as substring.
	 * 
	 * @param start
	 *            The beginning index inclusive.
	 * @param end
	 *            The ending index exclusive.
	 *
	 * @return A string containing the characters read.
	 */
	String getContent(int start, int end)
	{
		//return a string of characters from start to end.
		return fileContents.substring(start, end);
	}

	/**
	 * Returns the current position in the file. 
	 * This returns the linear position which includes
	 * newlines characters.
	 * 
	 * @return The current position in the file.
	 */
	int getPosition()
	{
		//return the position in the file.
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
		//set the position in the file.
		this.position = position;
	}

	/**
	 * Returns the name of the file associated with this object.
	 * 
	 * @return the filename as a string or null if no file is associated with this object.
	 */
	String getFileName()
	{
		//return the file name of the opened file.
		return this.fileName;
	}

	/**
	 * Get the line number at the current position in the file.
	 *
	 * @return the line number at the current position.
	 *
	 * @throws EOFException if we are at or past the end of the file.
	 */
	int getLineNumber() throws EOFException
	{
		// returns the line number at the current position in the file.
		return getLineNumber(this.position);
	}

	/**
	 * Get the line number at the specified position in the file.
	 * 
	 * @param position
	 *            the position at which to get the line number.
	 * @return the line number.
	 *
	 * @throws EOFException if we are at or past the end of the file.
	 */
	int getLineNumber(int position) throws EOFException
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			//return the the current line at byte n.
			return positionToLineNumberMap.floorEntry(position).getValue();
		}
		else
		{
			//we are at or past the end of the file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Get the column number at the current position in the file.
	 *
	 * @return the column number at the current position.
	 *
	 * @throws EOFException if we are at or past the end of the file.
	 */
	int getColumnNumber() throws EOFException
	{
		//return the column number at the current position in the file.
		return getColumnNumber(this.position);
	}

	/**
	 * Get the column number at the specified position in the file.
	 * 
	 * @param position
	 *            the position to get the column number at.

	 * @return the column number.
	 *
	 * @throws EOFException if we are at or past the end of the file.
	 */
	int getColumnNumber(int position) throws EOFException
	{
		//make sure we are not at the end of the file.
		if (position < fileContents.length())
		{
			//return the column number at position n.
			return position - positionToLineNumberMap.floorKey(position) + 1;
		}
		else
		{
			//we are at or past the end of the file so throw an exception.
			throw new EOFException();
		}
	}

	/**
	 * Returns a java.util.regex.Matcher consisting of the information about any
	 * matching text found. Returns null if the match isn't found.
	 * 
	 * @param pattern the regex pattern to match.
	 * 
	 * @return a Matcher consisting of information about the matching text or null
	 * if no matching text was found.
	 */
	Matcher regex(String pattern)
	{
		//compile the pattern.
		Pattern compiledPattern = Pattern.compile(pattern);

		//attempt to match the text.
		Matcher matcher = compiledPattern.matcher(this.fileContents);

		//check to see if we found matching text.
		boolean wasFound = matcher.find(this.getPosition());
		if (wasFound)
		{
			//we found matching text so return matcher.
			return matcher;
		}
		else
		{
			//we found nothing so return null.
			return null;
		}
	}
}
