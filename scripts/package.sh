#!/bin/bash
# This script will build the manual and the program, and pack everything up for
# distribution. Run this script from the root of the project folder not the scripts folder 
cd usersmanual/
pdflatex UsersManual.tex
cd ../
ant clean
ant jar
ant package


