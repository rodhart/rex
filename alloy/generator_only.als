one sig Generator
{
	uef: one UniversalExamFile,
	ecf: one ExamConfigurationFile,
	generated: set GeneratedExam
}


sig ExamConfigurationFile {}

sig UniversalExamFile {}

sig GeneratedExam {}



//********************************************



fact masterInGenerator {
	all m: UniversalExamFile | some g: Generator | m in g.uef
}
fact configInGenerator { 
	all c: ExamConfigurationFile | some g: Generator | c in g.ecf
}
fact generatedInGenerator {
	all ge: GeneratedExam | some g: Generator | ge in g.generated
}


pred show{}

run show for 3 but exactly 3 GeneratedExam
