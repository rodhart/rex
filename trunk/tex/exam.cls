\NeedsTeXFormat{LaTeX2e}

\LoadClass{article}

\RequirePackage{ifthen}

%declare boolean variable, initiate it as false
\newif\ifprintanswers
\printanswersfalse


        %example:
        %       \documentclass[master]{exam}
\DeclareOption{master}{\printanswerstrue}
\ProcessOptions

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

       %compares two string, returns \sametrue if strings equal
\newif\ifsame
\newcommand{\strcfstr}[2]{%
\samefalse
\begingroup
\def\1{#1}\def\2{#2}%
\ifx\1\2\endgroup \sametrue
\else \endgroup
\fi}


	%example: 
	%	\answer[correct,fixed] text
        % 0 arguements, 1 optional arguement
\newcommand{\answer}[1][]{
 \ifprintanswers %if master option present
   \strcfstr{#1}{correct}
   \ifsame %if correct, mark w/star
       \item
       $\star$
    \else
      \strcfstr{#1}{correct,fixed}
      \ifsame %if correct,fixed, mark w/star
         \item
	 $\star$
      \else 
         \item
      \fi
   \fi	
 \else %not master option
   \item
 \fi
}

