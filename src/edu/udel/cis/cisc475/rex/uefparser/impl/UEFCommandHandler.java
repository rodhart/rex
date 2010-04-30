package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
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
	 * Underlying file that was read from. Used to retrieve text from
	 * lines in the case that errors occur and used to fill out SourceIF
	 * objects needed by the ExamIF module.
	 */
	private UEFCharHandler uefCharHandler;
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
	 * Map of all references in the UEF file. The key is the reference name and
	 * the ExamElement all elements that have that ref
	 */
	private Map<String, List<ExamElementIF>> references;
	/**
	 * This is special List for references by answers. This List is needed for
	 * two reasons: 1. AnswerIF doesn't implement ExamElementIF. 2. If an answer
	 * has a ref then the whole problem uses that element.
	 */
	private List<String> answerReferences;

	/**
	 * Constructor for the class. Takes a reference to the underlying
	 * UEFCharHandler that reads the file.
	 * 
	 * @param uefCharHandler
	 *            the underlying UEFCharHandler.
	 */
	UEFCommandHandler(UEFCharHandler uefCharHandler)
	{
		//initialize object fields.
		this.uefCharHandler = uefCharHandler;
		this.uefCommandQueue = new LinkedList<UEFCommand>();
		this.sourceFactory = new SourceFactory();
		this.examFactory = new ExamFactory();
		this.references = new HashMap<String, List<ExamElementIF>>();
		this.answerReferences = new ArrayList<String>();
	}

	/**
	 * Adds a command into the command queue.
	 * 
	 * @param uefCommand
	 *            the command to add to the queue.
	 */
	void add(UEFCommand uefCommand)
	{
		//add the internal command to the queue.
		this.uefCommandQueue.add(uefCommand);
	}

	/**
	 * Finds the next command that matches a command found in the passed in array
	 * and returns that command.
	 * @param types an array of commands to match.
	 * @return the command in the queue that was matched or null if no commands were matched.
	 */
	private UEFCommand findMatchingCommand(Types types[])
	{
		//Iterate through all command until a matchin command is found.
		for (Iterator<UEFCommand> iter = uefCommandQueue.iterator(); iter.hasNext();)
		{
			//get the command located at the current iterator.
			UEFCommand peekedCommand = iter.next();

			//for each command we need to check in the array.
			for (int i = 0; i < types.length; i++)
			{
				//check the current command in the queue with a piece of the array.
				if (peekedCommand.getType() == types[i])
				{
					//matched command so return it.
					return peekedCommand;
				}
			}
		}

		//no matched commands of the types in the array so return null.
		return null;
	}

	/**
	 * Checks whether the argument contains the String: 'fixed'.
	 * 
	 * @param argument
	 *            the argument to check.
	 * @return Return true or false depending on whether the argument contains 'fixed'.
	 * Return false if the argument is null.
	 */
	private boolean isFixed(String argument)
	{
		//make sure the argument isn't null before checking.
		if (argument != null)
		{
			//check the argument and return the result.
			return argument.contains("fixed");
		}
		else
		{
			//false if the argument is null.
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
		//make sure the argument isn't null before checking.
		if (argument != null)
		{
			//check the argument and return the result.
			return argument.contains("correct");
		}
		else
		{
			//false if the argument is null.
			return false;
		}
	}

	/**
	 * Process an \answer command.

	 * @param index index to add in the case that this answer happens to be a fixed answer.
	 *
	 * @return a AnswerIF or FixedAnswerIF representation of the processed answer.
	 *
	 * @throws RexParseException if there is problem with the correctness of the file.
	 *
	 * @throws EOFException if we are somehow out of bounds when reading the underlying file
	 * to fill out the SourceIF. This should NEVER occur.
	 */
	AnswerIF processAnswer(int index) throws RexParseException, EOFException
	{
		//grab the /answer command from the queue.
		UEFCommand command = uefCommandQueue.poll();

		//get the optional argument
		String optionalArgument = command.getOptionalArgument();

		//get whether this is a fixed answer.
		boolean fixed = isFixed(optionalArgument);

		//get whether this is a correct answer.
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

		//do the actual peeking.
		UEFCommand peekedCommand = findMatchingCommand(type);

		//We should always find either /answer or /end{answer}. Something is wrong with the file if we do not.
		if (peekedCommand == null)
		{
			// should always be either endAnswers or answer command

			//create the source object.
			SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(command.
					getStartPosition()), uefCharHandler.getColumnNumber(command.getStartPosition()), uefCharHandler.getLineNumber(command.
					getEndPosition()), uefCharHandler.getColumnNumber(command.getEndPosition()));

			//add the source text to the object.
			exceptionSource.addText(uefCharHandler.getContent(command.getStartPosition(), command.getEndPosition()));

			//throw the exception.
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

		//check whether this is a fixed answer or not.
		if (!fixed)
		{
			//return the answer.
			return examFactory.newAnswer(correct, source);
		}
		else
		{
			//return the answer.
			return examFactory.newFixedAnswer(correct, index, source);
		}
	}

	/**
	 * Process the answers environment and command within.
	 * This includes processing of /ref commands and /answer commands.
	 *
	 * @return an array of processed AnswerIF that may be
	 * either AnswerIF or FixedAnswerIF.
	 *
	 * @throws RexParseException if there is problem with the correctness of the file.
	 *
	 * @throws EOFException if we are somehow out of bounds when reading the underlying file
	 * to fill out the SourceIF. This should NEVER occur.
	 */
	AnswerIF[] processAnswers() throws RexParseException, EOFException
	{
		// pop off the /begin{answers} command
		UEFCommand command = uefCommandQueue.poll();

		//set the answer index. Needed, because fixed answers must occur in a certain spot.
		int index = 0;

		//create a list of answers to add the answers we process to.
		List<AnswerIF> answersList = new ArrayList<AnswerIF>();

		//process until either the queue is empty or we hit an endAnswers command.
		while (!uefCommandQueue.isEmpty())
		{
			//check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case endAnswers:
				{
					//found endAnswers command.

					// pop the /end{answers} command off the queue
					uefCommandQueue.poll();

					//return the list of AnswerIF and FixedAnswerIF as an array.
					return answersList.toArray(new AnswerIF[0]);
				}
				case answer:
				{
					//found answer command.

					//process the answer command and add it to the list.
					answersList.add(processAnswer(index));

					//increment the index for fixed answers.
					index++;
					break;
				}
				case ref:
				{
					//add the reference to list of references so we can
					// later add USES relations for. We need to add these
					// answer references for the particular problem we are
					// creating. But, it can't be created until after we
					// get every answer. :-(
					this.answerReferences.add(processRef());
					break;
				}
				default:
				{
					//Reached a command that shouldn't be found within a answers environment.

					//Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					//add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					//return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within answers environment!", exceptionSource);
				}
			}
		}

		//file ended without endBlock being found.

		//set the start source to beginning of the \begin{answers} command.
		int startSource = command.getStartPosition();

		//set the end source to the end of the \begin{answers} command.
		int endSource = command.getEndPosition();

		//Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
				startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
				getColumnNumber(endSource));

		//add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		//return the exception.
		throw new RexParseException("\\begin{answers} without matching \\end{answers}!", exceptionSource);
	}

	/**
	 * Process a block environment.
	 * This includes processing of /ref commands and /answer commands.
	 *
	 * @return A BlockIF representation of the block environment.
	 *
	 * @throws RexParseException if there is problem with the correctness of the file.
	 *
	 * @throws EOFException if we are somehow out of bounds when reading the underlying file
	 * to fill out the SourceIF. This should NEVER occur.
	 */
	BlockIF processBlock() throws RexParseException, EOFException
	{
		// pop off the /begin{block} command
		UEFCommand command = uefCommandQueue.poll();

		//get the name of the block.
		String name = command.getArgument(0);

		// String to be filled with the source content.
		String content;

		// List of references inside the block.
		List<String> refs = new ArrayList<String>();

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource = 0;

		//process until either the queue is empty or we hit an endAnswers command.
		while (!uefCommandQueue.isEmpty())
		{
			//check the command type.
			switch (uefCommandQueue.peek().getType())
			{
				case endBlock:
				{
					//found the endBlock command.

					// pop the /end{problem command off the queue
					UEFCommand nextCommand = uefCommandQueue.poll();

					//set the end of the block position, for the SourceIF.
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

					//create the actual block object.
					BlockIF block = examFactory.newBlock(name, source);

					//Add each reference found within this block to our list of references
					//to later declareUses relationships for.
					//Since our BlockIF object is created, we can add global list.
					if (refs.size() != 0)
					{
						Iterator<String> i = refs.iterator();
						while (i.hasNext())
						{
							String r = i.next();
							if (this.references.containsKey(r))
							{
								this.references.get(r).add(block);
							}
							else
							{
								List<ExamElementIF> list = new ArrayList<ExamElementIF>();
								list.add(block);
								this.references.put(r, list);
							}
						}
					}

					//return our block object now.
					return block;
				}
				case ref:
				{
					//add the reference to our local list of refences to later
					//declareUses relationships for.
					refs.add(processRef());
					break;
				}
				default:
				{
					//Reached a command that shouldn't be found within a answers environment.

					//Fill out the source.
					SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					//add the file text to the source.
					exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					//return the exception.
					throw new RexParseException(uefCommandQueue.peek().getType() + " found within block environment!", exceptionSource);
				}
			}
		}

		//file ended without endBlock being found.

		//set the end source to the end of the \begin{block} command.
		endSource = command.getEndPosition();

		//Fill out the source.
		SourceIF exceptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
				startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
				getColumnNumber(endSource));

		//add the file text to the source.
		exceptionSource.addText(uefCharHandler.getContent(startSource, endSource));

		//return the exception.
		throw new RexParseException("\\begin{block} without matching \\end{block}!", exceptionSource);
	}

	/**
	 * Process the document environment.
	 */
	ExamIF processDocument() throws RexParseException, EOFException
	{
		// pull /begin{document} off the queue
		UEFCommand command = uefCommandQueue.poll();

		// create the ExamIF
		ExamIF exam = examFactory.newMasterExam();

		// get the frontal matter
		if (!uefCommandQueue.isEmpty())
		{
			// get the start and end positions of the frontal matter
			int startOfFrontalMatter = command.getEndPosition();
			int endOfFrontalMatter = uefCommandQueue.peek().getStartPosition();

			String content = uefCharHandler.getContent(startOfFrontalMatter, endOfFrontalMatter);

			// Create the source object for frontal matter
			SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
			source.setStartLine(uefCharHandler.getLineNumber(startOfFrontalMatter));
			source.setLastLine(uefCharHandler.getLineNumber(endOfFrontalMatter));
			source.setStartColumn(uefCharHandler.getColumnNumber(startOfFrontalMatter));
			source.setLastColumn(uefCharHandler.getColumnNumber(endOfFrontalMatter));
			source.addText(content);

			// System.out.println(content);

			// set the frontal matter
			exam.setFrontMatter(source);
		}

		// process commands within the document environment
		while (!uefCommandQueue.isEmpty())
		{
			switch (uefCommandQueue.peek().getType())
			{
				case beginBlock:
				{
					exam.addElement(processBlock());
					break;
				}
				case beginFigure:
				{
					exam.addElement(processFigure());
					break;
				}
				case beginProblem:
				{
					exam.addElement(processProblem());
					break;
				}
				case endDocument:
				{
					// pull /end{document} off the queue.
					uefCommandQueue.poll();
					return exam;
				}
				default:
				{
					SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					execptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					throw new RexParseException(uefCommandQueue.peek().getType() + " found within document environment!", execptionSource);
				}
			}
		}

		throw new RexParseException("End of file reached before \\end{document} found!", null);
	}

	/**
	 * Process the figure environment.
	 */
	FigureIF processFigure() throws RexParseException, EOFException
	{
		UEFCommand command = uefCommandQueue.poll();

		// String to be filled with the source content
		String content;

		// List of references by this figure
		List<String> refs = new ArrayList<String>();

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource = 0;

		String label = null;

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done)
		{
			switch (uefCommandQueue.peek().getType())
			{
				case label:
				{
					label = processLabel();
					break;
				}
				case ref:
				{
					refs.add(processRef());
					break;
				}
				case endFigure:
				{
					// use the end of the other command as the end source
					endSource = uefCommandQueue.peek().getEndPosition();
					// pull /end{figure} off the queue.
					uefCommandQueue.poll();
					done = true;
					break;
				}
				default:
				{
					SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					execptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					throw new RexParseException(uefCommandQueue.peek().getType() + " found within figure environment!", execptionSource);
				}
			}
		}

		// get the file content from beginning of '/begin{figure}'
		// to the end of '/end{figure}'.
		content = uefCharHandler.getContent(startSource, endSource);

		// Create the source object
		SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
		source.setStartLine(uefCharHandler.getLineNumber(startSource));
		source.setLastLine(uefCharHandler.getLineNumber(endSource));
		source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
		source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
		source.addText(content);

		FigureIF figure = examFactory.newFigure(label, source);

		if (refs.size() != 0)
		{
			Iterator<String> i = refs.iterator();
			while (i.hasNext())
			{
				String r = i.next();
				if (this.references.containsKey(r))
				{
					this.references.get(r).add(figure);
				}
				else
				{
					List<ExamElementIF> list = new ArrayList<ExamElementIF>();
					list.add(figure);
					this.references.put(r, list);
				}
			}
		}

		return figure;
	}

	/**
	 * Process the problem environment and commands found within.
	 */
	ProblemIF processProblem() throws RexParseException, EOFException
	{
		// pull this command off the stack
		UEFCommand command = uefCommandQueue.poll();

		String topic = command.getArgument(0);
		String difficulty = command.getArgument(1);

		// List of all references in the problem
		List<String> refs = new ArrayList<String>();

		// get required block
		String optionalArgument = command.getOptionalArgument();
		if (optionalArgument != null)
		{
			String split[] = optionalArgument.split("=");

			if (split.length == 2)
			{
				if (split[0].equals("require"))
				{
					// check for typoed requires
					refs.add(split[1]);
				}
				else if (split[0].equals("requires"))
				{
					// check for correct requires
					refs.add(split[1]);
				}
			}
		}

		// String to be filled with the source content
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getEndPosition();// use the end of this

		// Ignore whitespaces and lines at the beginning of the question,
		// because latex
		// does.
		while (uefCharHandler.read(startSource) == '\n' || uefCharHandler.read(startSource) == ' ' || uefCharHandler.read(startSource)
																									  == '\t')
		{
			startSource++;
		}

		// command
		int endSource = 0;

		String label = null;

		// Array to old answers in.
		AnswerIF answers[] = null;

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done)
		{
			switch (uefCommandQueue.peek().getType())
			{
				case beginAnswers:
				{
					// use the beginning of the other command as the end source
					endSource = uefCommandQueue.peek().getStartPosition();

					// Ignore whitespaces and lines at the end of question,
					// because
					// latex
					// does.
					while (uefCharHandler.read(endSource - 1) == '\n' || uefCharHandler.read(endSource - 1) == ' ' || uefCharHandler.read(
							endSource - 1) == '\t')
					{
						endSource--;
					}
					// get answers for the problem
					answers = processAnswers();
					if (answers == null)
					{
						SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
								startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
								getColumnNumber(endSource));

						execptionSource.addText(uefCharHandler.getContent(startSource, endSource));

						throw new RexParseException("No answers found within a problem environment.", execptionSource);
					}
					break;
				}
				case label:
				{
					label = processLabel();
					break;
				}
				case ref:
				{
					refs.add(processRef());
					break;
				}
				case endProblem:
				{
					done = true;
					// poll the /end{problem command off the queue
					uefCommandQueue.poll();
					break;
				}
				default:
				{
					SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(
							startSource), uefCharHandler.getColumnNumber(startSource), uefCharHandler.getLineNumber(endSource), uefCharHandler.
							getColumnNumber(endSource));

					execptionSource.addText(uefCharHandler.getContent(startSource, endSource));

					throw new RexParseException(uefCommandQueue.peek().getType() + " found within problem environment!", execptionSource);
				}
			}
		}
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

		ProblemIF problem = examFactory.newProblem(topic, label, source, answers);
		problem.setDifficulty(Double.valueOf(difficulty));

		// Add references from answers
		for (int i = 0; i < this.answerReferences.size(); i++)
		{
			refs.add(this.answerReferences.get(i));
		}

		this.answerReferences.clear();

		if (refs.size() != 0)
		{
			Iterator<String> i = refs.iterator();
			while (i.hasNext())
			{
				String r = i.next();
				if (this.references.containsKey(r))
				{
					this.references.get(r).add(problem);
				}
				else
				{
					List<ExamElementIF> list = new ArrayList<ExamElementIF>();
					list.add(problem);
					this.references.put(r, list);
				}
			}
		}
		return problem;
	}

	/**
	 * Process a \documentclass{} command.
	 */
	boolean processDocumentclass()
	{
		UEFCommand command = uefCommandQueue.poll();
		String classType = command.getArgument(0);
		if (classType.equals("exam"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Process a \label command.
	 */
	String processLabel()
	{
		UEFCommand command = uefCommandQueue.poll();
		return command.getArgument(0);
	}

	/**
	 * Process a \ref command.
	 */
	String processRef()
	{
		UEFCommand command = uefCommandQueue.poll();
		return command.getArgument(0);
	}

	/**
	 * Starts the processing of all commands in the command queue. Should be
	 * called by another class.
	 */
	ExamIF process() throws RexParseException, RexException, EOFException
	{
		boolean isExamDocumentclass = false;
		int startOfPreamble = 0;
		int endOfPreamble = 0;
		while (!uefCommandQueue.isEmpty())
		{
			switch (uefCommandQueue.peek().getType())
			{
				case beginDocument:
				{
					if (!isExamDocumentclass)
					{
						throw new RexException(uefCommandQueue.peek().getType() + " found when the documentclass is not of type 'exam'!");
					}

					// retrieve the preamble
					endOfPreamble = uefCommandQueue.peek().getStartPosition();
					String content = uefCharHandler.getContent(startOfPreamble, endOfPreamble);

					// process to make an ExamIF
					ExamIF exam = processDocument();

					// Create the source object for preamble
					SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
					source.setStartLine(uefCharHandler.getLineNumber(startOfPreamble));
					source.setLastLine(uefCharHandler.getLineNumber(endOfPreamble));
					source.setStartColumn(uefCharHandler.getColumnNumber(startOfPreamble));
					source.setLastColumn(uefCharHandler.getColumnNumber(endOfPreamble));
					source.addText(content);

					// set the preamble
					exam.setPreamble(source);

					// setting uses relationships
					Iterator<String> i = this.references.keySet().iterator();
					while (i.hasNext())
					{
						String label = i.next();
						List<ExamElementIF> e = this.references.get(label);
						ExamElementIF usedElement = null;

						usedElement = exam.elementWithLabel(label);

						if (usedElement == null)
						{
							throw new RexParseException("Element with label " + label + " not found within file.", null);
						}
						else
						{
							for (int j = 0; j < e.size(); j++)
							{
								exam.declareUse(e.get(j), usedElement);
							}
						}
					}

					// return the ExamIF
					return exam;
				}
				case documentclass:
				{
					startOfPreamble = uefCommandQueue.peek().getEndPosition();
					isExamDocumentclass = processDocumentclass();
					break;
				}
				default:
				{
					SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.
							peek().getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.
							getLineNumber(uefCommandQueue.peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().
							getEndPosition()));

					execptionSource.addText(uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().
							getEndPosition()));

					throw new RexParseException(uefCommandQueue.peek().getType() + " found outside of document environment!",
												execptionSource);
				}
			}
		}
		SourceIF execptionSource = sourceFactory.newSource(uefCharHandler.getFileName(), uefCharHandler.getLineNumber(uefCommandQueue.peek().
				getStartPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getStartPosition()), uefCharHandler.getLineNumber(uefCommandQueue.
				peek().getEndPosition()), uefCharHandler.getColumnNumber(uefCommandQueue.peek().getEndPosition()));

		execptionSource.addText(
				uefCharHandler.getContent(uefCommandQueue.peek().getStartPosition(), uefCommandQueue.peek().getEndPosition()));

		throw new RexParseException("End of document before \begin{document} found!", execptionSource);
	}
}
