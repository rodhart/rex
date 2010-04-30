package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.Types;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;
import java.util.regex.Matcher;

/**
 * Parser for the Universal Exam File (UEF). Provides methods that allow an
 * ExamIF to produced from valid tex file that uses the exam documentclass.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 * 
 */
public class UEFParser implements UEFParserIF
{

	/**
	 * Underlying file handler to move through and read the file. Even after
	 * processing we need this object, because we use it to get the information
	 * needed for SourceIF objects.
	 */
	private UEFCharHandler uefCharHandler;
	/**
	 * Instance of the class that handles processing all read in commands after
	 * they are processed from the underlying file.
	 */
	private UEFCommandHandler uefCommandHandler;

	/**
	 * This constructor internally creates a new UEFCharHandler object to handle
	 * the parsing of the file. And creates a new UEFCommandHandler object to
	 * handle the processing of the command queue that is created during
	 * parsing.
	 * 
	 * @see UEFParserFactoryIF
	 */
	public UEFParser()
	{
		// create the underlying file handler.
		uefCharHandler = new UEFCharHandler();

		// create the underlying command processor.
		uefCommandHandler = new UEFCommandHandler(uefCharHandler);

		// This class will itself handle parsing the file ahead of time.
	}

	/**
	 * Returns a reference the underlying UEFCharHandler created in the
	 * constructor. Used for junit testing.
	 * 
	 * @return the internal UEFCharHandler.
	 */
	UEFCharHandler getUEFCharHandler()
	{
		// return the internal file handler.
		return uefCharHandler;
	}

	/**
	 * Returns a reference the underlying UEFCommandHandler created in the
	 * constructor. Used for junit testing.
	 * 
	 * @return the internal UEFCharHandler.
	 */
	UEFCommandHandler getUEFCommandHandler()
	{
		// return the internal command queue processor.
		return uefCommandHandler;
	}

	/**
	 * Opens a file using the underlying UEFCharHandler.
	 * 
	 * @param file
	 *            The file to read from.
	 * 
	 * @throws IOException
	 *             If there is an issue reading the file.
	 * 
	 */
	void openFile(File file) throws IOException
	{
		// have the underlying file handler open the file for us.
		uefCharHandler.openFile(file);
	}

	/**
	 * Starting from the current position in the file returns an argument as a
	 * string. Always positions the file one character past '}' closing bracket.
	 * In the case where an argument is not found the position in the file is
	 * reset. And null is returned.
	 * 
	 * @return the argument if an argument exists. Characters found within
	 *         comments are ignored. Optional arguments are ignored. If a
	 *         character other than a linebreak or space is found outside of a
	 *         comment or optional argument then null is returned.
	 * 
	 * @throws EOFException
	 *             if an EOF occurs after an argument begins.
	 */
	String parseForArgument() throws RexParseException
	{
		// store the current position in case we need to reset our position.
		int position = uefCharHandler.getPosition();

		// try-catch in case we reach EOF- which is normal behavior,
		// so we return null.
		try
		{
			// Read until the first argument begins.
			while (uefCharHandler.read() != '{')
			{
				// Handle comments: ignore them.
				if (uefCharHandler.read() == '%')
				{
					// move forward until a line break.
					do
					{
						uefCharHandler.move();
					}
					while (!uefCharHandler.isLineBreak());

					// move one more time after hitting a line break.
					uefCharHandler.move();
				}
				// handle finding optional argument: parse past it.
				else if (uefCharHandler.read() == '[')
				{
					// call parse for optional argument.
					parseForOptionalArgument();
				}
				// Handle finding linebreaks or whitespaces: move foward.
				else if (uefCharHandler.isWhiteSpace())
				{
					// move foward one.
					uefCharHandler.move();
				}
				// Handle everything else: a-z, A-Z, other special characters.
				else
				{
					// reset the position back since there are no arguments
					// before another command.
					uefCharHandler.setPosition(position);
					return null;
				}
			}
		}
		catch (EOFException e)
		{
			// no argument left in the file. So return null.
			uefCharHandler.setPosition(position);
			return null;
		}

		// If we've gotten this far, we have found the beginning of an argument.
		// we have to get the actual argument now.

		// try-catch in case we reach EOF- which we should never reach
		// after starting an argument.
		try
		{
			uefCharHandler.move();
			StringBuffer argument = new StringBuffer();

			// Read until the end of the first argument
			while (uefCharHandler.read() != '}')
			{
				// append each character of the argument.
				argument.append(uefCharHandler.read());
				uefCharHandler.move();
			}

			// move the file one character past the argument terminator.
			uefCharHandler.move();

			// return the argument after trimming it.
			return argument.toString().trim();
		}
		catch (EOFException e)
		{
			// unexpected end of file after an argument began.
			throw new RexParseException("Unexpected EOF when parsing for an argument.", null);
		}
	}

	/**
	 * Starting from the current position in the file returns the optional
	 * argument as a string. Always positions the file one character past ']'
	 * closing bracket. In the case where an argument is not found the position
	 * in the file is reset. And null is returned.
	 * 
	 * @return the optional argument if an optional argument exists. Characters
	 *         found within comments are ignored. If a character other than a
	 *         linebreak or space is found outside of a comment then null is
	 *         returned.
	 * 
	 * @throws EOFException
	 *             if an EOF occurs after an optional argument begins.
	 */
	String parseForOptionalArgument() throws RexParseException
	{
		// store the current position in case we need to reset our position.

		// try-catch in case we reach EOF- which is normal behavior,
		// so we return null.
		int position = uefCharHandler.getPosition();
		try
		{
			// Read until the first argument begins.
			while (uefCharHandler.read() != '[')
			{
				// Handle comments: ignore them.
				if (uefCharHandler.read() == '%')
				{
					// move forward until a line break.
					do
					{
						uefCharHandler.move();
					}
					while (!uefCharHandler.isLineBreak());

					// move one more time after hitting a linebreak.
					uefCharHandler.move();
				}
				// Handle finding linebreaks or whitespaces: move foward.
				else if (uefCharHandler.isWhiteSpace())
				{
					// move foward one.
					uefCharHandler.move();
				}
				// Handle everything else: a-z, A-Z, other special characters.
				else
				{
					// reset the position back since there are no arguments
					// before another command.
					uefCharHandler.setPosition(position);
					return null;
				}
			}
		}
		catch (EOFException e)
		{
			// no optional argument left in the file. So return null.
			uefCharHandler.setPosition(position);
			return null;
		}

		// If we've gotten this far, we have found the beginning of an optional
		// argument.
		// we have to get the actual argument now.

		// try-catch in case we reach EOF- which we should never reach
		// after starting an optional argument.
		try
		{
			uefCharHandler.move();
			StringBuffer argument = new StringBuffer();

			// Read until the end of the first argument
			while (uefCharHandler.read() != ']')
			{
				// append each character of the argument.
				argument.append(uefCharHandler.read());
				uefCharHandler.move();
			}

			// move the file one character past the argument terminator.
			uefCharHandler.move();

			// return the argument after trimming it.
			return argument.toString().trim();
		}
		catch (EOFException e)
		{
			// unexpected end of file after an optional argument began
			throw new RexParseException("Unexpected EOF when parsing for an optional argument.", null);
		}
	}

	/**
	 * Starting at the current position reads until a command and then gets the
	 * command. And then returns it as a UEFCommand. Underlying file position is
	 * set to the character following the last argument. Or if no arguments
	 * occur then character after the command ends.
	 * 
	 * @return the current command as a UEFCommand or null if there are no more
	 *         commands.
	 * 
	 * @throws EOLException
	 *             if an unexpected EOL occurs.
	 * 
	 * @throws EOFException
	 *             if an unexpected EOF occurs.
	 */
	UEFCommand parseForCommand() throws RexParseException
	{
		while (!uefCharHandler.eof())
		{
			try
			{
				// Handle comments: %
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
				// EOF reached during comment. And that's okay.
				return null;
			}

			// Not OKAY to reach EOF below this
			try
			{
				// check for a command
				if (uefCharHandler.read() == '\\')
				{
					// not OK to reach EOF within this if(); unless in the case
					// of optional argument. Which is handled by the
					// parseForOptionalArgument() method anyway.nN

					int commandStart = uefCharHandler.getPosition();
					// move forward in the file.
					uefCharHandler.move();

					// Handle actual double backslash escape character: \\
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

					// Handle \verb
					if (commandString.equals("verb"))
					{
						char delimiter = uefCharHandler.read();
						uefCharHandler.move();
						while (uefCharHandler.read() != delimiter)
						{
							if (uefCharHandler.read() == '\n')
							{
								// EOL should not be reached before a matching
								// delimiter
								throw new RexParseException("Verb delimiter '" + delimiter + "' not matched in file by end of line.", null);
							}
							uefCharHandler.move();
						}
						continue;
					}
					// Handle \answer
					else if (commandString.equals("answer"))
					{
						String optionalArgument = parseForOptionalArgument();
						UEFCommand command = new UEFCommand(Types.answer, commandStart, uefCharHandler.getPosition());
						command.setOptionalArgument(optionalArgument);
						return command;
					}
					// Handle \begin{}
					else if (commandString.equals("begin"))
					{
						String environment = parseForArgument();

						// Handle \begin{verbatim}
						if (environment.equals("verbatim"))
						{
							// Handle \end{verbatim} technically...
							Matcher matcher = uefCharHandler.regex("\\\\end *\\{ *verbatim *\\}");
							if (matcher != null)
							{
								uefCharHandler.setPosition(matcher.end());
								continue;
							}
							else
							{
								// starting at the current position no match was
								// found within the file
								throw new EOFException("\\begin{verbatim} without matching \\end{verbatim}");
							}
						}
						// Handle \begin{answers}
						else if (environment.equals("answers"))
						{
							String optionalArgument = parseForOptionalArgument();
							UEFCommand command = new UEFCommand(Types.beginAnswers, commandStart, uefCharHandler.getPosition());
							command.setOptionalArgument(optionalArgument);
							return command;
						}
						// Handle \begin{block}
						else if (environment.equals("block"))
						{
							String name = parseForArgument();
							UEFCommand command = new UEFCommand(Types.beginBlock, commandStart, uefCharHandler.getPosition());
							command.addArgument(name);
							return command;
						}
						// Handle \begin{document}
						else if (environment.equals("document"))
						{
							UEFCommand command = new UEFCommand(Types.beginDocument, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \begin{figure}
						else if (environment.equals("figure"))
						{
							UEFCommand command = new UEFCommand(Types.beginFigure, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \begin{problem}
						else if (environment.equals("problem"))
						{
							String optionalArgument = parseForOptionalArgument();
							String topic = parseForArgument();
							String difficulty = parseForArgument();
							UEFCommand command = new UEFCommand(Types.beginProblem, commandStart, uefCharHandler.getPosition());
							command.setOptionalArgument(optionalArgument);
							command.addArgument(topic);
							command.addArgument(difficulty);
							return command;
						}
					}
					// Handle \documentclass
					else if (commandString.equals("documentclass"))
					{
						String master = parseForOptionalArgument();
						String documentclass = parseForArgument();
						UEFCommand command = new UEFCommand(Types.documentclass, commandStart, uefCharHandler.getPosition());
						command.setOptionalArgument(master);
						command.addArgument(documentclass);
						return command;
					}
					// Handle \end{}
					else if (commandString.equals("end"))
					{
						String environment = parseForArgument();
						// Handle \end{answers}
						if (environment.equals("answers"))
						{
							UEFCommand command = new UEFCommand(Types.endAnswers, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \end{block}
						else if (environment.equals("block"))
						{
							UEFCommand command = new UEFCommand(Types.endBlock, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \end{document}
						else if (environment.equals("document"))
						{
							UEFCommand command = new UEFCommand(Types.endDocument, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \end{figure}
						else if (environment.equals("figure"))
						{
							UEFCommand command = new UEFCommand(Types.endFigure, commandStart, uefCharHandler.getPosition());
							return command;
						}
						// Handle \end{problem}
						else if (environment.equals("problem"))
						{
							UEFCommand command = new UEFCommand(Types.endProblem, commandStart, uefCharHandler.getPosition());
							return command;
						}
					}
					// Handle \label
					else if (commandString.equals("label"))
					{
						String label = parseForArgument();
						UEFCommand command = new UEFCommand(Types.label, commandStart, uefCharHandler.getPosition());
						command.addArgument(label);
						return command;
					}
					// Handle \ref
					else if (commandString.equals("ref"))
					{
						String label = parseForArgument();
						UEFCommand command = new UEFCommand(Types.ref, commandStart, uefCharHandler.getPosition());
						command.addArgument(label);
						return command;
					}
				}
				uefCharHandler.move();
			}
			catch (EOFException e)
			{
				throw new RexParseException("Unexpected end of file when parsing for command.", null);
			}
		}
		return null;
	}

	/**
	 * Parses for all commands in file and adds them to internal command queue.
	 * They are ready to be processed after the call finishes.
	 * 
	 * @param file
	 *            the file handler for the uef file.
	 * 
	 * @throws EOLException
	 *             if an unexpected EOL occurs.
	 * @throws EOFException
	 *             if an unexpected EOF occurs.
	 * @throws IOException
	 *             If an I/O problem occurs.
	 */
	void parseForAllCommands(File file) throws RexParseException, IOException
	{
		this.openFile(file);
		UEFCommand uefCommand = parseForCommand();
		while (uefCommand != null)
		{
			uefCommandHandler.add(uefCommand);
			uefCommand = parseForCommand();
		}
	}

	/**
	 * Parses UEF file and generate ExamIF from it.
	 * 
	 * @param file
	 *            the file handler for the uef file.
	 * @return ExamIF representation of the UE file.
	 * @throws RexException
	 *             if a parsing error occurs.
	 * @throws IOException
	 *             if the file cannot be open or read.
	 */
	public ExamIF parse(File file) throws RexException, IOException
	{
		// catch any IO problem while opening the file
		this.openFile(file);
		UEFCommand uefCommand = parseForCommand();
		while (uefCommand != null)
		{
			uefCommandHandler.add(uefCommand);
			uefCommand = parseForCommand();
		}
		// completely parse the file
		parseForAllCommands(file);

		// process the queue
		return uefCommandHandler.process();
	}
}
