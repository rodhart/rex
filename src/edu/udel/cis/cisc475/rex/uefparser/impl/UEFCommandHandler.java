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
 * file.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 */
class UEFCommandHandler {

	/**
	 * Queue of all commands from the file.
	 */
	private Queue<UEFCommand> uefCommandQueue;
	/**
	 * Underlying file that was read from. Simply used to retrieve text from
	 * lines in the case that errors occur.
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
	UEFCommandHandler(UEFCharHandler uefCharHandler) {
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
	 *            the command to add.
	 */
	void add(UEFCommand uefCommand) {
		uefCommandQueue.add(uefCommand);
	}

	private UEFCommand findMatchingCommand(Types types[]) {
		for (Iterator<UEFCommand> iter = uefCommandQueue.iterator(); iter
				.hasNext();) {
			UEFCommand peekedCommand = iter.next();
			for (int i = 0; i < types.length; i++) {
				if (peekedCommand.getType() == types[i]) {
					return peekedCommand;
				}
			}
		}
		return null;
	}

	private boolean isFixed(String argument) {

		if (argument != null) {
			return argument.contains("fixed");
		} else {
			return false;
		}
	}

	private boolean isCorrect(String argument) {
		if (argument != null) {
			return argument.contains("correct");
		}
		return false;
	}

	/**
	 * Process an \answer command. Increments the answer index. Adds the new
	 * answer to the answers list.
	 */
	AnswerIF processAnswer(int index) throws RexParseException, EOFException {
		UEFCommand command = uefCommandQueue.poll();

		String optionalArgument = command.getOptionalArgument();
		boolean fixed = isFixed(optionalArgument);
		boolean correct = isCorrect(optionalArgument);

		// String to be filled with the source content
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource;

		Types type[] = new Types[2];
		type[0] = Types.answer;
		type[1] = Types.endAnswers;
		UEFCommand peekedCommand = findMatchingCommand(type);

		if (peekedCommand == null) {
			// should always be either endAnswers or answer command
			throw new RexParseException(
					"No \\end{answer} after \\begin{answer}.");
		}

		// set the end of the source to the position before
		// the beginning of the next command.
		endSource = peekedCommand.getStartPosition();

		// Ignore whitespaces and lines at the end of answers, because latex
		// does.
		while (uefCharHandler.read(endSource - 1) == '\n'
				|| uefCharHandler.read(endSource - 1) == ' '
				|| uefCharHandler.read(endSource - 1) == '\t') {
			endSource--;
		}

		// get the file content from beginning of '/answer'
		// to beginning of next command .
		content = uefCharHandler.getContent(startSource, endSource);

		// Create the source object
		SourceIF source = sourceFactory.newSource(uefCharHandler.getFileName());
		source.setStartLine(uefCharHandler.getLineNumber(startSource));
		source.setLastLine(uefCharHandler.getLineNumber(endSource));
		source.setStartColumn(uefCharHandler.getColumnNumber(startSource));
		source.setLastColumn(uefCharHandler.getColumnNumber(endSource));
		source.addText(content);

		if (!fixed) {
			// System.out.println(content);
			return examFactory.newAnswer(correct, source);
		} else {
			// System.out.println(content);
			return examFactory.newFixedAnswer(correct, index, source);
		}
	}

	/**
	 * Process a \begin{answers} command.
	 */
	AnswerIF[] processAnswers() throws RexParseException, EOFException {
		// pop off the /begin{answers} command
		uefCommandQueue.poll();
		int index = 0;
		List<AnswerIF> answersList = new ArrayList<AnswerIF>();

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done) {
			switch (uefCommandQueue.peek().getType()) {
			case endAnswers: {
				done = true;
				// poll the /end{answers} command off the queue
				uefCommandQueue.poll();
				break;
			}
			case answer: {
				answersList.add(processAnswer(index));
				index++;
				break;
			}
			case ref: {
				this.answerReferences.add(processRef());
				// uefCommandQueue.poll();
				break;
			}
			default: {
				throw new RexParseException(uefCommandQueue.peek().getType()
						+ " found within answers environment!");
			}
			}
		}
		return answersList.toArray(new AnswerIF[0]);
	}

	/**
	 * Process a \begin{block} command.
	 */
	BlockIF processBlock() throws RexParseException, EOFException {
		UEFCommand command = uefCommandQueue.poll();

		String name = command.getArgument(0);

		// String to be filled with the source content
		String content;

		// List of references inside the block
		List<String> refs = new ArrayList<String>();

		// Variables to hold the beginning and end of the source
		int startSource = command.getStartPosition();
		int endSource = 0;

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done) {
			switch (uefCommandQueue.peek().getType()) {
			case endBlock: {
				done = true;

				// poll the /end{problem command off the queue
				UEFCommand nextCommand = uefCommandQueue.poll();
				endSource = nextCommand.getEndPosition();
				break;
			}
			case ref: {
				refs.add(processRef());
				break;
			}
			default: {
				throw new RexParseException(uefCommandQueue.peek().getType()
						+ " found within block environment!");
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

		BlockIF block = examFactory.newBlock(name, source);

		if (refs.size() != 0) {
			Iterator<String> i = refs.iterator();
			while (i.hasNext()) {
				String r = i.next();
				if (this.references.containsKey(r)) {
					this.references.get(r).add(block);
				} else {
					List<ExamElementIF> list = new ArrayList<ExamElementIF>();
					list.add(block);
					this.references.put(r, list);
				}
			}
		}
		return block;
	}

	/**
	 * Process a \begin{document} command.
	 */
	ExamIF processDocument() throws RexParseException, EOFException {
		// pull /begin{document} off the queue
		UEFCommand command = uefCommandQueue.poll();

		// create the ExamIF
		ExamIF exam = examFactory.newMasterExam();

		// get the frontal matter
		if (!uefCommandQueue.isEmpty()) {
			// get the start and end positions of the frontal matter
			int startOfFrontalMatter = command.getEndPosition();
			int endOfFrontalMatter = uefCommandQueue.peek().getStartPosition();

			String content = uefCharHandler.getContent(startOfFrontalMatter,
					endOfFrontalMatter);

			// Create the source object for frontal matter
			SourceIF source = sourceFactory.newSource(uefCharHandler
					.getFileName());
			source.setStartLine(uefCharHandler
					.getLineNumber(startOfFrontalMatter));
			source
					.setLastLine(uefCharHandler
							.getLineNumber(endOfFrontalMatter));
			source.setStartColumn(uefCharHandler
					.getColumnNumber(startOfFrontalMatter));
			source.setLastColumn(uefCharHandler
					.getColumnNumber(endOfFrontalMatter));
			source.addText(content);

			// System.out.println(content);

			// set the frontal matter
			exam.setFrontMatter(source);
		}

		// process commands within the document environment
		while (!uefCommandQueue.isEmpty()) {
			switch (uefCommandQueue.peek().getType()) {
			case beginBlock: {
				exam.addElement(processBlock());
				break;
			}
			case beginFigure: {
				exam.addElement(processFigure());
				break;
			}
			case beginProblem: {
				exam.addElement(processProblem());
				break;
			}
			case endDocument: {
				// pull /end{document} off the queue.
				uefCommandQueue.poll();
				return exam;
			}
			default: {
				throw new RexParseException(uefCommandQueue.peek().getType()
						+ " found within document environment!");
			}
			}
		}
		throw new RexParseException(
				"End of file reached before \\end{document} found!");
	}

	/**
	 * Process a \begin{figure} command.
	 */
	FigureIF processFigure() throws EOFException {
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

		while (!uefCommandQueue.isEmpty() && !done) {
			switch (uefCommandQueue.peek().getType()) {
			case label: {
				label = processLabel();
				break;
			}
			case ref: {
				refs.add(processRef());
				break;
			}
			case endFigure: {
				// use the end of the other command as the end source
				endSource = uefCommandQueue.peek().getEndPosition();
				// pull /end{figure} off the queue.
				uefCommandQueue.poll();
				done = true;
				break;
			}
			default: {
				System.err.println("Error: " + uefCommandQueue.peek().getType()
						+ " found within figure environment!");
				System.exit(-1);
				break;
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

		if (refs.size() != 0) {
			Iterator<String> i = refs.iterator();
			while (i.hasNext()) {
				String r = i.next();
				if (this.references.containsKey(r)) {
					this.references.get(r).add(figure);
				} else {
					List<ExamElementIF> list = new ArrayList<ExamElementIF>();
					list.add(figure);
					this.references.put(r, list);
				}
			}
		}

		return figure;
	}

	/**
	 * Process a \begin{problem} command. Creates a new answer list for all
	 * answers in the problem. Resets the index for the answers in the problem.
	 * Pushes the problem state.
	 */
	ProblemIF processProblem() throws RexParseException, EOFException {
		// pull this command off the stack
		UEFCommand command = uefCommandQueue.poll();

		String topic = command.getArgument(0);
		String difficulty = command.getArgument(1);

		// String to be filled with the source content
		String content;

		// Variables to hold the beginning and end of the source
		int startSource = command.getEndPosition();// use the end of this

		// Ignore whitespaces and lines at the beginning of the question,
		// because latex
		// does.
		while (uefCharHandler.read(startSource) == '\n'
				|| uefCharHandler.read(startSource) == ' '
				|| uefCharHandler.read(startSource) == '\t') {
			startSource++;
		}

		// command
		int endSource = 0;

		String label = null;

		// Array to old answers in.
		AnswerIF answers[] = null;

		// List of all references in the problem
		List<String> refs = new ArrayList<String>();

		boolean done = false;

		while (!uefCommandQueue.isEmpty() && !done) {
			switch (uefCommandQueue.peek().getType()) {
			case beginAnswers: {
				// use the beginning of the other command as the end source
				endSource = uefCommandQueue.peek().getStartPosition();

				// Ignore whitespaces and lines at the end of question, because
				// latex
				// does.
				while (uefCharHandler.read(endSource - 1) == '\n'
						|| uefCharHandler.read(endSource - 1) == ' '
						|| uefCharHandler.read(endSource - 1) == '\t') {
					endSource--;
				}
				// get answers for the problem
				answers = processAnswers();
				if (answers == null) {
					throw new RexParseException(
							"No answers found within a problem environment.");
				}
				break;
			}
			case label: {
				label = processLabel();
				break;
			}
			case ref: {
				refs.add(processRef());
				break;
			}
			case endProblem: {
				done = true;
				// poll the /end{problem command off the queue
				uefCommandQueue.poll();
				break;
			}
			default: {
				System.err.println("Error: " + uefCommandQueue.peek().getType()
						+ " found within problem environment!");
				System.exit(-1);
				break;
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

		ProblemIF problem = examFactory.newProblem(topic, label, source,
				answers);
		problem.setDifficulty(Double.valueOf(difficulty));

		// Add references from answers

		for (int i = 0; i < this.answerReferences.size(); i++) {
			refs.add(this.answerReferences.get(i));
		}

		this.answerReferences.clear();

		if (refs.size() != 0) {
			Iterator<String> i = refs.iterator();
			while (i.hasNext()) {
				String r = i.next();
				if (this.references.containsKey(r)) {
					this.references.get(r).add(problem);
				} else {
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
	boolean processDocumentclass() {
		UEFCommand command = uefCommandQueue.poll();
		String classType = command.getArgument(0);
		if (classType.equals("exam")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Process a \label command.
	 */
	String processLabel() {
		UEFCommand command = uefCommandQueue.poll();
		return command.getArgument(0);
	}

	/**
	 * Process a \ref command.
	 */
	String processRef() {
		UEFCommand command = uefCommandQueue.poll();
		return command.getArgument(0);
	}

	/**
	 * Starts the processing of all commands in the command queue. Should be
	 * called by another class.
	 */
	ExamIF process() throws RexParseException, RexException, EOFException {
		boolean isExamDocumentclass = false;
		int startOfPreamble = 0;
		int endOfPreamble = 0;
		while (!uefCommandQueue.isEmpty()) {
			switch (uefCommandQueue.peek().getType()) {
			case beginDocument: {
				if (!isExamDocumentclass) {
					System.err
							.println("Error: "
									+ uefCommandQueue.peek().getType()
									+ " found when the documentclass is net set to exam!");
					System.exit(-1);
				}

				// retrieve the preamble
				endOfPreamble = uefCommandQueue.peek().getStartPosition();
				String content = uefCharHandler.getContent(startOfPreamble,
						endOfPreamble);

				// process to make an ExamIF
				ExamIF exam = processDocument();

				// Create the source object for preamble
				SourceIF source = sourceFactory.newSource(uefCharHandler
						.getFileName());
				source.setStartLine(uefCharHandler
						.getLineNumber(startOfPreamble));
				source.setLastLine(uefCharHandler.getLineNumber(endOfPreamble));
				source.setStartColumn(uefCharHandler
						.getColumnNumber(startOfPreamble));
				source.setLastColumn(uefCharHandler
						.getColumnNumber(endOfPreamble));
				source.addText(content);

				// set the preamble
				exam.setPreamble(source);

				// setting uses relationships
				Iterator<String> i = this.references.keySet().iterator();
				while (i.hasNext()) {
					String label = i.next();
					List<ExamElementIF> e = this.references.get(label);
					ExamElementIF usedElement = null;

					// FIXME: This try is work around to Ticket 74, but it
					// should be removed once the ticket is closed
					try {
						usedElement = exam.elementWithLabel(label);
					} catch (Exception ex) {
					}

					if (usedElement == null) {
						System.err.println("Error: " + "element with label "
								+ label + "is not found");
					} else {
						for (int j = 0; j < e.size(); j++) {
							exam.declareUse(e.get(j), usedElement);
						}
					}
				}

				// return the ExamIF
				return exam;
			}
			case documentclass: {
				startOfPreamble = uefCommandQueue.peek().getEndPosition();
				isExamDocumentclass = processDocumentclass();
				break;
			}
			default: {
				System.err.println("Error: " + uefCommandQueue.peek().getType()
						+ " found outside of document environment!");
				System.exit(-1);
				break;
			}
			}
		}
		System.err
				.println("Error: end of document before \begin{document} found!");
		System.exit(-1);
		return null;
	}
}
