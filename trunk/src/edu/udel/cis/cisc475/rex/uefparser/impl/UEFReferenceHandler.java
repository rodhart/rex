package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle references. Has the ability to add unmapped references
 * and later map them. I.e. they don't contain information about the exam
 * element that contains the reference. The reason being that the exam elements
 * are not created only after the references are found are found and we need to keep
 * track of the references and later map them to the exam element that
 * we create. Finally, it has the ability to declare the uses relationship
 * for all elements in it's internal list of mapped elements.
 *
 * @author Ahmed El-hassany
 * @author Aaron Myles Landwehr
 */
class UEFReferenceHandler
{

	/**
	 * Map of all references in the UEF file. The key is the reference name and
	 * the ExamElement that contains that reference.
	 */
	private Map<String, List<ExamElementIF>> mappedReferences;
	/**
	 * This is special List for references by answers. This List is needed for
	 * two reasons: 1. AnswerIF doesn't implement ExamElementIF. 2. If an answer
	 * has a ref then the whole problem uses that element.
	 */
	private List<String> unmappedReferences;

	/**
	 * Simple constructor to create our mapped reference and unmapped reference lists.
	 */
	UEFReferenceHandler()
	{
		//initialize fields.
		this.mappedReferences = new LinkedHashMap<String, List<ExamElementIF>>();
		this.unmappedReferences = new ArrayList<String>();
	}

	/**
	 * Adds unmapped references to the internal list so that they can later be mapped.
	 *
	 * @param reference the unmapped reference to add to the internal list.
	 */
	void addUnmappedReferences(String reference)
	{
		//add a reference to the the list of unammped references.
		this.unmappedReferences.add(reference);
	}

	/**
	 * Maps all currently unmapped references to the exam element provided.
	 * This is akin to  saying that the references are found within that
	 * particular exam element.
	 *
	 * @param examElement the exam element to map the references too.
	 */
	void mapReferences(ExamElementIF examElement)
	{
		//for each unmapped reference
		for (String reference : unmappedReferences)
		{
			//check to see if the referenced label is already in the mapped reference list.
			if (this.mappedReferences.containsKey(reference))
			{
				//if it is in there we just need to add the element to the list at the particular key.
				this.mappedReferences.get(reference).add(examElement);
			}
			else
			{
				//if it is not we need to create a new list and add  the element to the list
				List<ExamElementIF> list = new ArrayList<ExamElementIF>();
				list.add(examElement);

				//and we need to put the new list to the list of mapped references.
				this.mappedReferences.put(reference, list);
			}
		}
		this.unmappedReferences.clear();
	}

	/**
	 * Uses the ExamIF provided to declare a uses relationship between all references in the list
	 * and their cooresponding mapped element. This is akin to saying an Element uses the element
	 * it is referencing.
	 *
	 * @param exam
	 * @throws RexParseException
	 * @throws RexException
	 */
	void declareUses(ExamIF exam) throws RexParseException, RexException
	{
		//for each reference in the map.
		for (String label: this.mappedReferences.keySet())
		{
			//get the list of exam elements referencing the label.
			List<ExamElementIF> elementList = this.mappedReferences.get(label);

			//get the exam element with that has the label.
			ExamElementIF usedElement = exam.elementWithLabel(label);

			//make sure the element exists.
			if (usedElement == null)
			{
				//element doesn't exist so throw an error.
				throw new RexParseException("Element with label " + label
											+ " not found within file.", null);
			}
			else
			{
				//element exists so declare uses for each element in the lists.
				for (int j = 0; j < elementList.size(); j++)
				{
					//do actual declare uses.
					exam.declareUse(elementList.get(j), usedElement);
				}
			}
		}
	}
}
