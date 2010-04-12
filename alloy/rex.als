// Author: Tim Armstrong



sig Exam {
	preamble: one Source,
	frontMatter: one Source,
	finalBlock: lone Block,   // we don't need this since the exam is
											  // (<problem> | <block> | <figure>)*
	elements: set ExamElement
}

one sig MasterExam extends Exam {}

sig GeneratedExam extends Exam {}



one sig Config {
  count: Category ->one Int
}




sig ExamElement {
	label: lone String
}

// all fields accounted for
sig Problem extends ExamElement {
	block: lone Block,
	figures: set Figure,
	points: lone Int,   // lone because will be null in master exam.  (It is an integer, not a real number in the design.)
	category: one Category,
	source: one Source,
	answers: set Answer,
	difficulty: one Int   //actually REAL NUMBER, I have still to find how to say that
}

sig Block extends ExamElement {
	topic: one String,
	source: one Source
}

sig Figure extends ExamElement {}



sig Category {} // this is not a real object, it is just a Java String.  We can't give it properties.

sig Answer{}

sig Source{}





// A Problem may refer to a Block only of the same topic.
// This includes the constraint that all problems
// referring to a block must be of the same topic
fact problemBlockSameTopic{
	all p: Problem, b: Block | p.block = b implies p.topic = b.topic
}




// Say that in the generated exams, problems group into topics
fact generatedIsGrouped {
// TODO
}



// Says that in a generated exam, all questions that refer to a block will be grouped together, with the
// block immediately preceding.
fact blockWithQuestions {
//TODO
}



pred show{}

run show for 3


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
