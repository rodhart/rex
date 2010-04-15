package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;

public class RexUnsatisfiableExceptionTest {

	//A further test will be done when the Err module is fully implemented.
	
	private static RexUnsatisfiableException ex;
	private static RexUnsatisfiableException ex2;
	
	@Test
	public void testRexUnsatisfiableException(){
	
	assertEquals(ex,ex2);
	}
}

