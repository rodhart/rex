package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import edu.udel.cis.cisc475.rex.config.IF.GroupConstraintIF;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;

/**
 * @author Greg Simons
 * 
 * A TopicOrganizer houses a topic, and a Collection of GroupConstraintContainers.
 * TopicOrganizers live within MasterExamControllers; a MasterExamController will
 * never contain two TopicOrganizers referring to the same topic.
 */

public class TopicOrganizer 
{
	private String topic;
	private Collection<GroupConstraintContainer> theGCCs = new ArrayList<GroupConstraintContainer>();
	
	/**
	 * @param topic
	 * 				-Topic representative of the TopicOrganizer being instantiated.
	 */
	
	public TopicOrganizer(String topic)
	{
		this.topic = topic;
	}
	
	public String getTopic()
	{
		return this.topic;
	}
	
	public Collection<GroupConstraintContainer> getGCCs()
	{
		return this.theGCCs;
	}
	
	/**
	 * @param theGC
	 * 					-GroupConstraintIF to be unpacked in a new GroupConstraintContainer.
	 * @param master
	 * 					-The master ExamIF, needed to obtain the set of ProblemIFs that
	 * 				 	 could satisfy the GroupConstraintIF.
	 * @throws RexUnsatisfiableException
	 * 					-If the set of ProblemIFs that could satisfy the GroupConstraintIF
	 * 				 	 is less than the GroupConstraintIF's specified number of
	 * 				 	 ProblemIFs required.
	 */
	
	public void addConstraint(GroupConstraintIF theGC, ExamIF master) throws RexUnsatisfiableException
	{
		this.theGCCs.add(new GroupConstraintContainer(theGC, master));
	}
}