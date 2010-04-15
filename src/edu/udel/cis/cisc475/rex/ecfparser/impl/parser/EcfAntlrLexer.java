// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g 2010-04-15 02:15:45

package edu.udel.cis.cisc475.rex.ecfparser.impl.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EcfAntlrLexer extends Lexer {
    public static final int APPEND=32;
    public static final int EXPONENT=37;
    public static final int INCLUDE_STMNT=4;
    public static final int LETTER=41;
    public static final int POINTS=21;
    public static final int INCLUDEALL=30;
    public static final int OCTAL_ESC=49;
    public static final int LEFT_OPEN=25;
    public static final int FLOAT=36;
    public static final int PROBLEMS=14;
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

    public EcfAntlrLexer() {;} 
    public EcfAntlrLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EcfAntlrLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g"; }

    // $ANTLR start "INCLUDE"
    public final void mINCLUDE() throws RecognitionException {
        try {
            int _type = INCLUDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:139:8: ( 'include' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:139:10: 'include'
            {
            match("include"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INCLUDE"

    // $ANTLR start "INCLUDEALL"
    public final void mINCLUDEALL() throws RecognitionException {
        try {
            int _type = INCLUDEALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:140:11: ( 'includeall' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:140:13: 'includeall'
            {
            match("includeall"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INCLUDEALL"

    // $ANTLR start "APPEND"
    public final void mAPPEND() throws RecognitionException {
        try {
            int _type = APPEND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:141:7: ( 'append' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:141:9: 'append'
            {
            match("append"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "APPEND"

    // $ANTLR start "VERSIONS"
    public final void mVERSIONS() throws RecognitionException {
        try {
            int _type = VERSIONS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:142:9: ( 'versions' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:142:11: 'versions'
            {
            match("versions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VERSIONS"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:143:5: ( '\\;' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:143:7: '\\;'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "LEFT_OPEN"
    public final void mLEFT_OPEN() throws RecognitionException {
        try {
            int _type = LEFT_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:144:10: ( '(' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:144:12: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT_OPEN"

    // $ANTLR start "LEFT_CLOSED"
    public final void mLEFT_CLOSED() throws RecognitionException {
        try {
            int _type = LEFT_CLOSED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:145:12: ( '[' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:145:14: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT_CLOSED"

    // $ANTLR start "RIGHT_OPEN"
    public final void mRIGHT_OPEN() throws RecognitionException {
        try {
            int _type = RIGHT_OPEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:146:11: ( ')' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:146:13: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT_OPEN"

    // $ANTLR start "RIGHT_CLOSED"
    public final void mRIGHT_CLOSED() throws RecognitionException {
        try {
            int _type = RIGHT_CLOSED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:147:13: ( ']' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:147:15: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT_CLOSED"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:148:6: ( ',' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:148:8: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "INFTY"
    public final void mINFTY() throws RecognitionException {
        try {
            int _type = INFTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:149:6: ( '\\\\infty' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:149:8: '\\\\infty'
            {
            match("\\infty"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INFTY"

    // $ANTLR start "NEG_INFTY"
    public final void mNEG_INFTY() throws RecognitionException {
        try {
            int _type = NEG_INFTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:150:10: ( '-\\\\infty' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:150:12: '-\\\\infty'
            {
            match("-\\infty"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEG_INFTY"

    // $ANTLR start "PROBLEMS"
    public final void mPROBLEMS() throws RecognitionException {
        try {
            int _type = PROBLEMS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:153:9: ( 'problems' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:153:11: 'problems'
            {
            match("problems"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROBLEMS"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:154:3: ( 'on' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:154:5: 'on'
            {
            match("on"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:155:3: ( 'in' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:155:5: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:156:3: ( 'at' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:156:5: 'at'
            {
            match("at"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "ARE"
    public final void mARE() throws RecognitionException {
        try {
            int _type = ARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:157:4: ( 'are' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:157:6: 'are'
            {
            match("are"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARE"

    // $ANTLR start "WITH"
    public final void mWITH() throws RecognitionException {
        try {
            int _type = WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:158:5: ( 'with' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:158:7: 'with'
            {
            match("with"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WITH"

    // $ANTLR start "DIFFICULTY"
    public final void mDIFFICULTY() throws RecognitionException {
        try {
            int _type = DIFFICULTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:159:11: ( 'difficulty' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:159:13: 'difficulty'
            {
            match("difficulty"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIFFICULTY"

    // $ANTLR start "POINTS"
    public final void mPOINTS() throws RecognitionException {
        try {
            int _type = POINTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:160:7: ( 'points' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:160:9: 'points'
            {
            match("points"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "POINTS"

    // $ANTLR start "LABEL"
    public final void mLABEL() throws RecognitionException {
        try {
            int _type = LABEL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:165:8: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\\:' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\:' )* )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:165:10: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\\:' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\:' )*
            {
            if ( input.LA(1)==':'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:165:39: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\:' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<=':')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<=':')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LABEL"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:5: ( ( '-' )? ( '0' .. '9' )+ )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:7: ( '-' )? ( '0' .. '9' )+
            {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:7: ( '-' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='-') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:7: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:12: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:171:12: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:5: ( ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | ( '-' )? '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '-' )? ( '0' .. '9' )+ EXPONENT )
            int alt13=3;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:9: ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:9: ( '-' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='-') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:9: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:14: ( '0' .. '9' )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:15: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);

                    match('.'); 
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:30: ( '0' .. '9' )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:31: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:42: ( EXPONENT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='E'||LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:175:42: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:9: ( '-' )? '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:9: ( '-' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='-') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:9: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }

                    match('.'); 
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:18: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:19: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:30: ( EXPONENT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:176:30: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:177:9: ( '-' )? ( '0' .. '9' )+ EXPONENT
                    {
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:177:9: ( '-' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='-') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:177:9: '-'
                            {
                            match('-'); 

                            }
                            break;

                    }

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:177:14: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:177:15: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    mEXPONENT(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:5: ( '#' ( NONCONTROL_CHAR )* ( '\\r' )? '\\n' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:9: '#' ( NONCONTROL_CHAR )* ( '\\r' )? '\\n'
            {
            match('#'); 
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:13: ( NONCONTROL_CHAR )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='\t'||(LA14_0>=' ' && LA14_0<='!')||(LA14_0>='#' && LA14_0<='~')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:13: NONCONTROL_CHAR
            	    {
            	    mNONCONTROL_CHAR(); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:30: ( '\\r' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\r') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:181:30: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:187:4: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:187:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:187:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\t' && LA16_0<='\n')||LA16_0=='\r'||LA16_0==' ') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:189:7: ( '\"' ( NONCONTROL_CHAR )* '\"' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:189:9: '\"' ( NONCONTROL_CHAR )* '\"'
            {
            match('\"'); 
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:189:13: ( NONCONTROL_CHAR )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='\t'||(LA17_0>=' ' && LA17_0<='!')||(LA17_0>='#' && LA17_0<='~')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:189:13: NONCONTROL_CHAR
            	    {
            	    mNONCONTROL_CHAR(); 

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            match('\"'); 
            setText(getText().substring(1, getText().length()-1));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "NONCONTROL_CHAR"
    public final void mNONCONTROL_CHAR() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:191:25: ( LETTER | DIGIT | SYMBOL | SPACE )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            {
            if ( input.LA(1)=='\t'||(input.LA(1)>=' ' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='~') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NONCONTROL_CHAR"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:192:16: ( LOWER | UPPER )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "LOWER"
    public final void mLOWER() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:193:15: ( 'a' .. 'z' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:193:17: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "LOWER"

    // $ANTLR start "UPPER"
    public final void mUPPER() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:194:15: ( 'A' .. 'Z' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:194:17: 'A' .. 'Z'
            {
            matchRange('A','Z'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UPPER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:195:15: ( '0' .. '9' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:195:17: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:196:15: ( ' ' | '\\t' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            {
            if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "SPACE"

    // $ANTLR start "SYMBOL"
    public final void mSYMBOL() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:197:16: ( '!' | '#' .. '/' | ':' .. '@' | '[' .. '`' | '{' .. '~' )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
            {
            if ( input.LA(1)=='!'||(input.LA(1)>='#' && input.LA(1)<='/')||(input.LA(1)>=':' && input.LA(1)<='@')||(input.LA(1)>='[' && input.LA(1)<='`')||(input.LA(1)>='{' && input.LA(1)<='~') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "SYMBOL"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:200:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:200:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:200:22: ( '+' | '-' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='+'||LA18_0=='-') ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:200:33: ( '0' .. '9' )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='0' && LA19_0<='9')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:200:34: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:203:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:203:13: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:207:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt20=1;
                    }
                    break;
                case 'u':
                    {
                    alt20=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt20=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:207:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:208:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 

                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:209:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt21=3;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\\') ) {
                int LA21_1 = input.LA(2);

                if ( ((LA21_1>='0' && LA21_1<='3')) ) {
                    int LA21_2 = input.LA(3);

                    if ( ((LA21_2>='0' && LA21_2<='7')) ) {
                        int LA21_4 = input.LA(4);

                        if ( ((LA21_4>='0' && LA21_4<='7')) ) {
                            alt21=1;
                        }
                        else {
                            alt21=2;}
                    }
                    else {
                        alt21=3;}
                }
                else if ( ((LA21_1>='4' && LA21_1<='7')) ) {
                    int LA21_3 = input.LA(3);

                    if ( ((LA21_3>='0' && LA21_3<='7')) ) {
                        alt21=2;
                    }
                    else {
                        alt21=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 21, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:14: ( '0' .. '3' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:25: ( '0' .. '7' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:36: ( '0' .. '7' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:214:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:215:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:215:14: ( '0' .. '7' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:215:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:215:25: ( '0' .. '7' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:215:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:216:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:216:14: ( '0' .. '7' )
                    // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:216:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:221:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:221:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 
            match('u'); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNICODE_ESC"

    public void mTokens() throws RecognitionException {
        // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:8: ( INCLUDE | INCLUDEALL | APPEND | VERSIONS | SEMI | LEFT_OPEN | LEFT_CLOSED | RIGHT_OPEN | RIGHT_CLOSED | COMMA | INFTY | NEG_INFTY | PROBLEMS | ON | IN | AT | ARE | WITH | DIFFICULTY | POINTS | LABEL | INT | FLOAT | COMMENT | WS | STRING )
        int alt22=26;
        alt22 = dfa22.predict(input);
        switch (alt22) {
            case 1 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:10: INCLUDE
                {
                mINCLUDE(); 

                }
                break;
            case 2 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:18: INCLUDEALL
                {
                mINCLUDEALL(); 

                }
                break;
            case 3 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:29: APPEND
                {
                mAPPEND(); 

                }
                break;
            case 4 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:36: VERSIONS
                {
                mVERSIONS(); 

                }
                break;
            case 5 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:45: SEMI
                {
                mSEMI(); 

                }
                break;
            case 6 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:50: LEFT_OPEN
                {
                mLEFT_OPEN(); 

                }
                break;
            case 7 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:60: LEFT_CLOSED
                {
                mLEFT_CLOSED(); 

                }
                break;
            case 8 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:72: RIGHT_OPEN
                {
                mRIGHT_OPEN(); 

                }
                break;
            case 9 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:83: RIGHT_CLOSED
                {
                mRIGHT_CLOSED(); 

                }
                break;
            case 10 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:96: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 11 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:102: INFTY
                {
                mINFTY(); 

                }
                break;
            case 12 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:108: NEG_INFTY
                {
                mNEG_INFTY(); 

                }
                break;
            case 13 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:118: PROBLEMS
                {
                mPROBLEMS(); 

                }
                break;
            case 14 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:127: ON
                {
                mON(); 

                }
                break;
            case 15 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:130: IN
                {
                mIN(); 

                }
                break;
            case 16 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:133: AT
                {
                mAT(); 

                }
                break;
            case 17 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:136: ARE
                {
                mARE(); 

                }
                break;
            case 18 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:140: WITH
                {
                mWITH(); 

                }
                break;
            case 19 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:145: DIFFICULTY
                {
                mDIFFICULTY(); 

                }
                break;
            case 20 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:156: POINTS
                {
                mPOINTS(); 

                }
                break;
            case 21 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:163: LABEL
                {
                mLABEL(); 

                }
                break;
            case 22 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:169: INT
                {
                mINT(); 

                }
                break;
            case 23 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:173: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 24 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:179: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 25 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:187: WS
                {
                mWS(); 

                }
                break;
            case 26 :
                // /Users/burke/Documents/workspace/cisc475/grammars/EcfAntlr.g:1:190: STRING
                {
                mSTRING(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA13_eotS =
        "\6\uffff";
    static final String DFA13_eofS =
        "\6\uffff";
    static final String DFA13_minS =
        "\1\55\2\56\3\uffff";
    static final String DFA13_maxS =
        "\2\71\1\145\3\uffff";
    static final String DFA13_acceptS =
        "\3\uffff\1\2\1\3\1\1";
    static final String DFA13_specialS =
        "\6\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1\1\3\1\uffff\12\2",
            "\1\3\1\uffff\12\2",
            "\1\5\1\uffff\12\2\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "174:1: FLOAT : ( ( '-' )? ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | ( '-' )? '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '-' )? ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA22_eotS =
        "\1\uffff\3\20\10\uffff\4\20\1\uffff\1\41\4\uffff\1\43\1\20\1\45"+
        "\2\20\1\uffff\2\20\1\52\2\20\1\uffff\1\20\1\uffff\1\20\1\uffff\1"+
        "\57\3\20\1\uffff\4\20\1\uffff\3\20\1\72\6\20\1\uffff\2\20\1\103"+
        "\2\20\1\106\1\20\1\111\1\uffff\2\20\1\uffff\2\20\1\uffff\1\116\1"+
        "\117\2\20\2\uffff\1\20\1\123\1\124\2\uffff";
    static final String DFA22_eofS =
        "\125\uffff";
    static final String DFA22_minS =
        "\1\11\1\156\1\160\1\145\7\uffff\1\56\1\157\1\156\2\151\1\uffff\1"+
        "\56\4\uffff\1\60\1\160\1\60\1\145\1\162\1\uffff\1\157\1\151\1\60"+
        "\1\164\1\146\1\uffff\1\154\1\uffff\1\145\1\uffff\1\60\1\163\1\142"+
        "\1\156\1\uffff\1\150\1\146\1\165\1\156\1\uffff\1\151\1\154\1\164"+
        "\1\60\1\151\2\144\1\157\1\145\1\163\1\uffff\1\143\1\145\1\60\1\156"+
        "\1\155\1\60\1\165\1\60\1\uffff\2\163\1\uffff\2\154\1\uffff\2\60"+
        "\1\164\1\154\2\uffff\1\171\2\60\2\uffff";
    static final String DFA22_maxS =
        "\1\172\1\156\1\164\1\145\7\uffff\1\134\1\162\1\156\2\151\1\uffff"+
        "\1\145\4\uffff\1\172\1\160\1\172\1\145\1\162\1\uffff\1\157\1\151"+
        "\1\172\1\164\1\146\1\uffff\1\154\1\uffff\1\145\1\uffff\1\172\1\163"+
        "\1\142\1\156\1\uffff\1\150\1\146\1\165\1\156\1\uffff\1\151\1\154"+
        "\1\164\1\172\1\151\2\144\1\157\1\145\1\163\1\uffff\1\143\1\145\1"+
        "\172\1\156\1\155\1\172\1\165\1\172\1\uffff\2\163\1\uffff\2\154\1"+
        "\uffff\2\172\1\164\1\154\2\uffff\1\171\2\172\2\uffff";
    static final String DFA22_acceptS =
        "\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\5\uffff\1\25\1\uffff\1"+
        "\27\1\30\1\31\1\32\5\uffff\1\14\5\uffff\1\26\1\uffff\1\17\1\uffff"+
        "\1\20\4\uffff\1\16\4\uffff\1\21\12\uffff\1\22\10\uffff\1\3\2\uffff"+
        "\1\24\2\uffff\1\1\4\uffff\1\4\1\15\3\uffff\1\2\1\23";
    static final String DFA22_specialS =
        "\125\uffff}>";
    static final String[] DFA22_transitionS = {
            "\2\24\2\uffff\1\24\22\uffff\1\24\1\uffff\1\25\1\23\4\uffff\1"+
            "\5\1\7\2\uffff\1\11\1\13\1\22\1\uffff\12\21\1\20\1\4\5\uffff"+
            "\32\20\1\6\1\12\1\10\1\uffff\1\20\1\uffff\1\2\2\20\1\17\4\20"+
            "\1\1\5\20\1\15\1\14\5\20\1\3\1\16\3\20",
            "\1\26",
            "\1\27\1\uffff\1\31\1\uffff\1\30",
            "\1\32",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\1\uffff\12\21\42\uffff\1\33",
            "\1\35\2\uffff\1\34",
            "\1\36",
            "\1\37",
            "\1\40",
            "",
            "\1\22\1\uffff\12\21\13\uffff\1\22\37\uffff\1\22",
            "",
            "",
            "",
            "",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\2\20\1\42\27\20",
            "\1\44",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\46",
            "\1\47",
            "",
            "\1\50",
            "\1\51",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\53",
            "\1\54",
            "",
            "\1\55",
            "",
            "\1\56",
            "",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\60",
            "\1\61",
            "\1\62",
            "",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "",
            "\1\67",
            "\1\70",
            "\1\71",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "",
            "\1\101",
            "\1\102",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\104",
            "\1\105",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\107",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\1\110\31\20",
            "",
            "\1\112",
            "\1\113",
            "",
            "\1\114",
            "\1\115",
            "",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\120",
            "\1\121",
            "",
            "",
            "\1\122",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\13\20\6\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( INCLUDE | INCLUDEALL | APPEND | VERSIONS | SEMI | LEFT_OPEN | LEFT_CLOSED | RIGHT_OPEN | RIGHT_CLOSED | COMMA | INFTY | NEG_INFTY | PROBLEMS | ON | IN | AT | ARE | WITH | DIFFICULTY | POINTS | LABEL | INT | FLOAT | COMMENT | WS | STRING );";
        }
    }
 

}