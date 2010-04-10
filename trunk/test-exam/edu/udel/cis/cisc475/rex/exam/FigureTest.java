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

import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.examstubs.SourceFactoryStub;

/**
 * @author hboyd
 *
 */
public class FigureTest {
	public final static boolean useStubs = true;
	
	private static SourceFactoryIF sourceFactory;
	private static ExamFactoryIF examFactory;
	
	private static SourceIF figureSource;
	private static FigureIF figure;
	
	private static String testUEFfilename = "testFileName.txt";
	private static String testLabel = "test Label";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (useStubs) {
			sourceFactory = new SourceFactoryStub();
		}
		else{
			// TODO Uncomment when entry point is available
			//sourceFactory = Sources.newSourceFactory();
			sourceFactory = null;
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
		figureSource = sourceFactory.newSource(testUEFfilename);
		figureSource.addText("Test Figure Source");
		figure = examFactory.newFigure(testLabel, figureSource);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSource() {
		SourceIF result = figure.source();
		assertNotNull(result);
		assertNotNull(result.text());
		assertEquals(figureSource.text(), result.text());
		assertEquals(figureSource, result);
	}
	
	@Test
	public void testGetLabel() {
		assertNotNull(figure.label());
		assertEquals(testLabel, figure.label());
	}
}
