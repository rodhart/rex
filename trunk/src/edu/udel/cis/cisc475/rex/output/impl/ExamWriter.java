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
 * The ExamWriter class utilizes a print writer and an precreated Exam class to
 * output parts of the exam class to the print writer.
 * 
 * @author kiernan
 * @author justin
 */

public class ExamWriter implements ExamWriterIF {

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
		//header info
		if(E.isMaster())
			out.println("\\documentclass[master]{exam}");
		else
			out.println("\\documentclass{exam}");
		
		// output preamble if it exists
		if(E.preamble()!=null){
			E.preamble().write(out);
			out.flush();
		}
		
		out.println("\\newcommand{\\examversion}{" + E.version() + "}");
		out.println("\\begin{document}");
		
		//prints the front matter if it exists
		if(E.frontMatter()!=null){
			E.frontMatter().write(out);
			out.flush();
		}
		
		// output problems with respective answers
		for (int i = 0; i < E.elements().toArray().length; i++) {
			ExamElementIF temp = (ExamElementIF) E.elements().toArray()[i];

			// the following checks what type the current element is
			// and prints accordingly
			if (temp instanceof BlockIF) {
				//casts element type to a BlockIF type
				BlockIF tempBlockIF = (BlockIF) temp;
				//prints the block
				tempBlockIF.source().write(out);
			} else if (temp instanceof FigureIF) {
				//casts the element type to a figureIF type
				FigureIF tempFigureIF = (FigureIF) temp;
				//makes a new page so the questions and figures fall on the same page
				out.println("\\newpage");
				//prints the figure
				tempFigureIF.source().write(out);
				out.println("\\label{fig:" + tempFigureIF.label() + "}");
			} else if (temp instanceof ProblemIF) {
				//casts the element type to a problemIF type
				ProblemIF tempProblem = (ProblemIF) temp;
				
				//prints the question
				out.println("\\begin{problem}{" + tempProblem.topic() + "}{" + tempProblem.difficulty()+"}");
				tempProblem.question().write(out);
				
				// prints all the answers
				out.println("\\begin{answers}");
				for (int index = 0; index < tempProblem.answers().length; index++) {
					tempProblem.answers()[index].source().write(out);
				}// end of for loop
				out.println("\\end{answers}");
				out.println("\\end{problem}");
			}// endo of if(temp instanceof Problem)
			out.flush();
		}// endo of for loop (int i=0; i< E.elements().toArray().length; i++)

		
		
		// output the end block for exam if it exists
		if(E.finalBlock()!=null){
			E.finalBlock().source().write(out);
			out.flush();
		}
		out.println("\\end{document}");
		out.flush();
		
	}// end of write(PrintWriter out)

	// private vars
	private ExamIF E;
}// end of class
