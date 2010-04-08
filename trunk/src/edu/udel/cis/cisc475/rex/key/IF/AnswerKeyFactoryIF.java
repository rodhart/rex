package edu.udel.cis.cisc475.rex.key.IF;

/**
 * 
 /**
 * 
 * @author team 5
 * 
 */

public interface AnswerKeyFactoryIF {
	AnswerKeyIF newAnswerKey(String version, String examName, String date);
}

