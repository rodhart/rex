// Author: Tim Armstrong

/*

Beta comments:

I have the core of the model implemented, and the skeleton of a more detailed model.  I have
commented out a lot of fields that are irrelevant to my model presently, in order for the diagram
to be simpler.

The three commands to run are the two shows and the check just below.

The first show predicate just illustrates, in the first instance, the MasterExam having a linked list
of Problem1, Problem0, and Block, while the GeneratedExam just has the Block.

The second show predicate, in the first two instances, illustrates the functionality
of my saying that there exists a RexUnsatisfiableException iff a required problem constraint is 
unsatisfiable.  (I have not gotten to group constraints.)  See the fact requiredProblemUnsatisfiable
below.  To navigate the diagrams, follow the links between the RequiredProblemConstraints, their labels,
and their associated ExamElements.

check noDuplicatesInGenerated is an assertion that there are no repeats of an ExamElement in any
generated exam.  *Presently* my model does *not* insure that there are no duplicates.  Therefore
the check comes up with a counter-example.  To navigate the diagram, see NonEmptyNodes 0 and 2 both
pointing to the same figure.  (We don't want any kind of ExamElement duplicated.)

*/

pred show{}

run show for 6 but exactly 1 GeneratedExam, exactly 2 Problem

//run show for 4 but exactly 2 RequiredProblemConstraint,  exactly 2 Problem

//check noDuplicatesInGenerated for 5





//*******************************
//** Data structures and related facts **
//*******************************

one sig Generator
{
	master: one MasterExam,
	config: one Config,
	generated: set GeneratedExam
}
// These facts say that all MasterExams, Configs, and GeneratedExams belong to the Generator.
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
	//preamble: one Source,
	//frontMatter: one Source,
	elements: one ElementList, // an *ordered* list, see below
	finalBlock: lone Block
}
// We do not have different classes for the master and generated exams, but we can always tell
// the difference.  Therefore I make them subclasses of Exam.
sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {}





//***Basic List material.  Lists in the exams are ordered, which requires special handling in Alloy.

// A linked list of ExamElements:
abstract sig ElementList {}
sig EmptyNode extends ElementList {}
sig NonEmptyNode extends ElementList {
	element: one ExamElement,
	rest: one ElementList
}
fact listInExam { // no lists occur outside of an Exam
	all ls: ElementList | some e: Exam | ls in e.elements.*rest
}

fact listNoCycles{ //there are no cycles in a list
	all ls:ElementList | not ls in ls.^rest
}
fact listsHaveOwnNodes { // each Exam has its completely own list nodes
		all  ex, ex': Exam, ls: ElementList | (ex != ex' and ls in ex.elements.*rest)
				implies not ls in ex'.elements.*rest
}



fact allElementsInMaster { // the MasterExam contains all elements
	all e: ExamElement | some m: MasterExam | e in m.elements.*rest.element
}




// fields not complete
one sig Config {
	// seed: one Int
	constraints: set Constraint
}



// fields complete
abstract sig Constraint {
	//source: one Source,
	//points: one Int  // maybe would want real number?
}
fact { // all constraints are in the Config
	all c: Constraint | some conf: Config | c in conf.constraints
}


sig GroupConstraint extends Constraint {
	numProblems: one Int,
	category: one Category,
	interval: one Interval
}

// A line in the ECF might require several problems, but they break down into individual requests.
// That's all I model.
sig RequiredProblemConstraint extends Constraint {
	problemName: one Label
}




// Booleans are difficult to implement in Alloy (see Jackson pg 136).
// Therefore I omit here the Boolean values for the range being / not being inclusive.
// For now they have no real bearing on the model.  I also omit the possibilities of infinity values.
sig Interval {
//	low: one Int,
//	high: one Int
}
fact {
	all i: Interval | some u: univ | i in u
}




abstract sig ExamElement {
	label: one Label // FOR NOW I'm just saying every exam element has a label.  It is true that Team 3
							  // gives integer identifiers to every ExamElement.
}

// all fields accounted for
sig Problem extends ExamElement {
	category: one Category,
	block: lone Block,
	//figures: set Figure,
	//points: lone Int,   // lone because will be null in master exam.
	// source: one Source,   // not important for model!
	//difficulty: one Int,   //actually REAL NUMBER, but it doesn't matter for the Alloy model
	//answers: set Answer   // May be 0 if not a multiple choice.
}


/* We do not make the distinction in our design:
sig MultipleChoice extends Problem {
	answers: set Answer
}
sig OtherProblem extends Problem {} // no answers field
*/


/*
// fields not complete
sig Answer {}

fact answerInProblem {
	all a: Answer | some p: Problem | a in p.answers
}
*/




sig Block extends ExamElement {
	//source: one Source,
	category: lone Category
}
fact finalBlockWithoutCategory { // the final block does not have a category
	//TODO
}



sig Figure extends ExamElement {}


sig Label {}


sig Category {} // a Java string
fact categoryNotFreeFloating{
	all c: Category |
	(some b: Block | c in b.category) or
	(some p: Problem | c in p.category) or
	(some g: GroupConstraint | c in g.category)
}


// Says that there is a RexUnsatisfiableException iff a RequiredProblemConstraint requests by label
// a problem that doesn't exist in the MasterExam.
lone sig RexUnsatisfiableException {}
fact requiredProblemUnsatisfiable {
	one RexUnsatisfiableException iff some r: RequiredProblemConstraint | all m: MasterExam | r.problemName not in m.elements.*rest.element.label
	// old attempt: //some RexUnsatisfiableException iff some r:RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
		// TODO: OR there is a group constraint that is unsatisfiable
}
//fact forceUnsatisfiableExceptionToExist {
//	all r:RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
//}
//fact forceUnsatisfiableExceptionNotToExist {
//	all r: RequiredProblemConstraint | some p: Problem | r.problemName = p.label
//}




//********************
//** Miscellaneous facts **
//********************


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



fact finalBlockSameForAll{
//TODO
// attempts:
//	all m: MasterExam | some m.finalBlock implies (all g: GeneratedExam | g.finalBlock = m.finalBlock)
//	all m: MasterExam | no m.finalBlock implies (all g: GeneratedExam | no g.finalBlock)
}



// If a block is not required by any problem occurring in the generated exam, 
// and it is not appended, then that block will not occur in the exam.
fact doNotPrintUnusedBlock {
// TODO
// not right:	all b: Block, g: GeneratedExam, p: Problem | (b not in g.finalBlock) and (b not in p.block)  
//		  implies b not in g.elements
}



/* SOLVED probably sufficiently:
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
*/




// REX guarantees that if given the same arguments and seed twice, it will produce the exact same outputs.
// ...though, what if the UEF changes?  I will possibly issue a ticket on that matter soon.
fact seedDeterminesOutput {
	//TODO
}








// Answer-oriented (I have not yet modeled Answers):

//At least one answer must be labeled as correct.
fact atLeastOneCorrect {
//TODO
}

//"fixed": signifying that this answer must appear in its given position in the answer sequence
fact fixedAtCorrectPosition {
//TODO
}






// Says that in the generated exams, problems group into categories.  If our model included a Category class
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
// need to test: all g: GeneratedExam, m: MasterExam | g.elements.element = m.frontMatter

}



// IMPOSSIBLE to model:
//A problem refers to a figure if a \ref to the figure's label occurs anywhere in that problem's
// environment. If at least one problem referring to a figure A is included in a generated exam,
// the figure will also be included automatically.
// Alloy probably cannot handle this constraint in that it probably does not have good string abilites.


// I assume that we checked for this possibility!
fact elementsDoNotHaveIdenticalLabels
{
	all e, e': ExamElement | e != e' implies e.label != e'.label
}



// Just assume we have this check implemented
fact doNotRequestSameProblemTwice
{
	all r, r': RequiredProblemConstraint | r != r' implies r.problemName != r'.problemName
}


//*************
//** Assertions **
//*************


// Tries to prove that, based on the model, there can be no duplicate questions in any single GeneratedExam.
// The number of nodes in the list = total number of elements, + 1 for the empty list.  If any of the elements
// are *duplicated*, the count will be less.  WORKS!  It's round-about since recursion is difficult in Alloy.
assert noDuplicatesInGenerated {
	all g: GeneratedExam | #(g.elements.*rest) = #(g.elements.*rest.element) + 1
}



// If one Exam contains a final block, all do. 
// Should follow from how generated axams are produced.
assert finalBlockForAll {
	//TODO: need to test
	//all e, e': Exam | #e.finalBlock = #e'.finalBlock
}




assert validReqConstraintInAll { // if there is a valid RequiredProblemConstraint. this problem is in all GeneratedExams
	// TODO: need to test
//	all r: RequiredProblemConstraint, m: MasterExam | r.problemName in m.elements.*rest.element.label
//		implies all g: GeneratedExam | r.problemName in g.elements.*rest.element.label
}




//********************
//** MAIN CODE TO DO **
//********************

// if the Constraint is satisfiable from the MasterExam, then there exist satisfying problems in the generated exams
assert constraintFulfilled {
// TODO
}

// if the constraint is possible to fill, there exists a RexUnsatisfiableException
assert impossibleConstraintRejected {
// TODO
}

// end main code todo






//*****************************************
//** beginning of implementing Team 3's approach **
//*****************************************

/*
sig SatisfiedContainer {
	category: one Category,
	requiredProblems: one ElementList,
	groupConstraints: set ConstraintContainer
}

sig ConstraintContainer {
	numProblems: one Int,
	satisfiedProblems: one ElementList
}



pred isProblemAlreadyIncluded [p: Problem, s: SatisfiedContainer] {
	p in s.requiredProblems.*rest.element or p in s.groupConstraints.satisfiedProblems.*rest.element
}


pred generateExams (g, g': GeneratedExam, r: RequiredProblemConstraint, m, m': MasterExam) {
	#g.elements.*rest.element = 0

//	(set all p | some r | p.label = r.problemName) in m.elements.*rest.element

//	m'.elements.*rest.element = m.elements.*rest.element - r.

// something like this:	g'.elements.*rest.element =     r.problemName   m.elements.*rest.element.label

//	#g'.elements.*rest.element = 2
//	(#g'.elements.*rest.element = g'.elements.*rest.element + (r.)
}

*/
