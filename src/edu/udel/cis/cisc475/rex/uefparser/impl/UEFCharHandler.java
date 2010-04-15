package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles the underlying UEF file character by character.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-Hassany
 */
class UEFCharHandler {

	/**
	 * The current position in the file. This will count the new lines as just
	 * another charcte
	 */
	private int position = -1;

	/**
	 * Current Line no
	 */
	private int lineNo;

	/**
	 * The position of the char from the last line beggining
	 */
	private int charPos;

	/**
	 * The complete contents of the file
	 */
	private StringBuffer fileContents = new StringBuffer();

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
	void openFile(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			fileContents.append(line);
			fileContents.append('\n');
		}
		reader.close();

		// Reset local state
		position = 0;
		lineNo = 0;
		charPos = 0;
		this.fileName = file.getName();
	}

	/**
	 * Moves the current position ahead one byte in the file.
	 */
	void move() {
		position++;

		// keep track of the current line number and chat number on that line
		if (fileContents.length() < position) {
			if (fileContents.charAt(position) == '\n') {
				lineNo++;
				charPos = 0;
			} else {
				charPos++;
			}
		}
	}

	/**
	 * Reads one character from the underlying file and returns it.
	 * 
	 * @return The character read.
	 * 
	 */
	char read() {
		try {
			return fileContents.charAt(position);
		} catch (IndexOutOfBoundsException e) {
			System.out
					.println("FIXME: I'm not handled when the end of file is reached...");
			System.exit(-1);
		}
		return 0;
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
	String getContent(int start, int end) {
		return fileContents.substring(start, end);
	}

	/**
	 * Returns the current position in the file. This return linear position
	 * including counting the newlines chars
	 * 
	 * @return The current position in the file.
	 */
	int getPosition() {
		return position;
	}

	/**
	 * Sets the current position in the file.
	 * 
	 * @param position
	 *            The position to set the file to.
	 */
	void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Get the file name of the file being handled
	 * 
	 * @return
	 */
	String getFileName() {
		return this.fileName;
	}

	/**
	 * Get the line number
	 */
	int getLineNo() {
		return this.lineNo;
	}

	/**
	 * Get the column number
	 */
	int getColumnNo() {
		return this.charPos;
	}

	/**
	 * Returns a java.​util.​regex.Matcher consisting of the information about any matching text
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
