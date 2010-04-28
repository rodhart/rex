package udel.edu.cis.cisc475.rex.err;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

public class RexParseExceptionTest {

	/**
	 * @author Daniel Krapf
	 */
	
	private static SourceFactoryIF sourceFactory = new SourceFactory();
	private static SourceIF source1 = sourceFactory.newSource("testFile.txt");
	
	private static RexParseException ex = new RexParseException();
	private static RexParseException ex2 = new RexParseException();
	private static RexParseException ex3 = new RexParseException("Exception Message",source1);
	private static RexParseException ex4 = new RexParseException("Exception Message",source1);
	private static RexParseException ex5 = new RexParseException("Different Exception Message",source1);
	private static RexParseException ex6;
	private static RexParseException ex7;
	
	@Test
	public void testRexParseException(){
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

