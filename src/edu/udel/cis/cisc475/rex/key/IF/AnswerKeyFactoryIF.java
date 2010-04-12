package edu.udel.cis.cisc475.rex.key.IF;

/**
 * 
 /**
 * 
 * @author cardona
 * 
 */

public interface AnswerKeyFactoryIF {
	AnswerKeyIF newAnswerKey(String version, String examName, String date);
}

