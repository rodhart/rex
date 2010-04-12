package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * 
 * @author fxfitz
 *
 */
public class Block implements BlockIF {
	private String topic;
	private String label;
	private SourceIF source;

	public Block(String topic, String label, SourceIF source){
		this.topic = topic;
		this.source = source;
		this.label = label;
	}
	
	public SourceIF source() {
		// TODO Auto-generated method stub
		return source;
	}

	public String topic() {
		// TODO Auto-generated method stub
		return topic;
	}

	public String label() {
		// TODO Auto-generated method stub
		return this.label;
	}

}
