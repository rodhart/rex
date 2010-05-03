Our awesome team:
Fran Fitzpatrick
Reed Martz
Tim McClory

Problems we've found:
1) With the provided UEF and ECF in examples/ pdflatex does not run successfully on the output exam
2) With an ECF with all constraints commented out, REX produces a parsing error.
3) With an ECF with no constraints, REX produces an empty exam.
4) It appears that the ECF parser does not handle comments properly.
5) With one constraint, the output LaTeX is badly formed, and brings up a prompt from pdflatex.
6) Tags appear to be mismatched. There's a problem with nested blocks.
7) Fixed answers aren't fixed.
8) Errors should mention what file they were working on when they failed
9) Error messages should not end with a period
10) Goodbye message not always appended to the output.
11) Misplaced blocks
