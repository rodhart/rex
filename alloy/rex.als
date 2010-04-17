// Author: Tim Armstrong



one sig Generator
{
	master: one MasterExam,
	config: one Config,
	generated: set GeneratedExam
}



//***Basic List material.  Lists in the exams are ordered, which requires special handling in Alloy.

// A linked list:
abstract sig List {}
sig EmptyList extends List {}
sig NonEmptyList extends List {
	element: one ExamElement,
	rest: one List

}
/* TODO: test
fact listInExam { // no lists occur outside of an Exam
TODO: test
all ls: List | some e: Exam | ls in e.elements.*rest //listInList[ls, e.elements]
}
*/

fact listNoCycles{ //there are no cycles in a list
	all ls: List | not ls in ls.^rest
}

fact listsOwnNodes { // each Exam has its completely own list nodes
		all  ex, ex': Exam, ls: List | ex != ex' and nodeInList[ls, ex.elements] implies not nodeInList[ls, ex'.elements]
}

// asks if List ls is in another list, helper for listsOwnNodes
pred nodeInList [ls, ls': List] {
	ls in ls'.^rest
}



//*** First assertion:

assert NoDuplicatesInGen { // GeneratedExam does not include the same problem more than once.  It should *also* not include
											 		// duplicate Blocks or Figures (easier to write!)
	all g: GeneratedExam | (g.elements in EmptyList) or (not elementsDuplicated[g.elements]) //    ls.element = ls'.element implies ls = ls'
}

pred elementsDuplicated [ls: List] { // for all *problems* in a generated exam, 
	all e: ls.*rest.element | some e': ls.^rest.element | e = e' //and e.element = e'.element
}
//check NoDuplicatesInGen for 5


//assert NoDuplicatesInGen { // GeneratedExam does not include the same problem more than once
//	all ls, ls': List |  ls.element = ls'.element implies ls = ls'
//}
//check NoDuplicatesInGen for 3





// These seem to be necessary.  They say that all MasterExams, Configs, and GeneratedExams belong to the Generator.
fact masterInGenerator {
	all m: MasterExam | some g: Generator | m in g.master
}
fact configInGenerator { 
	all c: Config | some g: Generator | c in g.config
}
fact generatedInGenerator {
	all ge: GeneratedExam | some g: Generator | ge in g.generated
}


// all fields accounted for
abstract sig Exam {
	preamble: one Source,
	frontMatter: one Source,
	elements: one List, // needs to be an *ordered list*. Implementing that looks difficult.
														// See pg 157-9 in Jackson's book for lists in Alloy.
	finalBlock: lone Block
}

/* Without linked list:
// all fields accounted for
abstract sig Exam {
	preamble: one Source,
	frontMatter: one Source,
	elements: set ExamElement, // needs to be an *ordered list*. Implementing that looks difficult.
														// See pg 157-9 in Jackson's book for lists in Alloy.
	finalBlock: lone Block
}
*/


// We do not have different classes for the master and generated exams, but we can always tell
// the difference.  Therefore I make them subclasses of Exam.
one sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {}


/* before lists:
fact allElementsInMaster { // the MasterExam contains all elements
	all m: MasterExam, e: ExamElement | e in m.elements
}
*/
fact allElementsInMaster { // the MasterExam contains all elements
	all m: MasterExam, e: ExamElement | e in m.elements.*rest.element
}

fact finalBlockForAll { // if one Exam contains a final block, all do
	all e, e': Exam | #e.finalBlock = #e'.finalBlock
}






// fields not complete
one sig Config {
	seed: one Int,
	constraints: set Constraint   // There is Ticket #52, unresolved as of 4/16, that questions whether the constraints
														// should be an *oredered* list.
}



// fields complete.  Commented out for now for simpler diagram.  Re-insert!
abstract sig Constraint {
	//source: one Source,
	//points: one Int  // maybe would want real number?
}
fact { // all constraints are in the Config
	all c: Constraint | some conf: Config | c in conf.constraints
}

// fields not complete. Commented out for now for simpler diagram.  Re-insert!
sig GroupConstraint extends Constraint {
//	numProblems: one Int,
//	category: one Category,
//	interval: one Interval
}
//Commented out for now for simpler diagram.  Re-insert!
sig RequiredProblemConstraint extends Constraint {
	problemNames: set String
}

fact { // it is syntactically incorrect for a required request not to ask for any problems
	all r: RequiredProblemConstraint | #r.problemNames > 1 
}



//Commented out for now for simpler diagram.  Re-insert!
// Booleans are difficult to implement in Alloy (see Jackson pg 136).
// Therefore I omit here the Boolean values for the range being / not being inclusive.
// They have no real bearing on the model.  I also omit the possibilities of infinity values.
sig Interval {
//	low: one Int,
//	high: one Int
}



abstract sig ExamElement {
	label: lone String
}

// all fields accounted for
sig Problem extends ExamElement {
	category: one Category,
	block: lone Block,
	figures: set Figure,
	points: lone Int,   // lone because will be null in master exam.  (It is an integer, not a real number in the design.)
	source: one Source,
	difficulty: one Int,   //actually REAL NUMBER, but it doesn't matter for the Alloy model
	answers: set Answer   // May be 0 if not a multiple choice.
}


/* We do not make the distinction in our design.  I'll have to figure out how to model this.
sig MultipleChoice extends Problem {
	answers: set Answer
}
sig OtherProblem extends Problem {} // no answers field
*/



// fields not complete
sig Answer {}
fact answerInProblem {
	all a: Answer | some p: Problem | a in p.answers
}


abstract sig Block extends ExamElement {
	source: one Source,
	category: lone Category
}
fact finalBlockWithoutCategory { // the final block does not have a category
	//TODO
}



sig Figure extends ExamElement {}




/* This is not a real object, it is just a Java String.  I'll have to give it properties, but they might be more difficult
to implement in the code than if an Exam consisted of categories and a category consisted of problems?
*/
sig Category {}


sig Source {}





//**************************
//** Miscellaneous facts **
//**************************


// A Problem may refer to a Block only of the same category.
// This includes the constraint that all problems
// referring to a block must be of the same category
fact problemBlockSameTopic{
	all p: Problem, b: Block | p.block = b implies p.category = b.category
}


//A block cannot both be required by a problem and appended to the exam.
fact usedBlockNotAppended {
	all e: Exam, p: Problem | e.finalBlock not in p.block
}


// If a block is not required by any problem occurring in the generated exam, 
// and it is not appended, then that block will not occur in the exam.
fact doNotPrintUnusedBlock {
// not right:	all b: Block, g: GeneratedExam, p: Problem | (b not in g.finalBlock) and (b not in p.block)  
//		  implies b not in g.elements
// TODO
}



/* Only partially solved
Requirement: If the answers environment is used, it must include at least one answer.  The maximum number of answers is 26.
For the purposes of the Alloy model, all this says is that if the # of answers = 0, it is a non-multiple-choice problem.
And then if it is a multiple-choice, it follows that there is at least one answer.
But for the upper bound:

fact validNumAnswers {
	all p: Problem | #p.answers <= 26
}

...I get:
"A type error has occured:
Current bitwidth is set to 4, thus this integer constant 26
is bigger than the maximum integer 7"

I'll figure this out later.
*/






// REX guarantees that if given the same arguments and seed twice, it will produce the exact same outputs.
fact seedDeterminesOutput {
	// not right: all g1, g2: GeneratedExam | g1.config.seed = g2.config.seed implies g1 = g2   // is this == or .equals???
	//TODO
}








//ECF-oriented:
//  The first <integer> is a positive integer: it is the number of problems satisfying the constraint that
// should be included.
//assert?? sumConstraintProblems {






// Answer-oriented (I have not yet modeled Answers):

//At least one answer must be labeled as correct.
fact atLeastOneCorrect {
//TODO
}

//[fixed]: signifying that this answer must appear in its given position in the answer sequence
fact fixedAtCorrectPosition {
//TODO
}



// Need an ordered list:

// Say that in the generated exams, problems group into categories.  If our model included a Category class
// in the hierarchy, we could make this an assertion rahter than a fact: ie, we could say that this constraint
// would logically follow from our model.
fact generatedIsGrouped {
// TODO
}

// Says that in a generated exam, all questions that refer to a block will be grouped together, with the
// block immediately preceding.
fact blockWithQuestions {
//TODO
}

// Says front matter in document environment must appear before anything else.
fact frontMatterFirst {
//TODO
}




// fact regarding probability?




//A problem refers to a figure if a \ref to the figure's label occurs anywhere in that problem's
// environment. If at least one problem referring to a figure A is included in a generated exam,
// the figure will also be included automatically.
// Alloy probably cannot handle this constraint in that it probably does not have good string abilites.




/*
pred addProblem (r: RequiredProblemConstraint, m: MasterExam, g, g': GeneratedExam) {
	all pn: r.problemNames | pn in m.elements.label
		
}


assert noDuplicatesInGenerated {
	all g: GeneratedExam, p: Problem | 
}
*/

/*
pred addProblem (p: Problem, g, g': GeneratedExam) {
	not p in g.elements and p in g'.elements
}

run addProblem for 3
*/

pred show{}

// Just to get a generated exam and a problem in the diagram for inspection.
// There are different valid states of course.
run show for 4 but exactly 1 GeneratedExam, exactly 3 ExamElement, exactly 0 Answer

