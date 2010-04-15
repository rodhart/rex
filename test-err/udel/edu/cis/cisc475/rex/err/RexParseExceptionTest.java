package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexParseException;

public class RexParseExceptionTest {

	//A further test will be done when the Err module is fully implemented.
	
	private static RexParseException ex;
	private static RexParseException ex2;
	
	@Test
	public void testRexParseException(){
	
	assertEquals(ex,ex2);
	}
}
