package edu.udel.cis.cisc475.rex.ecfparser;

import static org.junit.Assert.*;

import org.junit.Test;
import junit.framework.TestCase;

import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParser;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;



/**
 * EcfParserTest
 *
 * @author cates
 * @since 04/15/10
 */
public class EcfParserTest { 
  
  
  /**
  * First test using text from the requirements document.
  */
  @Test
  public void test() {
    String reqDocExample = "include 5 problems on \"Finite State Automata\" with difficulty in [0,20)\n" +
    "  at 3 points;\n" +
    "include 5 problems on \"ssort\" with difficulty in [20,\\infty) at 2 points;\n" +
    "include 3 problems on \"arithmetic\" with difficulty in [0,10] at 1 points;\n" +
    "include 3 problems on \"arithmetic\" with difficulty in (-\\infty,\\infty) at 3 points;\n" +
    "\n" +
    "# the following problems will be included in their corresponding\n" +
    "# sections on any generated exam\n" +
    "includeall prob:arithmetic:prime, prob:arithmetic:composite, prob:misc:hat,\n" +
    "  prob:misc:complexity at 5 points;\n" +
    "\n" +
    "append \"Goodbye Message\"; # this block will be added at end\n" +
    "\n" +
    "versions are \"1/21/2010\", \"Jan.\\ 21, 2010\", \"21-jan-2010\", \"JAN.\\ 21 2010\";";

    EcfParser parser = new EcfParser(5);
    ConfigIF config = parser.parseString(reqDocExample);

    assertEquals("Goodbye Message", config.finalBlock());
  }
}