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
	 * Starting from the current position, repositions the underlying
	 * UEFCharHandler to the character directly after 'ch'.
	 *
	 * @param ch the character to match.
	 *
	 * @throws EOFException if an EOF occurs before ch is matched.
	 */
	void parsePastCharacter(char ch) throws EOFException
	{
		// Read until the end of the first argument
		while (uefCharHandler.read() != ch)
		{
			//move forward one character.
			uefCharHandler.move();
		}

		// move one more time after finding ch.
		uefCharHandler.move();
	}

	/**
	 * Starting from the current position, repositions the underlying
	 * UEFCharHandler to the starting character at the line
	 * following the current line.
	 *
	 * @throws EOFException if an EOF occurs before a new line occurs.
	 */
	void parsePastNewline() throws EOFException
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

	void parsePastVerb() throws RexParseException, EOFException
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
	}

	void parsePastVerbatim() throws EOFException
	{
		// Handle \end{verbatim} technically...
		Matcher matcher = uefCharHandler.regex("\\\\end *\\{ *verbatim *\\}");
		if (matcher != null)
		{
			uefCharHandler.setPosition(matcher.end());
		}
		else
		{
			// starting at the current position no match was
			// found within the file
			throw new EOFException("\\begin{verbatim} without matching \\end{verbatim}");
		}
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
					//go to next line.
					parsePastNewline();
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
			// move the file one character past the starter brace.
			uefCharHandler.move();

			// get the start position.
			int argumentStart = uefCharHandler.getPosition();

			// Read until the end of the first argument
			parsePastCharacter('}');

			// get the argument.
			String argument = uefCharHandler.getContent(argumentStart, uefCharHandler.getPosition() - 1);

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
					//go to next line.
					parsePastNewline();
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
			// move the file one character past the starter brace.
			uefCharHandler.move();

			// get the start position.
			int argumentStart = uefCharHandler.getPosition();

			// Read until the end of the first argument
			parsePastCharacter(']');

			// get the argument.
			String argument = uefCharHandler.getContent(argumentStart, uefCharHandler.getPosition() - 1);

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
	 * creates a answer UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the answer UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createAnswerCommand(int start) throws RexParseException
	{
		String optionalArgument = parseForOptionalArgument();
		UEFCommand command = new UEFCommand(Types.answer, start, uefCharHandler.getPosition());
		command.setOptionalArgument(optionalArgument);
		return command;
	}

	/**
	 * creates a documentclass UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the documentclass UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createDocumentclassCommand(int start) throws RexParseException
	{
		String master = parseForOptionalArgument();
		String documentclass = parseForArgument();
		UEFCommand command = new UEFCommand(Types.documentclass, start, uefCharHandler.getPosition());
		command.setOptionalArgument(master);
		command.addArgument(documentclass);
		return command;
	}

	/**
	 * creates a label UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the label UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createLabelCommand(int start) throws RexParseException
	{
		String label = parseForArgument();
		UEFCommand command = new UEFCommand(Types.label, start, uefCharHandler.getPosition());
		command.addArgument(label);
		return command;
	}

	/**
	 * creates a ref UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the ref UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createRefCommand(int start) throws RexParseException
	{
		String label = parseForArgument();
		UEFCommand command = new UEFCommand(Types.ref, start, uefCharHandler.getPosition());
		command.addArgument(label);
		return command;
	}

	/**
	 * creates a beginAnswers UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the beginAnswers UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createBeginAnswersCommand(int start) throws RexParseException
	{
		String optionalArgument = parseForOptionalArgument();
		UEFCommand command = new UEFCommand(Types.beginAnswers, start, uefCharHandler.getPosition());
		command.setOptionalArgument(optionalArgument);
		return command;
	}

	/**
	 * creates a beginBlock UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the beginBlock UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createBeginBlockCommand(int start) throws RexParseException
	{
		String name = parseForArgument();
		UEFCommand command = new UEFCommand(Types.beginBlock, start, uefCharHandler.getPosition());
		command.addArgument(name);
		return command;
	}

	/**
	 * creates a beginDocument UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the beginDocument UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createBeginDocumentCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.beginDocument, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a beginFigure UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the beginFigure UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createBeginFigureCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.beginFigure, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a beginProblem UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the beginProblem UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createBeginProblemCommand(int start) throws RexParseException
	{
		String optionalArgument = parseForOptionalArgument();
		String topic = parseForArgument();
		String difficulty = parseForArgument();
		UEFCommand command = new UEFCommand(Types.beginProblem, start, uefCharHandler.getPosition());
		command.setOptionalArgument(optionalArgument);
		command.addArgument(topic);
		command.addArgument(difficulty);
		return command;
	}

	/**
	 * creates a endAnswers UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the endAnswers UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createEndAnswersCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.endAnswers, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a endBlockUEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the endBlock UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createEndBlockCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.endBlock, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a endDocument UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the endDocument UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createEndDocumentCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.endDocument, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a endFigure UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the endFigure UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createEndFigureCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.endFigure, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a endProblem UEFCommand object. Sets the start position to 'start' and parses for arguments.
	 * Internally repositions the file to the character following the command.
	 *
	 * @param start the start of the command.
	 * @return the endProblem UEFCommand object.
	 *
	 * @throws RexParseException if there is a problem during parsing.
	 */
	UEFCommand createEndProblemCommand(int start) throws RexParseException
	{
		UEFCommand command = new UEFCommand(Types.endProblem, start, uefCharHandler.getPosition());
		return command;
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
		//Check for end of file before reading each character.
		while (!uefCharHandler.eof())
		{
			//Try-catch in case file ends within a comment.
			try
			{
				// Handle comments: ignore them.
				if (uefCharHandler.read() == '%')
				{
					//go to next line.
					parsePastNewline();
					continue;
				}
			}
			catch (EOFException e)
			{
				// EOF reached during comment. And that's okay.
				return null;
			}

			// Not OKAY to reach EOF below this, because we will be parsing commands.
			try
			{
				// check for a command.
				if (uefCharHandler.read() == '\\')
				{
					//matched a command.

					//get the start of the command.
					int commandStart = uefCharHandler.getPosition();

					// move forward in the file.
					uefCharHandler.move();

					// Handle double backslash escape character: \\
					if (uefCharHandler.read() == '\\')
					{
						//escape character, so move foward.
						uefCharHandler.move();
						continue;
					}

					// move until no more letters.
					while (uefCharHandler.isLetter())
					{
						uefCharHandler.move();
					}

					//get the command name.
					String command = uefCharHandler.getContent(commandStart, uefCharHandler.getPosition());

					// Handle \verb
					if (command.equals("\\verb"))
					{
						parsePastVerb();
						continue;
					}
					// Handle \answer
					else if (command.equals("\\answer"))
					{
						return createAnswerCommand(commandStart);
					}
					// Handle \documentclass
					else if (command.equals("\\documentclass"))
					{
						return createDocumentclassCommand(commandStart);
					}
					// Handle \label
					else if (command.equals("\\label"))
					{
						return createLabelCommand(commandStart);
					}
					// Handle \ref
					else if (command.equals("\\ref"))
					{
						return createRefCommand(commandStart);
					}
					// Handle \begin{} command
					else if (command.equals("\\begin"))
					{
						String environment = parseForArgument();

						// Handle \begin{verbatim}
						if (environment.equals("verbatim"))
						{
							parsePastVerbatim();
							continue;
						}
						// Handle \begin{answers}
						else if (environment.equals("answers"))
						{
							return createBeginAnswersCommand(commandStart);
						}
						// Handle \begin{block}
						else if (environment.equals("block"))
						{
							return createBeginBlockCommand(commandStart);
						}
						// Handle \begin{document}
						else if (environment.equals("document"))
						{
							return createBeginDocumentCommand(commandStart);
						}
						// Handle \begin{figure}
						else if (environment.equals("figure"))
						{
							return createBeginFigureCommand(commandStart);
						}
						// Handle \begin{problem}
						else if (environment.equals("problem"))
						{
							return createBeginProblemCommand(commandStart);
						}
					}
					// Handle \end{}
					else if (command.equals("\\end"))
					{
						String environment = parseForArgument();
						// Handle \end{answers}
						if (environment.equals("answers"))
						{
							return createEndAnswersCommand(commandStart);
						}
						// Handle \end{block}
						else if (environment.equals("block"))
						{
							return createEndBlockCommand(commandStart);
						}
						// Handle \end{document}
						else if (environment.equals("document"))
						{
							return createEndDocumentCommand(commandStart);
						}
						// Handle \end{figure}
						else if (environment.equals("figure"))
						{
							return createEndFigureCommand(commandStart);
						}
						// Handle \end{problem}
						else if (environment.equals("problem"))
						{
							return createEndProblemCommand(commandStart);
						}
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
