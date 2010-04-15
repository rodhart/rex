grammar EcfAntlr;

options {
output=AST;
}

tokens {
	INCLUDE_STMNT;
	INCLUDEALL_STMNT;
	APPEND_STMNT;
	VERSIONS_STMNT;
	OPEN;
	CLOSED;
	INTERVAL;
	INFINITY;
	LABELS;
}

@lexer::header {
package edu.udel.cis.cisc475.rex.ecfparser.impl.parser;
}

@header {
package edu.udel.cis.cisc475.rex.ecfparser.impl.parser; 
import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalIF;
import edu.udel.cis.cisc475.rex.interval.IF.IntervalFactoryIF;
import edu.udel.cis.cisc475.rex.interval.impl.IntervalFactory;
import edu.udel.cis.cisc475.rex.source.IF.SourceIF;
import edu.udel.cis.cisc475.rex.source.IF.SourceFactoryIF;
import edu.udel.cis.cisc475.rex.source.impl.SourceFactory;
import java.util.LinkedList;
import java.lang.Double;
import java.lang.Integer;
}


@members {
	private ConfigIF config;
	private IntervalFactoryIF intervalFactory = new IntervalFactory();
	private SourceFactoryIF sourceFactory = new SourceFactory();
}



//------------------
// parser
ecf[ConfigIF configuration]
    :   statement* EOF!
    {
        config = (ConfigIF)$configuration;
    }
    ;

statement
	:	include | includeall | append | versions
	;

include
	:	INCLUDE n=numInt PROBLEMS
		ON topic=STRING
		WITH DIFFICULTY IN interval
		AT p=numInt POINTS
		SEMI
		{   SourceIF funsauce = sourceFactory.newSource("TODO");
		    System.out.println(funsauce.filename());
		    config.addGroupConstraint($topic.text, $interval.i, $n.value, $p.value, funsauce);
		}
		//-> ^(INCLUDE_STMNT $topic $n interval $p)
	;

interval returns [IntervalIF i]
	:	l=leftBound COMMA r=rightBound //-> ^(INTERVAL leftBound rightBound)
	    {
	        System.out.println(" " + $l.strict + " " + $l.value + " " + $r.strict + " " + $r.value);
	        $i = intervalFactory.interval($l.strict, $l.value, $r.strict, $r.value);
	    }
	;

leftBound returns [boolean strict, Double value]
	:	LEFT_CLOSED {$strict = true;}
	    numDouble {$value = $numDouble.value;}
	|	LEFT_OPEN {$strict = false;}
	    NEG_INFTY {$value = Double.valueOf(Double.NEGATIVE_INFINITY);}
	|	LEFT_OPEN {$strict = false;}
	    numDouble {$value = $numDouble.value;}
	;

rightBound returns [boolean strict, Double value]
	:	numDouble {$value = $numDouble.value;}
	    RIGHT_CLOSED {$strict = true;}
	|	INFTY {$value = Double.valueOf(Double.POSITIVE_INFINITY);}
	    RIGHT_OPEN {$strict = false;}
	|	numDouble {$value = $numDouble.value;}
	    RIGHT_OPEN {$strict = false;}
	;

includeall
	:	{
	        LinkedList<String> labels = new LinkedList<String>();
	    }
	    INCLUDEALL (COMMA? LABEL {labels.add($LABEL.text);})+
	    AT numInt POINTS SEMI
	    {
	        for (String l : labels)
	            config.addRequiredProblemConstraint(l, $numInt.value, sourceFactory.newSource("TODO"));
	    }
	;

/*labels
	:	(COMMA? LABEL)+ -> ^(LABELS LABEL*)
	;*/

append
	:	APPEND STRING SEMI {config.setFinalBlock($STRING.text);}
	;

versions
	:	{
	        LinkedList<String> versions = new LinkedList<String>();
	    }
	    VERSIONS ARE (COMMA? STRING {versions.add($STRING.text);})+ SEMI
	    {
	        config.setVersionStrings(versions.toArray(new String[0]));
	    }
	;

numInt returns [int value]
    :   INT {$value = Integer.valueOf($INT.text);}
    ;

numDouble returns [Double value]
    :   (n=FLOAT|n=INT) {$value = Double.valueOf($n.text);}
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

/*STRING
    :  '"' ('a'..'z' | 'A'..'Z' | '\\' | '-' |  )* '"'
    ;*/
WS	: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN; };

STRING: '"' NONCONTROL_CHAR* '"' {setText(getText().substring(1, getText().length()-1));} ;

fragment NONCONTROL_CHAR: LETTER | DIGIT | SYMBOL | SPACE;
fragment LETTER: LOWER | UPPER;
fragment LOWER: 'a'..'z';
fragment UPPER: 'A'..'Z';
fragment DIGIT: '0'..'9';
fragment SPACE: ' ' | '\t';
fragment SYMBOL: '!' | '#'..'/' | ':'..'@' | '['..'`' | '{'..'~';

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

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
    

// maybe this will work


// $>
