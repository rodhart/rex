// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g 2010-04-15 02:15:44

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class EcfAntlrParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INCLUDE_STMNT", "INCLUDEALL_STMNT", "APPEND_STMNT", "VERSIONS_STMNT", "OPEN", "CLOSED", "INTERVAL", "INFINITY", "LABELS", "INCLUDE", "PROBLEMS", "ON", "STRING", "WITH", "DIFFICULTY", "IN", "AT", "POINTS", "SEMI", "COMMA", "LEFT_CLOSED", "LEFT_OPEN", "NEG_INFTY", "RIGHT_CLOSED", "INFTY", "RIGHT_OPEN", "INCLUDEALL", "LABEL", "APPEND", "VERSIONS", "ARE", "INT", "FLOAT", "EXPONENT", "NONCONTROL_CHAR", "COMMENT", "WS", "LETTER", "DIGIT", "SYMBOL", "SPACE", "LOWER", "UPPER", "HEX_DIGIT", "UNICODE_ESC", "OCTAL_ESC", "ESC_SEQ"
    };
    public static final int APPEND=32;
    public static final int EXPONENT=37;
    public static final int INCLUDE_STMNT=4;
    public static final int LETTER=41;
    public static final int POINTS=21;
    public static final int INCLUDEALL=30;
    public static final int OCTAL_ESC=49;
    public static final int LEFT_OPEN=25;
    public static final int PROBLEMS=14;
    public static final int FLOAT=36;
    public static final int SPACE=44;
    public static final int EOF=-1;
    public static final int INTERVAL=10;
    public static final int VERSIONS=33;
    public static final int AT=20;
    public static final int VERSIONS_STMNT=7;
    public static final int ESC_SEQ=50;
    public static final int ARE=34;
    public static final int IN=19;
    public static final int COMMA=23;
    public static final int INFTY=28;
    public static final int INCLUDE=13;
    public static final int INCLUDEALL_STMNT=5;
    public static final int DIGIT=42;
    public static final int NEG_INFTY=26;
    public static final int COMMENT=39;
    public static final int WITH=17;
    public static final int RIGHT_CLOSED=27;
    public static final int SYMBOL=43;
    public static final int UNICODE_ESC=48;
    public static final int ON=15;
    public static final int HEX_DIGIT=47;
    public static final int LEFT_CLOSED=24;
    public static final int INT=35;
    public static final int DIFFICULTY=18;
    public static final int SEMI=22;
    public static final int LABELS=12;
    public static final int OPEN=8;
    public static final int WS=40;
    public static final int NONCONTROL_CHAR=38;
    public static final int LABEL=31;
    public static final int INFINITY=11;
    public static final int APPEND_STMNT=6;
    public static final int CLOSED=9;
    public static final int LOWER=45;
    public static final int RIGHT_OPEN=29;
    public static final int UPPER=46;
    public static final int STRING=16;

    // delegates
    // delegators


        public EcfAntlrParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EcfAntlrParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EcfAntlrParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g"; }


    	private ConfigIF config;
    	private IntervalFactoryIF intervalFactory = new IntervalFactory();
    	private SourceFactoryIF sourceFactory = new SourceFactory();


    public static class ecf_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ecf"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:48:1: ecf[ConfigIF configuration] : ( statement )* EOF ;
    public final EcfAntlrParser.ecf_return ecf(ConfigIF configuration) throws RecognitionException {
        EcfAntlrParser.ecf_return retval = new EcfAntlrParser.ecf_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF2=null;
        EcfAntlrParser.statement_return statement1 = null;


        Object EOF2_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:49:5: ( ( statement )* EOF )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:49:9: ( statement )* EOF
            {
            root_0 = (Object)adaptor.nil();

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:49:9: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INCLUDE||LA1_0==INCLUDEALL||(LA1_0>=APPEND && LA1_0<=VERSIONS)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:49:9: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_ecf94);
            	    statement1=statement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, statement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_ecf97); 

                    config = (ConfigIF)configuration;
                

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ecf"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:55:1: statement : ( include | includeall | append | versions );
    public final EcfAntlrParser.statement_return statement() throws RecognitionException {
        EcfAntlrParser.statement_return retval = new EcfAntlrParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        EcfAntlrParser.include_return include3 = null;

        EcfAntlrParser.includeall_return includeall4 = null;

        EcfAntlrParser.append_return append5 = null;

        EcfAntlrParser.versions_return versions6 = null;



        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:56:2: ( include | includeall | append | versions )
            int alt2=4;
            switch ( input.LA(1) ) {
            case INCLUDE:
                {
                alt2=1;
                }
                break;
            case INCLUDEALL:
                {
                alt2=2;
                }
                break;
            case APPEND:
                {
                alt2=3;
                }
                break;
            case VERSIONS:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:56:4: include
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_include_in_statement118);
                    include3=include();

                    state._fsp--;

                    adaptor.addChild(root_0, include3.getTree());

                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:56:14: includeall
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_includeall_in_statement122);
                    includeall4=includeall();

                    state._fsp--;

                    adaptor.addChild(root_0, includeall4.getTree());

                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:56:27: append
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_append_in_statement126);
                    append5=append();

                    state._fsp--;

                    adaptor.addChild(root_0, append5.getTree());

                    }
                    break;
                case 4 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:56:36: versions
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_versions_in_statement130);
                    versions6=versions();

                    state._fsp--;

                    adaptor.addChild(root_0, versions6.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class include_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "include"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:59:1: include : INCLUDE n= numInt PROBLEMS ON topic= STRING WITH DIFFICULTY IN interval AT p= numInt POINTS SEMI ;
    public final EcfAntlrParser.include_return include() throws RecognitionException {
        EcfAntlrParser.include_return retval = new EcfAntlrParser.include_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token topic=null;
        Token INCLUDE7=null;
        Token PROBLEMS8=null;
        Token ON9=null;
        Token WITH10=null;
        Token DIFFICULTY11=null;
        Token IN12=null;
        Token AT14=null;
        Token POINTS15=null;
        Token SEMI16=null;
        EcfAntlrParser.numInt_return n = null;

        EcfAntlrParser.numInt_return p = null;

        EcfAntlrParser.interval_return interval13 = null;


        Object topic_tree=null;
        Object INCLUDE7_tree=null;
        Object PROBLEMS8_tree=null;
        Object ON9_tree=null;
        Object WITH10_tree=null;
        Object DIFFICULTY11_tree=null;
        Object IN12_tree=null;
        Object AT14_tree=null;
        Object POINTS15_tree=null;
        Object SEMI16_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:60:2: ( INCLUDE n= numInt PROBLEMS ON topic= STRING WITH DIFFICULTY IN interval AT p= numInt POINTS SEMI )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:60:4: INCLUDE n= numInt PROBLEMS ON topic= STRING WITH DIFFICULTY IN interval AT p= numInt POINTS SEMI
            {
            root_0 = (Object)adaptor.nil();

            INCLUDE7=(Token)match(input,INCLUDE,FOLLOW_INCLUDE_in_include141); 
            INCLUDE7_tree = (Object)adaptor.create(INCLUDE7);
            adaptor.addChild(root_0, INCLUDE7_tree);

            pushFollow(FOLLOW_numInt_in_include145);
            n=numInt();

            state._fsp--;

            adaptor.addChild(root_0, n.getTree());
            PROBLEMS8=(Token)match(input,PROBLEMS,FOLLOW_PROBLEMS_in_include147); 
            PROBLEMS8_tree = (Object)adaptor.create(PROBLEMS8);
            adaptor.addChild(root_0, PROBLEMS8_tree);

            ON9=(Token)match(input,ON,FOLLOW_ON_in_include151); 
            ON9_tree = (Object)adaptor.create(ON9);
            adaptor.addChild(root_0, ON9_tree);

            topic=(Token)match(input,STRING,FOLLOW_STRING_in_include155); 
            topic_tree = (Object)adaptor.create(topic);
            adaptor.addChild(root_0, topic_tree);

            WITH10=(Token)match(input,WITH,FOLLOW_WITH_in_include159); 
            WITH10_tree = (Object)adaptor.create(WITH10);
            adaptor.addChild(root_0, WITH10_tree);

            DIFFICULTY11=(Token)match(input,DIFFICULTY,FOLLOW_DIFFICULTY_in_include161); 
            DIFFICULTY11_tree = (Object)adaptor.create(DIFFICULTY11);
            adaptor.addChild(root_0, DIFFICULTY11_tree);

            IN12=(Token)match(input,IN,FOLLOW_IN_in_include163); 
            IN12_tree = (Object)adaptor.create(IN12);
            adaptor.addChild(root_0, IN12_tree);

            pushFollow(FOLLOW_interval_in_include165);
            interval13=interval();

            state._fsp--;

            adaptor.addChild(root_0, interval13.getTree());
            AT14=(Token)match(input,AT,FOLLOW_AT_in_include169); 
            AT14_tree = (Object)adaptor.create(AT14);
            adaptor.addChild(root_0, AT14_tree);

            pushFollow(FOLLOW_numInt_in_include173);
            p=numInt();

            state._fsp--;

            adaptor.addChild(root_0, p.getTree());
            POINTS15=(Token)match(input,POINTS,FOLLOW_POINTS_in_include175); 
            POINTS15_tree = (Object)adaptor.create(POINTS15);
            adaptor.addChild(root_0, POINTS15_tree);

            SEMI16=(Token)match(input,SEMI,FOLLOW_SEMI_in_include179); 
            SEMI16_tree = (Object)adaptor.create(SEMI16);
            adaptor.addChild(root_0, SEMI16_tree);

               SourceIF funsauce = sourceFactory.newSource("TODO");
            		    System.out.println(funsauce.filename());
            		    config.addGroupConstraint((topic!=null?topic.getText():null), (interval13!=null?interval13.i:null), (n!=null?n.value:0), (p!=null?p.value:0), funsauce);
            		

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "include"

    public static class interval_return extends ParserRuleReturnScope {
        public IntervalIF i;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "interval"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:72:1: interval returns [IntervalIF i] : l= leftBound COMMA r= rightBound ;
    public final EcfAntlrParser.interval_return interval() throws RecognitionException {
        EcfAntlrParser.interval_return retval = new EcfAntlrParser.interval_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA17=null;
        EcfAntlrParser.leftBound_return l = null;

        EcfAntlrParser.rightBound_return r = null;


        Object COMMA17_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:73:2: (l= leftBound COMMA r= rightBound )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:73:4: l= leftBound COMMA r= rightBound
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_leftBound_in_interval203);
            l=leftBound();

            state._fsp--;

            adaptor.addChild(root_0, l.getTree());
            COMMA17=(Token)match(input,COMMA,FOLLOW_COMMA_in_interval205); 
            COMMA17_tree = (Object)adaptor.create(COMMA17);
            adaptor.addChild(root_0, COMMA17_tree);

            pushFollow(FOLLOW_rightBound_in_interval209);
            r=rightBound();

            state._fsp--;

            adaptor.addChild(root_0, r.getTree());

            	        System.out.println(" " + (l!=null?l.strict:false) + " " + (l!=null?l.value:null) + " " + (r!=null?r.strict:false) + " " + (r!=null?r.value:null));
            	        retval.i = intervalFactory.interval((l!=null?l.strict:false), (l!=null?l.value:null), (r!=null?r.strict:false), (r!=null?r.value:null));
            	    

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "interval"

    public static class leftBound_return extends ParserRuleReturnScope {
        public boolean strict;
        public Double value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "leftBound"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:80:1: leftBound returns [boolean strict, Double value] : ( LEFT_CLOSED numDouble | LEFT_OPEN NEG_INFTY | LEFT_OPEN numDouble );
    public final EcfAntlrParser.leftBound_return leftBound() throws RecognitionException {
        EcfAntlrParser.leftBound_return retval = new EcfAntlrParser.leftBound_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LEFT_CLOSED18=null;
        Token LEFT_OPEN20=null;
        Token NEG_INFTY21=null;
        Token LEFT_OPEN22=null;
        EcfAntlrParser.numDouble_return numDouble19 = null;

        EcfAntlrParser.numDouble_return numDouble23 = null;


        Object LEFT_CLOSED18_tree=null;
        Object LEFT_OPEN20_tree=null;
        Object NEG_INFTY21_tree=null;
        Object LEFT_OPEN22_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:81:2: ( LEFT_CLOSED numDouble | LEFT_OPEN NEG_INFTY | LEFT_OPEN numDouble )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==LEFT_CLOSED) ) {
                alt3=1;
            }
            else if ( (LA3_0==LEFT_OPEN) ) {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==NEG_INFTY) ) {
                    alt3=2;
                }
                else if ( ((LA3_2>=INT && LA3_2<=FLOAT)) ) {
                    alt3=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:81:4: LEFT_CLOSED numDouble
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_CLOSED18=(Token)match(input,LEFT_CLOSED,FOLLOW_LEFT_CLOSED_in_leftBound232); 
                    LEFT_CLOSED18_tree = (Object)adaptor.create(LEFT_CLOSED18);
                    adaptor.addChild(root_0, LEFT_CLOSED18_tree);

                    retval.strict = true;
                    pushFollow(FOLLOW_numDouble_in_leftBound241);
                    numDouble19=numDouble();

                    state._fsp--;

                    adaptor.addChild(root_0, numDouble19.getTree());
                    retval.value = (numDouble19!=null?numDouble19.value:null);

                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:83:4: LEFT_OPEN NEG_INFTY
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_OPEN20=(Token)match(input,LEFT_OPEN,FOLLOW_LEFT_OPEN_in_leftBound248); 
                    LEFT_OPEN20_tree = (Object)adaptor.create(LEFT_OPEN20);
                    adaptor.addChild(root_0, LEFT_OPEN20_tree);

                    retval.strict = false;
                    NEG_INFTY21=(Token)match(input,NEG_INFTY,FOLLOW_NEG_INFTY_in_leftBound257); 
                    NEG_INFTY21_tree = (Object)adaptor.create(NEG_INFTY21);
                    adaptor.addChild(root_0, NEG_INFTY21_tree);

                    retval.value = Double.valueOf(Double.NEGATIVE_INFINITY);

                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:85:4: LEFT_OPEN numDouble
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_OPEN22=(Token)match(input,LEFT_OPEN,FOLLOW_LEFT_OPEN_in_leftBound264); 
                    LEFT_OPEN22_tree = (Object)adaptor.create(LEFT_OPEN22);
                    adaptor.addChild(root_0, LEFT_OPEN22_tree);

                    retval.strict = false;
                    pushFollow(FOLLOW_numDouble_in_leftBound273);
                    numDouble23=numDouble();

                    state._fsp--;

                    adaptor.addChild(root_0, numDouble23.getTree());
                    retval.value = (numDouble23!=null?numDouble23.value:null);

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "leftBound"

    public static class rightBound_return extends ParserRuleReturnScope {
        public boolean strict;
        public Double value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rightBound"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:89:1: rightBound returns [boolean strict, Double value] : ( numDouble RIGHT_CLOSED | INFTY RIGHT_OPEN | numDouble RIGHT_OPEN );
    public final EcfAntlrParser.rightBound_return rightBound() throws RecognitionException {
        EcfAntlrParser.rightBound_return retval = new EcfAntlrParser.rightBound_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token RIGHT_CLOSED25=null;
        Token INFTY26=null;
        Token RIGHT_OPEN27=null;
        Token RIGHT_OPEN29=null;
        EcfAntlrParser.numDouble_return numDouble24 = null;

        EcfAntlrParser.numDouble_return numDouble28 = null;


        Object RIGHT_CLOSED25_tree=null;
        Object INFTY26_tree=null;
        Object RIGHT_OPEN27_tree=null;
        Object RIGHT_OPEN29_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:90:2: ( numDouble RIGHT_CLOSED | INFTY RIGHT_OPEN | numDouble RIGHT_OPEN )
            int alt4=3;
            switch ( input.LA(1) ) {
            case FLOAT:
                {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==RIGHT_CLOSED) ) {
                    alt4=1;
                }
                else if ( (LA4_1==RIGHT_OPEN) ) {
                    alt4=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
                }
                break;
            case INT:
                {
                int LA4_2 = input.LA(2);

                if ( (LA4_2==RIGHT_CLOSED) ) {
                    alt4=1;
                }
                else if ( (LA4_2==RIGHT_OPEN) ) {
                    alt4=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
                }
                break;
            case INFTY:
                {
                alt4=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:90:4: numDouble RIGHT_CLOSED
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_numDouble_in_rightBound290);
                    numDouble24=numDouble();

                    state._fsp--;

                    adaptor.addChild(root_0, numDouble24.getTree());
                    retval.value = (numDouble24!=null?numDouble24.value:null);
                    RIGHT_CLOSED25=(Token)match(input,RIGHT_CLOSED,FOLLOW_RIGHT_CLOSED_in_rightBound299); 
                    RIGHT_CLOSED25_tree = (Object)adaptor.create(RIGHT_CLOSED25);
                    adaptor.addChild(root_0, RIGHT_CLOSED25_tree);

                    retval.strict = true;

                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:92:4: INFTY RIGHT_OPEN
                    {
                    root_0 = (Object)adaptor.nil();

                    INFTY26=(Token)match(input,INFTY,FOLLOW_INFTY_in_rightBound306); 
                    INFTY26_tree = (Object)adaptor.create(INFTY26);
                    adaptor.addChild(root_0, INFTY26_tree);

                    retval.value = Double.valueOf(Double.POSITIVE_INFINITY);
                    RIGHT_OPEN27=(Token)match(input,RIGHT_OPEN,FOLLOW_RIGHT_OPEN_in_rightBound315); 
                    RIGHT_OPEN27_tree = (Object)adaptor.create(RIGHT_OPEN27);
                    adaptor.addChild(root_0, RIGHT_OPEN27_tree);

                    retval.strict = false;

                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:94:4: numDouble RIGHT_OPEN
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_numDouble_in_rightBound322);
                    numDouble28=numDouble();

                    state._fsp--;

                    adaptor.addChild(root_0, numDouble28.getTree());
                    retval.value = (numDouble28!=null?numDouble28.value:null);
                    RIGHT_OPEN29=(Token)match(input,RIGHT_OPEN,FOLLOW_RIGHT_OPEN_in_rightBound331); 
                    RIGHT_OPEN29_tree = (Object)adaptor.create(RIGHT_OPEN29);
                    adaptor.addChild(root_0, RIGHT_OPEN29_tree);

                    retval.strict = false;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rightBound"

    public static class includeall_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "includeall"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:98:1: includeall : INCLUDEALL ( ( COMMA )? LABEL )+ AT numInt POINTS SEMI ;
    public final EcfAntlrParser.includeall_return includeall() throws RecognitionException {
        EcfAntlrParser.includeall_return retval = new EcfAntlrParser.includeall_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INCLUDEALL30=null;
        Token COMMA31=null;
        Token LABEL32=null;
        Token AT33=null;
        Token POINTS35=null;
        Token SEMI36=null;
        EcfAntlrParser.numInt_return numInt34 = null;


        Object INCLUDEALL30_tree=null;
        Object COMMA31_tree=null;
        Object LABEL32_tree=null;
        Object AT33_tree=null;
        Object POINTS35_tree=null;
        Object SEMI36_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:99:2: ( INCLUDEALL ( ( COMMA )? LABEL )+ AT numInt POINTS SEMI )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:99:4: INCLUDEALL ( ( COMMA )? LABEL )+ AT numInt POINTS SEMI
            {
            root_0 = (Object)adaptor.nil();


            	        LinkedList<String> labels = new LinkedList<String>();
            	    
            INCLUDEALL30=(Token)match(input,INCLUDEALL,FOLLOW_INCLUDEALL_in_includeall351); 
            INCLUDEALL30_tree = (Object)adaptor.create(INCLUDEALL30);
            adaptor.addChild(root_0, INCLUDEALL30_tree);

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:102:17: ( ( COMMA )? LABEL )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA||LA6_0==LABEL) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:102:18: ( COMMA )? LABEL
            	    {
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:102:18: ( COMMA )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==COMMA) ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:102:18: COMMA
            	            {
            	            COMMA31=(Token)match(input,COMMA,FOLLOW_COMMA_in_includeall354); 
            	            COMMA31_tree = (Object)adaptor.create(COMMA31);
            	            adaptor.addChild(root_0, COMMA31_tree);


            	            }
            	            break;

            	    }

            	    LABEL32=(Token)match(input,LABEL,FOLLOW_LABEL_in_includeall357); 
            	    LABEL32_tree = (Object)adaptor.create(LABEL32);
            	    adaptor.addChild(root_0, LABEL32_tree);

            	    labels.add((LABEL32!=null?LABEL32.getText():null));

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            AT33=(Token)match(input,AT,FOLLOW_AT_in_includeall368); 
            AT33_tree = (Object)adaptor.create(AT33);
            adaptor.addChild(root_0, AT33_tree);

            pushFollow(FOLLOW_numInt_in_includeall370);
            numInt34=numInt();

            state._fsp--;

            adaptor.addChild(root_0, numInt34.getTree());
            POINTS35=(Token)match(input,POINTS,FOLLOW_POINTS_in_includeall372); 
            POINTS35_tree = (Object)adaptor.create(POINTS35);
            adaptor.addChild(root_0, POINTS35_tree);

            SEMI36=(Token)match(input,SEMI,FOLLOW_SEMI_in_includeall374); 
            SEMI36_tree = (Object)adaptor.create(SEMI36);
            adaptor.addChild(root_0, SEMI36_tree);


            	        for (String l : labels)
            	            config.addRequiredProblemConstraint(l, (numInt34!=null?numInt34.value:0), sourceFactory.newSource("TODO"));
            	    

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "includeall"

    public static class append_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "append"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:114:1: append : APPEND STRING SEMI ;
    public final EcfAntlrParser.append_return append() throws RecognitionException {
        EcfAntlrParser.append_return retval = new EcfAntlrParser.append_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token APPEND37=null;
        Token STRING38=null;
        Token SEMI39=null;

        Object APPEND37_tree=null;
        Object STRING38_tree=null;
        Object SEMI39_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:115:2: ( APPEND STRING SEMI )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:115:4: APPEND STRING SEMI
            {
            root_0 = (Object)adaptor.nil();

            APPEND37=(Token)match(input,APPEND,FOLLOW_APPEND_in_append395); 
            APPEND37_tree = (Object)adaptor.create(APPEND37);
            adaptor.addChild(root_0, APPEND37_tree);

            STRING38=(Token)match(input,STRING,FOLLOW_STRING_in_append397); 
            STRING38_tree = (Object)adaptor.create(STRING38);
            adaptor.addChild(root_0, STRING38_tree);

            SEMI39=(Token)match(input,SEMI,FOLLOW_SEMI_in_append399); 
            SEMI39_tree = (Object)adaptor.create(SEMI39);
            adaptor.addChild(root_0, SEMI39_tree);

            config.setFinalBlock((STRING38!=null?STRING38.getText():null));

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "append"

    public static class versions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "versions"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:118:1: versions : VERSIONS ARE ( ( COMMA )? STRING )+ SEMI ;
    public final EcfAntlrParser.versions_return versions() throws RecognitionException {
        EcfAntlrParser.versions_return retval = new EcfAntlrParser.versions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VERSIONS40=null;
        Token ARE41=null;
        Token COMMA42=null;
        Token STRING43=null;
        Token SEMI44=null;

        Object VERSIONS40_tree=null;
        Object ARE41_tree=null;
        Object COMMA42_tree=null;
        Object STRING43_tree=null;
        Object SEMI44_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:119:2: ( VERSIONS ARE ( ( COMMA )? STRING )+ SEMI )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:119:4: VERSIONS ARE ( ( COMMA )? STRING )+ SEMI
            {
            root_0 = (Object)adaptor.nil();


            	        LinkedList<String> versions = new LinkedList<String>();
            	    
            VERSIONS40=(Token)match(input,VERSIONS,FOLLOW_VERSIONS_in_versions419); 
            VERSIONS40_tree = (Object)adaptor.create(VERSIONS40);
            adaptor.addChild(root_0, VERSIONS40_tree);

            ARE41=(Token)match(input,ARE,FOLLOW_ARE_in_versions421); 
            ARE41_tree = (Object)adaptor.create(ARE41);
            adaptor.addChild(root_0, ARE41_tree);

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:122:19: ( ( COMMA )? STRING )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==STRING||LA8_0==COMMA) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:122:20: ( COMMA )? STRING
            	    {
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:122:20: ( COMMA )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==COMMA) ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:122:20: COMMA
            	            {
            	            COMMA42=(Token)match(input,COMMA,FOLLOW_COMMA_in_versions424); 
            	            COMMA42_tree = (Object)adaptor.create(COMMA42);
            	            adaptor.addChild(root_0, COMMA42_tree);


            	            }
            	            break;

            	    }

            	    STRING43=(Token)match(input,STRING,FOLLOW_STRING_in_versions427); 
            	    STRING43_tree = (Object)adaptor.create(STRING43);
            	    adaptor.addChild(root_0, STRING43_tree);

            	    versions.add((STRING43!=null?STRING43.getText():null));

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            SEMI44=(Token)match(input,SEMI,FOLLOW_SEMI_in_versions433); 
            SEMI44_tree = (Object)adaptor.create(SEMI44);
            adaptor.addChild(root_0, SEMI44_tree);


            	        config.setVersionStrings(versions.toArray(new String[0]));
            	    

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "versions"

    public static class numInt_return extends ParserRuleReturnScope {
        public int value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numInt"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:128:1: numInt returns [int value] : INT ;
    public final EcfAntlrParser.numInt_return numInt() throws RecognitionException {
        EcfAntlrParser.numInt_return retval = new EcfAntlrParser.numInt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INT45=null;

        Object INT45_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:129:5: ( INT )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:129:9: INT
            {
            root_0 = (Object)adaptor.nil();

            INT45=(Token)match(input,INT,FOLLOW_INT_in_numInt460); 
            INT45_tree = (Object)adaptor.create(INT45);
            adaptor.addChild(root_0, INT45_tree);

            retval.value = Integer.valueOf((INT45!=null?INT45.getText():null));

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "numInt"

    public static class numDouble_return extends ParserRuleReturnScope {
        public Double value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numDouble"
    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:132:1: numDouble returns [Double value] : (n= FLOAT | n= INT ) ;
    public final EcfAntlrParser.numDouble_return numDouble() throws RecognitionException {
        EcfAntlrParser.numDouble_return retval = new EcfAntlrParser.numDouble_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token n=null;

        Object n_tree=null;

        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:133:5: ( (n= FLOAT | n= INT ) )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:133:9: (n= FLOAT | n= INT )
            {
            root_0 = (Object)adaptor.nil();

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:133:9: (n= FLOAT | n= INT )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==FLOAT) ) {
                alt9=1;
            }
            else if ( (LA9_0==INT) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:133:10: n= FLOAT
                    {
                    n=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_numDouble488); 
                    n_tree = (Object)adaptor.create(n);
                    adaptor.addChild(root_0, n_tree);


                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:133:18: n= INT
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_numDouble492); 
                    n_tree = (Object)adaptor.create(n);
                    adaptor.addChild(root_0, n_tree);


                    }
                    break;

            }

            retval.value = Double.valueOf((n!=null?n.getText():null));

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "numDouble"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_ecf94 = new BitSet(new long[]{0x0000000340002000L});
    public static final BitSet FOLLOW_EOF_in_ecf97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_include_in_statement118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_includeall_in_statement122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_append_in_statement126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_versions_in_statement130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCLUDE_in_include141 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_numInt_in_include145 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_PROBLEMS_in_include147 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ON_in_include151 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_STRING_in_include155 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_WITH_in_include159 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DIFFICULTY_in_include161 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IN_in_include163 = new BitSet(new long[]{0x0000000003000000L});
    public static final BitSet FOLLOW_interval_in_include165 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_AT_in_include169 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_numInt_in_include173 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_POINTS_in_include175 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SEMI_in_include179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_leftBound_in_interval203 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COMMA_in_interval205 = new BitSet(new long[]{0x0000001810000000L});
    public static final BitSet FOLLOW_rightBound_in_interval209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CLOSED_in_leftBound232 = new BitSet(new long[]{0x0000001800000000L});
    public static final BitSet FOLLOW_numDouble_in_leftBound241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OPEN_in_leftBound248 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_NEG_INFTY_in_leftBound257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OPEN_in_leftBound264 = new BitSet(new long[]{0x0000001800000000L});
    public static final BitSet FOLLOW_numDouble_in_leftBound273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numDouble_in_rightBound290 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RIGHT_CLOSED_in_rightBound299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INFTY_in_rightBound306 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_RIGHT_OPEN_in_rightBound315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numDouble_in_rightBound322 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_RIGHT_OPEN_in_rightBound331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCLUDEALL_in_includeall351 = new BitSet(new long[]{0x0000000080800000L});
    public static final BitSet FOLLOW_COMMA_in_includeall354 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LABEL_in_includeall357 = new BitSet(new long[]{0x0000000080900000L});
    public static final BitSet FOLLOW_AT_in_includeall368 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_numInt_in_includeall370 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_POINTS_in_includeall372 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SEMI_in_includeall374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_APPEND_in_append395 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_STRING_in_append397 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SEMI_in_append399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VERSIONS_in_versions419 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_ARE_in_versions421 = new BitSet(new long[]{0x0000000000810000L});
    public static final BitSet FOLLOW_COMMA_in_versions424 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_STRING_in_versions427 = new BitSet(new long[]{0x0000000000C10000L});
    public static final BitSet FOLLOW_SEMI_in_versions433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_numInt460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_numDouble488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_numDouble492 = new BitSet(new long[]{0x0000000000000002L});

}