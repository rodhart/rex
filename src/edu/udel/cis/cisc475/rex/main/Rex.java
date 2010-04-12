package edu.udel.cis.cisc475.rex.main;

public class Rex {

	static void main(String[] args)
	{ 

		int numArgs = args.length();
		long seed = 1;
		boolean pdfOpt = false;
		int numExams = 0;
		if (numArgs < 3) {
			System.err
					.println("Usage: rex [options] <UEF filename> <ECF filename>");
			exit(-1);
		}
		int i = 1;
		while ( i < numArgs - 2) {
			if (numExams == 0 && args[i].equals("-n")) {
				numExams = Integer.parseInt(args[i+1]);
				i += 2;
			} 
			else if (seed == 1 && args[i].equals("-seed")) {
				seed = Long.parseLong(args[i+1]);
				i+=2
			} 
			else if (!pdfOpt && args[i].equals("-pdf")) {
				pdfOpt = true;
				i++;
				
			}
			else{
				System.err.println(args[i] + " is not a valid option!");
				exit(-1);
			}
		}

	}
}
