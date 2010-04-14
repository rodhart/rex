package edu.udel.cis.cisc475.rex.uefparser.impl;

import java.io.File;

import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;

public class UEFPasrerMain {
	public static void main(String[] args)
	{
	UEFParserIF parser = new UEFParser();
	File file = new File("/home/ahassany/cisc675/test.tex");
	parser.parse(file);
	return;
	}
}
