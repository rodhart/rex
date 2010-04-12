package edu.udel.cis.cisc475.rex.uefparser;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Parser for the Universal Exam File (UEF).
 * 
 * FIXME: Doesn't handle /labels{}.
 * 
 * FIXME: Does not handle optional arguments in the form of /command[].
 * 
 * FIXME: Doesn't handle the escape character '\\'.
 * 
 * FIXME: Doesn't handle \begin{verbatim} \end{verbatim} environment.
 * 
 * FIXME: Doesn't handle the \verb command.
 * 
 * @author Aaron Myles Landwehr
 * 
 */
public class UEFParser implements UEFParserIF
{

	// Some useful states
	private boolean isComment = false;
	// Subclass that implements general parsing of the file
	private UEF uef = new UEF();
	// Subclass that implements parsing of commands.
	private Command command = new Command();

	// The latex commands that we currently understand.
	//Be careful to not add any commands that we don't want to be peaked ahead.
	//Otherwise Command.peakUntil() will break.
	//FIXME: Command.peakUntil() should have a list of commands it treats specially.
	private enum CommandTypes {

		documentclass, begin, end, answer, none
	}

	// The latex environments that we currently understand.
	private enum EnvironmentTypes {

		problem, answers, none
	}

	/**
	 * Returns whether our current position in the stream is within a comment.
	 * 
	 * @return true if we are in a comment and false if we are not.
	 */
	private boolean isComment()
	{
		return isComment;
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
		 * parsing.
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

			for (String line = reader.readLine(); line != null; line = reader
					.readLine())
			{
				fileContents.append(line);
				fileContents.append('\n');
			}
			reader.close();
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
			return fileContents.charAt(position);
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

		// Instance of an environment class to handle things related to latex
		// environments
		private Environment environment = new Environment();

		/**
		 * This subclass handles everything related to environments.
		 */
		private class Environment
		{

			/**
			 * Inteprets the environment we are at from the current string.
			 * 
			 * E.g. if the String is "problem" then returns
			 * EvironmentTypes.problem
			 * 
			 * @param environment
			 *            A string representing a possible environment.
			 * @return The environment type found if any. If no environment is
			 *         found then returns EnvironmentTypes.none.
			 */
			private EnvironmentTypes process(String environment)
			{
				if (environment.equals("problem"))
				{
					return EnvironmentTypes.problem;
				}
				else if (environment.equals("answers"))
				{
					return EnvironmentTypes.answers;
				}
				else
				{
					return EnvironmentTypes.none;
				}
			}

			/**
			 * Handles the \begin{problem} environment.
			 * 
			 */
			private void processProblem()
			{
				// get the arguments for Problem
				String[] arguments = getArguments(2);

				String topic = arguments[0];// grab the topic
				String difficulty = arguments[1];// grab the difficulty

				// do some stuff we don't need to do
				System.out.println("Found Problem Environment with topic '"
						+ topic + "' of difficulty '" + difficulty
						+ "' with the following text:");

				// get text for the problem
				String buffer = peakUntil();
				System.out.println("-----------------------------------");
				System.out.println(buffer);
				System.out.println("-----------------------------------");
				System.out.println();
			}

			/**
			 * Handles the \begin{answers} environment.
			 * 
			 */
			private void processAnswers()
			{
				// FIXME: we need to process this.
				System.out.println("Found Answers Environment");
			}
		}

		/**
		 * Starting at the current position in the file this reads until the
		 * next one of our handled commands and returns a String with all the
		 * characters read.
		 * 
		 * @return A String with all characters read
		 */
		private String peakUntil()
		{
			// get the current position in the file
			int currentPosition = uef.getPosition();

			// process until we find a command
			CommandTypes commandType = command.process();

			// loop until we find one of our commands
			while (commandType.equals(CommandTypes.none))
			{
				// process until we find a command
				commandType = command.process();
			}

			// grap all the characters inbetween our original position and our
			// new position
			String peakedData = uef.getContent(currentPosition, uef
					.getPosition() -
					commandType.toString().length() - 2);

			// reset our position
			uef.setPosition(currentPosition);

			// return all text content found
			return peakedData;
		}

		/**
		 * Reads until a command and then gets the command and returns it as a
		 * String. Always moves passed the commands characters in the file.
		 * 
		 * @return the current command as a string without the '\' prefix
		 */
		private CommandTypes process()
		{

			// FIXME: while(true) is bad form
			while (true)
			{
				uef.move();

				// pre-process to set some flags (only comment detection at this
				// moment).
				preprocessCurrentCharacter();

				// command detection below.
				// FIXME: Add Handler for escaped backslash here.

				// Check to see if the current character begins a command and
				// whether it is in a comment or not.
				if (uef.read() == '\\' && !isComment())
				{

					String command = new String();

					// move forward in the file.
					uef.move();

					//check to see if the command is an escape character
					if(uef.read()=='\\')
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

					// Below just returns the type of command we found.
					if (command.equals("documentclass"))
					{
					    uef.move();
					    return CommandTypes.documentclass;
					}
					else if (command.equals("begin"))
					{
					    uef.move();
					    return CommandTypes.begin;
					}
					else if (command.equals("end"))
					{
					    uef.move();
					    return CommandTypes.end;
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
		}

		/**
		 * Handles the \documentclass command.
		 * 
		 */
		private void processDocumentclass()
		{
			// FIXME: code isn't finished in processDocumentclass()
			String[] arguments = getArguments(1);
			if (!arguments[0].equals("exam"))
			{
				return; // throw an error or something
			}
			System.out.println("Found documentclass of type 'exam'");
		}

		/**
		 * Handles the \begin command.
		 * 
		 */
		private void processBegin()
		{
			// get the type of environment
			String[] arguments = getArguments(1);
			EnvironmentTypes environmentType = environment
					.process(arguments[0]);
			// check for environments we handle
			if (environmentType.equals(EnvironmentTypes.problem))
			{
				environment.processProblem();
			}
			else if (environmentType.equals(EnvironmentTypes.answers))
			{
				environment.processAnswers();
			}
		}

		/**
		 * Handles the \end command.
		 * 
		 */
		private void processEnd() throws IOException
		{
			// get the type of environment
			String[] arguments = getArguments(1);
			EnvironmentTypes environmentType = environment
					.process(arguments[0]);

			if (environmentType.equals(EnvironmentTypes.problem))
			{
				// do something maybe
			}
			else if (environmentType.equals(EnvironmentTypes.answers))
			{
				// do something maybe
			}
		}

		/**
		 * Handles the Answer command.
		 * 
		 */
		private void processAnswer()
		{
			// get text after Answer until the next command
			String buffer = command.peakUntil();
			System.out.println("Found an answer with the following text:");
			System.out.println("-----------------------------------");
			System.out.println(buffer);
			System.out.println("-----------------------------------");
			System.out.println();
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
			this.isComment = true;
		}
		else if (uef.read() == '\n')
		{
			// new line so unset that we are at a comment
			this.isComment = false;
		}
	}

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
				else if (commandType.equals(CommandTypes.begin))
				{
					command.processBegin();
				}
				else if (commandType.equals(CommandTypes.end))
				{
					command.processEnd();
				}
				else if (commandType.equals(CommandTypes.answer))
				{
					command.processAnswer();
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
			System.out.println("End of file reached.");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
