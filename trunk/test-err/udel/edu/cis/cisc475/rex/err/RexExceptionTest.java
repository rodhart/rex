package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.udel.cis.cisc475.rex.err.RexException;

public class RexExceptionTest {

	//A further test will be done when the Err module is fully implemented.
	
	private static RexException ex;
	private static RexException ex2;
	
	@Test
	public void testRexException(){
	
	assertEquals(ex,ex2);
	}
}
