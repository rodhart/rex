/**
 * 
 */

/**
 * @author  team 5
 *
 */
/* General purpose random chooser: 
 * can be used to permute answers 
 * (let numItems=items.length) 
 * permute topics, select problems 
 * satisfying constraints (first filter then select) */
public interface randomizerIF {
     Object[]  choose(int numItems, Object[]  items);
   
}//end of interface
