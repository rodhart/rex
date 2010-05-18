package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class RexUnsatisfiableExceptionTest {

	/**
	 * @author Daniel Krapf
	 */
	
	private static SourceFactoryIF sourceFactory = new SourceFactory();
	private static SourceIF source1 = sourceFactory.newSource("testFile.txt");
	
	private static RexUnsatisfiableException ex = new RexUnsatisfiableException();
	private static RexUnsatisfiableException ex2 = new RexUnsatisfiableException();
	private static RexUnsatisfiableException ex3 = new RexUnsatisfiableException("Exception Message",source1);
	private static RexUnsatisfiableException ex4 = new RexUnsatisfiableException("Exception Message",source1);
	private static RexUnsatisfiableException ex5 = new RexUnsatisfiableException("Different Exception Message",source1);
	private static RexUnsatisfiableException ex6;
	private static RexUnsatisfiableException ex7;
	
	@Test
	public void testRexUnsatisfiableException(){
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
		String message1 = ex.toString();
		String message2 = ex5.toString();
		boolean result = message1.equals(message2);
		assertFalse(result);
	}
	@Test
	public void testSourceEquals()
	{
		SourceIF s1 = ex3.source();
		SourceIF s2 = ex4.source();
		assertEquals(s1,s2);
	}
}

