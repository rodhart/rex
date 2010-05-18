package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.Types;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Parser for the Universal Exam File (UEF). Provides methods that allow an
 * ExamIF to produced from valid tex file that uses the exam documentclass.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 * @author Tim Armstrong
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
	
	/**
	 * The command line command to run the UEF through pdflatex.  nonstopmode means
	 * that on an invalid file, pdflatex terminates and does not give a prompt
	 * as it usually would.
	 */
	private static final String[] PDFLATEXCMD = {"pdflatex", "-interaction", "nonstopmode"};
	
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
	 * @param ch
	 *            the character to match.
	 * 
	 * @throws EOFException
	 *             if an EOF occurs before ch is matched.
	 */
	void parsePastCharacter(char ch) throws EOFException
	{
		// Read until the end of the first argument
		while (uefCharHandler.read() != ch)
		{
			// move forward one character.
			uefCharHandler.move();
		}

		// move one more time after finding ch.
		uefCharHandler.move();
	}

	/**
	 * Starting from the current position, repositions the underlying
	 * UEFCharHandler to the starting character at the line following the
	 * current line.
	 * 
	 * @throws EOFException
	 *             if an EOF occurs before a new line occurs.
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
					// go to next line.
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
					// go to next line.
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
	 * creates a answer UEFCommand object. Sets the start position to 'start'
	 * and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the answer UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createAnswerCommand(int start) throws RexParseException
	{
		// get the optional argument.
		// In this case, should only be 'correct' or 'fixed' or 'correct,fixed'
		// or 'fixed,correct'.
		String optionalArgument = parseForOptionalArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.answer, start, uefCharHandler.getPosition());
		command.setOptionalArgument(optionalArgument);

		// return the created command.
		return command;
	}

	/**
	 * creates a documentclass UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the documentclass UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createDocumentclassCommand(int start) throws RexParseException
	{
		// get whether this is a r exam file.
		String master = parseForOptionalArgument();

		// get the documentclass type.
		String documentclass = parseForArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.documentclass, start, uefCharHandler.getPosition());
		command.setOptionalArgument(master);
		command.addArgument(documentclass);

		// return the created command.
		return command;
	}

	/**
	 * creates a label UEFCommand object. Sets the start position to 'start' and
	 * parses for arguments. Internally repositions the file to the character
	 * following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the label UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createLabelCommand(int start) throws RexParseException
	{
		// get the label id.
		String label = parseForArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.label, start, uefCharHandler.getPosition());
		command.addArgument(label);

		// return the created command.
		return command;
	}

	/**
	 * creates a ref UEFCommand object. Sets the start position to 'start' and
	 * parses for arguments. Internally repositions the file to the character
	 * following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the ref UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createRefCommand(int start) throws RexParseException
	{
		// get the label id.
		String label = parseForArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.ref, start, uefCharHandler.getPosition());
		command.addArgument(label);

		// return the created command.
		return command;
	}

	/**
	 * creates a beginAnswers UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the beginAnswers UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createBeginAnswersCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.beginAnswers, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a beginBlock UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the beginBlock UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createBeginBlockCommand(int start) throws RexParseException
	{
		// get the name of the block.
		String name = parseForArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.beginBlock, start, uefCharHandler.getPosition());
		command.addArgument(name);

		// return the created command.
		return command;
	}

	/**
	 * creates a beginDocument UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the beginDocument UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createBeginDocumentCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.beginDocument, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a beginFigure UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the beginFigure UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createBeginFigureCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.beginFigure, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a beginProblem UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the beginProblem UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createBeginProblemCommand(int start) throws RexParseException
	{
		// get the optional argument.
		// Should only be 'requires=somelabelid'.
		String optionalArgument = parseForOptionalArgument();

		// get the topic.
		String topic = parseForArgument();

		// get the difficulty.
		String difficulty = parseForArgument();

		// create the command and add arguments.
		UEFCommand command = new UEFCommand(Types.beginProblem, start, uefCharHandler.getPosition());
		command.setOptionalArgument(optionalArgument);
		command.addArgument(topic);
		command.addArgument(difficulty);

		// return the created command.
		return command;
	}

	/**
	 * creates a endAnswers UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the endAnswers UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createEndAnswersCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.endAnswers, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a endBlockUEFCommand object. Sets the start position to 'start'
	 * and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the endBlock UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createEndBlockCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.endBlock, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a endDocument UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the endDocument UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createEndDocumentCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.endDocument, start, uefCharHandler.getPosition());
		return command;
	}

	/**
	 * creates a endFigure UEFCommand object. Sets the start position to 'start'
	 * and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the endFigure UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createEndFigureCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.endFigure, start, uefCharHandler.getPosition());

		// return the created command.
		return command;
	}

	/**
	 * creates a endProblem UEFCommand object. Sets the start position to
	 * 'start' and parses for arguments. Internally repositions the file to the
	 * character following the command.
	 * 
	 * @param start
	 *            the start of the command.
	 * @return the endProblem UEFCommand object.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand createEndProblemCommand(int start) throws RexParseException
	{
		// create the command.
		UEFCommand command = new UEFCommand(Types.endProblem, start, uefCharHandler.getPosition());

		// return the created command.
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
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 */
	UEFCommand parseForCommand() throws RexParseException
	{
		// Check for end of file before reading each character.
		while (!uefCharHandler.eof())
		{
			// Try-catch in case file ends within a comment.
			try
			{
				// Handle comments: ignore them.
				if (uefCharHandler.read() == '%')
				{
					// go to next line.
					parsePastNewline();
					continue;
				}
			}
			catch (EOFException e)
			{
				// EOF reached during comment. And that's okay.
				return null;
			}

			// Not OKAY to reach EOF below this, because we will be parsing
			// commands.
			try
			{
				// check for a command.
				if (uefCharHandler.read() == '\\')
				{
					// matched a command.

					// get the start of the command.
					int commandStart = uefCharHandler.getPosition();

					// move forward in the file.
					uefCharHandler.move();

					// Handle double backslash escape character: \\
					if (uefCharHandler.read() == '\\')
					{
						// escape character, so move foward.
						uefCharHandler.move();
						continue;
					}

					// move until no more letters.
					while (uefCharHandler.isLetter())
					{
						uefCharHandler.move();
					}

					// get the command name.
					String command = uefCharHandler.getContent(commandStart, uefCharHandler.getPosition());

					// handle the supported commands below.
					if (command.equals("\\verb"))
					{
						// move past the verb.
						parsePastVerb();
						continue;
					}
					else if (command.equals("\\answer"))
					{
						// parse and return an answer command.
						return createAnswerCommand(commandStart);
					}
					else if (command.equals("\\documentclass"))
					{
						// parse and return an document command.
						return createDocumentclassCommand(commandStart);
					}
					else if (command.equals("\\label"))
					{
						// parse and return an label command.
						return createLabelCommand(commandStart);
					}
					else if (command.equals("\\ref"))
					{
						// parse and return an ref command.
						return createRefCommand(commandStart);
					}
					else if (command.equals("\\begin"))
					{
						// get the environment argument of the begin command.
						String environment = parseForArgument();

						// handle the supported environments below.
						if (environment.equals("verbatim"))
						{
							// move past the verbatim environment.
							parsePastVerbatim();
							continue;
						}
						else if (environment.equals("answers"))
						{
							// parse and return an beginAnswers command.
							return createBeginAnswersCommand(commandStart);
						}
						else if (environment.equals("block"))
						{
							// parse and return an beginBlock command.
							return createBeginBlockCommand(commandStart);
						}
						else if (environment.equals("document"))
						{
							// parse and return an beginDocument command.
							return createBeginDocumentCommand(commandStart);
						}
						else if (environment.equals("figure"))
						{
							// parse and return an beginFigure command.
							return createBeginFigureCommand(commandStart);
						}
						else if (environment.equals("problem"))
						{
							// parse and return an beginProblem command.
							return createBeginProblemCommand(commandStart);
						}
					}
					else if (command.equals("\\end"))
					{
						// get the environment argument of the end command.
						String environment = parseForArgument();

						if (environment.equals("answers"))
						{
							// parse and return an endAnswers command.
							return createEndAnswersCommand(commandStart);
						}
						else if (environment.equals("block"))
						{
							// parse and return an endBlock command.
							return createEndBlockCommand(commandStart);
						}
						else if (environment.equals("document"))
						{
							// parse and return an endDocument command.
							return createEndDocumentCommand(commandStart);
						}
						else if (environment.equals("figure"))
						{
							// parse and return an endFigure command.
							return createEndFigureCommand(commandStart);
						}
						else if (environment.equals("problem"))
						{
							// parse and return an endProblem command.
							return createEndProblemCommand(commandStart);
						}
					}
				}

				// no command found, so move to next character in file.
				uefCharHandler.move();
			}
			catch (EOFException e)
			{
				// hit EOF when parsing for a command or in the middle of
				// parsing a command.
				throw new RexParseException("Unexpected end of file when parsing for command.", null);
			}
		}

		// reached EOF and no commands were found so return null.
		return null;
	}

	/**
	 * Parses for all commands in file and adds them to internal command queue.
	 * They are ready to be processed by the underlying CommandHandler after the
	 * call finishes.
	 * 
	 * @param file
	 *            the underlying file to open and parse.
	 * 
	 * @throws RexParseException
	 *             if there is a problem during parsing.
	 * 
	 * @throws IOException
	 *             If an I/O problem occurs.
	 */
	void parseForAllCommands(File file) throws RexParseException, IOException
	{
		// open the file to parse.
		this.openFile(file);

		// parse each command.
		UEFCommand uefCommand = parseForCommand();
		while (uefCommand != null)
		{
			// add the command to the queue.
			uefCommandHandler.add(uefCommand);

			// get the next command.
			uefCommand = parseForCommand();
		}
	}

	/**
	 * Parses UEF file and generates an ExamIF from it.
	 * 
	 * @param file
	 *            the file handler for the uef file.
	 * @return ExamIF representation of the UE file.
	 * @throws RexException
	 *             if some kind of parsing error occurs.
	 * @throws IOException
	 *             if the file cannot be open or read.
	 */
	public ExamIF parse(File file) throws RexException, IOException
	{	
		// runs the UEF through pdflatex
		uefPdflatex(file);

		// completely parse the file.
		parseForAllCommands(file);

		// process the queue and return the generated ExamIF.
		return uefCommandHandler.process();
	}
	
	/**
	 * Runs the UEF through pdflatex to detect Latex errors.  If there is an error,
	 * gives pdflatex's output.  Otherwise continues with rex as normal.  Generates
	 * .aux and .log files, and a .pdf file when it can, upon every run of rex.
	 * 
	 * @author Tim Armstrong
	 * @param uef - the UEF file
	 * @throws IOException - if the file cannot be opened or read, or if rex cannot read from the
	 * 						 pdflatex input stream
	 * @throws RexParseException - if the Latex isn't valid
	 * @throws RexException - if there is a system error executing pdflatex 
	 */
	public static void uefPdflatex(File uef) throws IOException, RexException
	{				
		String uefFilename = uef.getName();
		String uefPath = uef.getCanonicalPath();
		
		// directory of the UEF file
		File uefDirectory = new File(uefPath.substring(0, uefPath.lastIndexOf(System.getProperty("file.separator"))));
		
				
		// create command for pdflatex.  A little awkward so that the main command can be a constant
		// at the top of the file.
		List<String> cmd = new ArrayList<String>();		
		for (String s : PDFLATEXCMD)
		{
			cmd.add(s);
		}
		cmd.add(uefFilename);
		
		
		// set up for executing command
		ProcessBuilder pb = new ProcessBuilder(cmd);
		
		// Run pdflatex from uef's directory.  Problems arise otherwise!
		pb.directory(uefDirectory);
		
		
		// Run pdflatex first time. Sometimes it needs to run twice.
		
		Process pdfLatexProcess = pb.start();
		pdfLatexProcess.getInputStream().close();
		pdfLatexProcess.getErrorStream().close();
		try
		{
			pdfLatexProcess.waitFor(); // wait for it to end
		}
		catch (InterruptedException e)
		{
			throw new RexException("Error in running the File through pdflatex");
		}
		
		
		// Run pdflatex the second time
		
		pdfLatexProcess = pb.start();
		
		// pdflatex's command line output
		BufferedReader pdfLatexInput = new BufferedReader(new InputStreamReader(pdfLatexProcess.getInputStream()));

		// to store the message pdflatex gives
		StringBuffer pdflatexMessage = new StringBuffer();	
		
		String newline = System.getProperty("line.separator");		
		String line;		
		
		// until reach end of command line output:
		while ((line = pdfLatexInput.readLine()) != null)			
		{		
			// add the line
			pdflatexMessage.append(line + newline);
		}			
		pdfLatexInput.close();			
		pdfLatexProcess.getErrorStream().close();
		
		// waits for the process to terminate and get its exit status
		int exitStatus;
		try
		{
			exitStatus = pdfLatexProcess.waitFor();
		}
		catch (InterruptedException e)
		{
			throw new RexException("Error in running the File through pdflatex");
		}

		
		if (exitStatus != 0) // i.e., if pdflatex cannot produce the pdf file because of invalid Latex
		{			
			throw new RexParseException(pdflatexMessage.append
					(newline + "REX: " + uefFilename + " is not valid Latex.").toString(), null);
		}
	}
}
