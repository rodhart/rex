package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import edu.udel.cis.cisc475.rex.uefparser.impl.UEFCommand.Types;

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
	Queue<UEFCommand> uefCommandQueue;
	/**
	 * Stack of current states in the file.
	 */
	Stack<States> uefStateStack;
	/**
	 * Underlying file that was read from.
	 * Simply used to retrieve text from lines
	 * in the case that errors occur.
	 */
	UEFCharHandler uefCharHandler;
	/**
	 * Source factory is only created once at the constructor and used every
	 * time
	 */
	private SourceFactoryIF sourceFactory;

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
		this.uefStateStack = new Stack<States>();
	}

	/**
	 * Adds a command into the command queue.
	 * @param uefCommand the command to add.
	 */
	void add(UEFCommand uefCommand)
	{
		uefCommandQueue.add(uefCommand);
	}

	/**
	 * Process an \answer command.
	 */
	void processAnswer()
	{
		UEFCommand command = uefCommandQueue.poll();
		String optionalArgument = command.getOptionalArgument();
		String content;
		for (Iterator<UEFCommand> iter = uefCommandQueue.iterator(); iter.hasNext();)
		{
			UEFCommand peekedCommand = iter.next();
			if (peekedCommand.getType() == Types.answer
				|| peekedCommand.getType() == Types.endAnswers)
			{
				content = uefCharHandler.getContent(command.endPosition,
													peekedCommand.getStartPosition() - 1);
				System.out.println("answer: '" + content + "'");
				break;
			}
		}
		SourceIF s = sourceFactory.newSource(uefCharHandler.getFileName());
		s.setStartColumn(this.uefCharHandler.getColumnNumber());
		s.setStartLine(this.uefCharHandler.getLineNumber());
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
	void processBeginBlock()
	{
		uefStateStack.push(States.block);
		uefCommandQueue.poll();
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
	 * Process a \begin{problem} command.
	 */
	void processBeginProblem()
	{
		if (uefStateStack.peek() == States.document)
		{
			uefStateStack.push(States.problem);
			uefCommandQueue.poll();
		}
		else
		{
			//error
			System.exit(-1);
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
	 * Process a \end{problem} command.
	 */
	void processEndProblem()
	{
		if (uefStateStack.pop() != States.problem)
		{
			System.out.println("Error: \\end{problem} without a matching \\begin{problem}");
			System.exit(-1);
		}
		uefCommandQueue.poll();
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
	void process()
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
