package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.util.Stack;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParser.States;

/**
 * This subclass handles everything related to commands.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 */
class UEFCommand {
	// The beginning of the last command.

	int lastPosition = -1;
	/**
	 * Reference to UEF char handler
	 */
	private UEFCharHandler uefCharHandler;
	/**
	 * Reference to the parser state
	 */
	private Stack<States> state;
	/**
	 * Exam factory is only created once at the constructor and used every time
	 */
	private ExamFactoryIF examFactory;
	/**
	 * Source factory is only created once at the constructor and used every
	 * time
	 */
	private SourceFactoryIF sourceFactory;

	/**
	 * The latex commands used by UEF as in the requirements.
	 */
	enum CommandTypes {

		documentclass, label, verb, beginBlock, endBlock, beginVerbatim, endVerbatim, beginProblem, endProblem, beginAnswers, endAnswers, answer, none
	}

	/**
	 * Create new object of the command handler
	 * 
	 * @param uefCharHandler
	 *            reference to the UEFCharHandler
	 * @param state
	 *            reference to the stack of states
	 */
	UEFCommand(UEFCharHandler uefCharHandler, Stack<States> state) {
		this.uefCharHandler = uefCharHandler;
		this.state = state;
		this.examFactory = new ExamFactory();
		this.sourceFactory = new SourceFactory();
	}

	/**
	 * Starting from the current position in the file returns the optional
	 * argument as a string. Always positions the file one character past ']'
	 * closing bracket.
	 * 
	 * @return the optional argument.
	 */
	String getOptionalArgument() {
		int position = uefCharHandler.getPosition();
		while (uefCharHandler.read() != '[') {
			if (uefCharHandler.read() == '{') {
				// reset the position back since there are no optional arguments
				uefCharHandler.setPosition(position);
				return null;
			}
			// Handle comments: %
			else if (uefCharHandler.read() == '%') {
				while (uefCharHandler.read() != '\n') {
					uefCharHandler.move();
				}
			}
			uefCharHandler.move();
		}

		// Read until the end of the first argument
		uefCharHandler.move();

		StringBuffer argument = new StringBuffer();
		while (uefCharHandler.read() != ']') {
			argument.append(uefCharHandler.read());
			uefCharHandler.move();
		}

		// Move past the last argument delimiter and return the arguments
		uefCharHandler.move();
		return argument.toString();
	}

	/**
	 * Starting at the currently retrieved character from the stream this method
	 * returns arguments surrounded by curly brackets.
	 * 
	 * @param numberOfArguments
	 *            The number of arguments you want to retrieve
	 * @return A string array with the arguments as Strings
	 */
	String[] getArguments(int numberOfArguments) {
		String[] arguments = new String[numberOfArguments];
		for (int i = 0; i < numberOfArguments; i++) {
			// Read until the first argument
			while (uefCharHandler.read() != '{') {
				// Handle comments: %
				if (uefCharHandler.read() == '%') {
					while (uefCharHandler.read() != '\n') {
						uefCharHandler.move();
					}
				}
				uefCharHandler.move();
			}

			// Read until the end of the first argument
			uefCharHandler.move();

			StringBuffer argument = new StringBuffer();
			while (uefCharHandler.read() != '}') {
				argument.append(uefCharHandler.read());
				uefCharHandler.move();
			}

			// Add the argument to our array after trimming it
			arguments[i] = argument.toString().trim();
		}

		// Move past the last argument delimeter and return the arguments
		uefCharHandler.move();
		return arguments;
	}

	/**
	 * Starting at the current position in the file this reads until the next
	 * one of our handled commands and returns a String with all the characters
	 * read.
	 * 
	 * @return A String with all characters read
	 */
	String peekUntil() {
		// get the current position in the file
		int currentPosition = uefCharHandler.getPosition();

		// process until we find a command
		CommandTypes commandType = process();

		// loop until we find one of our commands
		while (!commandType.equals(CommandTypes.beginAnswers)
				&& !commandType.equals(CommandTypes.endAnswers)
				&& !commandType.equals(CommandTypes.beginProblem)
				&& !commandType.equals(CommandTypes.endProblem)
				&& !commandType.equals(CommandTypes.answer)
				&& !commandType.equals(CommandTypes.endBlock)) {
			// process until we find a command
			commandType = process();
		}

		// grap all the characters inbetween our original position and our
		// new position
		String peakedData = uefCharHandler.getContent(currentPosition,
				lastPosition);

		// reset our position
		uefCharHandler.setPosition(currentPosition);

		// return all text content found
		return peakedData;
	}

	/**
	 * Sets or unsets whether we are at comment in the stream.
	 */
	private void preprocessCurrentCharacter() {
		if (uefCharHandler.read() == '%') {
			// comment character to set that we are at a comment
			state.push(States.comment);
		} else if (uefCharHandler.read() == '\n') {
			// new line so unset that we are at a comment
			if (state.peek() == States.comment) {
				state.pop();
			}
		}
	}

	/**
	 * Stating at the current position reads until a command and then gets the
	 * command and returns it as a String. Always moves passed the commands
	 * characters in the file.
	 * 
	 * @return the current command as a string without the '\' prefix
	 */
	CommandTypes process() {

		// FIXME: while(true) is bad form
		while (true) {
			// pre-process to set some flags (only comment detection at this
			// moment).
			preprocessCurrentCharacter();

			// command detection below.
			// FIXME: Add Handler for escaped backslash here.

			// Check to see if the current character begins a command and
			// whether it is in a comment or not.
			if (uefCharHandler.read() == '\\' && state.peek() != States.comment) {
				lastPosition = uefCharHandler.getPosition();
				String command = new String();

				// move forward in the file.
				uefCharHandler.move();

				// check to see if the command is an escape character
				if (uefCharHandler.read() == '\\') {
					// move forward because we don't care about this command
					uefCharHandler.move();
					return CommandTypes.none;
				}

				// Grab each letter in the command while there are letters
				// to grab
				// remember only letters are valid for command names
				while (Character.isLetter(uefCharHandler.read())) {
					command = command + uefCharHandler.read();
					uefCharHandler.move();
				}

				// Below handle the end command first to check for verbatim
				if (command.equals("end")) {
					String arguments[] = getArguments(1);
					String environment = arguments[0];

					// check for /end{verbatim} first!!!
					if (environment.equals("verbatim")) {
						// Needs to be treated differently than normal
						// commands.
						processEndVerbatim();
						continue;
					}

					// If we are in verbatim ignore processing for other
					// environments
					if (state.peek() == States.verbatim) {
						continue;
					} else {
						if (environment.equals("block")) {
							return CommandTypes.endBlock;
						} else if (environment.equals("problem")) {
							return CommandTypes.endProblem;
						} else if (environment.equals("answers")) {
							return CommandTypes.endAnswers;
						} else {
							return CommandTypes.none;
						}
					}
				}

				// If we are in verbatim ignore processing for other
				// commands
				if (state.peek() == States.verbatim) {
					continue;
				} else {
					if (command.equals("label")) {
						processLabel();
					}
					if (command.equals("verb")) {
						processVerb();
						continue;
					} else if (command.equals("documentclass")) {
						return CommandTypes.documentclass;
					} else if (command.equals("begin")) {
						String arguments[] = getArguments(1);
						String environment = arguments[0];

						if (environment.equals("verbatim")) {
							processBeginVerbatim();
							continue;
						} else if (environment.equals("block")) {
							return CommandTypes.beginBlock;
						} else if (environment.equals("problem")) {
							return CommandTypes.beginProblem;
						} else if (environment.equals("answers")) {
							return CommandTypes.beginAnswers;
						} else {
							return CommandTypes.none;
						}
					} else if (command.equals("answer")) {
						return CommandTypes.answer;
					} else {
						// A command we don't handle or care about.
						return CommandTypes.none;
					}
				}
			}

			// move to the next character
			uefCharHandler.move();
		}
	}

	/**
	 * Handles the \documentclass command.
	 * 
	 */
	void processDocumentclass() {
		// push the new state.
		state.push(States.documentclass);

		// FIXME: code isn't finished in processDocumentclass()
		String[] arguments = getArguments(1);
		if (!arguments[0].equals("exam")) {
			return; // throw an error or something
		}
		System.out.println("Found documentclass of type 'exam'");

		if (state.peek() == States.documentclass) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: program reached an invalid state in processDocumentClass()");
			System.exit(-1);
		}
	}

	/**
	 * Handles the Answer command.
	 * 
	 */
	AnswerIF processAnswer() {
		AnswerIF answer;
		SourceIF s = this.sourceFactory.newSource(this.uefCharHandler
				.getFileName());
		s.setStartColumn(this.uefCharHandler.getColumnNo());
		s.setStartLine(this.uefCharHandler.getLineNo());

		// push the new state.
		state.push(States.answer);

		// get optional argument for Problem
		String optionalArgument = getOptionalArgument();

		// get text after Answer until the next command
		String buffer = peekUntil();
		System.out.println("Found an answer with the optional argument '"
				+ optionalArgument + "' the following text:");
		System.out.println("-----------------------------------");
		System.out.println(buffer);
		System.out.println("-----------------------------------");
		System.out.println();

		s.addText(buffer);
		s.setLastColumn(this.uefCharHandler.getColumnNo());
		s.setLastLine(this.uefCharHandler.getLineNo());

		String args[] = this.processOptionalArguments(optionalArgument);
		boolean isCorrect = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("correct")) {
				isCorrect = true;
			}
		}

		answer = this.examFactory.newAnswer(isCorrect, s);

		if (state.peek() == States.answer) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: program reached an invalid state in processAnswer()");
			System.exit(-1);
		}
		return answer;
	}

	/**
	 * Handles the \label command
	 * 
	 */
	String processLabel() {
		// push the new state.
		state.push(States.label);

		String[] arguments = getArguments(1);

		String label = arguments[0];
		System.out.println("Found label '" + label + "'");

		if (state.peek() == States.label) {
			// pop the old state.
			state.pop();
		}
		return label.trim();
	}

	/**
	 * Handles the \verb* command
	 * 
	 */
	void processVerb() {
		// push the new state.
		state.push(States.verb);
		char delimiter = uefCharHandler.read();
		uefCharHandler.move();

		// make sure that the delimiter is the first none white space char
		while (Character.isWhitespace(delimiter)) {
			delimiter = uefCharHandler.read();
			uefCharHandler.move();
		}

		while (uefCharHandler.read() != delimiter) {
			uefCharHandler.move();
		}

		uefCharHandler.move();

		if (state.peek() == States.verb) {
			// pop the old state.
			state.pop();
		}
	}

	/**
	 * Handles the \begin{verbatim} environment.
	 * 
	 */
	private void processBeginVerbatim() {
		// push the new state.
		state.push(States.verbatim);
	}

	/**
	 * Handles the \end{verbatim} environment.
	 * 
	 */
	private void processEndVerbatim() {
		if (state.peek() == States.verbatim) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: \\end{verbatim} without a matching \\begin{verbatim}");
			System.exit(-1);
		}
	}

	/**
	 * Handles the \begin{problem} environment.
	 * 
	 */
	ProblemIF processBeginProblem() {
		ProblemIF problem;
		SourceIF source = sourceFactory.newSource(this.uefCharHandler
				.getFileName());
		source.setStartColumn(this.uefCharHandler.getColumnNo());
		source.setStartLine(this.uefCharHandler.getLineNo());

		// push the new state.
		state.push(States.problem);

		// get optional argument for Problem
		String optionalArgument = getOptionalArgument();

		// get the arguments for Problem
		String[] arguments = getArguments(2);

		String topic = arguments[0];// grab the topic
		String difficulty = arguments[1];// grab the difficulty

		// do some stuff we don't need to do
		System.out.println("Found Problem Environment with topic '" + topic
				+ "' of difficulty '" + difficulty
				+ "' with optional argument '" + optionalArgument
				+ "' with the following text:");

		// get text for the problem
		String buffer = peekUntil();
		source.setLastColumn(this.uefCharHandler.getColumnNo());
		source.setLastLine(this.uefCharHandler.getLineNo());

		System.out.println("-----------------------------------");
		System.out.println(buffer);
		System.out.println("-----------------------------------");
		System.out.println();

		problem = this.examFactory.newProblem(topic, "", source, null);
		return problem;
	}

	/**
	 * Handles the \end{problem} environment.
	 * 
	 */
	void processEndProblem() {
		if (state.peek() == States.problem) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: \\end{problem} without a matching \\begin{problem}");
			System.exit(-1);
		}
	}

	/**
	 * Handles the \begin{block} environment.
	 * 
	 */
	BlockIF processBeginBlock() {
		SourceIF source = sourceFactory.newSource(this.uefCharHandler
				.getFileName());
		source.setStartColumn(this.uefCharHandler.getColumnNo());
		source.setStartLine(this.uefCharHandler.getLineNo());

		// push the new state.
		state.push(States.block);

		// do some stuff we don't need to do
		System.out.println("Found Block Environment with the following text:");

		// get text for the block
		String buffer = peekUntil();

		System.out.println("-----------------------------------");
		System.out.println(buffer);
		System.out.println("-----------------------------------");
		System.out.println();

		source.setLastColumn(this.uefCharHandler.getColumnNo());
		source.setLastLine(this.uefCharHandler.getLineNo());

		BlockIF block = new Block("", "", source);

		return block;
	}

	/**
	 * Handles the \end{block} environment.
	 * 
	 */
	void processEndBlock() {
		if (state.peek() == States.block) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: \\end{block} without a matching \\begin{block}");
			System.exit(-1);
		}
	}

	/**
	 * Handles the \begin{answers} environment.
	 * 
	 */
	void processBeginAnswers() {
		// push the new state.
		state.push(States.answers);
		System.out.println("Found Answers Environment");
	}

	/**
	 * Handles the \end{answers} environment.
	 * 
	 */
	void processEndAnswers() {
		if (state.peek() == States.answers) {
			// pop the old state.
			state.pop();
		} else {
			System.out
					.println("Error: \\end{answers} without a matching \\begin{answers}");
			System.exit(-1);
		}
	}

	/**
	 * This method will take the optional arguments delimited by comma can broke
	 * them into array of strings
	 * 
	 * @param args
	 *            string of comma delimited string
	 * @return
	 */
	String[] processOptionalArguments(String args) {
		args = args.trim();
		String r[] = args.split(",");
		for (int i = 0; i < r.length; i++) {
			r[i] = r[i].trim();
		}
		return r;
	}
}
