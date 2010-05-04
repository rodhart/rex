/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udel.cis.cisc475.rex.uefparser.impl;

import edu.udel.cis.cisc475.rex.err.RexException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import edu.udel.cis.cisc475.rex.exam.IF.ExamElementIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
class UEFReferenceHandler
{

	/**
	 * Map of all references in the UEF file. The key is the reference name and
	 * the ExamElement all elements that have that ref
	 */
	private Map<String, List<ExamElementIF>> mappedReferences;
	/**
	 * This is special List for references by answers. This List is needed for
	 * two reasons: 1. AnswerIF doesn't implement ExamElementIF. 2. If an answer
	 * has a ref then the whole problem uses that element.
	 */
	private List<String> unmappedReferences;

	UEFReferenceHandler()
	{
		this.mappedReferences = new HashMap<String, List<ExamElementIF>>();
		this.unmappedReferences = new ArrayList<String>();
	}

	void addUnmappedReferences(String reference)
	{
		this.unmappedReferences.add(reference);
	}

	void mapReferences(ExamElementIF examElement)
	{
		Iterator<String> iter = unmappedReferences.iterator();
		while (iter.hasNext())
		{
			String reference = iter.next();
			if (this.mappedReferences.containsKey(reference))
			{
				this.mappedReferences.get(reference).add(examElement);
			}
			else
			{
				List<ExamElementIF> list = new ArrayList<ExamElementIF>();
				list.add(examElement);
				this.mappedReferences.put(reference, list);
			}
		}
		this.unmappedReferences.clear();
	}

	void declareUses(ExamIF exam) throws RexParseException, RexException
	{
		Iterator<String> i = this.mappedReferences.keySet().iterator();
		while (i.hasNext())
		{
			String label = i.next();
			List<ExamElementIF> e = this.mappedReferences.get(label);
			ExamElementIF usedElement = null;

			usedElement = exam.elementWithLabel(label);

			if (usedElement == null)
			{
				throw new RexParseException("Element with label " + label
											+ " not found within file.", null);
			}
			else
			{
				for (int j = 0; j < e.size(); j++)
				{
					exam.declareUse(e.get(j), usedElement);
				}
			}
		}
	}
}
