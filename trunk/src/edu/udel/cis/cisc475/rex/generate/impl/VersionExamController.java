package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;
import java.util.LinkedHashMap;

/**
 * @author Greg Simons
 * 
 * A VersionExamController aids in the dissemination of all pertinent data
 * surrounding randomly generated ExamIFs.
 */

public class VersionExamController 
{
	private MasterExamController mec;
	private ExamFactoryIF theExamFactory = new ExamFactory();
	private RandomizerIF theRandomizer;
	
	private HashMap<Integer, ProblemIF> subset = new LinkedHashMap<Integer, ProblemIF>();
	private HashMap<String, TopicContainer> theTCs = new LinkedHashMap<String, TopicContainer>();
	
	/**
	 * @param mec
	 * 				-A MasterExamController that feeds the VersionExamController
	 * 				 data as necessary.
	 * @param theRandomizer
	 * 				-A RandomizerIF, used extensively to randomize the random exam
	 * 				 as much as possible. The same RandomizerIF is used everywhere
	 * 				 to ensure the same random exams will be generated on the same
	 * 				 seed.
	 */
	
	public VersionExamController(MasterExamController mec, RandomizerIF theRandomizer) 
	{ 
		this.mec = mec;
		this.theRandomizer = theRandomizer; 
	}
	
	/**
	 * @param topic
	 * 			-Topic identifying the TopicContainer to be returned
	 * 			 (a new TopicContainer will be instantiated if one does not already exist.)
	 * @return
	 * 			-TopicContainer specified by the topic taken as input.
	 */
	
	public TopicContainer getTC(String topic)
	{
		TopicContainer theTC;
		
		if (this.theTCs.containsKey(topic))
			return (TopicContainer) this.theTCs.get(topic);
		
		else
		{
			theTC = new TopicContainer(topic);
			this.theTCs.put(topic, theTC);
			
			return theTC;
		}
	}
	
	/**
	 * @param inputProblem
	 * 				-A ProblemIF whose non-FixedAnswers need to be randomized.
	 * @return
	 * 				-An AnswerIF[] with randomized non-FixedAnswers from the
	 * 				 answer set of the ProblemIF taken as input.
	 * @throws NullPointerException
	 * 				-If theRandomizer.choose() fails (or a logic error causes an invalid data set
	 * 				 to be randomized).
	 */
	
	public AnswerIF[] randomizeAnswers(ProblemIF inputProblem)
	{
		AnswerIF[] theAnswers = inputProblem.answers();
		Collection<AnswerIF> beforeRandomization = new ArrayList<AnswerIF>();
		AnswerIF[] afterRandomization;
		int j = 0;
		
		//theConstraint.getClass().getSimpleName().equals("GroupConstraint")
		
		
		for (int i = 0; i < theAnswers.length; i++)
		//	if (!(theAnswers[i].getClass().getSimpleName().equals("FixedAnswerIF")))
			if (!(theAnswers[i] instanceof FixedAnswerIF))
				beforeRandomization.add(theAnswers[i]);
		
		//Next 6 lines are maybe a fix to the problem we were having.  Certainly there is a better way.
		Object[] array = this.theRandomizer.choose(beforeRandomization.size(), beforeRandomization.toArray());
		afterRandomization = new AnswerIF[array.length];
		for(int i = 0; i < array.length; i++)
		{
			afterRandomization[i] = (AnswerIF)array[i];
		}
		
		//afterRandomization = (AnswerIF[]) this.theRandomizer.choose(beforeRandomization.size(), beforeRandomization.toArray());
		
		for (int i = 0; i < theAnswers.length; i++)
		//	if (!(theAnswers[i].getClass().getSimpleName().equals("FixedAnswerIF")))
			if (!(theAnswers[i] instanceof FixedAnswerIF))
			{
				theAnswers[i] = afterRandomization[j];
				j++;
			}
		
		return theAnswers;
	}
	
	/**
	 * @param input
	 * 			-An integer, assumed to be the index in an array of a
	 * 			 ProblemIFs answer set.
	 * @return
	 * 			-A letter depending on the index given
	 * 			 as input. Appears to match what the key module
	 * 			 expects; subject to change.
	 */
	
	public String answerChar(int input)
	{
		input += 65;
		return String.valueOf((char) input);
	}
	
	/**
	 * @param inputProblem
	 * 				-A ProblemIF whose correct answers need to be
	 * 				 added to an answer key.
	 * @return
	 * 				-A Collection of Strings, formed from the
	 * 				 answerChars of the indices of the correct answers
	 * 				 in the input ProblemIF's answer set.
	 */
	
	public Collection<String> correctAnswers(ProblemIF inputProblem)
	{
		Collection<String> returnSet = new ArrayList<String>();
		
		for (int i = 0; i < inputProblem.answers().length; i++)
			if (inputProblem.answers()[i].isCorrect())
				returnSet.add(answerChar(i));
		
		return returnSet;
	}
	
	/**
	 * Adds to the VersionExamController all problems from the
	 * MasterExamController that are required.
	 */
	
	public void addRequiredProblems()
	{
		this.subset.putAll(this.mec.getRequiredProblems());
	}
	
	
	/* Test to determine that addRequiredProblems (above) is working as intended.
	 */
	
//	public void testOne()
//	{
//		System.out.println("The number of required problems is: " + this.subset.size());
//		
//		Iterator<ProblemIF> problemIterator = this.subset.values().iterator();
//		
//		int i = 0;
//		
//		while (problemIterator.hasNext())
//		{
//			ProblemIF theProblem = problemIterator.next();
//			
//			System.out.println("Problem " + i + " is " + theProblem.question().text());
//			i++;
//		}
//	}

	/**
	 * Adds to the VersionExamController a number of problems from
	 * the MasterExamController's GroupConstraintContainers specified
	 * by each GroupConstraintContainer's internal set of ProblemIFs,
	 * and specified number of ProblemIFs required.
	 * 	
	 * @throws RexUnsatisfiableException
	 * 						-If a RequiredProblemConstraintIF or conflicting
	 * 						 GroupConstraintIFs preclude a GroupConstraintIF from
	 * 						 being fulfilled.
	 * @throws NullPointerException
	 * 	  					-If theRandomizer.choose() fails (or a logic error causes an invalid data set
	 * 				 		 to be randomized).
	 */
	
	public void satisfyConstraints() throws RexUnsatisfiableException
	{
		Iterator<TopicOrganizer> TOIterator =  this.mec.getTheTOs().values().iterator();
		TopicOrganizer theTO = null;
		
		Iterator<GroupConstraintContainer> gccIterator;
		GroupConstraintContainer theGCC;
		
		Collection<ProblemIF> superset = new ArrayList<ProblemIF>();
		ProblemIF randomProblem;
		
		Integer identifier;
		int problemsAdded = 0;
		
		while (TOIterator.hasNext())
		{
			theTO = TOIterator.next();
			
			gccIterator = theTO.getGCCs().iterator();
			
			while (gccIterator.hasNext())
			{
				theGCC = gccIterator.next();

				this.mec.getOverlaps(theTO, theGCC);
				superset.addAll(theGCC.getSet());
				
				while (!superset.isEmpty() && problemsAdded < theGCC.getConstraintValue())
				{	
					randomProblem = (ProblemIF) (this.theRandomizer.choose(1, superset.toArray()))[0];
					identifier = (Integer) this.mec.getIdentifiers().get(randomProblem);
					
					if (!this.subset.containsKey(identifier))
					{
						this.subset.put(identifier, randomProblem);
						problemsAdded++;
					}
					
					superset.remove(randomProblem);
				}
				
				if (problemsAdded < theGCC.getConstraintValue())
				{
					//Temporary error message pending the resolution of Ticket #130.
					System.out.println("The constraint requesting " + theGCC.getConstraintValue() +
 									" problem(s) from " + theGCC.getTopic() +
 									" of point value " + theGCC.getPointValue() +
 									" within difficulty " + theGCC.getDifficultyInterval().low() +
 									" through " + theGCC.getDifficultyInterval().high() +
									" is unsatisfiable.");
					
					throw new RexUnsatisfiableException("The constraint requesting " + theGCC.getConstraintValue() +
							 							" problem(s) from " + theGCC.getTopic() +
							 							" of point value " + theGCC.getPointValue() +
							 							" within difficulty " + theGCC.getDifficultyInterval().low() +
							 							" through " + theGCC.getDifficultyInterval().high() +
														" is unsatisfiable.", theGCC.getSource());
				}
				
				problemsAdded = 0;
				superset.clear();
			}
		}
	}
	
	/**
	 * Adds every problem to a FigureContainer, every
	 * FigureContainer to a BlockContainer, and every
	 * BlockContainer to a TopicContainer.
	 */
	
	public void distributeProblems()
	{
		Collection<ProblemIF> subsetCollection = this.subset.values();
		TopicContainer theTC = null;
		BlockContainer theBC = null;
		FigureContainer theFC = null;
		
		for (ProblemIF currentProblem : subsetCollection)
		{
			theTC = this.getTC(currentProblem.topic());
			
			if (currentProblem.requiredBlock() == null)
				theBC = theTC.getBC(null);
			
			else
				theBC = theTC.getBC(currentProblem.requiredBlock());
			
			if (currentProblem.referencedFigures().isEmpty())
			{
				theFC = theBC.getFC(null);
				theFC.addProblem(currentProblem);
			}
			
			else
				for (FigureIF currentFigure : currentProblem.referencedFigures())
				{
					theFC = theBC.getFC(currentFigure);
					theFC.addProblem(currentProblem);
				}
		}
	}
	
	/* Old distributeProblems; rewritten above.
	 */
	
//	public void distributeProblems()
//	{
//		Collection<ProblemIF> subsetCollection = this.subset.values();
//		TopicContainer theTC;
//		BlockContainer theBC;
//		FigureContainer theFC = null;
//		
//		for (ProblemIF currentProblem : subsetCollection)
//		{
//			theTC = this.getTC(currentProblem.topic());
//			theBC = theTC.getBC(currentProblem.requiredBlock());
//			
//			if (currentProblem.referencedFigures().isEmpty())
//			{
//				theFC = theBC.getFC(null);
//				theFC.addProblem(currentProblem);
//			}
//			
//			else
//				for (FigureIF currentFigure : currentProblem.referencedFigures())
//				{
//					theFC = theBC.getFC(currentFigure);
//					theFC.addProblem(currentProblem);
//				}
//		}
//	}
	
	/**
	 * @param versionExam
	 * 				-The ExamIF that will have ExamElementIFs added to it.
	 * @param versionAnswerKey
	 * 				-The AnswerKey that will have correct answer sets added to it.
	 * @throws NullPointerException
	 * 	  			-If theRandomizer.choose() fails (or a logic error causes an invalid data set
	 * 				 to be randomized).
	 * 
	 * 1.) Randomly selects a TopicOrganizer, a BlockContainer within the TopicOrganizer,
	 * 	   a FigureContainer within the BlockContainer, and a Problem within the FigureConatiner.
	 * 
	 * 2.) Adds the Block to the Exam, if it is not null. Adds the Figure to the Exam if it
	 * 	   is not null, and has not already been added to the Exam.
	 * 
	 * 3.) If the Problem has not already been added to the Exam, for each Figure it references,
	 *     add that Figure to the Exam if it has not already been added. If the Problem references
	 *     no Figures, no element will be added to the Exam.
	 *     
	 * 4.) If the Problem has Answers, generate a new Problem with the same information, save
	 *     a shuffled fixed answer set. Add the Problem to the Exam, and its correct Answers to 
	 *     the AnswerKey (if applicable).
	 *     
	 * 5.) Continue this process for all other Answers in the FigureContainer, then all other
	 * 	   FigureContainers in the BlockContainer, then all other BlockContainers in the
	 * 	   TopicContainer, then all other TopicContainers.
	 * 
	 * The commented out print statements demonstrate the validity of this algorithm.
	 */
	
	public void fillExam(ExamIF versionExam, AnswerKeyIF versionAnswerKey)
	{
		Map<Integer, ExamElementIF> figureBlacklist = new LinkedHashMap<Integer, ExamElementIF>();
		Map<Integer, ProblemIF> problemBlacklist = new LinkedHashMap<Integer, ProblemIF>();
		Integer problemIdentifier;
		Integer primaryFigureIdentifier;
		Integer secondaryFigureIdentifier;
		Integer blockFigureIdentifier;
		
		Collection<TopicContainer> myTCs = new ArrayList<TopicContainer>();
		TopicContainer theTC;
		
		Collection<BlockContainer> myBCs = new ArrayList<BlockContainer>();
		BlockContainer theBC;
		
		Collection<FigureContainer> myFCs = new ArrayList<FigureContainer>();
		FigureContainer theFC;
		
		Collection<ProblemIF> myProblems = new ArrayList<ProblemIF>();
		ProblemIF theProblem;
		
		myTCs.addAll(this.theTCs.values());
		
		
		while (!myTCs.isEmpty())
		{
			theTC = (TopicContainer) (this.theRandomizer.choose(1, myTCs.toArray(new TopicContainer[myTCs.size()])))[0];
			myBCs.addAll(theTC.getBCs().values());
			
			while (!myBCs.isEmpty())
			{
				theBC = (BlockContainer) (this.theRandomizer.choose(1, myBCs.toArray(new BlockContainer[myBCs.size()])))[0];
				myFCs.addAll(theBC.getFCs().values());
				
				if (theBC.getBlock() != null)
				{
					versionExam.addElement(theBC.getBlock());
				
					for (ExamElementIF theElement : this.mec.getMaster().elementsUsingElement(theBC.getBlock()))
						if (theElement instanceof FigureIF)
						{	
							blockFigureIdentifier = (Integer) this.mec.getIdentifiers().get(theElement);
						
							if (!figureBlacklist.containsKey(blockFigureIdentifier))
							{
								versionExam.addElement(theElement);
								figureBlacklist.put(blockFigureIdentifier, theElement);
							}
						
						}
				}				
				
				while (!myFCs.isEmpty())
				{
					theFC = (FigureContainer) (this.theRandomizer.choose(1, myFCs.toArray(new FigureContainer[myFCs.size()])))[0];
					myProblems.addAll(theFC.getProblems());
					
					primaryFigureIdentifier = (Integer) this.mec.getIdentifiers().get(theFC.getFigure());
					
					if (theFC.getFigure() != null && !figureBlacklist.containsKey(primaryFigureIdentifier))
					{
						versionExam.addElement(theFC.getFigure());
						figureBlacklist.put(primaryFigureIdentifier, theFC.getFigure());
					}
					
					while (!myProblems.isEmpty())
					{
						theProblem = (ProblemIF) (this.theRandomizer.choose(1, myProblems.toArray(new ProblemIF[myProblems.size()])))[0];
						
						problemIdentifier = (Integer) this.mec.getIdentifiers().get(theProblem);
						
						if (!problemBlacklist.containsKey(problemIdentifier))
						{
							for (FigureIF secondaryFigure : theProblem.referencedFigures())
							{
								secondaryFigureIdentifier = (Integer) this.mec.getIdentifiers().get(secondaryFigure);
								
								if (!figureBlacklist.containsKey(secondaryFigureIdentifier))
								{
									versionExam.addElement(secondaryFigure);
									figureBlacklist.put(secondaryFigureIdentifier, secondaryFigure);
								}
							}
							
							if (theProblem.answers() != null)
							{
//								System.out.println(theProblem.question().text() + ", ");
//								
//								if (theFC.getFigure() == null)
//									System.out.println("no figure, ");
//								
//								else
//									System.out.println(theFC.getFigure().source().text());
//								
//								if (theBC.getBlock() == null)
//									System.out.println("no block, ");
//								
//								else
//									System.out.println(theBC.getBlock().source().text());
//								
//								System.out.println(theTC.getTopic() + ".\n");
								
								
								
								ProblemIF newProblem = this.theExamFactory.newProblem(theProblem.topic(), 
																					  theProblem.label(), 
																					  theProblem.question(), 
																					  randomizeAnswers(theProblem));
								newProblem.setDifficulty(theProblem.difficulty());
								versionExam.addElement(newProblem);
								versionAnswerKey.addProblem(correctAnswers(newProblem));
							}
							
							else
							{
								versionExam.addElement(theProblem);
								
								Collection<String> answerSet = new ArrayList<String>();
								answerSet.add("Problem with no Answers.");
								versionAnswerKey.addProblem(answerSet);
							}
							
							problemBlacklist.put(problemIdentifier, theProblem);
						}
						
						myProblems.remove(theProblem);
					}
					
					myFCs.remove(theFC);
				}
				
				myBCs.remove(theBC);
			}
			
			myTCs.remove(theTC);
		}
		
		if (this.mec.getFinalBlock() != null)
			versionExam.addElement(this.mec.getFinalBlock());
		
		
//		for (int k = 0; k < versionExam.elements().size(); k++)
//			if (versionExam.element(k) instanceof ProblemIF)
//				System.out.println(((ProblemIF) versionExam.element(k)).question().text());
//		
//			else if (versionExam.element(k) instanceof FigureIF)
//				System.out.println(((FigureIF) versionExam.element(k)).source().text());
//		
//			else if (versionExam.element(k) instanceof BlockIF)
//				System.out.println(((BlockIF) versionExam.element(k)).source().text());
	}
}
	
/* Old fillExam, rewritten above.
 */

//	public void fillExam(ExamIF versionExam, AnswerKeyIF versionAnswerKey)
//	{
//		HashMap figureBlacklist = new HashMap();
//		HashMap problemBlacklist = new HashMap();
//		Integer identifier;
//		Integer otherFigureIdentifier;
//		
//		Collection<TopicContainer> myTCs = new ArrayList<TopicContainer>();
//		
//		myTCs.addAll(this.theTCs.values());
//
//		TopicContainer theTC;
//		
//		Collection<BlockContainer> myBCs = new ArrayList<BlockContainer>();
//		BlockContainer theBC;
//		
//		Collection<FigureContainer> myFCs = new ArrayList<FigureContainer>();
//		FigureContainer theFC;
//		
//		ProblemIF[] theProblemsArray;
//		
//		while (!myTCs.isEmpty())
//		{
//			theTC = (TopicContainer) (this.theRandomizer.choose(1, myTCs.toArray(new TopicContainer[myTCs.size()])))[0];
//			
//			myBCs.addAll(theTC.getBCs().values());
//			
//			while (!myBCs.isEmpty())
//			{
//				theBC = (BlockContainer) (this.theRandomizer.choose(1, myBCs.toArray(new BlockContainer[myBCs.size()])))[0];
//				
//				if (theBC.getBlock() != null)
//					versionExam.addElement(theBC.getBlock());
//				
//				myFCs.addAll(theBC.getFCs().values());
//				
//				while (!myFCs.isEmpty())
//				{
//					theFC = (FigureContainer) (this.theRandomizer.choose(1, myFCs.toArray(new FigureContainer[myFCs.size()])))[0];		
//					
//					identifier = (Integer) this.mec.getIdentifiers().get(theFC.getFigure());
//					
//					if (!figureBlacklist.containsKey(identifier))
//					{
//						if (theFC.getFigure() != null)
//							versionExam.addElement(theFC.getFigure());
//						
//						figureBlacklist.put(identifier, theFC.getFigure());
//					}
//					
//					//Next 6 lines are maybe a fix to the problem we were having.  Certainly there is a better way.
//					Object[] array = this.theRandomizer.choose(theFC.getProblems().size(), theFC.getProblems().toArray());
//					theProblemsArray = new ProblemIF[array.length];
//					for(int i = 0; i < array.length; i++)
//					{
//						theProblemsArray[i] = (ProblemIF)array[i];
//					}
//					
//					//The line that was causing the problem, fixed (maybe?) by the 6 lines above
//					//theProblemsArray = (ProblemIF[]) (this.theRandomizer.choose(theFC.getProblems().size(), theFC.getProblems().toArray(new ProblemIF[theFC.getProblems().size()])));
//					
//					for (int i = 0; i < theProblemsArray.length; i++)
//					{
//						identifier = (Integer) this.mec.getIdentifiers().get(theProblemsArray[i]);
//						
//						if (!problemBlacklist.containsKey(identifier))
//						{
//							for (FigureIF otherFigure : theProblemsArray[i].referencedFigures())
//							{
//								otherFigureIdentifier = (Integer) this.mec.getIdentifiers().get(otherFigure);
//								
//								if (!figureBlacklist.containsKey(otherFigureIdentifier))
//								{
//									versionExam.addElement(otherFigure);
//									figureBlacklist.put(otherFigureIdentifier, otherFigure);
//								}
//							}
//							
//							ProblemIF newProblem = this.theExamFactory.newProblem(theProblemsArray[i].topic(), 
//																			 	  theProblemsArray[i].label(), 
//																			 	  theProblemsArray[i].question(), 
//																			 	  randomizeAnswers(theProblemsArray[i]));
//							
//							newProblem.setDifficulty(theProblemsArray[i].difficulty());
//							versionExam.addElement(newProblem);
//							versionAnswerKey.addProblem(correctAnswers(newProblem));
//							problemBlacklist.put(identifier, theProblemsArray[i]);
//						}
//					}
//					
//					myFCs.remove(theFC);
//				}
//				
//				myBCs.remove(theBC);
//			}
//			
//			myTCs.remove(theTC);
//		}
//		
//		if (this.mec.getFinalBlock() != null)
//			versionExam.addElement(this.mec.getFinalBlock());
//		
//	}
//}


