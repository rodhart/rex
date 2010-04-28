package edu.udel.cis.cisc475.rex.output;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.impl.Answer;
import edu.udel.cis.cisc475.rex.exam.impl.Block;
import edu.udel.cis.cisc475.rex.exam.impl.Exam;
import edu.udel.cis.cisc475.rex.exam.impl.Figure;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyFactoryIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.key.impl.AnswerKeyFactory;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.output.impl.OutputFactory;
import edu.udel.cis.cisc475.rex.source.impl.Source;

/**
 * @author cardona
 * @author justin
 */

public class OutputMainExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//declare a pointer to an interface and
		//create an object of the interface type
		AnswerKeyFactoryIF AKF = new AnswerKeyFactory(); 
		AnswerKeyIF K = AKF.newAnswerKey("version string name", "exam string name ", "date string");		

		//create the writer only once the key is built
		OutputFactoryIF OF = new OutputFactory(); 
		AnswerKeyWriterIF AKW = OF.newAnswerKeyWriter (K);		

		
		//create temp containers
		Collection <String> Answer_a = new ArrayList <String>(); 
		Answer_a.add(new String("B"));
		//more than one correct answer
		Collection <String> Answer_b = new ArrayList <String>(); 
		Answer_b.add(new String("A"));
		Answer_b.add(new String("C"));
		Collection <String> Answer_c = new ArrayList <String>(); 
		Answer_c.add(new String("B"));

		//add problems to our class from the containers
		K.addProblem(Answer_a);	 	
		K.addProblem(Answer_b);	 	
		K.addProblem(Answer_c);	 	


		//create output stream
		PrintWriter pw = new PrintWriter(System.out,true); 

		//make call to write out the answer key
//		AKW.write(pw);

		/* now we are done fooling around with answer keys
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * now lets do some exam stuff
		 * 
		 */
			
		
		
		ExamIF EM = new Exam(true);		
		
		//creates the preamble
		Source preamble = new Source("preamble");
		preamble.addText("\\documentclass[12pt]{article}\n"); 
		preamble.addText("\\usepackage{graphicx}\n");
		preamble.addText("\\usepackage[letterpaper,textheight=9.5in,left=1in,textwidth=6.5in,bottom=1in]{geometry}\n");
		preamble.addText("\\usepackage{amsmath}\n");
		preamble.addText("\\author{Dr T. Harvey}\n");
		preamble.addText("\\title{test TEST}\n");
		preamble.addText("\\date{April 08, 2010}\n");
		preamble.addText("\\begin{document}\n");
		preamble.addText("\\maketitle\n");
		preamble.addText("\\newpage\n");
	
		//sets the preamble
		EM.setPreamble(preamble);
		
		//creates the front matter
		Source frontMatter = new Source("frontmatter.txt");
		frontMatter.addText("\\begin{document}\n");
		frontMatter.addText("\\maketitle\n");
		frontMatter.addText("\\newpage\n");
		
		//sets the front matter
		EM.setFrontMatter(frontMatter);
		
		//creates the final matter
		Source finalSource = new Source("finalBlock.txt");
		finalSource.addText("\\end{document}");
		Block finalBlock = new Block("Final topic", finalSource);
		
		//sets the final block
		EM.setFinalBlock(finalBlock);
	
		
		
		//creates a test block case
		Source testSourceBlock = new Source("test source block");
		testSourceBlock.addText("this is text inside the block");
		Block testblock = new Block("test block", testSourceBlock);
		// creates a test figure case
		Source testSourceFigure = new Source("test source figure");
		testSourceFigure.addText("this is the text inside the figure");
		Figure testFigure = new Figure("test figure", testSourceFigure);
		
		//creates a test problem case
		Source testSourceProblem = new Source("test source problem");
		testSourceProblem.addText("this is the text inside the problem");
		
		Source ans1Source = new Source("ans1");
		ans1Source.addText("this is the answer 1");
		Answer ans1 = new Answer(true,ans1Source);
		
		Source ans2Source = new Source("ans2");
		ans2Source.addText("this is the answer 2");
		Answer ans2 = new Answer(true,ans2Source);
		
		Answer[] answerArray = {ans1, ans2};
		
		//Problem testProblem = new Problem("test topic", "test label", testSourceProblem, answerArray );
		
		
		
		//creates a test problem case with a block
		Source testSourceProblem2 = new Source("test source problem 2");
		testSourceProblem.addText("this is the text inside the problem2");
		
		
		//Problem testProblem2= new Problem("test topic", "test label", testSourceProblem, answerArray );
		//testProblem2.setRequiredBlock(testblock);
		
		
		EM.addElement(testblock);
		EM.addElement(testFigure);
		//EM.addElement(testProblem);
		//EM.addElement(testProblem2);
		ExamWriterIF EW = OF.newExamWriter (EM);		
		
		EW.write(pw);
		
	}//end of main
}//end of class 
