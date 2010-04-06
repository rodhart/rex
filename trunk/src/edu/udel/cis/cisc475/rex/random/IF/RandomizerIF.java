package edu.udel.cis.cisc475.rex.random.IF;

/**
 * 
 */

/**
 * @author team 5
 * 
 */
/*
 * General purpose random chooser: can be used to permute answers (let
 * numItems=items.length) permute topics, select problems satisfying constraints
 * (first filter then select)
 */
public interface RandomizerIF {
	Object[] choose(int numItems, Object[] items);

}// end of interface