package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.config.IF.RequiredProblemConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.exam.IF.ProblemIF;

/**
 * @author Greg Simons
 * 
 *         A MasterExamController aids in the dissemination of all the pertinent
 *         data surrounding a master ExamIF. Only one MasterExamController is
 *         needed in Generator, significantly reducing the complexity of
 *         generating random exam versions.
 */

public class MasterExamController
{
	private ExamIF master;
	private HashMap identifiers = new HashMap();
	private HashMap requiredProblems = new HashMap();
	private HashMap theTOs = new HashMap();
	private BlockIF finalBlock;

	/**
	 * @param master
	 *            -The master ExamIF being disseminated. Every ExamElementIF is
	 *            hashed, along with a unique Integer, to later detect
	 *            duplicates in O(1) time.
	 */

	public MasterExamController(ExamIF master)
	{
		this.master = master;
		this.finalBlock = master.finalBlock();
		ExamElementIF[] allElements = (ExamElementIF[]) master.elements()
				.toArray(new ExamElementIF[master.elements().size()]);

		for (int i = 0; i < allElements.length; i++)
			this.identifiers.put(allElements[i], new Integer(i));
	}

	public HashMap getIdentifiers()
	{
		return this.identifiers;
	}

	public HashMap getRequiredProblems()
	{
		return this.requiredProblems;
	}

	public HashMap getTheTOs()
	{
		return this.theTOs;
	}

	public BlockIF getFinalBlock()
	{
		return this.finalBlock;
	}

	/**
	 * @param topic
	 *            -Topic identifying the TopicOrganizer to be returned (a new
	 *            TopicOrganizer will be instantiated if one does not already
	 *            exist.)
	 * @return -TopicOrganizer specified by the topic taken as input.
	 */

	private TopicOrganizer findTO(String topic)
	{
		TopicOrganizer theTO;

		if (this.theTOs.containsKey(topic))
			return (TopicOrganizer) this.theTOs.get(topic);

		else 
		{
			theTO = new TopicOrganizer(topic);
			this.theTOs.put(topic, theTO);

			return theTO;
		}
	}

	/**
	 * @param theRPC
	 *            -RequiredProblemConstraintIF referring to a required ProblemIF
	 *            to be added to random exam versions. (a warning is printed if
	 *            this RequiredProblemConstraintIF has already been added).
	 * @throws Exception
	 * 			  -If theRPC has a label that does not refer to a ProblemIF.
	 */

	public void addRequiredProblem(RequiredProblemConstraintIF theRPC) throws Exception
	{
		String requiredLabel = theRPC.label();
		ProblemIF requiredProblem = (ProblemIF) this.master.elementWithLabel(requiredLabel);
		
		if (requiredProblem == null)
		{
			System.err.println("The label" + requiredLabel +
					   " does not have an associated problem.");
			throw new Exception();
		}

		Integer identifier = (Integer) this.identifiers.get(requiredProblem);

		if (!this.requiredProblems.containsKey(identifier)) 
		{
			requiredProblem.setPoints(theRPC.points());
			this.requiredProblems.put(identifier, requiredProblem);
		}

		else
			System.err.println("The problem \"" + requiredProblem.question()
					+ "\" is referenced by two required problem constraints.");
	}

	/**
	 * @param theGC
	 *            -GroupConstraintIF to be unpacked in a new
	 *            GroupConstraintContainer.
	 * @throws RexUnsatisfiableException
	 *             -If the set of ProblemIFs that could satisfy the
	 *             GroupConstraintIF is less than the GroupConstraintIF's
	 *             specified number of ProblemIFs required.
	 */

	public void distributeGroupConstraint(GroupConstraintIF theGC) throws RexUnsatisfiableException
	{
		String topic = theGC.topic();

		TopicOrganizer theTO = findTO(topic);
		theTO.addConstraint(theGC, this.master);
	}

	/**
	 * @param theTO
	 *            -The TopicOrganizer in which overlapping
	 *            GroupConstraintContainers are to be checked.
	 * @param theGCC
	 *            -The GroupConstraintContainer that other
	 *            GroupConstraintContainers within the TopicOrganizer will be
	 *            checked against.
	 * @return -A Collection of all GroupConstraintContainers that theGCC
	 *         overlaps (reference is currently discarded; warning of
	 *         false-positive RexUnsatisfiableExceptions is printed).
	 */

	public Collection<GroupConstraintContainer> getOverlaps(TopicOrganizer theTO, GroupConstraintContainer theGCC)
	{
		Collection<GroupConstraintContainer> returnSet = new ArrayList<GroupConstraintContainer>();

		Iterator<GroupConstraintContainer> gccIterator = theTO.getGCCs().iterator();
		GroupConstraintContainer currentGCC;

		int myConstraintValue = theGCC.getConstraintValue();
		double myLow = theGCC.getDifficultyInterval().low();
		double myHigh = theGCC.getDifficultyInterval().high();

		int yourConstraintValue;
		double yourLow;
		double yourHigh;

		while (gccIterator.hasNext()) 
		{
			currentGCC = gccIterator.next();

			if (!currentGCC.equals(theGCC)) 
			{
				yourConstraintValue = currentGCC.getConstraintValue();
				yourLow = currentGCC.getDifficultyInterval().low();
				yourHigh = currentGCC.getDifficultyInterval().high();

				if (((yourLow <= myLow) && (myLow <= yourHigh))
						|| ((yourLow <= myHigh) && (myHigh <= yourHigh))) 
				{
					System.err.println("Warning: The constraint requesting " + myConstraintValue +
									   " problem(s) from " + theTO.getTopic() +
									   " within difficulty " + myLow +
									   " through " + myHigh +
									   " conflicts with the constraint requesting " + yourConstraintValue +
									   " problem(s) from " + theTO.getTopic() +
									   " within difficulty " + yourLow +
									   " through " + yourHigh +
									   ". As a result, false-positive RexUnsatisfiableExceptions might occur " +
									   "(Please refer to alloy for satisfiability.)");
					returnSet.add(currentGCC);
				}
			}
		}

		return returnSet;
	}
}
