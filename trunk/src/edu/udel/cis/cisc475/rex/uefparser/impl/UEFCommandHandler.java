package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import java.io.EOFException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the processing of commands after they are parsed from a file.
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
	 * Underlying file that was read from.
	 * Simply used to retrieve text from lines
	 * in the case that errors occur.
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
	 * The list of states we have.
	 */
	enum States
	{

		answers, block, document, figure, problem
	}

	/**
	 * Constructor for the class. Takes a reference to the underlying UEFCharHandler that
	 * reads the file.
	 * @param uefCharHandler the underlying UEFCharHandler.
	 */
	UEFCommandHandler(UEFCharHandler uefCharHandler)
	{
		this.uefCharHandler = uefCharHandler;
		this.uefCommandQueue = new LinkedList<UEFCommand>();
		this.sourceFactory = new SourceFactory();
		this.examFactory = new ExamFactory();
	}

	/**
	 * Adds a command into the command queue.
	 * @param uefCommand the command to add.
	 */
	void add(UEFCommand uefCommand)
	{
		uefCommandQueue.add(uefCommand);
	}

	private UEFCommand findMatchingCommand(Types types[])
	{
		for (Iterator<UEFCommand> iter = uefCommandQueue.iterator(); iter.hasNext();)
		{
			UEFCommand peekedCommand = iter.next();
			for (int i = 0; i < types.length; i++)
			{
				if (peekedCommand.getType() == types[i])
				{
					return peekedCommand;
				}
			}
		}
		return null;
	}

	private boolean isFixed(String argument)
	{

		if (argument != null)
		{
			return argument.contains("fixed");
		}
		else
		{
			return false;
		}
	}

	private boolean isCorrect(String argument)
	{
		if (argument != null)
		{
			return argument.contains("correct");
		}
		return false;
	}

	/**
	 * Process an \answer command. Increments the answer index.
	 * Adds the new answer to the answers list.
	 */
	AnswerIF processAnswer(int index) throws EOFException, Exception
	{
		UEFCommand command = uefCommandQueue.poll();

		String optionalArgument = command.getOptionalArgument();
		boolean fixed = isFixed(optionalArgument);
		boolean correct = isCorrect(optionalArgument);

		//String to be filled with the source content
		String content;

		//Variables to hold the beginning and end of the source
		int startSource = command.startPosition;
		int endSource;

		Types type[] = new Types[2];
		type[0] = Types.answer;
		type[1] = Types.endAnswers;
		UEFCommand peekedCommand = findMatchingCommand(type);

		if (peekedCommand == null)
		{
			throw new Exception();
		}

		//set the end of the source to the position before
		//the beginning of the next command.
		endSource = peekedCommand.getStartPosition() - 1;

		//get the file content from beginning of '/answer'
		//to beginning of next command .
		content = uefCharHandler.getContent(startSource, endSource);

		//Create the source object
		SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
		source.setStartLine(uefCharHandler.getLineNumber(startSource));
		source.setLastLine(uefCharHandler.getLineNumber(endSource));
		source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
		source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
		source.addText(content);
		if (!fixed)
		{
			//System.out.println(content);
			return examFactory.newAnswer(correct, source);
		}
		else
		{
			//System.out.println(content);
			return examFactory.newFixedAnswer(correct, index, source);
		}
	}

	/**
	 * Process a \begin{answers} command.
	 */
	AnswerIF[] processBeginAnswers() throws Exception
	{
		//pop off the /begin{answers} command
		uefCommandQueue.poll();
		int index = 0;
		List<AnswerIF> answersList = new ArrayList<AnswerIF>();
		while (uefCommandQueue.peek().getType() != Types.endAnswers)
		{
			if (uefCommandQueue.peek().getType() == Types.answer)
			{
				answersList.add(processAnswer(index));
				index++;
			}
			else
			{
				throw new Exception();
			}
		}
		//poll the /end{answers} command off the queue
		uefCommandQueue.poll();
		return answersList.toArray(new AnswerIF[0]);
	}

	/**
	 * Process a \begin{block} command.
	 */
	void processBeginBlock() throws Exception
	{
		UEFCommand command = uefCommandQueue.poll();

		String name = command.getArgument(0);

		//String to be filled with the source content
		String content;

		//Variables to hold the beginning and end of the source
		int startSource = command.startPosition;
		int endSource;

		Types type[] = new Types[1];
		type[0] = Types.endBlock;

		UEFCommand peekedCommand = uefCommandQueue.poll();

		if (peekedCommand != null && peekedCommand.getType() == Types.endBlock)
		{
			//get the file content from beginning of '/begin{block}'
			//to the end of '/end{block}'.
			endSource = peekedCommand.getEndPosition();
			content = uefCharHandler.getContent(startSource, endSource);

			//Create the source object
			SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
			source.setStartLine(uefCharHandler.getLineNumber(startSource));
			source.setLastLine(uefCharHandler.getLineNumber(endSource));
			source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
			source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
			source.addText(content);

			//BlockIF newBlock(String topic, String label, SourceIF text);
			//System.out.println(content);
		}
		else
		{
			throw new Exception();
		}
	}

	/**
	 * Process a \begin{document} command.
	 */
	void processBeginDocument()
	{
		uefCommandQueue.poll();
	}

	/**
	 * Process a \begin{figure} command.
	 */
	void processBeginFigure() throws EOFException, Exception
	{
		UEFCommand command = uefCommandQueue.poll();

		//String to be filled with the source content
		String content;

		//Variables to hold the beginning and end of the source
		int startSource = command.startPosition;
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
				case endFigure:
				{
					//use the end of the other command as the end source
					endSource = uefCommandQueue.peek().getEndPosition();
					processEndFigure();
					done = true;
					break;
				}
				default:
				{
					System.err.println("Error: " + uefCommandQueue.peek().getType()
									   + " found within figure environment!");
					System.exit(-1);
					break;
				}
			}
		}

		//get the file content from beginning of '/begin{figure}'
		//to the end of '/end{figure}'.
		content = uefCharHandler.getContent(startSource, endSource);

		//Create the source object
		SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
		source.setStartLine(uefCharHandler.getLineNumber(startSource));
		source.setLastLine(uefCharHandler.getLineNumber(endSource));
		source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
		source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
		source.addText(content);

		//create the object
		examFactory.newFigure(label, source);

		//System.out.println(content)
	}

	/**
	 * Process a \begin{problem} command. Creates a new answer
	 * list for all answers in the problem. Resets the index
	 * for the answers in the problem. Pushes the problem state.
	 */
	ProblemIF processBeginProblem() throws EOFException, Exception
	{
		//pull this command off the stack
		UEFCommand command = uefCommandQueue.poll();

		String topic = command.getArgument(0);

		//String to be filled with the source content
		String content;

		//Variables to hold the beginning and end of the source
		int startSource = command.getEndPosition();//use the end of this command
		int endSource = 0;

		String label = null;

		//Array to old answers in.
		AnswerIF answers[] = null;

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done)
		{
			switch (uefCommandQueue.peek().getType())
			{
				case beginAnswers:
				{
					//use the beginning of the other command as the end source
					endSource = uefCommandQueue.peek().getStartPosition();

					//get answers for the problem
					answers = processBeginAnswers();
					break;
				}
				case label:
				{
					label = processLabel();
					break;
				}
				case endProblem:
				{
					done = true;
					//poll the /end{problem command off the queue
					uefCommandQueue.poll();
					break;
				}
				default:
				{
					System.err.println("Error: " + uefCommandQueue.peek().getType()
									   + " found within problem environment!");
					System.exit(-1);
					break;
				}
			}
		}

		if (answers != null)
		{
			//get the file content from beginning of '/begin{block}'
			//to the end of '/end{block}'.
			content = uefCharHandler.getContent(startSource, endSource);

			//Create the source object
			SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
			source.setStartLine(uefCharHandler.getLineNumber(startSource));
			source.setLastLine(uefCharHandler.getLineNumber(endSource));
			source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
			source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
			source.addText(content);
			return examFactory.newProblem(topic, label, source, answers);
		}
		else
		{
			throw new Exception();
		}

	}

	/**
	 * Process a \documentclass{} command.
	 */
	void processDocumentclass()
	{
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{document} command.
	 */
	void processEndDocument()
	{
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{figure} command.
	 */
	void processEndFigure()
	{
		uefCommandQueue.poll();
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
	 * Starts the processing of all commands in the command queue.
	 * Should be called by another class.
	 */
	void process() throws EOFException, Exception
	{
		while (!uefCommandQueue.isEmpty())
		{
			switch (uefCommandQueue.peek().getType())
			{
				case beginAnswers:
					processBeginAnswers();
					break;
				case beginBlock:
					processBeginBlock();
					break;
				case beginDocument:
					processBeginDocument();
					break;
				case beginFigure:
					processBeginFigure();
					break;
				case beginProblem:
					processBeginProblem();
					break;
				case documentclass:
					processDocumentclass();
					break;
				case endDocument:
					processEndDocument();
					break;
				default:
					System.err.println("Error: " + uefCommandQueue.peek().getType()
									   + " found within figure environment!");
					System.exit(-1);
					break;
			}
		}
	}
}
