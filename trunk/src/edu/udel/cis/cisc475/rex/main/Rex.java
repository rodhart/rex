package edu.udel.cis.cisc475.rex.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserFactoryIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserIF;
import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParserFactory;
import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.output.IF.AnswerKeyWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.ExamWriterIF;
import edu.udel.cis.cisc475.rex.output.IF.OutputFactoryIF;
import edu.udel.cis.cisc475.rex.output.impl.OutputFactory;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParserFactory;

/**
 * @author Keith McLoughlin
 *
 * Main class of Rex program; takes and parses input given
 * on the command line. Tailors the use of the program to the user's
 * specified request (see printUsage() below).
 */

public class Rex {

	public static void main(String[] args) throws IOException
	{
		process(args);
	}
	
	protected static int process(String[] args) throws IOException {

		int numArgs = args.length;
		long seed = 1;
		boolean pdfOpt = false;
		int numExams = 1;
		File ecf = null;
		File uef = null;
		/*
		 * Test Usage
		 */
		System.out.println("args = " + numArgs);
		if (numArgs < 2) {
			printUsage();
			System.out.println("NOT ENOUGH ARGS! "+ numArgs);
			return -1;
		}
		int i = 0;
		/*
		 * Parse command line
		 */
		while (i < numArgs - 2) {
			/*
			 * Test for -n option and set numExams
			 */
			if (numExams == 1 && args[i].equals("-n")) {
				numExams = Integer.parseInt(args[i + 1]);
				i += 2;
			}
			/*
			 * Test for -seed option and set seed
			 */
			else if (seed == 1 && args[i].equals("-seed")) {
				seed = Long.parseLong(args[i + 1]);
				i += 2;
			}
			/*
			 * Test for -pdf option and set boolean pdfOpt
			 */
			
			else if (!pdfOpt && args[i].equals("-pdf")) {
				pdfOpt = true;
				i++;
			}
			/*
			 * Exit if an incorrect option is given
			 */
			else {
				System.err.println(args[i] + " is not a valid option!");
				printUsage();

				System.out.println("opts loop");
				return -1;
			}
		}
	
		
			uef = new File(args[numArgs - 2]);
		
		
			ecf = new File(args[numArgs - 1]);
		
	
		/*
		 * Create an EcfParserFactory Get a new parser Parse the ECF to get
		 * theConfig
		 */
		EcfParserFactoryIF theEcfParserFactory = new EcfParserFactory();
		EcfParserIF theEcfParser = theEcfParserFactory.newParser(numExams);
		ConfigIF theConfig = null;
		try{
		theConfig = theEcfParser.parse(ecf);
	}
	catch(Exception e){
		e.printStackTrace();
		return 3;
	}
		/*
		 * Create an UefParserFactory Get a new parser Parse the UEF to get
		 * theMaster
		 */
		UEFParserFactoryIF theUefParserFactory = new UEFParserFactory();
		UEFParserIF theUefParser = theUefParserFactory.newUEFParser();
		ExamIF theMaster = null;
		try{
			theMaster = theUefParser.parse(uef);
			}
			catch(Exception e){
				e.printStackTrace();
				return 3;
			}
		/*
		 * Create the GeneratorFactory Create the Generator
		 */
		GeneratorIF theGenerator = null;
		GeneratorFactoryIF theGeneratorFactory = new GeneratorFactory();
		try{
		theGenerator = theGeneratorFactory.newGenerator(theMaster,
				theConfig);
		}
		catch(RexException e){
			e.toString();
			e.printStackTrace();
			return 1;
			
		}
		// TODO Write an appropriate error.

		/*
		 * Create the container for the generated exams and answer keys
		 */
		ExamIF[] theExams = new ExamIF[numExams];
		AnswerKeyIF[] theAnswerKeys = new AnswerKeyIF[numExams];

		/*
		 
		 */
		//This looks out of date; commenting out for now. -Greg
		
//		for (i = 0; i < numExams; i++) {
//
//		}
		/*
		 * Set up output files and file streams
		 */
		File[] theLatexFiles = new File[numExams];
		File[] theKeyFiles = new File[numExams];
		PrintWriter[] theLatexWriters = new PrintWriter[numExams];
		PrintWriter[] theKeyWriters = new PrintWriter[numExams];
		/*
		 * Set up the output module containers
		 */
		OutputFactoryIF theOutputFactory = new OutputFactory();
		ExamWriterIF[] theExamWriters = new ExamWriterIF[numExams];
		AnswerKeyWriterIF[] theAnswerKeyWriters = new AnswerKeyWriterIF[numExams];

		/*
		 * Get an exam and key Create the actual files Initiate the PrintWriters
		 * Use the output module to fill the files
		 */
		for (i = 0; i < numExams; i++) {
			/*
			 * Fill the containers with the generated material
			 */
			
			theExams[i] = theGenerator.getGeneratedExam(i);
			theAnswerKeys[i] = theGenerator.getAnswerKey(i);

			// TODO Write appropriate error message

			/*
			 * create the File
			 */
			theLatexFiles[i] = new File("exam" + (i + 1) + ".tex");
			theKeyFiles[i] = new File("key" + (i + 1) + ".txt");
			try {
				/*
				 * create the exam(i).tex file on the file system
				 */
				theLatexFiles[i].createNewFile();

			} catch (Exception e) {
				System.err.println("Error creating "
						+ theLatexFiles[i].getAbsolutePath());
				e.toString();
				return 4;
			}
			/*
			 * create the key(i).txt file on the file system
			 */
			try {
				theKeyFiles[i].createNewFile();

			} catch (Exception e) {
				System.err.println("Error creating "
						+ theKeyFiles[i].getAbsolutePath());
				e.toString();
				return 4;
			}
			/*
			 * Create the PrintWriters from the newly created files
			 */
			try {
				theLatexWriters[i] = new PrintWriter(new FileOutputStream(
						theLatexFiles[i]));
				theKeyWriters[i] = new PrintWriter(new FileOutputStream(
						theKeyFiles[i]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			/*
			 * Set up the exam writers and answer key writers
			 */

			theExamWriters[i] = theOutputFactory.newExamWriter(theExams[i]);
			theAnswerKeyWriters[i] = theOutputFactory
					.newAnswerKeyWriter(theAnswerKeys[i]);

			/*
			 * Write the Exams and answer keys to their proper files
			 */

			theExamWriters[i].write(theLatexWriters[i]);
			theAnswerKeyWriters[i].write(theKeyWriters[i]);
			/*
			 * Close the output streams
			 */
			theLatexWriters[i].close();
			theKeyWriters[i].close();
		}
		if(pdfOpt){
			//call pdflatex on each exam
			//for(int j = 0; j < numExams; j++){}
			
			
		}
		printCompletionMessage(pdfOpt);
		return 0;
	}

	private static void printUsage() {

		System.err
				.println("Usage: java -jar REX.jar [options] <UEF filename> <ECF filename>");
		System.err.println("Options: ");
		System.err.println("    -n numExams : int numExams declares"
				+ " number of exams");
		System.err.println("    -seed seed : long seed declares"
				+ " the seed for randomization");
		System.err.println("    -pdf       : sets the pdf option"
				+ " in order to have the exams output in pdf as well"
				+ " as .tex files.");
	}

	private static void printCompletionMessage(boolean pdf) {
		System.out.println("Rex has completed your request!");
		System.out.println("The exam(n).tex files hold your exams"
				+ " in latex format.");
		System.out.println("The key(n).txt files hold your answer keys"
				+ " in plain text format.");
		if (pdf)
			System.out.println("The exam(n).pdf files hold your exams"
					+ " in PDF format.");
	}
}
