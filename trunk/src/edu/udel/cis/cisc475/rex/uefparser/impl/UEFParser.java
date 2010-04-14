package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;

/**
 * Parser for the Universal Exam File (UEF).
 * 
 * FIXME: Doesn't handle /labels{}.
 * 
 * FIXME: Doesn't handle the \verb command.
 * 
 * @author Aaron Myles Landwehr
 * @author Ahmed El-hassany
 * 
 */
public class UEFParser implements UEFParserIF {

	/**
	 * Stack to maintain a hierarchy of states.
	 */
	Stack<States> state;

	/**
	 * The list of states we have. States.top is just so we know we are at the
	 * top of the stack
	 */
	enum States {

		top, comment, documentclass, label, verb, verbatim, problem, answers, answer
	}

	/**
	 * Subclass that implements general parsing of the file.
	 */
	UEFCharHandler uefCharHandler;
	/**
	 * Subclass that implements parsing of commands.
	 */
	UEFCommand command;

	/**
	 * Creates new object of the parser. UEFParserFactory should be used to
	 * create this object.
	 * 
	 * @see UEFParserFactoryIF
	 */
	UEFParser() {
		// Stack to maintain a hierarchy of states.
		state = new Stack<States>();
		state.push(States.top);

		// Subclass that implements general parsing of the file
		uefCharHandler = new UEFCharHandler();
		// Subclass that implements parsing of commands.
		command = new UEFCommand(uefCharHandler, state);
	}

	/**
	 * Parse UEF file and generate ExamIF from it.
	 * 
	 * @param file
	 *            the file handler for the uef file.
	 * @return ExamIF of the uef file.
	 */
	public ExamIF parse(File file) {
		try {
			uefCharHandler.openFile(file);
			UEFCommand.CommandTypes commandType;
			ExamFactoryIF examFactory = new ExamFactory();

			// reference to the last problem handled;
			ProblemIF problem = null;
			// reference to the last answer handled
			AnswerIF answer;

			//List of answers for the problem
			List<AnswerIF> answers = new ArrayList<AnswerIF>();

			// Read each command until the end of the file.
			while ((commandType = command.process()) != null) {
				// Check to see what the current command is and process it.
				if (commandType.equals(UEFCommand.CommandTypes.documentclass)) {
					command.processDocumentclass();
				} else if (commandType.equals(UEFCommand.CommandTypes.answer)) {
					answer = command.processAnswer();
					answers.add(answer);
				} else if (commandType
						.equals(UEFCommand.CommandTypes.beginProblem)) {
					problem = command.processBeginProblem();
				} else if (commandType
						.equals(UEFCommand.CommandTypes.endProblem)) {
					command.processEndProblem();
					// FIXME: need more error checking for closed problem
					// without begin
					// FIXME: Creating new problem each time is not good idea,
					// may be add addAnswer to the problem interface will help.
					if (problem != null) {
						problem = examFactory.newProblem(problem.topic(),
								problem.label(), problem.question(), answers
										.toArray(new AnswerIF[0]));
						answers.clear();
					}
				} else if (commandType
						.equals(UEFCommand.CommandTypes.beginAnswers)) {
					command.processBeginAnswers();
				} else if (commandType
						.equals(UEFCommand.CommandTypes.endAnswers)) {
					command.processEndAnswers();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
