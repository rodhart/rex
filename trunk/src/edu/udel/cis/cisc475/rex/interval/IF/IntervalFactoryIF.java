//package edu.udel.cis.cisc475.rex.interval;

import java.util.*;
import java.io.*;

/*
 * @author team 2
 *
 */

public interface IntervalFactoryIF {
    IntervalIF interval(boolean strictLow, Double low, boolean strictHigh, Double high);
}//end of interface

