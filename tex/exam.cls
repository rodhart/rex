%Author: Lucero Carmona
\NeedsTeXFormat{LaTeX2e}

\LoadClass{article}

\RequirePackage{ifthen}

%declare boolean variable, initiate it as false
%used to print answers, if master option is present
\newif\ifprintanswers
\printanswersfalse

        %example:
        %       \documentclass[master]{exam}
        %prints stars next to correct answers
        %       \documentclass{exam}
        %does not print stars
\DeclareOption{master}{\printanswerstrue}
\ProcessOptions

        %example:
        %       \examheader{CISC 106 Fall 2009}{First Midterm Learning Experience}{Date}
        %prints header on page, includes sections for students to enter information
        %options allow for instructor to initiate Left Header, Center Header, Right Header
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
        %creates environment for message blocks
        %ex. instruction, goodbye message
\newenvironment{block}[1]{}{}

%create problem number counter, set
\newcounter{probnum}
\setcounter{probnum}{1}

	%example:
	%	\begin{problem}[requires=FSA Description,points=3]{topic}{difficulty}
	%	\end{problem}
        %creates environment for problems, counter used to number problems
        %does not need to include answer environment
\newenvironment{problem}[3][]{\begin{enumerate} \item[] \arabic{probnum}. \stepcounter{probnum} }{\end{enumerate}}


	%example:
	%	\begin{answers}
	%	end{answers}
        %creates environment for multiple choice answers
\newenvironment{answers}{\begin{enumerate}}{\end{enumerate}}

        %example:
        %       \answer[correct,fixed] text
        %lists answers, has ability to mark which answers are correct
        %or fixed, more than one correct or fixed answer is permitted
\newcommand{\answer}[1][\@empty]{
 \ifprintanswers %if master option present
   \ifthenelse{\equal{#1}{correct}}{\item $\star$}{ %if correct, mark w/star
   \ifthenelse{\equal{#1}{correct,fixed}}{\item $\star$}{ %if correct,fixed
   \ifthenelse{\equal{#1}{fixed,correct}}{\item $\star$}{\item}}} %if fixed,correct
 \else %not master option
   \item
 \fi
}

        %example
        %\examversion
        %used by output module to print exam versions
\newcommand{\examversion}{}