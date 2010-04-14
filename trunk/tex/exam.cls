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
        %       \examheader{CISC 106 Fall 2009}{First Midterm Learning Experience}{Date}
\newcommand{\examheader}[3]{
\vskip .1in
\noindent
{\large  #1 \hfill #2 \hfill #3 \\}
\vskip 0.05in

\vspace{.6in}
Name \rule{3in}{1pt} 
Login name \rule{2in}{1pt} 

\vspace{.4in}
Lecture Section:   \rule{.5in}{1pt}\rule{.25in}{0pt} Lab Section:   \rule{.5in}{
1pt}  \rule{.5in}{0pt} TA \rule{.8in}{1pt}
\vspace{14pt}
} 

	%example:
	%	\begin{block}{FSA Description}
	%	\end{block}
\newenvironment{block}[1]{}{}

%create problem number counter, set
\newcounter{probnum}
\setcounter{probnum}{1}

	%example:
	%	\begin{problem}[requires=FSA Description,points=3]{topic}{difficulty}
	%	\end{problem}
\newenvironment{problem}[3][]{\begin{enumerate} \item[] \arabic{probnum}. \stepcounter{probnum} }{\end{enumerate}}


	%example:
	%	\begin{answers}
	%	end{answers}
\newenvironment{answers}{\begin{enumerate}}{\end{enumerate}}

        %example:
        %       \answer[correct,fixed] text
\newcommand{\answer}[1][\@empty]{
 \ifprintanswers %if master option present
   \ifthenelse{\equal{#1}{correct}}{\item $\star$}{ %if correct, mark w/star
   \ifthenelse{\equal{#1}{correct,fixed}}{\item $\star$}{ %if correct,fixed
   \ifthenelse{\equal{#1}{fixed,correct}}{\item $\star$}{\item}}} %if fixed,correct
 \else %not master option
   \item
 \fi
}
