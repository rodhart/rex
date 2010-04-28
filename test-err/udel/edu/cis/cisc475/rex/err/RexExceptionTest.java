package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.err.RexException;

public class RexExceptionTest {

	/**
	 * @author Daniel Krapf
	 */
	
	private static RexException ex = new RexException();
	private static RexException ex2 = new RexException();
	private static RexException ex3 = new RexException("Exception Message");
	private static RexException ex4 = new RexException("Exception Message");
	private static RexException ex5 = new RexException("Different Exception Message");
	private static RexException ex6;
	private static RexException ex7;
	
	@Test
	public void testRexException(){
		assertEquals(ex6,ex7);
	}
	
	@Test
	public void testExceptionNotEqualsNull() {  	
    	boolean result = ex.equals(null);
    	assertFalse(result);
    }
	
	@Test
    public void testExceptionNotEqualsNonException() {  	
    	boolean result = ex.equals(5);
    	assertFalse(result);
    }
	
	@Test
	public void testDefaultMessage(){
		String message1 = ex.getMessage();
		String message2 = ex2.getMessage();
		assertEquals(message1,message2);
	}
	
	@Test
	public void testSameMessage(){
		String message1 = ex3.getMessage();
		String message2 = ex4.getMessage();
		assertEquals(message1,message2);
	}
	
	@Test
	public void testDifferentMessage(){
		String message1 = ex3.getMessage();
		String message2 = ex5.getMessage();
		boolean result = message1.equals(message2);
		assertFalse(result);
	}
	
	@Test
	public void testDefaultvCustomMessage(){
		String message1 = ex.getMessage();
		String message2 = ex5.getMessage();
		boolean result = message1.equals(message2);
		assertFalse(result);
	}

}
