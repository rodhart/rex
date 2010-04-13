// Author: Tim Armstrong

/*
one sig Totality
{
	master: one MasterExam
}
*/

// all fields accounted for
abstract sig Exam {
	preamble: one Source,
	frontMatter: one Source,
	finalBlock: lone Block,
	elements: set ExamElement // needs to be an *ordered list*. Implementing that looks difficult.
														// See pg 157-9 in Jackson's book for lists in Alloy
}


one sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {
	master: one MasterExam,
	config: one Config
}



// fields not complete
one sig Config {
	//count: Category ->one Int // ??? From Dr. Siegel
	seed: one Int,
	constraints: set Constraint   // no order necessary
}

// fields complete
abstract sig Constraint {
	source: one Source,
	points: one Int  // maybe would want real number?
}
fact {
	all c: Constraint | some conf: Config | c in conf.constraints
}

// fields not complete
sig GroupConstraint extends Constraint {
	numProblems: one Int,
	category: one Category,
	interval: one Interval
}

sig RequiredProblemConstraint extends Constraint {
	problemNames: set String
}
fact {
	all r: RequiredProblemConstraint | #r.problemNames > 1
}
 

// Booleans are difficult to implement in Alloy (see Jackson pg 136).
// Therefore I omit here the Boolean values for the range being / not being inclusive.
// They have no real bearing on the model.  I also omit the possibilities of infinity values.
sig Interval {
	low: one Int,
	high: one Int
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
	difficulty: one Int   //actually REAL NUMBER, I have still to find how to say that
}

sig MultipleChoice extends Problem {
	answers: set Answer
}

sig OtherProblem extends Problem {} // no answers field





sig Block extends ExamElement {
	category: one Category, // or lone if we say the final block's category may be null
	source: one Source
}

sig Figure extends ExamElement {}




sig Category {} // this is not a real object, it is just a Java String.  We can't give it many properties.

sig Answer{}

sig Source{}





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
	all b: Block, g: GeneratedExam, p: Problem | (b not in g.finalBlock) and (b not in p.block) implies b not in g.elements
}


//"REX guarantees that if given the same arguments and seed twice, it will produce the exact same outputs."
fact seedDeterminesOutput {
	all g1, g2: GeneratedExam | g1.config.seed = g2.config.seed implies g1 = g2   // is this == or .equals???
}



/*
If the answers environment is used, it must include at least one answer
The maximum number of answers is 26.

ATTEMPT:
fact validNumAnswers {
	all m: MultipleChoice | #m.answers >= 1 and #m.answers <= 26
}

ERROR message:
"A type error has occured:
Current bitwidth is set to 4, thus this integer constant 26
is bigger than the maximum integer 7"

Though in any case we CAN do:
*/
fact validNumAnswers {
	all m: MultipleChoice | #m.answers >= 1
}






//ECF:  The first <integer> is a positive integer: it is the number of problems satisfying the constraint that
// should be included.

//assert sumConstraintProblems {







//At least one answer must be labeled as correct.
fact atLeastOneCorrect {
//TODO
}

//[fixed]: signifying that this answer must appear in its given position in the answer sequence
fact fixedAtCorrectPosition {
//TODO
}




// no problem will occur in a generated exam more than once (even if it satisfies more than one constraint)
fact problemsUnique {
//TODO
}


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












pred show{}

run show for 3 but 1 GeneratedExam


/*  Dr. Siegel's original:

sig Category {
}

sig Problem {
  category: one Category
}

one sig MasterExam {
  probs: set Problem
}

sig Config {
  count: Category ->one Int
}

fact {
  all config: Config, cat: Category | config.count[cat] > 0       //Tim's note: config.count[cat] = cat.(config.count)
}

sig GenExam {
  master: one MasterExam,
  config: one Config,
  genprobs: set Problem
}

pred generate[gen: GenExam] {
    gen.genprobs in gen.master.probs
    all c: Category | #{p: gen.genprobs | p.category = c} = gen.config.count[c]
}

pred showgenerate[gen: GenExam] {
    #gen.genprobs > 3
    #GenExam = 1
    #Category > 1
    generate[gen]
}

run showgenerate for 5
*/
