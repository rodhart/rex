package edu.udel.cis.cisc475.rex.uefparser.IF;

import java.io.File;

import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;

/**
 * Interface for the parser of the Universal Exam File (UEF).
 * Provides interfaces for methods that any class implementing
 * a UEF parser must implement.
 * 
 * @author Ahmed El-Hassany
 * @author Aaron Myles Landwehr
 * 
 */
public interface UEFParserIF {
	/**
	 * Any class implementing this method needs to be able
	 * to internally open and parse any tex file using the
	 * exam documentclass. It also must be able to provide
	 * a valid and correct ExamIF object from that.
	 * 
	 * @param file
	 *            the file handler for the ueffile.
	 * @return ExamIF of the uef file.
	 */
	ExamIF parse(File file);
}

