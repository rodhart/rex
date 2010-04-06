\LoadClass{article}

	%example:
	%	\begin{block}{FSA Description}
	%	\end{block}
\newenvironment{block}[1]{}{}

	%example:
	%	\begin{problem}[requires=FSA Description,points=3]{topic}{difficulty}
	%	\end{problem}
\newenvironment{problem}[3][]{\begin{enumerate} \item}{\end{enumerate}}

	%example:
	%	\begin{answers}
	%	end{answers}
\newenvironment{answers}{\begin{enumerate}}{\end{enumerate}}

	%example:
	%	\answer[correct|fixed] text
\newcommand{\answer}[1][]{\item}
