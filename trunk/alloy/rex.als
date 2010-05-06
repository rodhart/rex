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


// for Beta:
//run show for 6 but exactly 1 GeneratedExam, exactly 2 Problem
//run show for 4 but exactly 2 RequiredProblemConstraint,  exactly 2 Problem
//check noDuplicatesInGenerated for 5

//run show for 5 but exactly 0 GeneratedExam, exactly 3 Problem



//*******************************
//** Data structures and related facts **
//*******************************

one sig Generator // aka MasterExamController
{
	master: one MasterExam,
	config: one Config,
	generated: set GeneratedExam
	//answer keys
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
	problems: set Problem, // the problems from the ElementList.  I couldn't check if an ExamElement was
										// an "instanceof" a Problem
	finalBlock: lone Block
}
fact problemsInExamEqualsProblemsInElementList {
	all e: Exam, p: Problem | p in e.elements.*rest.element iff p in e.problems
}

// We do not have different classes for the master and generated exams, but we can always tell
// the difference.  Therefore I make them subclasses of Exam.
sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {
	groupConstraints: set GroupConstraint // ADDED SINCE BETA.  Each gets its own copy so that the selected problems may differ
}





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
	// source
}
fact numProblemsInGroupConstraint {
	all g: GroupConstraint | g.numProblems >= 1
}
fact {
	all c: GroupConstraint | some g: GeneratedExam | c in g.groupConstraints
}


// A line in the ECF might require several problems, but they break down into individual requests.
// That's all I model.
sig RequiredProblemConstraint extends Constraint {
	problemName: one Label
	// source
}
fact labelOfRequiredProblemConstraintIsAProblem {
	all r: RequiredProblemConstraint | r.problemName in Problem.label
}



// Booleans are difficult to implement in Alloy (see Jackson pg 136).
// Therefore I omit here the Boolean values for the range being / not being inclusive.
// For now they have no real bearing on the model.  I also omit the possibilities of infinity values.
sig Interval {
	low: one Int,
	high: one Int
}
fact {
	all i: Interval | some g: GroupConstraint | i in g.interval
}
fact {
	all i: Interval | i.low <= i.high
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
	difficulty: one Int,   //actually REAL NUMBER, but it doesn't matter for the Alloy model
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
lone sig RexRequiredUnsatisfiableException {}
fact requiredProblemUnsatisfiable {
	one RexRequiredUnsatisfiableException iff 

	some r: RequiredProblemConstraint | all m: MasterExam | r.problemName 
		not in m.elements.*rest.element.label and r.problemName in Problem.label

//      beta bug: did not specify problems:
//		some r: RequiredProblemConstraint | all m: MasterExam | r.problemName not in m.elements.*rest.element.labe
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



fact finalBlockSameForAll {
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
	no RexRequiredUnsatisfiableException implies
		(all r: RequiredProblemConstraint, g: GeneratedExam, p: Problem | p.label in r.problemName implies
			p in g.elements.*rest.element)
}

//check constraintFulfilled for 5

// if the constraint is possible to fill, there exists a RexUnsatisfiableException
assert impossibleConstraintRejected {
// TODO
}

// end main code todo






//*********************************************************************
//**implementation of  Team 3's approach without considering overlapping difficulties **
//*********************************************************************


//fact forceGroupUnsatisfiableExceptionToExist {
//	all r:RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
//}
fact forceGroupUnsatisfiableExceptionNotToExist {
	no RexGroupUnsatisfiableException
}


// I give each GeneratedExam *it's own copy* of the GroupConstraints to simulate the code
// in fillExam() in the generator's VersionExamController.
fact eachGeneratedOwnGroupConstraint {

	// each GeneratedExam has its own seperate GroupConstraints
	all ge, ge': GeneratedExam, gc: GroupConstraint | gc in ge.groupConstraints and ge != ge' implies gc not in ge'.groupConstraints

	//the number of GroupConstraints in all GeneratedExams are equal
	all ge, ge': GeneratedExam | #ge.groupConstraints = #ge'.groupConstraints

	// all GeneratedExams have all 


	// the GroupConstraints in all of the GeneratedExams have the same values for category, numProblems, and interval
//	all ge, ge': GeneratedExam, gc, gc': GroupConstraint | gc in ge.groupConstraints implies
//		some gc' in ge'.groupConstraints | gc.category = gc'.category and gc.numProblems = gc'.numProblems and gc.interval = gc'.interval

}

run show for 6 but exactly 2 GeneratedExam, exactly 4 GroupConstraint



//  As in Java code, returns all problems that satisfy group constraint (category and interval)
// without yet selecting randomly
fun allProblemsFromGroupConstraint[m: MasterExam, g: GroupConstraint]: set Problem { 
	{p: Problem | p in m.problems and p.category = g.category 
			and p.difficulty >= g.interval.low and p.difficulty <= g.interval.high}
}
 

lone sig RexGroupUnsatisfiableException {}
// produces an error iff the GroupConstraint requires more problems than are taken from the MasterExam
// as in the function above
fact sufficientNumberOfProblemsForGroupConstraint {
	one RexGroupUnsatisfiableException iff
	(some GroupConstraint) and 
	(all gc: GroupConstraint, m: MasterExam | gc.numProblems > #allProblemsFromGroupConstraint[m, gc])
}

/*
fact ifAllSatisfiableThenInGenerated {
	all g: GeneratedExam | not some RexGroupUnsatisfiableException and not some RexRequiredUnsatisfiableException
		implies all p: Problem | 
}
*/







// for testing:
//fact boundsOnGroupConstraint {
//	all g: GroupConstraint | g.numProblems = 2
//}


//*********
// attempt to handle overlapping difficulty ranges
//**********


// ONLY FOR setting up test for finding a solution with overlapping difficulties


/*
fact {
	one Category
	one gc: GroupConstraint | gc.interval.low = 1 and gc.interval.high = 2 and gc.numProblems = 1
	one gc: GroupConstraint | gc.interval.low = 2 and gc.interval.high = 3 and gc.numProblems = 1

	one p: Problem | p.difficulty = 1
	one p: Problem | p.difficulty = 2
	one p: Problem | p.difficulty = 3
}
*/


/* not useful
fun getProblemsFromGroupConstraintContainer[remaining: Problem, gc: GroupConstraint]: set Problem {

}
fact {
	all ge: GeneratedExam | 
}
*/


///doesn't run, will fix:
//run show for 6 but exactly 2 GroupConstraint, exactly 3 Problem, 
//	exactly 0 RequiredProblemConstraint, exactly 1 GeneratedExam





//**************************
//** scratch space (don't read) **
//**************************


/*	gc.interval
	gc.numProblems
	m.problems
	m.problems.difficulty
*/


/*
lone sig RexGroupUnsatisfiableException {}
fact groupProblemUnsatisfiable {
	one RexGroupUnsatisfiableException iff
		some gc: GroupConstraint | all m: MasterExam | all gc. not some 



		all g, g': GroupConstraint | some p: Problem | p.difficulty >= g.interval.low or p.difficulty <= g.interval.low
								and g != g' implies not g.problems in 
		

 r.problemName not in m.elements.*rest.element.label
	// old attempt: //some RexUnsatisfiableException iff some r:RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
		// TODO: OR there is a group constraint that is unsatisfiable
}
//fact forceUnsatisfiableExceptionToExist {
//	all r:RequiredProblemConstraint | not some p: Problem | r.problemName = p.label
//}
//fact forceUnsatisfiableExceptionNotToExist {
//	all r: RequiredProblemConstraint | some p: Problem | r.problemName = p.label
//}

fact {
	all ge: GeneratedExam, gc: GroupConstraint, p: Problem, m: MasterExam | 
		



satisfiableTogether [gc, m] and 
}


sig TopicOrganizer {
	one category: Category,
	set GroupConstraint
}





one sig MasterExamController {
	topicOrganizers: set TopicOrganizer
}



one 
all gcc: GroupConstraintContainer | 
*/
















//assert correctNumProblems {
//	all g: GeneratedExam,  | #g.problems = 
//}


/*
sig SatisfiedContainer {
	category: one Category,
	requiredProblems: one ProblemList,
	groupConstraints: set ConstraintContainer
}

sig GroupConstraintContainer {
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
