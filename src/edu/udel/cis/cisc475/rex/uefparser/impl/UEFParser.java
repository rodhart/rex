package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.regex.Matcher;

/**
 * Parser for the Universal Exam File (UEF).
 *
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 *
 */
public class UEFParser implements UEFParserIF
{

	/**
	 * Underlying file handler to move through and read the file.
	 */
	UEFCharHandler uefCharHandler;
	/**
	 * Instance of the class that handles processing all read in commands.
	 */
	UEFCommandHandler uefCommandHandler;

	/**
	 * End of Line Exception for when something is suppose to occur
	 * by the end of a line and doesn't.
	 */
	public class EOLException extends EOFException
	{

		public EOLException(String detail)
		{
			super(detail);
		}
	}

	/**
	 * Creates new object of the parser. UEFParserFactory should be used to
	 * create this object.
	 *
	 * @see UEFParserFactoryIF
	 */
	public UEFParser()
	{
		uefCharHandler = new UEFCharHandler();
		uefCommandHandler = new UEFCommandHandler(uefCharHandler);
	}

	/**
	 * Opens a file using the underlying UEFCharHandler.
	 *
	 * @param file
	 *            The file to read from.
	 * @throws IOException If there is an issue reading the file.
	 *
	 */
	void openFile(File file) throws IOException
	{
		uefCharHandler.openFile(file);
	}

	/**
	 * Starting from the current position in the file returns an argument as
	 * a string. Always positions the file one character past '}' closing bracket.
	 * In the case where an argument is not found the position in the file is reset.
	 * And null is returned.
	 *
	 * @return the argument if an argument exists. Characters found within
	 * comments are ignored. If a character other than a linebreak or space is
	 * found outside of a comment then null is returned.
	 *
	 * @throws EOFException if an EOF occurs after an argument begins.
	 */
	String parseForArgument() throws EOFException
	{
		int position = uefCharHandler.getPosition();
		try
		{
			// Read until the first argument
			while (uefCharHandler.read() != '{')
			{
				//Handle comments.
				if (uefCharHandler.read() == '%')
				{
					while (!uefCharHandler.isLineBreak())
					{
						uefCharHandler.move();
					}
					uefCharHandler.move();
				}
				//Handle finding linebreaks or whitespaces.
				else if (uefCharHandler.isWhiteSpace())
				{
					uefCharHandler.move();
				}
				//Handle everything else
				else
				{
					// reset the position back since there are no arguments before another command
					uefCharHandler.setPosition(position);
					return null;
				}
			}
		}
		catch (EOFException ex)
		{
			//no argument left in the file. So return null.
			uefCharHandler.setPosition(position);
			return null;
		}
		try
		{
			// Read until the end of the first argument
			uefCharHandler.move();

			StringBuffer argument = new StringBuffer();
			while (uefCharHandler.read() != '}')
			{
				argument.append(uefCharHandler.read());
				uefCharHandler.move();
			}
			uefCharHandler.move();
			return argument.toString().trim();
		}
		catch (EOFException ex)
		{
			//unexpected end of file after an argument began
			throw new EOFException("Unexpected EOF when parsing for an argument.");
		}
	}

	/**
	 * Starting from the current position in the file returns the optional
	 * argument as a string. Always positions the file one character past ']'
	 * closing bracket. In the case where an argument is not found the position
	 * in the file is reset. And null is returned.
	 *
	 * @return the optional argument if an optional argument exists. Characters 
	 * found within comments are ignored. If a character other than a linebreak
	 * or space is found outside of a comment then null is returned.
	 *
	 * @throws EOFException if an EOF occurs after an optional argument begins.
	 */
	String parseForOptionalArgument() throws EOFException
	{
		int position = uefCharHandler.getPosition();
		try
		{
			while (uefCharHandler.read() != '[')
			{
				//Handle comments.
				if (uefCharHandler.read() == '%')
				{
					while (!uefCharHandler.isLineBreak())
					{
						uefCharHandler.move();
					}
					uefCharHandler.move();
				}
				//Handle finding linebreaks or whitespaces.
				else if (uefCharHandler.isWhiteSpace())
				{
					uefCharHandler.move();
				}
				//Handle everything else
				else
				{
					// reset the position back since there are no arguments before another command
					uefCharHandler.setPosition(position);
					return null;
				}
			}
		}
		catch (EOFException ex)
		{
			//no optional argument left in the file. So return null.
			uefCharHandler.setPosition(position);
			return null;
		}

		try
		{
			// Read until the end of the first argument
			uefCharHandler.move();

			StringBuffer argument = new StringBuffer();
			while (uefCharHandler.read() != ']')
			{
				argument.append(uefCharHandler.read());
				uefCharHandler.move();
			}
			// Move past the last argument delimiter and return the arguments
			uefCharHandler.move();
			return argument.toString().trim();
		}
		catch (EOFException ex)
		{
			//unexpected end of file after an optional argument began
			throw new EOFException("Unexpected EOF when parsing for an optional argument.");
		}
	}

	/**
	 * Starting at the current position reads until a command and then gets the command.
	 * And then returns it as a UEFCommand.
	 *
	 * @return the current command as a UEFCommand or null if there are no more commands.
	 *
	 * @throws EOLException if an unexpected EOL occurs. 
	 *
	 * @throws EOFException if an unexpected EOF occurs.
	 */
	UEFCommand parseForCommand() throws EOLException, EOFException
	{
		UEFCommand uefCommand = new UEFCommand();
		while (!uefCharHandler.eof())
		{
			try
			{
				//Handle comments: %
				if (uefCharHandler.read() == '%')
				{
					while (uefCharHandler.read() != '\n')
					{
						uefCharHandler.move();
					}
					uefCharHandler.move();
					continue;
				}
			}
			catch (EOFException ex)
			{
				//EOF reached during comment. And that's okay.
				return null;
			}

			//Not OKAY to reach EOF below this

			// check for a command
			if (uefCharHandler.read() == '\\')
			{
				//not OK to reach EOF within this if(); unless in the case
				//of optional argument. Which is handled by the
				//parseForOptionalArgument() method anyway.nN

				int commandStart = uefCharHandler.getPosition();
				// move forward in the file.
				uefCharHandler.move();

				//Handle actual double backslash escape character: \\
				if (uefCharHandler.read() == '\\')
				{
					uefCharHandler.move();
					continue;
				}

				// Grab each letter in the command while there are letters
				// remember only letters are valid for command names
				StringBuffer commandBuffer = new StringBuffer();
				while (Character.isLetter(uefCharHandler.read()))
				{
					commandBuffer = commandBuffer.append(uefCharHandler.read());
					uefCharHandler.move();
				}
				String commandString = commandBuffer.toString();

				//Handle \verb
				if (commandString.equals("verb"))
				{
					char delimiter = uefCharHandler.read();
					uefCharHandler.move();
					while (uefCharHandler.read() != delimiter)
					{
						if (uefCharHandler.read() == '\n')
						{
							//EOL should not be reached before a matching delimiter
							throw new EOLException("Error: Verb delimiter '" + delimiter
												   + "' not matched in file by end of line.");
						}
						uefCharHandler.move();
					}
					continue;
				}
				//Handle \answer
				else if (commandString.equals("answer"))
				{
					String optionalArgument = parseForOptionalArgument();
					uefCommand.setType(UEFCommand.Types.answer);
					uefCommand.setStartPosition(commandStart);
					uefCommand.setEndPosition(uefCharHandler.getPosition());
					uefCommand.setOptionalArgument(optionalArgument);
					return uefCommand;
				}
				//Handle \begin{}
				else if (commandString.equals("begin"))
				{
					String environment = parseForArgument();

					//Handle \begin{verbatim}
					if (environment.equals("verbatim"))
					{
						//Handle \end{verbatim} technically...
						Matcher matcher = uefCharHandler.regex("\\\\end *\\{ *verbatim *\\}");
						if (matcher != null)
						{
							uefCharHandler.setPosition(matcher.end());
							continue;
						}
						else
						{
							//starting at the current position no match was found within the file
							throw new EOFException(
									"\\begin{verbatim} without matching \\end{verbatim}");
						}
					}
					//Handle \begin{answers}
					else if (environment.equals("answers"))
					{
						String optionalArgument = parseForOptionalArgument();
						uefCommand.setType(UEFCommand.Types.beginAnswers);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						uefCommand.setOptionalArgument(optionalArgument);
						return uefCommand;
					}
					//Handle \begin{block}
					else if (environment.equals("block"))
					{
						String name = parseForArgument();
						uefCommand.setType(UEFCommand.Types.beginBlock);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						uefCommand.addArgument(name);
						return uefCommand;
					}
					//Handle \begin{document}
					else if (environment.equals("document"))
					{
						uefCommand.setType(UEFCommand.Types.beginDocument);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
					//Handle \begin{figure}
					else if (environment.equals("figure"))
					{
						uefCommand.setType(UEFCommand.Types.beginFigure);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
					//Handle \begin{problem}
					else if (environment.equals("problem"))
					{
						String optionalArgument = parseForOptionalArgument();
						String topic = parseForArgument();
						String difficulty = parseForArgument();
						uefCommand.setType(UEFCommand.Types.beginProblem);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						uefCommand.setOptionalArgument(optionalArgument);
						uefCommand.addArgument(topic);
						uefCommand.addArgument(difficulty);
						return uefCommand;
					}
				}
				//Handle \documentclass
				else if (commandString.equals("documentclass"))
				{
					String cls = parseForArgument();
					uefCommand.setType(UEFCommand.Types.documentclass);
					uefCommand.setStartPosition(commandStart);
					uefCommand.setEndPosition(uefCharHandler.getPosition());
					uefCommand.addArgument(cls);
					return uefCommand;
				}
				//Handle \end{}
				else if (commandString.equals("end"))
				{
					String environment = parseForArgument();
					//Handle \end{answers}
					if (environment.equals("answers"))
					{
						uefCommand.setType(UEFCommand.Types.endAnswers);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());

						return uefCommand;
					}
					//Handle \end{block}
					else if (environment.equals("block"))
					{
						uefCommand.setType(UEFCommand.Types.endBlock);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
					//Handle \end{document}
					else if (environment.equals("document"))
					{
						uefCommand.setType(UEFCommand.Types.endDocument);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
					//Handle \end{figure}
					else if (environment.equals("figure"))
					{
						uefCommand.setType(UEFCommand.Types.endFigure);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
					//Handle \end{problem}
					else if (environment.equals("problem"))
					{
						uefCommand.setType(UEFCommand.Types.endProblem);
						uefCommand.setStartPosition(commandStart);
						uefCommand.setEndPosition(uefCharHandler.getPosition());
						return uefCommand;
					}
				}
				//Handle \label
				else if (commandString.equals("label"))
				{
					String label = parseForArgument();
					uefCommand.setType(UEFCommand.Types.label);
					uefCommand.setStartPosition(commandStart);
					uefCommand.setEndPosition(uefCharHandler.getPosition());
					uefCommand.addArgument(label);
					return uefCommand;
				}
				//Handle \ref
				else if (commandString.equals("ref"))
				{
					String label = parseForArgument();
					uefCommand.setType(UEFCommand.Types.ref);
					uefCommand.setStartPosition(commandStart);
					uefCommand.setEndPosition(uefCharHandler.getPosition());
					uefCommand.addArgument(label);
					return uefCommand;
				}
			}
			uefCharHandler.move();
		}
		return null;
	}

	/**
	 * Parse UEF file and generate ExamIF from it.
	 *
	 * @param file
	 *            the file handler for the uef file.
	 * @return ExamIF of the uef file.
	 */
	public ExamIF parse(File file)
	{
		//catch any IO problem while opening the file
		try
		{
			this.openFile(file);
			UEFCommand uefCommand = parseForCommand();
			while (uefCommand != null)
			{
				uefCommandHandler.add(uefCommand);
				uefCommand = parseForCommand();
			}
			uefCommandHandler.process();
			return null;
		}
		catch (EOLException ex)
		{
			System.out.println("EOLException: " + ex.getMessage());
		}
		catch (EOFException ex)
		{
			System.out.println("EOFException: " + ex.getMessage());
		}
		catch (IOException ex)
		{
			System.out.println("IOException: " + ex.getMessage());
		}
		return null;
	}
}
