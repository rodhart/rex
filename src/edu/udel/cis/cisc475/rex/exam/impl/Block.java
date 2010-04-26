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

	@Deprecated
	public Block(String topic, String label, SourceIF source){
		this.topic = topic;
		this.source = source;
		this.label = label;
	}
	
	public Block(String label, SourceIF source){
		this.label = label;
		this.source = source;
	}
	
	public SourceIF source() {
		return source;
	}

	public String topic() {
		return topic;
	}

	public String label() {
		return this.label;
	}

}
