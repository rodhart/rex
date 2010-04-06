//package edu.udel.cis.cisc475.rex.config;

import java.util.*;
import java.io.*;


/*
 * @author team 2
 *
 */

public interface GroupConstraintIF {
    int numProblems();
    IntervalIF difficultyInterval();
    String topic();
    int points();

}//end of interface