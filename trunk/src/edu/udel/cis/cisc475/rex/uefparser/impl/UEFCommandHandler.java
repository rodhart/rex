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
import java.util.Stack;

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
	 * Stack of current states in the file.
	 */
	private Stack<States> uefStateStack;
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
	 * The current amount of answers within a particular problem.
	 */
	private int answerAmount;
	/**
	 * List of answers for the current problem.
	 */
	private List<AnswerIF> answersList;

	/**
	 * Constructor for the class. Takes a reference to the underlying UEFCharHandler that
	 * reads the file.
	 * @param uefCharHandler the underlying UEFCharHandler.
	 */
	UEFCommandHandler(UEFCharHandler uefCharHandler)
	{
		this.uefCharHandler = uefCharHandler;
		this.uefCommandQueue = new LinkedList<UEFCommand>();
		this.uefStateStack = new Stack<States>();
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
	void processAnswer() throws EOFException, Exception
	{
		if (uefStateStack.peek() == States.answers)
		{
			//set our answer index for the current problem
			int answerIndex = answerAmount;

			//increment the amount of answers for this problem
			this.answerAmount++;

			UEFCommand command = uefCommandQueue.poll();

			String optionalArgument = command.getOptionalArgument();
			boolean fixed = isFixed(optionalArgument);
			boolean correct = isCorrect(optionalArgument);

			//String to be filled with the source content
			String content;

			//Variables to hold the beginning and end of the source
			int startSource = command.startPosition;
			int endSource;

			UEFCommand peekedCommand = findMatchingCommand(new Types[]
					{
						Types.answer, Types.endAnswers
					});

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
				answersList.add(examFactory.newAnswer(correct, source));
			}
			else
			{
				answersList.add(examFactory.newFixedAnswer(correct, answerIndex - 1, source));
			}
			//System.out.println("index: " + answerIndex);
			//System.out.println("fixed = " + fixed);
			//System.out.println("correct = " + correct);
			//System.out.println();
			//System.out.println(content);
		}
		else
		{
			throw new Exception();
		}
	}

	/**
	 * Process a \begin{answers} command.
	 */
	void processBeginAnswers()
	{
		if (uefStateStack.peek() == States.problem)
		{
			uefStateStack.push(States.answers);
			uefCommandQueue.poll();
		}
		else
		{
			//error
			System.exit(-1);
		}
	}

	/**
	 * Process a \begin{block} command.
	 */
	void processBeginBlock() throws Exception
	{
		uefStateStack.push(States.block);

		UEFCommand command = uefCommandQueue.poll();

		String name = command.getArgument(0);

		//String to be filled with the source content
		String content;

		//Variables to hold the beginning and end of the source
		int startSource = command.startPosition;
		int endSource;

		UEFCommand peekedCommand = findMatchingCommand(new Types[]
				{
					Types.endBlock
				});

		if (peekedCommand == null)
		{
			throw new Exception();
		}

		endSource = peekedCommand.getEndPosition();

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

		//BlockIF newBlock(String topic, String label, SourceIF text);
		//System.out.println(content);
	}

	/**
	 * Process a \begin{document} command.
	 */
	void processBeginDocument()
	{
		uefStateStack.push(States.document);
		uefCommandQueue.poll();
	}

	/**
	 * Process a \begin{figure} command.
	 */
	void processBeginFigure()
	{
		uefStateStack.push(States.figure);
		uefCommandQueue.poll();
	}

	/**
	 * Process a \begin{problem} command. Creates a new answer
	 * list for all answers in the problem. Resets the index
	 * for the answers in the problem. Pushes the problem state.
	 */
	void processBeginProblem() throws EOFException, Exception
	{
		if (uefStateStack.peek() == States.document)
		{
			this.answersList = new ArrayList<AnswerIF>();
			//reset the answer amount for this problem
			this.answerAmount = 0;
			//push the new state
			uefStateStack.push(States.problem);
			//pull this command off the stack
			UEFCommand command = uefCommandQueue.poll();

			//String to be filled with the source content
			String content;

			//Variables to hold the beginning and end of the source
			int startSource = command.startPosition;
			int endSource;

			UEFCommand peekedCommand = findMatchingCommand(new Types[]
					{
						Types.endProblem
					});

			if (peekedCommand == null)
			{
				throw new Exception();
			}


			endSource = peekedCommand.getEndPosition();

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

			//System.out.println(content);
		}
		else
		{
			//error
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
	 * Process a \end{answers} command.
	 */
	void processEndAnswers()
	{
		if (uefStateStack.pop() != States.answers)
		{
			System.out.println("Error: \\end{answers} without a matching \\begin{answers}");
			System.exit(-1);
		}
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{block} command.
	 */
	void processEndBlock()
	{
		if (uefStateStack.pop() != States.block)
		{
			System.out.println("Error: \\end{block} without a matching \\begin{block}");
			System.exit(-1);
		}
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{document} command.
	 */
	void processEndDocument()
	{
		if (uefStateStack.pop() != States.document)
		{
			System.out.println("Error: \\end{document} without a matching \\begin{document}");
			System.exit(-1);
		}
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{figure} command.
	 */
	void processEndFigure()
	{
		if (uefStateStack.pop() != States.figure)
		{
			System.out.println("Error: \\end{figure} without a matching \\begin{figure}");
			System.exit(-1);
		}
		uefCommandQueue.poll();
	}

	/**
	 * Process a \end{problem} command. Creates a ProblemIF to add to the ExamIF.
	 */
	void processEndProblem()
	{
		if (uefStateStack.pop() == States.problem)
		{
			uefCommandQueue.poll();
			//ProblemIF problem = examFactory.newProblem(null, null, null, answers)
		}
		else
		{
			System.out.println("Error: \\end{problem} without a matching \\begin{problem}");
			System.exit(-1);
		}
	}

	/**
	 * Process a \label command.
	 */
	void processLabel()
	{
		uefCommandQueue.poll();
	}

	/**
	 * Process a \ref command.
	 */
	void processRef()
	{
		uefCommandQueue.poll();
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
				case answer:
					processAnswer();
					break;
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
				case endAnswers:
					processEndAnswers();
					break;
				case endBlock:
					processEndBlock();
					break;
				case endDocument:
					processEndDocument();
					break;
				case endFigure:
					processEndFigure();
					break;
				case endProblem:
					processEndProblem();
					break;
				case label:
					processLabel();
					break;
				case ref:
					processLabel();
					break;
				default:
					System.err.println("Error: Unidentified command found in the UEFCommandQueue!");
					System.exit(-1);
					break;
			}
		}
	}
}
