package edu.udel.cis.cisc475.rex.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Date;


import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserFactoryIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserIF;
import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParserFactory;
import edu.udel.cis.cisc475.rex.err.RexException;
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
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParser;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParserFactory;

/**
 *         Main class of Rex program; takes and parses input given on the
 *         command line. Tailors the use of the program to the user's specified
 *         request (see printUsage() below).
 *
 * @author Keith McLoughlin
 *
 */

public class Rex {

	public static void main(String[] args){
		int retVal;
		retVal = process(args);
		switch(retVal){	
			case -2: break;
			case -1: break;
			case 1: System.err.println("There was an error in generating the exams!");break;
			case 2: break;
			case 3: System.err.println("There was an error parsing the ECF file !");break;
			case 4: System.err.println("There was an error parsing the UEF file !");break;
			case 5: System.err.println("There was an error creating an exam file !");break;
			case 6: System.err.println("There was an error creating a key file !");break;
			case 7: System.err.println("There was an error while calling pdflatex !");break;
		}
	}

	protected static int process(String[] args) {

		int numArgs = args.length;
		Date date = new Date();
		long fileStamp = date.getTime();
		long seed = fileStamp;
		boolean pdfOpt = false, 
			seedflag = true,
			nflag = true,
			pdfflag = true;
		int numExams = 1;
		File ecf = null;
		File uef = null;
		/*
		 * Test Usage
		 */
		// System.out.println("args = " + numArgs);
		if (numArgs < 2) {
			printUsage();
			System.err.println("NOT ENOUGH ARGUMENTS! ");
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
				if(nflag){
				try{
				numExams = Integer.parseInt(args[i + 1]);
				nflag = false;
				}
				catch(Exception e){
					System.err.println("A valid integer must follow -n!");
					printUsage();
					return -2;
				}
				}
				else{
					System.err.println("Please only declare -n <int> once!");
					return -2;
				}
				i += 2;
			}
			/*
			 * Test for -seed option and set seed
			 */
			else if (args[i].equals("-seed")) {
				if(seedflag){
				try{
				seed = Long.parseLong(args[i + 1]);
				seedflag = false;
				}
				catch(Exception e){
					System.err.println("A valid long must follow -seed!");
					printUsage();
					return -2;
				}
				}
				else{
					System.err.println("Please only declare -seed <long> once!");
					return -2;
				}
				i += 2;
			}
			/*
			 * Test for -pdf option and set boolean pdfOpt
			 */

			else if (args[i].equals("-pdf")) {
				if(pdfflag){
				pdfOpt = true;
				pdfflag = false;
				}
				else{
					System.err.println("Please only declare -pdf once!");
					return -2;
				}
				i++;
			}
			/*
			 * Exit if an incorrect option is given
			 */
			else {
				System.err.println(args[i] + " is not a valid option!");
				printUsage();

				// System.out.println("opts loop");
				return -2;
			}
		}
		if ((i+2) != numArgs 
				|| args[numArgs-2].equals("-pdf") 
				|| args[numArgs-2].equals("-seed") 
				|| args[numArgs-2].equals("-n")
				|| args[numArgs-1].equals("-pdf") 
				|| args[numArgs-1].equals("-seed") 
				|| args[numArgs-1].equals("-n")){
			System.err.println("You must include the UEF and ECF File paths!");
			printUsage();
			return 2;
		}
		uef = new File(args[numArgs - 2]);
		if(!uef.canRead()){
			printUsage();
			System.err.println("Could not read from the UEF file.");
			return 2;
		}
		ecf = new File(args[numArgs - 1]);
		if( !ecf.canRead()){
			printUsage();
			System.err.println("Could not read from the ECF file.");
			return 2;
		}
		/*
		 * Create an EcfParserFactory Get a new parser Parse the ECF to get
		 * theConfig
		 */
		EcfParserFactoryIF theEcfParserFactory = new EcfParserFactory();
		EcfParserIF theEcfParser = theEcfParserFactory.newParser(numExams);
		ConfigIF theConfig = null;
		try {
			theEcfParser.setPdfOption(pdfOpt);
			theEcfParser.setSeed(seed);
			theConfig = theEcfParser.parse(ecf);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.toString());
			return 3;
		}
		/*
		 * Create an UefParserFactory Get a new parser Parse the UEF to get
		 * theMaster
		 */
		UEFParserFactoryIF theUefParserFactory = new UEFParserFactory();
		UEFParserIF theUefParser = theUefParserFactory.newUEFParser();
		ExamIF theMaster = null;
		try {
			theMaster = theUefParser.parse(uef);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.toString());
			return 4;
		}
		/*
		 * Create the GeneratorFactory Create the Generator
		 */
		GeneratorIF theGenerator = null;
		GeneratorFactoryIF theGeneratorFactory = new GeneratorFactory();
		try {
			theGenerator = theGeneratorFactory.newGenerator(theMaster,
					theConfig);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.toString());
			return 1;

		}

		/*
		 * Create the container for the generated exams and answer keys
		 */
		ExamIF[] theExams = new ExamIF[numExams];
		AnswerKeyIF[] theAnswerKeys = new AnswerKeyIF[numExams];

		
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


			/*
			 * create the File
			 */
			theLatexFiles[i] = new File(fileStamp + "_" + "exam_" + (i+1) +".tex");
			theKeyFiles[i] = new File(fileStamp + "_" + "key_" + (i+1)+ ".txt");
			try {
				/*
				 * create the exam(i).tex file on the file system
				 */
				theLatexFiles[i].createNewFile();

			} catch (Exception e) {
				System.err.println("Error creating "
						+ theLatexFiles[i].getAbsolutePath());
				System.err.println(e.getMessage());
				return 5;
			}
			/*
			 * create the key(i).txt file on the file system
			 */
			try {
				theKeyFiles[i].createNewFile();

			} catch (Exception e) {
				System.err.println("Error creating "
						+ theKeyFiles[i].getAbsolutePath());
				System.err.println(e.getMessage());
				return 6;
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
				System.err.println(e.getMessage());
			
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
			try{
				theExamWriters[i].write(theLatexWriters[i]);
			}
			catch(IOException e){
				System.err.println("IOException while writing"+
						" the exam files.");
				System.err.println(e.getMessage());
				return -1;
			}
			theAnswerKeyWriters[i].write(theKeyWriters[i]);
			/*
			 * Close the output streams
			 */
			theLatexWriters[i].close();
			theKeyWriters[i].close();
		}
		int exitVal = 0;
		if (pdfOpt) {
			// call pdflatex on each exam
			//String line;
			//OutputStream stdin = null;
			//InputStream stdout = null;
			//InputStream stderr = null;
			
			for(int j = 0; j < numExams; j++){
				try {
					UEFParser.uefPdflatex(theLatexFiles[j]);
//				Process p = null;
//				
//					p = Runtime.getRuntime().exec(
//							"pdflatex -interaction nonstopmode "+theLatexFiles[j].getName());
//				
//				
//				stdin = p.getOutputStream();
//				stdout = p.getInputStream();
//				stderr = p.getErrorStream();
//				
//				// clean up if any output in stdout
//			      BufferedReader brCleanUp = 
//			        new BufferedReader (new InputStreamReader (stdout));
//			   
//			      while ((line = brCleanUp.readLine ()) != null) {
//			    	if(line.contains("! LaTeX Error")){
//			    		System.out.println ("pdflatex found error: \"" + line + "\"");
//			    		p.destroy();
//			    		exitVal = -1;
//			    		brCleanUp.close();
//			    		break;
//			    	}
//			      }
//			      brCleanUp.close();
//			     
//			      if(exitVal == -1){
//			    	  break;
//			      }
//			      // clean up if any output in stderr
//			      brCleanUp = 
//			        new BufferedReader (new InputStreamReader (stderr));
//			      while ((line = brCleanUp.readLine ()) != null) {
//			        //System.out.println ("[Stderr] " + line);
//			      }
//			      brCleanUp.close();
			      
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
					return 7;
				}
				catch (RexException e){
					System.err.println(e.toString());
					return 7;
				}
			}

		}
		if(exitVal == -1) {
			printCompletionMessage(false, fileStamp);
		}
		else{
			printCompletionMessage(pdfOpt, fileStamp);
		}
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

	private static void printCompletionMessage(boolean pdf, long fileStamp) {
		System.out.println("Rex has completed your request!");
		System.out.println("The "+fileStamp+"_exam_(n).tex files hold your exams"
				+ " in latex format.");
		System.out.println("The "+fileStamp+"_key_(n).txt files hold your answer keys"
				+ " in plain text format.");
		if (pdf)
			System.out.println("The "+fileStamp+"_exam_(n).pdf files hold your exams"
					+ " in PDF format.");
	}
}
