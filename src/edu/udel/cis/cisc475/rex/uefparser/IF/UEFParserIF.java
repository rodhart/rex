package edu.udel.cis.cisc475.rex.uefparser.IF;

import java.io.File;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;

/**
 * Interface to the Universal Exam Parser (UEF).
 * 
 * @author Ahmed El-Hassany
 * 
 */
public interface UEFParserIF {
	/**
	 * Parse UEF file and generate ExamIF of it.
	 * 
	 * @param file
	 *            the file handler for the ueffile.
	 * @return ExamIF of the uef file.
	 */
	ExamIF parse(File file);
}

