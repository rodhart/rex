/**
 * 
 */
package edu.udel.cis.cisc475.rex.uefparser;

import java.io.File;
import java.io.FileNotFoundException;

import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserFactoryIF;
import edu.udel.cis.cisc475.rex.uefparser.IF.UEFParserIF;
import edu.udel.cis.cisc475.rex.uefparser.impl.UEFParserFactory;

/**
 * UEFParser test cases
 * 
 * @author Ahmed El-Hassany
 * 
 */
public class UEFParserTest {

	/**
	* Program Entry Test
	*
	* @param args
	* @throws FileNotFoundException
	*/
	public static void main(String[] args)
	{
		UEFParserFactoryIF f = new UEFParserFactory();
	UEFParserIF parser = f.newUEFParser();
	File file = new File("/home/ahassany/cisc675/test.tex");
	parser.parse(file);
	return;
	}
}
