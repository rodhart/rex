#!/bin/bash

ant clean
ant jar
tar -czf Rex.tar.gz REX.jar usersmanual/UsersManual.pdf examples/exam.cls examples/exam.tex examples/exam.ecf

