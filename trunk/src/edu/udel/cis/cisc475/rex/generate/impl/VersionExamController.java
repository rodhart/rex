package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.config.IF.ConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.AnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamFactoryIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.FigureIF;
import edu.udel.cis.cisc475.rex.exam.IF.FixedAnswerIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;
import edu.udel.cis.cisc475.rex.exam.impl.ExamFactory;
import edu.udel.cis.cisc475.rex.exam.impl.Problem;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;
import edu.udel.cis.cisc475.rex.random.IF.RandomizerIF;

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
	
	private HashMap subset = new HashMap();
	private HashMap theTCs = new HashMap();
	
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
	 * 			-A letter, A through G, depending on the index given
	 * 			 as input. Appears to match what the key module
	 * 			 expects; subject to change.
	 */
	
	public String answerChar(int input)
	{
		switch (input)
		{
			case 0: return "A"; 
			case 1: return "B"; 
			case 2: return "C"; 
			case 3: return "D"; 
			case 4: return "E"; 
			case 5: return "F"; 
			case 6: return "G"; 
			default: return "?";
		}
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
					throw new RexUnsatisfiableException("The constraint requesting " + theGCC.getConstraintValue() +
							 							" problem(s) from " + theGCC.getTopic() +
							 							" of point value " + theGCC.getPointValue() +
							 							" within difficulty " + theGCC.getDifficultyInterval().low() +
							 							" through " + theGCC.getDifficultyInterval().high() +
														" is unsatisfiable.", theGCC.getSource());
			}
			
			superset.clear();
			problemsAdded = 0;
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
		TopicContainer theTC;
		BlockContainer theBC;
		FigureContainer theFC = null;
		
		for (ProblemIF currentProblem : subsetCollection)
		{
			theTC = this.getTC(currentProblem.topic());
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
	 * 	   and a FigureContainer within the BlockContainer.
	 * 
	 * 2.) Adds the FigureIF to the ExamIF (if it has not already been added), 
	 * 	   adds the FigureIF to a blacklist of FigureIFs already added to the ExamIF 
	 * 	   (since more than one ProblemIF can refer to a FigureIF), and randomly selects a 
	 * 	   ProblemIF within the FigureContainer.
	 * 
	 * 3.) If the ProblemIF is not in a blacklist of ProblemIFs already added to the ExamIF
	 * 	   (since a ProblemIF can live in multiple FigureContainers), for each FigureIF
	 * 	   the ProblemIF references, if it is not already in the FigureIF blacklist, add it to the ExamIF 
	 * 	   then add it to the blacklist. Then, generate a new ProblemIF that
	 *     contains all of the same information as the current ProblemIF, with a randomized
	 *     answer set. Add the new ProblemIF to the ExamIF, its correct answers to the AnswerKeyIF,
	 *     and then the ProblemIF to the problem blacklist.
	 *     
	 * 4.) Continue this process for all ProblemIFs in the FigureContainer. Then remove the FigureContainer
	 *     from the set of FigureContainers to be processed. Repeat until all FigureContainers
	 *     in the BlockContainer have been processed, then remove the BlockContainer from the set
	 *     of BlockContainers to be processed. Repeat until all BlockContainers in the TopicContainer
	 *     have been processed, then remove the TopicContainer from the set of TopicContainers
	 *     to be processed.
	 */
	
	public void fillExam(ExamIF versionExam, AnswerKeyIF versionAnswerKey)
	{
		HashMap figureBlacklist = new HashMap();
		HashMap problemBlacklist = new HashMap();
		Integer identifier;
		Integer otherFigureIdentifier;
		
		Collection<TopicContainer> myTCs = new ArrayList<TopicContainer>();
		
		myTCs.addAll(this.theTCs.values());

		TopicContainer theTC;
		
		Collection<BlockContainer> myBCs = new ArrayList<BlockContainer>();
		BlockContainer theBC;
		
		Collection<FigureContainer> myFCs = new ArrayList<FigureContainer>();
		FigureContainer theFC;
		
		ProblemIF[] theProblemsArray;
		
		while (!myTCs.isEmpty())
		{
			theTC = (TopicContainer) (this.theRandomizer.choose(1, myTCs.toArray(new TopicContainer[myTCs.size()])))[0];
			
			myBCs.addAll(theTC.getBCs().values());
			
			while (!myBCs.isEmpty())
			{
				theBC = (BlockContainer) (this.theRandomizer.choose(1, myBCs.toArray(new BlockContainer[myBCs.size()])))[0];
				versionExam.addElement(theBC.getBlock());
				
				myFCs.addAll(theBC.getFCs().values());
				
				while (!myFCs.isEmpty())
				{
					theFC = (FigureContainer) (this.theRandomizer.choose(1, myFCs.toArray(new FigureContainer[myFCs.size()])))[0];		
					
					identifier = (Integer) this.mec.getIdentifiers().get(theFC.getFigure());
					
					if (!figureBlacklist.containsKey(identifier))
					{
						versionExam.addElement(theFC.getFigure());
						figureBlacklist.put(identifier, theFC.getFigure());
					}
					
					//Next 6 lines are maybe a fix to the problem we were having.  Certainly there is a better way.
					Object[] array = this.theRandomizer.choose(theFC.getProblems().size(), theFC.getProblems().toArray());
					theProblemsArray = new ProblemIF[array.length];
					for(int i = 0; i < array.length; i++)
					{
						theProblemsArray[i] = (ProblemIF)array[i];
					}
					
					//The line that was causing the problem, fixed (maybe?) by the 6 lines above
					//theProblemsArray = (ProblemIF[]) (this.theRandomizer.choose(theFC.getProblems().size(), theFC.getProblems().toArray(new ProblemIF[theFC.getProblems().size()])));
					
					for (int i = 0; i < theProblemsArray.length; i++)
					{
						identifier = (Integer) this.mec.getIdentifiers().get(theProblemsArray[i]);
						
						if (!problemBlacklist.containsKey(identifier))
						{
							for (FigureIF otherFigure : theProblemsArray[i].referencedFigures())
							{
								otherFigureIdentifier = (Integer) this.mec.getIdentifiers().get(otherFigure);
								
								if (!figureBlacklist.containsKey(otherFigureIdentifier))
								{
									versionExam.addElement(otherFigure);
									figureBlacklist.put(otherFigureIdentifier, otherFigure);
								}
							}
							
							ProblemIF newProblem = this.theExamFactory.newProblem(theProblemsArray[i].topic(), 
																			 	  theProblemsArray[i].label(), 
																			 	  theProblemsArray[i].question(), 
																			 	  randomizeAnswers(theProblemsArray[i]));
							
							newProblem.setDifficulty(theProblemsArray[i].difficulty());
							versionExam.addElement(newProblem);
							versionAnswerKey.addProblem(correctAnswers(newProblem));
							problemBlacklist.put(identifier, theProblemsArray[i]);
						}
					}
					
					myFCs.remove(theFC);
				}
				
				myBCs.remove(theBC);
			}
			
			myTCs.remove(theTC);
		}
		
		if (this.mec.getFinalBlock() != null)
			versionExam.addElement(this.mec.getFinalBlock());
		
	}
}


