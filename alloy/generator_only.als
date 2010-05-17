one sig Generator
{
	master: one UniversalExamFile,
	config: one ExamConfigurationFile,
	generated: set GeneratedExam
}


sig ExamConfigurationFile {}

sig UniversalExamFile {}

sig GeneratedExam {}


fact masterInGenerator {
	all m: UniversalExamFile | some g: Generator | m in g.master
}
fact configInGenerator { 
	all c: ExamConfigurationFile | some g: Generator | c in g.config
}
fact generatedInGenerator {
	all ge: GeneratedExam | some g: Generator | ge in g.generated
}


pred show{}

run show for 3 but exactly 3 GeneratedExam
