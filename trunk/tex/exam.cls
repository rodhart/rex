\NeedsTeXFormat{LaTeX2e}

\LoadClass{article}

\RequirePackage{ifthen}

%declare boolean variable, initiate it as false
\newif\ifprintanswers
\printanswersfalse


        %example:
        %       \documentclass[master]{exam}
\DeclareOption{master}{\printanswerstrue}
%\DeclareOption{foo}{\typeout{Saw foo.}}
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

	%example: 
	%	\answer[correct,fixed] text
        % 0 arguements, 1 optional arguement
\newcommand{\answer}[1][\@empty]{
 \ifprintanswers
   \ifx\@empty#1
     \item
    \else
      \item
	$\star$
   \fi	
 \else
   \item
 \fi
}

%prints to commandline if master is present
%\ifprintanswers
%typeout{MASTER HERE}
%\else
%\typeout{NOT HERE}
%\fi

