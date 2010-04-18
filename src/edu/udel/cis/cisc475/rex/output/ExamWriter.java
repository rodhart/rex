package edu.udel.cis.cisc475.rex.output;

import java.io.PrintWriter;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
/**
 * @author team 5
 * The ExamWriter class utilizes a print writer and an precreated Exam
 * class to output parts of the exam class to the print writer.
 *  
 */
public class ExamWriter implements ExamWriterIF {


	// Notes for creation:
	// loop with exam.numElements through a hash(?) to find 
	// out what to print
	// Does not include front matter.

	/*
	 * Default constructor for ExamWriter
	 * 	
	 * @param E created with the Exam module
	 */
	public ExamWriter(Exam E) {

		this.E = E;
		numElements = E.numElements();

	}//end of constructor


	/*
	 * Writes the Exam class to a specified PrintWriter object
	 * 	
	 * @param out any writer used to output data
	 */
	public void write(PrintWriter out){
		// output beginning of exam file
		out.printf("%s\n", E.preamble());
		out.printf("%s\n", E.frontMatter());
		
		// output problems with respective answers
		for(int i=0; i< E.elements().toArray().length; i++){
			ExamElementIF temp = (ExamElementIF) E.elements().toArray()[i];


			if(temp instanceof BlockIF){
				BlockIF tempBlockIF  = (BlockIF)temp;
				out.printf("%s\n", tempBlockIF.source().text());
			}
			else if(temp instanceof FigureIF){
				FigureIF tempFigureIF = (FigureIF)temp;
				out.printf("%s\n",tempFigureIF.source().text());
			}
			else if(temp instanceof Problem){
				Problem tempProblem = (Problem)temp;
				
				//prints the required block if it does not equal null
				if(tempProblem.requiredBlock()!= null)
					out.printf("%s\n",tempProblem.requiredBlock());
				//prints the question
				out.printf("%s\n",tempProblem.question().text());
				
				//prints all the answers
				for(int index=0; index<tempProblem.answers().length; index++){
					out.printf("%s\n",tempProblem.answers()[index].source().text());
				}
			}
		}
		
		// output the end block for exam
		out.printf("%s\n", E.finalBlock());
		
	}//end of write(PrintWriter out)

	
	//private vars
	private Exam E;
	private Collection <ExamElementIF> C;	
	private int numElements;
}//end of class 
