/**
 * @author cates
 */
grammar EcfAntlr;

// Suppress a warning about ANTLR defaulting to this anyways
options {
output=AST;
}


// lexer needs its own header, otherwise it gets confused
@lexer::header {
package edu.udel.cis.cisc475.rex.ecfparser.impl.parser;
}

// tons of stuff to include
@header {
package edu.udel.cis.cisc475.rex.ecfparser.impl.parser; 
import edu.udel.cis.cisc475.rex.ecfparser.impl.err.EcfParseHackException;
import edu.udel.cis.cisc475.rex.ecfparser.impl.err.EcfUnsatisfiableHackException;
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import edu.udel.cis.cisc475.rex.err.RexUnsatisfiableException;
import edu.udel.cis.cisc475.rex.err.RexParseException;
import java.util.LinkedList;
import java.lang.Double;
import java.lang.Integer;
}


// these will be used throughout the parser
@members {
	private String filename;
	private ConfigIF config;
	private IntervalFactoryIF intervalFactory = new IntervalFactory();
	private SourceFactoryIF sourceFactory = new SourceFactory();
	private boolean haveVersions = false;
	private boolean haveAppend = false;
	
	@Override
  protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
  }
}

@rulecatch {
    catch (RecognitionException e) {
        throw e;
    }
}

@lexer::members {
    @Override
    public void reportError(RecognitionException e) {
        throw new RuntimeException(e);
    }

}    

//------------------
// parser
//------------------
ecf [ConfigIF configuration, String filename]
    :   {config = (ConfigIF)$configuration; filename = $filename;}
        statement* EOF!
    ;

// catch all for a statement
statement
	:	include | includeall | append | versions
	;

// include statement
include
	:	INCLUDE n=numInt PROBLEMS
		ON topic=STRING
		WITH DIFFICULTY IN interval
		AT p=numInt POINTS
		SEMI
		{   SourceIF mySource = sourceFactory.newSource(filename);
		    mySource.setStartLine($INCLUDE.line);
		    mySource.setStartColumn($INCLUDE.pos);
		    mySource.setLastLine($SEMI.line);
		    mySource.setLastColumn($SEMI.pos);
		    mySource.addText($text);
		    
		    config.addGroupConstraint($topic.text, $interval.i, $n.value, $p.value, mySource);
		}
	;

// interval statement
interval returns [IntervalIF i]
	:	l=leftBound COMMA r=rightBound
	    {
					if($l.value.compareTo($r.value) > 0) {
						throw new EcfUnsatisfiableHackException("lower bound greater than upper bound", $text, input);
					}
	        $i = intervalFactory.interval($l.strict, $l.value, $r.strict, $r.value);
	    }
	;


leftBound returns [boolean strict, Double value]
	:	   ( LEFT_CLOSED {$strict = true;} | LEFT_OPEN {$strict = false;} )
	     numDouble {$value = $numDouble.value;}
	;

rightBound returns [boolean strict, Double value]
	:    numDouble {$value = $numDouble.value;}
	     ( RIGHT_CLOSED {$strict = true;} | RIGHT_OPEN {$strict = false;} )
	;


// includeall statement
includeall
	:		{
	        LinkedList<String> labels = new LinkedList<String>();
	    }
	    INCLUDEALL (COMMA? LABEL {labels.add($LABEL.text);})+
	    AT numInt POINTS SEMI
	    {
	       SourceIF includeSource = sourceFactory.newSource(filename);
          includeSource.setStartLine($INCLUDEALL.line);
          includeSource.setStartColumn($INCLUDEALL.pos);
          includeSource.setLastLine($SEMI.line);
          includeSource.setLastColumn($SEMI.pos);
          includeSource.addText($text);
	        for (String l : labels) {
	            config.addRequiredProblemConstraint(l, $numInt.value, includeSource);
	        }
	    }
	;


// append statement
append
	:	APPEND LABEL SEMI
	{
	   SourceIF appendSource = sourceFactory.newSource(filename);
     appendSource.setStartLine($APPEND.line);
     appendSource.setStartColumn($APPEND.pos);
     appendSource.setLastLine($SEMI.line);
     appendSource.setLastColumn($SEMI.pos);
     appendSource.addText($text);
     
	   if(haveAppend) {
	     throw new EcfParseHackException("Must have only one append block", $text, input, appendSource);
	   } else {
	     config.setFinalBlock($LABEL.text);
	     haveAppend = true;
	   }
	}
	;

// versions statement
versions
	:	  {
	        LinkedList<String> versions = new LinkedList<String>();
	    }
	    VERSIONS ARE (COMMA? STRING {versions.add($STRING.text);})+ SEMI
	{
     SourceIF versionsSource = sourceFactory.newSource(filename);
     versionsSource.setStartLine($VERSIONS.line);
     versionsSource.setStartColumn($VERSIONS.pos);
     versionsSource.setLastLine($SEMI.line);
     versionsSource.setLastColumn($SEMI.pos);
     versionsSource.addText($text);
     
     if(haveVersions) {
       throw new EcfParseHackException("Only one set of version strings can be defined.", $text, input, versionsSource);
     } else {
       try {
         config.setVersionStrings(versions.toArray(new String[0]));
       } catch(RexParseException e) {
         throw new EcfParseHackException(e.getMessage(), $text, input, versionsSource);
       }
     }
  }
	;

numInt returns [int value]
    :   INT {$value = Integer.valueOf($INT.text);}
    ;

numDouble returns [Double value]
    :   (n=FLOAT|n=INT) {$value = Double.valueOf($n.text);}
    |   INFTY {$value = Double.valueOf(Double.POSITIVE_INFINITY);}
    |   NEG_INFTY {$value = Double.valueOf(Double.NEGATIVE_INFINITY);}
    ;


// $<Lexer

INCLUDE: 'include';
INCLUDEALL: 'includeall';
APPEND: 'append';
VERSIONS: 'versions';
SEMI: '\;';
LEFT_OPEN: '(';
LEFT_CLOSED: '[';
RIGHT_OPEN: ')';
RIGHT_CLOSED: ']';
COMMA: ',';
INFTY: '\\infty';
NEG_INFTY: '-\\infty';

// ignorable parts of statements
PROBLEMS: 'problems';
ON: 'on';
IN: 'in';
AT: 'at';
ARE: 'are';
WITH:	'with';
DIFFICULTY: 'difficulty';
POINTS: 'points';



// predefined stuff
LABEL  :	('a'..'z'|'A'..'Z'|'_'|'\:') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'\:')*
    ;




INT :	'-'? '0'..'9'+
    ;

FLOAT
    :   '-'? ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '-'? '.' ('0'..'9')+ EXPONENT?
    |   '-'? ('0'..'9')+ EXPONENT
    ;

COMMENT
    :   '#' NONCONTROL_CHAR* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS	: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN; };

STRING: '"' NONCONTROL_CHAR* '"' {setText(getText().substring(1, getText().length()-1));} ;

fragment NONCONTROL_CHAR: LETTER | DIGIT | SYMBOL | SPACE;
fragment LETTER: LOWER | UPPER;
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment DIGIT: '0'..'9';
fragment SPACE: ' ' | '\t';
fragment SYMBOL: '!' | '#'..'/' | ':'..'@' | '['..'`' | '{'..'~';

fragment EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
    


// $>
