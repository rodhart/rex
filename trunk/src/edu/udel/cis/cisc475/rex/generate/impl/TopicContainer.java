package edu.udel.cis.cisc475.rex.generate.impl;

import java.util.HashMap;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;

/**
 * @author Greg Simons
 * 
 * A TopicContainer houses a topic, and a HashMap of <BlockIF, BlockContainer> pairs.
 * TopicContainers live within VersionExamControllers; a VersionExamController will
 * never contain two TopicContainers referring to the same topic.
 */

public class TopicContainer 
{
	private String topic;
	private HashMap theBCs = new HashMap();
	
	/**
	 * @param topic
	 * 				-Topic representative of the TopicContainer being instantiated.
	 */
	
	public TopicContainer(String topic)
	{
		this.topic = topic;
	}
	
	public String getTopic()
	{
		return this.topic;
	}
	
	public HashMap getBCs()
	{
		return this.theBCs;
	}
	
	/**
	 * @param theBlock
	 * 					-BlockIF identifying the BlockContainer to be returned
	 * 					 (A new BlockContainer will be instantiated if one does not already exist.)
	 * @return
	 * 					-BlockContainer specified by the BlockIF taken as input.
	 */
	
	public BlockContainer getBC(BlockIF theBlock)
	{
		BlockContainer theBC;
		
		if (this.theBCs.containsKey(theBlock))
			return (BlockContainer) this.theBCs.get(theBlock);
		
		else
		{
			theBC = new BlockContainer(theBlock);
			this.theBCs.put(theBlock, theBC);
			
			return theBC;
		}
	}
}

