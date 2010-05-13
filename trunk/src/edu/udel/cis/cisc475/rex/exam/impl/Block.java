package edu.udel.cis.cisc475.rex.exam.impl;

import edu.udel.cis.cisc475.rex.exam.IF.BlockIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;

/**
 * The block interface!
 * 
 * @author fxfitz
 * 
 */
public class Block implements BlockIF {
	private String topic;
	private String label;
	private SourceIF source;

	public Block(String label, SourceIF source) {
		this.label = label;
		this.source = source;
		this.topic = null;
	}

	/**
	 * 
	 * Reference to where in the UEF the particular block is referenced.
	 */
	public SourceIF source() {
		return source;
	}

	/**
	 * The topic of the block as inferred by what problems it is related to
	 */
	public String topic() {
		return topic;
	}

	public String label() {
		return this.label;
	}

	// To be used by declare use.
	void setTopic(String topic) {
		this.topic = topic;
	}

}
