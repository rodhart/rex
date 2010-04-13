package edu.udel.cis.cisc475.rex.main;

import java.io.File;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserFactoryIF;
import edu.udel.cis.cisc475.rex.ecfparser.IF.EcfParserIF;
import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParserFactory;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorFactoryIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.generate.impl.GeneratorFactory;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParserFactory;

public class Rex {

	static void main(String[] args)
	{ 

		int numArgs = args.length;
		long seed = 1;
		boolean pdfOpt = false;
		int numExams = 1;
		File ecf = null;
		File uef = null;
		
		/*
		 * Test Usage
		 */
		if (numArgs < 3) {
			printUsage();
			System.exit(-1);
		}
		int i = 1;
		/*
		 * Parse command line
		 */
		while ( i < numArgs - 2) {
			/*
			 * Test for -n option and set numExams
			 */
			if (numExams == 1 && args[i].equals("-n")) {
				numExams = Integer.parseInt(args[i+1]);
				i += 2;
			} 
			/*
			 * Test for -seed option and set seed
			 */
			else if (seed == 1 && args[i].equals("-seed")) {
				seed = Long.parseLong(args[i+1]);
				i+=2;
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
			else{
				System.err.println(args[i] + " is not a valid option!");
				printUsage();
				System.exit(-1);
			}
		}
		/*
		 * Make sure the UEF input file is a .tex file
		 */
		if(args[numArgs-2].contains(".tex")){
			uef = new File(args[numArgs-2]);
		}
		else{
			System.err.println(args[numArgs-2] + " does not appear to" +
					"be a valid .tex file!");
			printUsage();
			System.exit(-1);
		}
		/*
		 * Make sure the ECF input file is a .ecf file
		 */
		if(args[numArgs-1].contains(".ecf")){
			ecf = new File(args[numArgs-1]);
		}
		else{
			System.err.println(args[numArgs-2] + " does not appear to" +
					"be a valid .ecf file!");
			printUsage();
			System.exit(-1);
		}
		/*
		 * Create an EcfParserFactory
		 * Get a new parser
		 * Parse the ECF to get theConfig
		 */
		EcfParserFactoryIF theEcfParserFactory = new EcfParserFactory();
		EcfParserIF theEcfParser = theEcfParserFactory.newParser(numExams);
		ConfigIF theConfig = theEcfParser.parse(ecf);
		/*
		 * Create an UefParserFactory
		 * Get a new parser
		 * Parse the UEF to get theMaster
		 */
		UEFParserFactoryIF theUefParserFactory = new UEFParserFactory(); 
		UEFParserIF theUefParser = theUefParserFactory.newUEFParser();
		ExamIF theMaster = theUefParser.parse(uef);
		/*
		 * 
		 */
		GeneratorFactoryIF theGeneratorFactory = new GeneratorFactory();
		GeneratorIF theGenerator = theGeneratorFactory.newGenerator(theMaster, theConfig);
		
	}
	private static void printUsage(){
		
		System.err
		.println("Usage: rex [options] <UEF filename> <ECF filename>");
		System.err.println("Options: ");
		System.err.println("    -n numExams : int numExams declares"+
				" number of exams");
		System.err.println("    -seed seed : long seed declares"+ 
				"the seed for randomization");
		System.err.println("    -pdf       : sets the pdf option"+ 
				"in order to have the exams output in pdf as well"+ 
				" as .tex files.");
	}
}
