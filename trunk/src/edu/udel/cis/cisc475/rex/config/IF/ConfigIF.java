//package edu.udel.cis.cisc475.rex.config;

import java.util.*;
import java.io.*;


/*
 * @author team 2
 *
 */

public interface ConfigIF {
       boolean pdfOption();
       long seed();
       Collection<ConstraintIF> constraints();
       String finalBlock();
       String[] versionStrings;
       int seed();
       int numVersions();
       void setSeed(long value);
       void setVersionStrings(String[] names);
       void setFinalBlock(String label);
       GroupConstraintIF addGroupConstraintIF(String topic, IntervalIF difficulty, int points, SourceIF source);
       RequriedProblemConstraintIF addRequiredProblemConstraint(String label, int points SourceIF source);

}//end of interface