#!/bin/bash
cd usersmanual/
pdflatex UsersManual.tex
cd ../
ant clean
ant
ant package
