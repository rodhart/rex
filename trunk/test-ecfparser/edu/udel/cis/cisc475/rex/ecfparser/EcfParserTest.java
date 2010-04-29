package edu.udel.cis.cisc475.rex.ecfparser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import junit.framework.TestCase;

import java.util.TreeSet;

import edu.udel.cis.cisc475.rex.ecfparser.impl.EcfParser;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.ConfigFactoryIF;
import edu.udel.cis.cisc475.rex.config.impl.ConfigFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.err.RexParseException;



/**
 * EcfParserTest
 *
 * @author cates
 * @since 04/15/10
 */
public class EcfParserTest { 
  
  private ConfigFactoryIF configFactory;
  private IntervalFactoryIF intervalFactory;
  private SourceFactoryIF sourceFactory;

	// these help save time coding tests
	private final Double posInfinity = new Double(Double.POSITIVE_INFINITY);
	private final Double negInfinity = new Double(Double.NEGATIVE_INFINITY);

	// these are for the config equality function
	private final int GC = 1;
	private final int RPC = -1;
	private final int NEQ = 0;

  @Before
  public void setUp() {
    configFactory = new ConfigFactory();
    intervalFactory = new IntervalFactory();
    sourceFactory = new SourceFactory();
  }

  /**
  * First test using text from the requirements document.
  */
  @Test
  public void test() throws RexUnsatisfiableException {
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
    "append block:GoodbyeMessage ; # this block will be added at end\n" +
    "\n" +
    "versions are \"1/21/2010\", \"Jan.\\ 21, 2010\", \"21-jan-2010\", \"JAN.\\ 21 2010\";";

		// recreate the above config as an object to compare with the parser output
		ConfigIF realConfig = configFactory.newConfig(true, 4);

		// set ending block
		realConfig.setFinalBlock("block:GoodbyeMessage");

		// add group constraints

		realConfig.addGroupConstraint(
				"Finite State Automata",
				intervalFactory.interval(true, 0.0, false, 20.0),
				5,
				3,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addGroupConstraint(
				"ssort",
				intervalFactory.interval(true, 20.0, false, this.posInfinity),
				5,
				2,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addGroupConstraint(
				"arithmetic",
				intervalFactory.interval(true, 0.0, true, 10.0),
				3,
				1,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addGroupConstraint(
				"arithmetic",
				intervalFactory.interval(false, this.negInfinity, false, this.posInfinity),
				3,
				3,
				sourceFactory.newSource("fakefile.ecf")
		);

		// required problem constraints
		realConfig.addRequiredProblemConstraint(
				"prob:arithmetic:prime",
				5,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addRequiredProblemConstraint(
				"prob:arithmetic:composite",
				5,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addRequiredProblemConstraint(
				"prob:misc:hat",
				5,
				sourceFactory.newSource("fakefile.ecf")
		);

		realConfig.addRequiredProblemConstraint(
				"prob:misc:complexity",
				5,
				sourceFactory.newSource("fakefile.ecf")
		);

    EcfParser parser = new EcfParser(5);

		ConfigIF config;
    try {
			config = parser.parseString(reqDocExample);
			assertTrue(configEquality(config, realConfig));
		} catch (Exception e) {
			System.out.println("something went wrong");
			assertTrue(false);
		}

  }

	private boolean intervalEquality(IntervalIF a, IntervalIF b) {
		if(
				a.low().doubleValue() == b.low().doubleValue() &&
				a.high().doubleValue() == b.high().doubleValue() &&
				!(a.strictLow() ^ b.strictLow()) &&
				!(a.strictHigh() ^ b.strictHigh())
			) { return true; }
		return false;
	}

	private String intervalToString(IntervalIF i) {
		return (i.strictLow()?"(":"[") + i.low() + "," + i.high() + (i.strictHigh()?")":"]");
	}

	private boolean requiredProblemConstraintEquality(
			RequiredProblemConstraintIF a,
			RequiredProblemConstraintIF b
	) {
		if( a.label().equals(b.label()) && a.points() == b.points() ) { return true; }
		return false;
	}

	private boolean groupConstraintEquality(GroupConstraintIF a, GroupConstraintIF b) {
		if(
				a.topic().equals(b.topic()) &&
				a.points() == b.points() &&
				a.numProblems() == b.numProblems() &&
				intervalEquality(a.difficultyInterval(), b.difficultyInterval()) 
			) { return true; }
		return false;
	}


	private int sameConstraintType(ConstraintIF x, ConstraintIF y) {
		if(x instanceof GroupConstraintIF && y instanceof GroupConstraintIF) return GC;
		else if(x instanceof RequiredProblemConstraintIF && y instanceof RequiredProblemConstraintIF) return RPC;
		return NEQ;
	}

	private boolean versionsEquality(String[] a, String[] b) {
		if(a.length != b.length) { return false; }
		TreeSet<String> aa = new TreeSet<String>();
		TreeSet<String> bb = new TreeSet<String>();

		for(String s : a) { aa.add(s); }
		for(String s : b) { bb.add(s); }

		return aa.equals(bb);
	}

	/**
	 * Check two configs for good-enough equality.
	 */
	private boolean configEquality(ConfigIF a, ConfigIF b) {
	
		// check final block equality
		if(!a.finalBlock().equals(b.finalBlock())) { return false; }


		// check seed equality
//		if(a.seed() != b.seed()) { return false; }

		// check pdf option
//		if(a.pdfOption() != b.pdfOption()) { return false; }

//		if(!versionsEquality(a.versionStrings(), b.versionStrings())) { return false; }

		// make sure the constraints collections are the same size
		if(a.constraints().size() != b.constraints().size()) { return false; }

		// this is O(n^2), but whatever. I don't have any other way of doing it for now.
		for(ConstraintIF ac : a.constraints()) {
			boolean found = false;
			for(ConstraintIF bc : b.constraints()) {
				int typeEq = sameConstraintType(ac, bc);
				if(typeEq == GC) {
					if(groupConstraintEquality((GroupConstraintIF)ac, (GroupConstraintIF)bc)) {
						found = true;
						break;
					}
				}
				else if(typeEq == RPC) {
					if(requiredProblemConstraintEquality((RequiredProblemConstraintIF)ac, (RequiredProblemConstraintIF)bc)) {
						found = true;
						break;
					}
				}
				
			}
			if(!found) { return false; }
		}

		return true;
	}
}

// vim: ts=2:sw=2:
