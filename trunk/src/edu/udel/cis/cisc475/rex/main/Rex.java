package edu.udel.cis.cisc475.rex.main;

import java.io.File;

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
			System.err
					.println("Usage: rex [options] <UEF filename> <ECF filename>");
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
			System.exit(-1);
		}
		
	}
}
