package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Parser for the Universal Exam File (UEF).
 *
 * FIXME: Doesn't handle /labels{}.
 *
 * FIXME: Does not handle optional arguments in the form of /command[].
 *
 * FIXME: Doesn't handle the \verb command.
 *
 * @author Aaron Myles Landwehr
 *
 */
public class UEFParser implements UEFParserIF
{

	// Stack to maintain a hiearchary of states.
	Stack<States> state;

	// The list of states we have.
	//States.top is just so we know we are at the top of the stack
	private enum States
	{

		top, comment, documentclass, verbatim, problem, answers, answer
	}
	// Subclass that implements general parsing of the file.
	private UEF uef;
	// Subclass that implements parsing of commands.
	private Command command;

	// The latex commands that we currently understand.
	private enum CommandTypes
	{

		documentclass, beginVerbatim, endVerbatim, beginProblem, endProblem, beginAnswers,
		endAnswers, answer, none
	}

	public UEFParser()
	{
		// Stack to maintain a hiearchary of states.
		state = new Stack<States>();
		state.push(States.top);

		// Subclass that implements general parsing of the file
		uef = new UEF();
		// Subclass that implements parsing of commands.
		command = new Command();

	}

	/**
	 * Class that handles the underlying file character by character.
	 */
	private class UEF
	{
		// The current position in the file

		private int position = -1;
		// The complete contents of the file
		private StringBuffer fileContents = new StringBuffer();

		/**
		 * Opens a file and completely reads it into a StringBuffer for easy
		 * parsing. Set's the position to the first character of the file.
		 *
		 * @param file
		 *            The file to read from.
		 * @throws IOException
		 *
		 */
		private void openFile(File file) throws IOException
		{
			FileInputStream stream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));

			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				fileContents.append(line);
				fileContents.append('\n');
			}
			reader.close();
			position = 0;
		}

		/**
		 * Moves the current position ahead one byte in the file.
		 */
		private void move()
		{
			position++;
		}

		/**
		 * Reads one character from the underlying file and returns it.
		 *
		 * @return The character read.
		 *
		 */
		private char read()
		{
			try
			{
				return fileContents.charAt(position);
			}
			catch (IndexOutOfBoundsException e)
			{
				System.out.println("FIXME: I'm not handled when the end of file is reached...");
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
		private String getContent(int start, int end)
		{
			return fileContents.substring(start, end);
		}

		/**
		 * Returns the current position in the file.
		 *
		 * @return The current position in the file.
		 */
		private int getPosition()
		{
			return position;
		}

		/**
		 * Sets the current position in the file.
		 *
		 * @param position
		 *            The position to set the file to.
		 */
		private void setPosition(int position)
		{
			this.position = position;
		}
	}

	/**
	 * This subclass handles everything related to commands.
	 */
	private class Command
	{
		//The beginning of the last command.

		int lastPosition = -1;

		/**
		 * Starting at the currently retrieved character from the stream this method
		 * returns arguments surrounded by squiggle brackets. Yes, misspelled
		 * squiggle brackets.
		 *
		 * FIXME: Needs error checking for when there are any character other than
		 * spaces and new lines between arguments.
		 *
		 * FIXME: Needs to check for comments and ignore arguments found within
		 * those.
		 *
		 * @param numberOfArguments
		 *            The number of arguments you want to retrieve
		 * @return A string array with the arguments as Strings
		 */
		private String[] getArguments(int numberOfArguments)
		{
			String[] arguments = new String[numberOfArguments];
			for (int i = 0; i < numberOfArguments; i++)
			{
				// Read until the first argument
				while (uef.read() != '{')
				{
					uef.move();
				}

				// Read until the end of the first argument
				uef.move();

				StringBuffer argument = new StringBuffer();
				while (uef.read() != '}')
				{
					argument.append(uef.read());
					uef.move();
				}

				// Add the argument to our array after trimming it
				arguments[i] = argument.toString().trim();
			}

			//Move past the last argument delimeter and return the arguments
			uef.move();
			return arguments;
		}

		/**
		 * Starting at the current position in the file this reads until the
		 * next one of our handled commands and returns a String with all the
		 * characters read.
		 *
		 * @return A String with all characters read
		 */
		private String peekUntil()
		{
			// get the current position in the file
			int currentPosition = uef.getPosition();

			// process until we find a command
			CommandTypes commandType = command.process();

			// loop until we find one of our commands
			while (!commandType.equals(CommandTypes.beginAnswers) && !commandType.equals(
					CommandTypes.endAnswers) && !commandType.equals(CommandTypes.beginProblem)
				   && !commandType.equals(CommandTypes.endProblem) && !commandType.equals(
					CommandTypes.answer))
			{
				// process until we find a command
				commandType = command.process();
			}

			// grap all the characters inbetween our original position and our
			// new position
			String peakedData = uef.getContent(currentPosition, command.lastPosition);

			// reset our position
			uef.setPosition(currentPosition);

			// return all text content found
			return peakedData;
		}

		/**
		 * Stating at the current position reads until a command and then gets the command and
		 * returns it as a String. Always moves passed the commands characters in the file.
		 *
		 * @return the current command as a string without the '\' prefix
		 */
		private CommandTypes process()
		{

			// FIXME: while(true) is bad form
			while (true)
			{
				// pre-process to set some flags (only comment detection at this
				// moment).
				preprocessCurrentCharacter();

				// command detection below.
				// FIXME: Add Handler for escaped backslash here.

				// Check to see if the current character begins a command and
				// whether it is in a comment or not.
				if (uef.read() == '\\' && state.peek() != States.comment)
				{
					lastPosition = uef.getPosition();
					String command = new String();

					// move forward in the file.
					uef.move();

					//check to see if the command is an escape character
					if (uef.read() == '\\')
					{
						//move forward because we don't care about this command
						uef.move();
						return CommandTypes.none;
					}

					// Grab each letter in the command while there are letters
					// to grab
					// remember only letters are valid for command names
					while (Character.isLetter(uef.read()))
					{
						command = command + uef.read();
						uef.move();
					}

					// Below handle the end command first to check for verbatim
					if (command.equals("end"))
					{
						String arguments[] = getArguments(1);
						String environment = arguments[0];

						//check for /end{verbatim} first!!!
						if (environment.equals("verbatim"))
						{
							//Needs to be treated differently than normal commands.
							processEndVerbatim();
							continue;
						}

						//If we are in verbatim ignore processing for other environments
						if (state.peek() == States.verbatim)
						{
							continue;
						}
						else
						{
							if (environment.equals("problem"))
							{
								return CommandTypes.endProblem;
							}
							else if (environment.equals("answers"))
							{
								return CommandTypes.endAnswers;
							}
							else
							{
								return CommandTypes.none;
							}
						}
					}

					//If we are in verbatim ignore processing for other commands
					if (state.peek() == States.verbatim)
					{
						continue;
					}
					else
					{
						if (command.equals("documentclass"))
						{
							uef.move();
							return CommandTypes.documentclass;
						}
						else if (command.equals("begin"))
						{
							String arguments[] = getArguments(1);
							String environment = arguments[0];

							if (environment.equals("verbatim"))
							{
								processBeginVerbatim();
								continue;
							}
							else if (environment.equals("problem"))
							{
								return CommandTypes.beginProblem;
							}
							else if (environment.equals("answers"))
							{
								return CommandTypes.beginAnswers;
							}
							else
							{
								return CommandTypes.none;
							}
						}
						else if (command.equals("answer"))
						{
							uef.move();
							return CommandTypes.answer;
						}
						else
						{
							// A command we don't handle or care about.
							uef.move();
							return CommandTypes.none;
						}
					}
				}

				//move to the next character
				uef.move();
			}
		}

		/**
		 * Handles the \documentclass command.
		 *
		 */
		private void processDocumentclass()
		{
			//push the new state.
			state.push(States.documentclass);

			// FIXME: code isn't finished in processDocumentclass()
			String[] arguments = getArguments(1);
			if (!arguments[0].equals("exam"))
			{
				return; // throw an error or something
			}
			System.out.println("Found documentclass of type 'exam'");

			if (state.peek() == States.documentclass)
			{
				//pop the old state.
				state.pop();
			}
			else
			{
				System.out.println(
						"Error: program reached an invalid state in processDocumentClass()");
				System.exit(-1);
			}
		}

		/**
		 * Handles the Answer command.
		 *
		 */
		private void processAnswer()
		{
			//push the new state.
			state.push(States.answer);

			// get text after Answer until the next command
			String buffer = command.peekUntil();
			System.out.println("Found an answer with the following text:");
			System.out.println("-----------------------------------");
			System.out.println(buffer);
			System.out.println("-----------------------------------");
			System.out.println();

			if (state.peek() == States.answer)
			{
				//pop the old state.
				state.pop();
			}
			else
			{
				System.out.println("Error: program reached an invalid state in processAnswer()");
				System.exit(-1);
			}
		}

		/**
		 * Handles the \begin{verbatim} environment.
		 *
		 */
		private void processBeginVerbatim()
		{
			//push the new state.
			state.push(States.verbatim);
		}

		/**
		 * Handles the \end{verbatim} environment.
		 *
		 */
		private void processEndVerbatim()
		{
			if (state.peek() == States.verbatim)
			{
				//pop the old state.
				state.pop();
			}
			else
			{
				System.out.println("Error: \\end{verbatim} without a matching \\begin{verbatim}");
				System.exit(-1);
			}
		}

		/**
		 * Handles the \begin{problem} environment.
		 *
		 */
		private void processBeginProblem()
		{
			//push the new state.
			state.push(States.problem);

			// get the arguments for Problem
			String[] arguments = getArguments(2);

			String topic = arguments[0];// grab the topic
			String difficulty = arguments[1];// grab the difficulty

			// do some stuff we don't need to do
			System.out.println("Found Problem Environment with topic '"
							   + topic + "' of difficulty '" + difficulty
							   + "' with the following text:");

			// get text for the problem
			String buffer = peekUntil();
			System.out.println("-----------------------------------");
			System.out.println(buffer);
			System.out.println("-----------------------------------");
			System.out.println();
		}

		/**
		 * Handles the \end{problem} environment.
		 *
		 */
		private void processEndProblem()
		{
			if (state.peek() == States.problem)
			{
				//pop the old state.
				state.pop();
			}
			else
			{
				System.out.println("Error: \\end{problem} without a matching \\begin{problem}");
				System.exit(-1);
			}
		}

		/**
		 * Handles the \begin{answers} environment.
		 *
		 */
		private void processBeginAnswers()
		{
			//push the new state.
			state.push(States.answers);
			System.out.println("Found Answers Environment");
		}

		/**
		 * Handles the \end{answers} environment.
		 *
		 */
		private void processEndAnswers()
		{
			if (state.peek() == States.answers)
			{
				//pop the old state.
				state.pop();
			}
			else
			{
				System.out.println("Error: \\end{answers} without a matching \\begin{answers}");
				System.exit(-1);
			}
		}
	}

	/**
	 * Sets or unsets whether we are at comment in the stream.
	 */
	private void preprocessCurrentCharacter()
	{
		if (uef.read() == '%')
		{
			// comment character to set that we are at a comment
			state.push(States.comment);
		}
		else if (uef.read() == '\n')
		{
			// new line so unset that we are at a comment
			if (state.peek() == States.comment)
			{
				state.pop();
			}
		}
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
		try
		{
			uef.openFile(file);
			CommandTypes commandType;

			// Read each command until the end of the file.
			while ((commandType = command.process()) != null)
			{
				// Check to see what the current command is and process it.
				if (commandType.equals(CommandTypes.documentclass))
				{
					command.processDocumentclass();
				}
				else if (commandType.equals(CommandTypes.answer))
				{
					command.processAnswer();
				}
				else if (commandType.equals(CommandTypes.beginProblem))
				{
					command.processBeginProblem();
				}
				else if (commandType.equals(CommandTypes.endProblem))
				{
					command.processEndProblem();
				}
				else if (commandType.equals(CommandTypes.beginAnswers))
				{
					command.processBeginAnswers();
				}
				else if (commandType.equals(CommandTypes.endAnswers))
				{
					command.processEndAnswers();
				}

			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (StringIndexOutOfBoundsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
