package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.Types;

/**
 * This class handles the processing of commands after they are parsed from a
 * file. It contains an internal queue of all commands that the parser fills.
 * Internally, the class attempts to hierarchically handle commands. For
 * example, the processDocument() method will handle the processProblem(),
 * processBlock(), and processFigure() commands.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 */
class UEFCommandHandler
{

	/**
	 * Queue of all commands from the file.
	 */
	private Queue<UEFCommand> uefCommandQueue;
	/**
	 * Underlying file that was read from. Used to retrieve text from lines in
	 * the case that errors occur and used to fill out SourceIF objects needed
	 * by the ExamIF module.
	 */
	private UEFCharHandler uefCharHandler;
	private UEFReferenceHandler uefReferenceHandler;
	/**
	 * Source factory is only created once at the constructor and used every
	 * time a source object needs to be created.
	 */
	private SourceFactoryIF sourceFactory;
	/**
	 * Exam Factor is only created once at the constructor and used every time
	 * parts of exam needs to be created.
	 */
	private ExamFactoryIF examFactory;

	/**
	 * Constructor for the class. Takes a reference to the underlying
	 * UEFCharHandler that reads the file.
	 * 
	 * @param uefCharHandler
	 *            the underlying UEFCharHandler.
	 */
	UEFCommandHandler(UEFCharHandler uefCharHandler)
	{
		// initialize object fields.
		this.uefCharHandler = uefCharHandler;
		this.uefCommandQueue = new LinkedList<UEFCommand>();
		this.uefReferenceHandler = new UEFReferenceHandler();
		this.sourceFactory = new SourceFactory();
		this.examFactory = new ExamFactory();
	}

	/**
	 * Adds a command into the command queue.
	 * 
	 * @param uefCommand
	 *            the command to add to the queue.
	 */
	void add(UEFCommand uefCommand)
	{
		// add the internal command to the queue.
		this.uefCommandQueue.add(uefCommand);
	}

	/**
	 * Finds the next command that matches a command found in the passed in
	 * array and returns that command.
	 * 
	 * @param types
	 *            an array of commands to match.
	 * @return the command in the queue that was matched or null if no commands
	 *         were matched.
	 */
	private UEFCommand findMatchingCommand(Types types[])
	{
		// Iterate through all command until a matchin command is found.
		for (Iterator<UEFCommand> iter = uefCommandQueue.iterator(); iter.hasNext();)
		{
			// get the command located at the current iterator.
			UEFCommand peekedCommand = iter.next();

			// for each command we need to check in the array.
			for (int i = 0; i < types.length; i++)
			{
				// check the current command in the queue with a piece of the
				// array.
				if (peekedCommand.getType() == types[i])
				{
					// matched command so return it.
					return peekedCommand;
				}
			}
		}

		// no matched commands of the types in the array so return null.
		return null;
	}

	/**
	 * Checks whether the argument contains the String: 'fixed'.
	 * 
	 * @param argument
	 *            the argument to check.
	 * @return Return true or false depending on whether the argument contains
	 *         'fixed'. Return false if the argument is null.
	 */
	private boolean isFixed(String argument)
	{
		// make sure the argument isn't null before checking.
		if (argument != null)
		{
			// check the argument and return the result.
			return argument.contains("fixed");
		}
		else
		{
			// false if the argument is null.
			return false;
		}
	}

	/**
	 * Checks whether the argument contains 'correct'. Returns true if so else
	 * false.
	 * 
	 * @param argument
	 *            the argument to check.
	 * @return true or false depending on whether the argument contains
	 *         'correct'. Return false if the argument is null.
	 */
	private boolean isCorrect(String argument)
	{
		// make sure the argument isn't null before checking.
		if (argument != null)
		{
			// check the argument and return the result.
			return argument.contains("correct");
		}
		else
		{
			// false if the argument is null.
			return false;
		}
	}

	/**
	 * Process an \answer command.
	 * 
	 * @param index
	 *            index to add in the case that this answer happens to be a
	 *            fixed answer.
	 * 
	 * @return a AnswerIF or FixedAnswerIF representation of the processed
	 *         answer.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	AnswerIF processAnswer(int index) throws RexParseException, EOFException
	{
		// grab the /answer command from the queue.
		UEFCommand command = uefCommandQueue.poll();

		// get the optional argument
		String optionalArgument = command.getOptionalArgument();

		// get whether this is a fixed answer.
		boolean fixed = isFixed(optionalArgument);

		// get whether this is a correct answer.
		boolean correct = isCorrect(optionalArgument);

		// String to be filled with the source content.
		String content;

		// Variables to hold the beginning and end of the source.
		int startSource = command.getStartPosition();
		int endSource;

		// fill out array so we can peek for answer and endAnswer commands.
		// This is how we tell where this current answer ends.
		Types type[] = new Types[2];
		type[0] = Types.answer;
		type[1] = Types.endAnswers;

		// do the actual peeking.
		UEFCommand peekedCommand = findMatchingCommand(type);

		// We should always find either /answer or /end{answer}. Something is
		// wrong with the file if we do not.
		if (peekedCommand == null)
		{
			// should always be either endAnswers or answer command

			// create the source object.
			SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(command.
					getStartPosition()), uefCharHandler.getColumnNumber(command.getStartPosition()), uefCharHandler.getLineNumber(command.
					getEndPosition()), uefCharHandler.getColumnNumber(command.getEndPosition()));

			// add the source text to the object.
			exceptionSource.addText(uefCharHandler.getContent(command.getStartPosition(), command.getEndPosition()));

			// throw the exception.
			throw new RexParseException("No \\end{answer} after \\begin{answer}.", exceptionSource);
		}

		// set the end of the source to the position before
		// the beginning of the next command.
		endSource = peekedCommand.getStartPosition();

		// Ignore whitespaces and lines at the end of answers, because latex
		// does. This is for the SourceIF object.
		while (uefCharHandler.read(endSource - 1) == '\n' || uefCharHandler.read(endSource - 1) == ' ' || uefCharHandler.read(endSource - 1)
																										  == '\t')
		{
			endSource--;
		}

		// get the file content from beginning of '/answer'
		// to beginning of next command.
		content = uefCharHandler.getContent(startSource, endSource);

		// Create the source object.
		SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
		source.setStartLine(uefCharHandler.getLineNumber(startSource));
		source.setLastLine(uefCharHandler.getLineNumber(endSource));
		source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
		source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
		source.addText(content);

		// check whether this is a fixed answer or not.
		if (!fixed)
		{
			// return the answer.
			return examFactory.newAnswer(correct, source);
		}
		else
		{
			// return the answer.
			return examFactory.newFixedAnswer(correct, index, source);
		}
	}

	/**
	 * Process the answers environment and command within. This includes
	 * processing of /ref commands and /answer commands.
	 * 
	 * @return an array of processed AnswerIF that may be either AnswerIF or
	 *         FixedAnswerIF.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	AnswerIF[] processAnswers() throws RexParseException, EOFException
	{
		// pop off the /begin{answers} command
		UEFCommand command = uefCommandQueue.poll();

		// set the answer index. Needed, because fixed answers must occur in a
		// certain spot.
		int index = 0;

		// create a list of answers to add the answers we process to.
		List<AnswerIF> answersList = new ArrayList<AnswerIF>();

		//boolean to make sure atleast one answer is correct.
		boolean foundCorrectAnswer = false;

		// process until either the queue is empty or we hit an endAnswers
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case answer:
				{
					// found answer command.

					// process the answer command.
					AnswerIF answer = processAnswer(index);

					// Check if it is correct, bc we need atleast one correct answer.
					if (answer.isCorrect())
					{
						foundCorrectAnswer = true;
					}

					// add the answer to the list.
					answersList.add(answer);

					// increment the index for fixed answers.
					index++;
					break;
				}
				case ref:
				{
					// add the reference to list of references so we can
					// later add USES relations for. We need to add these
					// answer references for the particular problem we are
					// creating. But, it can't be created until after we
					// get every answer. :-(
					this.uefReferenceHandler.addUnmappedReferences(processRef());
					break;
				}
				case endAnswers:
				{
					// found endAnswers command.

					// pop the /end{answers} command off the queue.
					UEFCommand endCommand = uefCommandQueue.poll();

					//make sure we found a correct answer within this environment.
					if (foundCorrectAnswer == false)
					{
						//didn't find a correct answer, so we throw an exception.

						// set the start source to beginning of the \begin{answers} command.
						int startSource = command.getStartPosition();

						// set the end source to the end of the \end{answers} command.
						int endSource = endCommand.getEndPosition();

						// Fill out the source.
						SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
								startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
								getColumnNumber(endSource));

						// add the file text to the source.
						exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

						// return the exception.
						throw new RexParseException("No correct answer found within answers environment!", exceptionSource);
					}

					// return the list of AnswerIF and FixedAnswerIF as an array.
					return answersList.toArray(new AnswerIF[0]);
				}
				default:
				{
					// Reached a command that shouldn't be found within a answers
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within answers environment!", exceptionSource);
				}
			}
		}

		// file ended without endBlock being found.

		// set the start source to beginning of the \begin{answers} command.
		int startSource = command.getStartPosition();

		// set the end source to the end of the \begin{answers} command.
		int endSource = command.getEndPosition();

		// Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(startSource), uefCharHandler.
				getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.getColumnNumber(endSource));

		// add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		// return the exception.
		throw new RexParseException("\\begin{answers} without matching \\end{answers}!", exceptionSource);
	}

	/**
	 * Process a block environment. This includes processing of /ref commands.
	 * 
	 * @return A BlockIF representation of the block environment.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	BlockIF processBlock() throws RexParseException, EOFException
	{
		// pop off the /begin{block} command
		UEFCommand command = uefCommandQueue.poll();

		// get the name of the block.
		String name = command.getArgument(0);

		// String to be filled with the source content.
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource = 0;

		// process until either the queue is empty or we hit an endBlock
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case ref:
				{
					// add the reference to our local list of refences to later
					// declareUses relationships for.
					this.uefReferenceHandler.addUnmappedReferences(processRef());
					break;
				}
				case endBlock:
				{
					// found the endBlock command.

					// pop the /end{problem command off the queue
					UEFCommand nextCommand = uefCommandQueue.poll();

					// set the end of the block position, for the SourceIF.
					endSource = nextCommand.getEndPosition();

					// get the file content from beginning of '/begin{block}'
					// to the end of '/end{block}'.
					content = uefCharHandler.getContent(startSource, endSource);

					// Create the source object for the block environment.
					SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
					source.setStartLine(uefCharHandler.getLineNumber(startSource));
					source.setLastLine(uefCharHandler.getLineNumber(endSource));
					source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
					source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
					source.addText(content);

					// create the actual block object.
					BlockIF block = examFactory.newBlock(name, source);

					// Add each reference found within this block to our list of
					// references
					// to later declareUses relationships for.
					// Since our BlockIF object is created, we can add to the global
					// list.
					this.uefReferenceHandler.mapReferences(block);

					// return our block object now.
					return block;
				}
				default:
				{
					// Reached a command that shouldn't be found within a block
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within block environment!", exceptionSource);
				}
			}
		}

		// file ended without endBlock being found.

		// set the end source to the end of the \begin{block} command.
		endSource = command.getEndPosition();

		// Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(startSource), uefCharHandler.
				getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.getColumnNumber(endSource));

		// add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		// return the exception.
		throw new RexParseException("\\begin{block} without matching \\end{block}!", exceptionSource);
	}

	/**
	 * Process the document environment. This includes processing of block,
	 * figure, and problem environments.
	 * 
	 * @return An ExamIF representation of the commands and environments found
	 *         within. Does not set the exam Preamble.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	ExamIF processDocument() throws RexParseException, EOFException
	{
		// pop /begin{document} off the queue.
		UEFCommand command = uefCommandQueue.poll();

		// create the ExamIF.
		ExamIF exam = examFactory.newMasterExam();

		// Make sure the queue isn't empty before attempting to get the frontal
		// matter.
		if (!uefCommandQueue.isEmpty())
		{
			// get the start and end positions of the frontal matter.
			int startOfFrontalMatter = command.getEndPosition();
			int endOfFrontalMatter = uefCommandQueue.peek().getStartPosition();

			// get the frontmatter content from the underlying file.
			String content = uefCharHandler.getContent(startOfFrontalMatter, endOfFrontalMatter);

			// Create the source object for frontal matter
			SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
			source.setStartLine(uefCharHandler.getLineNumber(startOfFrontalMatter));
			source.setLastLine(uefCharHandler.getLineNumber(endOfFrontalMatter));
			source.setStartColumn(uefCharHandler.getColumnNumber(startOfFrontalMatter));
			source.setLastColumn(uefCharHandler.getColumnNumber(endOfFrontalMatter));
			source.addText(content);

			// set the frontal matter
			exam.setFrontMatter(source);
		}

		// process until either the queue is empty or we hit an endDocument
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case beginBlock:
				{
					// found the beginBlock command.

					// process the block environment and add it to the exam.
					exam.addElement(processBlock());
					break;
				}
				case beginFigure:
				{
					// found the beginFigure command.

					// process the figure environment and add it to the exam.
					exam.addElement(processFigure());
					break;
				}
				case beginProblem:
				{
					// found the beginProblem command.

					// process the problem environment and add it to the exam.
					exam.addElement(processProblem());
					break;
				}
				case endDocument:
				{
					// found the endDocument command.

					// pop /end{document} off the queue.
					uefCommandQueue.poll();

					// return our ExamIF.
					return exam;
				}
				default:
				{
					// Reached a command that shouldn't be found within a document
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within document environment!", exceptionSource);
				}
			}
		}

		// file ended without endDocument being found.

		// set the start source to beginning of the \begin{document} command.
		int startSource = command.getStartPosition();

		// set the end source to the end of the \begin{document} command.
		int endSource = command.getEndPosition();

		// Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(startSource), uefCharHandler.
				getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.getColumnNumber(endSource));

		// add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		// return the exception.
		throw new RexParseException("\\begin{document} without matching \\end{document}!", exceptionSource);
	}

	/**
	 * Process the figure environment. This includes processing of /ref commands
	 * and /label commands.
	 * 
	 * @return A FigureIF representation of the figure environment.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	FigureIF processFigure() throws RexParseException, EOFException
	{
		UEFCommand command = uefCommandQueue.poll();

		// String to be filled with the source content
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource = 0;

		// label for this figure.
		String label = null;

		// process until either the queue is empty or we hit an endFigure
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case label:
				{
					// found the label command.

					// get the label.
					label = processLabel();
					break;
				}
				case ref:
				{
					// found the ref command.

					// add the reference to our local list of refences to later
					// declareUses relationships for.
					this.uefReferenceHandler.addUnmappedReferences(processRef());
					break;
				}
				case endFigure:
				{
					// found the endFigure command.

					// use the end of the endFigure command as the end source.
					endSource = uefCommandQueue.peek().getEndPosition();

					// pop /end{figure} off the queue.
					uefCommandQueue.poll();

					// get the file content from beginning of '/begin{figure}'
					// to the end of '/end{figure}'.
					content = uefCharHandler.getContent(startSource, endSource);

					// Create the source object.
					SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
					source.setStartLine(uefCharHandler.getLineNumber(startSource));
					source.setLastLine(uefCharHandler.getLineNumber(endSource));
					source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
					source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
					source.addText(content);

					// create the figure object.
					FigureIF figure = examFactory.newFigure(label, source);

					// Add each reference found within this block to our list of
					// references
					// to later declareUses relationships for.
					// Since our FigureIF object is created, we can add to the
					// global list.
					this.uefReferenceHandler.mapReferences(figure);

					// return our FigureIF.
					return figure;
				}
				default:
				{
					// Reached a command that shouldn't be found within a figure
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within figure environment!", exceptionSource);
				}
			}
		}

		// file ended without endDocument being found.

		// set the end source to the end of the \begin{figure} command.
		endSource = command.getEndPosition();

		// Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(startSource), uefCharHandler.
				getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.getColumnNumber(endSource));

		// add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		// return the exception.
		throw new RexParseException("\\begin{figure} without matching \\end{figure}!", exceptionSource);
	}

	/**
	 * Process the problem environment and commands found within. This includes
	 * processing of answer environments, /label commands, and /ref commands.
	 * 
	 * @return A ProblemIF representation of the problem environment.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 */
	ProblemIF processProblem() throws RexParseException, EOFException
	{
		// pop this command off the queue.
		UEFCommand command = uefCommandQueue.poll();

		// get the topic.
		String topic = command.getArgument(0);

		// get the difficulty.
		String difficulty = command.getArgument(1);

		// get required block
		String optionalArgument = command.getOptionalArgument();
		if (optionalArgument != null)
		{
			// split the argument by the equals found within.
			String split[] = optionalArgument.split("=");

			// make sure it was of correct length.
			if (split.length == 2)
			{
				if (split[0].equals("require"))
				{
					// check for typoed requires
					this.uefReferenceHandler.addUnmappedReferences(split[1]);
				}
				else if (split[0].equals("requires"))
				{
					// check for correct requires
					this.uefReferenceHandler.addUnmappedReferences(split[1]);
				}
			}
		}

		// String to be filled with the source content
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getEndPosition();// use the end of this
		int endSource = 0;

		// Ignore whitespaces and lines at the beginning of the question,
		// because latex
		// does.
		while (uefCharHandler.read(startSource) == '\n' || uefCharHandler.read(startSource) == ' ' || uefCharHandler.read(startSource)
																									  == '\t')
		{
			startSource++;
		}

		// The possible problem label.
		String label = null;

		// Array to hold answers in.
		AnswerIF answers[] = null;

		//boolean to make sure we only ever find one answers environment within a problem.
		boolean foundAnswerEnvironment = false;

		// process until either the queue is empty or we hit an endProblem
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case beginAnswers:
				{
					// found the beginAnswers command.

					// make sure we didn't previously process an answers environment.
					if (foundAnswerEnvironment == true)
					{
						// previously found an answers environment, so throw an exception.

						// Set the start source to beginning of the second \begin{answers} command.
						startSource = uefCommandQueue.peek().getStartPosition();

						// set the end source to the end of the \begin{answers} command.
						endSource = uefCommandQueue.peek().getEndPosition();

						// Fill out the source.
						SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
								startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
								getColumnNumber(endSource));

						// add the file text to the source.
						exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

						// return the exception.
						throw new RexParseException("More than one answers environment found within a problem environment!", exceptionSource);
					}

					//set the answers environment true, bc we found one.
					foundAnswerEnvironment = true;

					// use the beginning of the other command as the end source.
					endSource = uefCommandQueue.peek().getStartPosition();

					// Ignore whitespaces and lines at the end of question because
					// latex does.
					while (uefCharHandler.read(endSource - 1) == '\n' || uefCharHandler.read(endSource - 1) == ' ' || uefCharHandler.read(
							endSource - 1) == '\t')
					{
						endSource--;
					}

					// get answers for the problem.
					answers = processAnswers();

					// make sure answers were actually found.
					if (answers == null)
					{
						// No answers were found...

						// Fill out the source.
						SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
								startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
								getColumnNumber(endSource));

						// add the file text to the source.
						exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

						// return the exception.
						throw new RexParseException("No answers found within a problem environment.", exceptionSource);
					}

					// continue.
					break;
				}
				case label:
				{
					// found the label command.

					// get the label for the command.
					label = processLabel();
					break;
				}
				case ref:
				{
					// found the ref command.

					// add the reference to our local list of refences to later
					// declareUses relationships for.
					this.uefReferenceHandler.addUnmappedReferences(processRef());
					break;
				}
				case endProblem:
				{
					// found the endProblem command.

					// pop the /end{problem command off the queue
					uefCommandQueue.poll();

					// get the file content from beginning of '/begin{block}'
					// to the end of '/end{block}'.
					content = uefCharHandler.getContent(startSource, endSource);

					// Create the source object
					SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
					source.setStartLine(uefCharHandler.getLineNumber(startSource));
					source.setLastLine(uefCharHandler.getLineNumber(endSource));
					source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
					source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
					source.addText(content);

					// create the problem.
					ProblemIF problem = examFactory.newProblem(topic, label, source, answers);

					// add the difficulty.
					problem.setDifficulty(Double.valueOf(difficulty));

					// Add each reference found within this block to our list of
					// references
					// to later declareUses relationships for.
					// Since our ProblemIF object is created, we can add to the
					// global list.
					this.uefReferenceHandler.mapReferences(problem);

					// return our problem.
					return problem;
				}
				default:
				{
					// Reached a command that shouldn't be found within a problem
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within problem environment!", exceptionSource);
				}
			}
		}

		// file ended without endProblem being found.

		// set the end source to the end of the \begin{problem} command.
		endSource = command.getEndPosition();

		// Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(startSource), uefCharHandler.
				getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.getColumnNumber(endSource));

		// add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		// return the exception.
		throw new RexParseException("\\begin{problem} without matching \\end{problem}!", exceptionSource);

	}

	/**
	 * Process a \documentclass{} command. Simply checks whether it is of exam
	 * type and returns true if it is.
	 * 
	 * @return true if documentclass is of type exam. False otherwise.
	 */
	boolean processDocumentclass()
	{
		// pop the command off the queue.
		UEFCommand command = uefCommandQueue.poll();

		// get the documentclass type.
		String classType = command.getArgument(0);

		// check it's type.
		if (classType.equals("exam"))
		{
			// is of type exam.
			return true;
		}
		else
		{
			// is not of type exam.
			return false;
		}
	}

	/**
	 * Process a \label command. Simply returns a String with the label name.
	 * 
	 * @return the label name as a String.
	 */
	String processLabel()
	{
		// pop the command off the queue.
		UEFCommand command = uefCommandQueue.poll();

		// return the name.
		return command.getArgument(0);
	}

	/**
	 * Process a \ref command. Simply returns a String with the reference name.
	 * 
	 * @return the reference name as a String.
	 */
	String processRef()
	{
		// pop the command off the queue.
		UEFCommand command = uefCommandQueue.poll();

		// return the name.
		return command.getArgument(0);
	}

	/**
	 * Processes the outermost environment of the file. This includes processing
	 * of document enviroments, and the documentclass command.
	 * 
	 * @return A complete ExamIF representation of the underlying file.
	 * 
	 * @throws RexParseException
	 *             if there is a problem with the correctness of the file.
	 * 
	 * @throws RexException
	 *             if there are problems with the USES relationships.
	 * 
	 * @throws EOFException
	 *             if we are somehow out of bounds when reading the underlying
	 *             file to fill out the SourceIF. This should NEVER occur.
	 * 
	 */
	ExamIF process() throws RexParseException, RexException, EOFException
	{
		// boolean to hold the type of documentclass
		boolean isExamDocumentclass = false;

		// variables to hold the start and end of the preamble to store.
		int startOfPreamble = 0;
		int endOfPreamble = 0;

		// process until either the queue is empty or we hit an beginDocument
		// command.
		while (!uefCommandQueue.isEmpty())
		{
			// check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				// found the documentclass command.

				case documentclass:
				{
					// set the start of the preamble to directly after the
					// documentclass command.
					startOfPreamble = uefCommandQueue.peek().getEndPosition();

					// get the type of document class.
					isExamDocumentclass = processDocumentclass();
					break;
				}
				case beginDocument:
				{
					// found the endProblem command.

					// make sure the document class is of type exam.
					if (!isExamDocumentclass)
					{
						// document class is the wrong type.
						throw new RexException(uefCommandQueue.peek().getType() + " found when the documentclass is not of type 'exam'!");
					}

					// retrieve the preamble.
					endOfPreamble = uefCommandQueue.peek().getStartPosition();
					String content = uefCharHandler.getContent(startOfPreamble, endOfPreamble);

					// process to make an ExamIF.
					// still need to add the preamble after this.
					ExamIF exam = processDocument();

					// Create the source object for preamble.
					SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
					source.setStartLine(uefCharHandler.getLineNumber(startOfPreamble));
					source.setLastLine(uefCharHandler.getLineNumber(endOfPreamble));
					source.setStartColumn(uefCharHandler.getColumnNumber(startOfPreamble));
					source.setLastColumn(uefCharHandler.getColumnNumber(endOfPreamble));
					source.addText(content);

					// set the preamble.
					exam.setPreamble(source);

					// setting USES relationships finally.
					this.uefReferenceHandler.declareUses(exam);

					// ... almost done...

					// return the ExamIF
					return exam;
				}
				default:
				{
					// Reached a command that shouldn't be found in the main
					// environment.

					// Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					// add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					// return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found outside of document environment!",
												exceptionSource);
				}
			}
		}

		// file ended without beginDocument being found.

		// return the exception. There is no source, because we never even found
		// a document environment.
		throw new RexParseException("End of document before \begin{document} found!", null);
	}
}
