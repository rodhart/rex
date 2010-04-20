// Author: Tim Armstrong



one sig Generator
{
	master: one MasterExam,
	config: one Config,
	generated: set GeneratedExam
}
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
	//preamble: one Source,
	//frontMatter: one Source,
	elements: one ElementList, // an *ordered* list, see below
	finalBlock: lone Block
}
// We do not have different classes for the master and generated exams, but we can always tell
// the difference.  Therefore I make them subclasses of Exam.
one sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {}





//***Basic List material.  Lists in the exams are ordered, which requires special handling in Alloy.

// A linked list of ExamElements:
abstract sig ElementList {}
sig EmptyList extends ElementList {}
sig NonEmptyList extends ElementList {
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
		all  ex, ex': Exam, ls: ElementList | (ex != ex' and ls in ex.elements.*rest) implies not ls in ex'.elements.*rest
}

/*  Old and incorrect:
fact listsOwnNodes { // each Exam has its completely own list nodes
		all  ex, ex': Exam, ls: ElementList | ex != ex' and nodeInList[ls, ex.elements] implies not nodeInList[ls, ex'.elements]
}
// asks if List ls is in another list, helper for listsOwnNodes
pred nodeInList [ls, ls': ElementList] {
	ls in ls'.^rest
}
*/





fact allElementsInMaster { // the MasterExam contains all elements
	all e: ExamElement | some m: MasterExam | e in m.elements.*rest.element
}




// fields not complete
one sig Config {
	// seed: one Int, // Commented out for now for simpler diagram.  Re-insert!
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
	category: one Category,
//	interval: one Interval
}
// A line in the ECF might require several problems, but they break down into individual requests.
// That's all I model
sig RequiredProblemConstraint extends Constraint {
	problemName: one Label  //Commented out for now for simpler diagram.  Re-insert!
}
/* from when I permitted > 1 problem request per RequiredProblemConstraint
fact { // it is syntactically incorrect for a required request not to ask for any problems
	all r: RequiredProblemConstraint | #r.problemNames >= 1 
}
*/


//Commented out for now for simpler diagram.  Re-insert!
/*
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
*/



abstract sig ExamElement {
	label: one Label // Just say "one"
}

// all fields accounted for
sig Problem extends ExamElement {
	category: one Category,
	block: lone Block,
	//figures: set Figure,
	points: lone Int,   // lone because will be null in master exam.
	// source: one Source,   // not important for model!
	//difficulty: one Int,   //actually REAL NUMBER, but it doesn't matter for the Alloy model
	answers: set Answer   // May be 0 if not a multiple choice.
}


/* We do not make the distinction in our design:
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
// Doesn't work for some reason:
//fact { // c belongs to *something* (it's difficult to be specific)
//	all c: Category | some u: univ | u != c and c in u
//}



/* ACTUALLY, don't need Source for model
sig Source {}
fact sourceNotFreeFloating {
	all s: Source |
	(some e: ExamElement | s in e.source) or
	(some c: Constraint | s in c.source)
	// or Exam preamble, frontMatter, but I'm leaving these out of the model
}
// Does not work:
//fact { // s belongs to *something*
//	all s: Source | some u: univ | u != s  and s in u
//}
*/



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



fact finalBlockSameForAll{
//TODO
//	all m: MasterExam | some m.finalBlock implies (all g: GeneratedExam | g.finalBlock = m.finalBlock)
//	all m: MasterExam | no m.finalBlock implies (all g: GeneratedExam | no g.finalBlock)
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
lone sig RequiredProblemError {}

fact errorIfReqConstInvalid {
	one RequiredProblemError iff some r: RequiredProblemConstraint | all m: MasterExam | r.problemName not in m.elements.*rest.element.label
//	some r: RequiredProblemConstraint  implies one Error
}
*/



//******************
//*** Assertions ***
//******************


// Tries to prove that, based on the model, there can be no duplicate questions in any single GeneratedExam.
// The number of nodes in the list = total number of elements, + 1 for the empty list.  If any of the elements
// are *duplicated*, the count will be less.
assert noDuplicatesInGenerated {
	all g: GeneratedExam | #(g.elements.*rest) = #(g.elements.*rest.element) + 1
}
//check noDuplicatesInGenerated for 5



// If one Exam contains a final block, all do. 
// Should follow from how generated axams are produced.
assert finalBlockForAll {
	all e, e': Exam | #e.finalBlock = #e'.finalBlock
}





/*  look at, maybe good
assert validReqConstraintInAll { // if there is a valid RequiredProblemConstraint. this problem is in all GeneratedExams
	all r: RequiredProblemConstraint, m: MasterExam | r.problemName in m.elements.*rest.element.label
		implies all g: GeneratedExam | r.problemName in g.elements.*rest.element.label
}
*/



// if the Constraint is fulfillable from the MasterExam, then there exist satisfying problems in the generated exams
assert constraintFulfilled {
// TODO
}

// if the constraint is possible to fill, there exists a ConstraintError
assert impossibleConstraintRejected {
// TODO
}


// 2010 04 20: interesting to say # problems in MasterExam = total # problems?  Follows trivialy from allElementsInMaster
assert {
//TODO
}


//*************************
//** Predicates to run? **
//*************************

// 2010 04 20

// I assume that we checked for this possibility!
fact elementsDoNotHaveIdenticalLabels
{
	all e, e': ExamElement | e != e' implies e.label != e'.label
}


// assume ONE OF THESE to be true for test purposes here:
fact allRequiredProblemsAreInMaster {
	all r: RequiredProblemConstraint | some p: Problem | r.problemName = p.label
}
// OR: NOT WORKING!:
//fact someRequiredProblemNotInMaster {
//		some r: RequiredProblemConstraint | some p:Problem| l: Label | r.problemName = p.label
//		not all r: RequiredProblemConstraint | some p: Problem | r.problemName = p.label
//		all r: RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
//}


// Just assume we have this check implemented
fact doNotRequestSameProblemTwice
{
	all r, r': RequiredProblemConstraint | r != r' implies r.problemName != r'.problemName
}





pred generateExams (g, g': GeneratedExam, r: RequiredProblemConstraint)//, m: MasterExam, 
	#g.elements.*rest.element = 0

// something like this:	g'.elements.*rest.element =     r.problemName   m.elements.*rest.element.label




//	#g'.elements.*rest.element = 2
//	(#g'.elements.*rest.element = g'.elements.*rest.element + (r.)
}

run generateExams for 7
//run generateExams for 3 but exactly 2 GeneratedExam, exactly 1 MasterExam


//end 2010 04 20





pred show{}

// PROBLEM: need to get it so that can specify exactly 3 of each subclass of ExamElement!

//run show for 5 but exactly 1 GeneratedExam, exactly 2 Problem, exactly 2 RequiredProblemConstraint,
//	exactly 0 GroupConstraint, exactly 0 Answer

//run show for 5 but exactly 1 GeneratedExam, exactly 3 NonEmptyList, exactly 3 ExamElement, exactly 0 Answer // not good: modified!

//check noDuplicatesInGenerated for 5 // GOOD!
