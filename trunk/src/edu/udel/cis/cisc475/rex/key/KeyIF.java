//package edu.udel.cis.cisc475.rex.source.IF;

import java.util.*;
import java.io.*;

/**
 * 


/**
 * @author team 5
 *
 */
public interface KeyIF {
	String version(); 
	String examName(); 
	String date(); 
	int numProblems(); 
	Collection <String> answers(int i); 
	void addProblem( Collection <String> Problem);
}//end of interface 
 
