/**
 * rex
 * edu.udel.cis.cisc475.rex.exam
 * Apr 9, 2010
 * hboyd
 */
package edu.udel.cis.cisc475.rex.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;

/**
 * @author hboyd
 *
 */
public class BlockTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static SourceIF blockSource;
	private static BlockIF block;
	
	private static String testUEFfilename = "testFileName.txt";
	private static String testTopic = "test Topic";
	private static String testLabel = "test Label";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			sourceFactory = new SourceFactoryStub();
		}
		else{
			// TODO Uncomment when entry point is available
			//sourceFactory = Sources.newSourceFactory();
			sourceFactory = new SourceFactory();
		}
		// TODO Uncomment when entry point is available
		//examFactory = Exams.newExamFactory();
		examFactory = new ExamFactory();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		blockSource = sourceFactory.newSource(testUEFfilename);
		blockSource.addText("Test Block Source");
		block = examFactory.newBlock(testLabel, blockSource);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTopic() {
		assertNotNull(block.topic());
		assertEquals(testTopic, block.topic());
	}

	@Test
	public void testGetLabel() {
		assertNotNull(block.label());
		assertEquals(testLabel, block.label());
	}
	
	@Test
	public void testGetSource() {
		SourceIF result = block.source();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(blockSource.text(), result.text());
		assertEquals(blockSource, result);
	}
}
