package edu.udel.cis.cisc475.rex.output.impl;

import java.io.IOException;
import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;

/**
 * The ExamWriterIF is used to output a Exam object using a print writer object 
 * and the public write() function.
 * 
 * The ExamWriter class outputs a formatted output of the newly generated exams 
 * created in the generator class.
 * The files are written to a PrintWriter specified by the caller.
 * 
 ** 
 * @author kiernan
 * @author justin
 */

public class ExamWriter implements ExamWriterIF {

	// private variables
	private ExamIF E;

	/**
	 * Default constructor
	 * 
	 * @param exam
	 *            an exam object with a preamble, front matter, and problems
	 */
	public ExamWriter(ExamIF exam) {
		this.E = exam;
	}// end of constructor

	/**
	 * Loops through each exam element and writes it to the PrintWriter object.
	 * 
	 * @param out
	 *            any writer used to output data
	 * @throws IOException 
	 *  
	 */
	public void write(PrintWriter out) throws IOException {
		// Header info
		if(E.isMaster())
			out.println("\\documentclass[master]{exam}");
		else
			out.println("\\documentclass{exam}");
		
		// Output preamble if it exists
		if(E.preamble()!=null){
			E.preamble().write(out);
			out.flush();
		}
		
		// Declares a LaTeX command to handle the printing of the exam version
		out.println("\\renewcommand{\\examversion}{" + E.version() + "}");
		out.println("\\begin{document}");
		
		// Prints the front matter if it exists
		if(E.frontMatter()!= null){
			E.frontMatter().write(out);
			out.flush();
		}
		
		// Output problems with respective answers
		for (int i = 0; i < E.elements().toArray().length; i++) {
			ExamElementIF temp = (ExamElementIF) E.elements().toArray()[i];

			// The following checks what type the current element is
			// and prints accordingly
			if (temp instanceof BlockIF) {
				// Casts element type to a BlockIF type
				BlockIF tempBlockIF = (BlockIF) temp;
				// Prints the block
				tempBlockIF.source().write(out);
			} else if (temp instanceof FigureIF) {
				// Casts the element type to a figureIF type
				FigureIF tempFigureIF = (FigureIF) temp;
				// Makes a new page so the questions and figures fall on the same page
				out.println("\\newpage");
				// Prints the figure
				tempFigureIF.source().write(out);
				out.println("\\label{fig:" + tempFigureIF.label() + "}");
			} else if (temp instanceof ProblemIF) {
				// Casts the element type to a problemIF type
				ProblemIF tempProblem = (ProblemIF) temp;
				
				// Prints the question
				out.println("\\begin{problem}{" + tempProblem.topic() + "}{" + tempProblem.difficulty()+"}");
				tempProblem.question().write(out);
				
				if (tempProblem.answers() != null) {
					// Prints all the answers
					out.println("\\begin{answers}");
					for (int index = 0; index < tempProblem.answers().length; index++) {
						tempProblem.answers()[index].source().write(out);
					}// end of for loop
					out.println("\\end{answers}");
				}
				out.println("\\end{problem}");
			}// end of if(temp instanceof Problem)
			out.flush();
		}// end of for loop (int i=0; i< E.elements().toArray().length; i++)

		// output the end block for exam if it exists
		if(E.finalBlock()!=null){
			E.finalBlock().source().write(out);
			out.flush();
		}
		out.println("\\end{document}");
		out.flush();
		
	}// end of write(PrintWriter out)

}// end of class
