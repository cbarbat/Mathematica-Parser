/*
 * Copyright (c) 2013 Patrick Scheibe & 2016 Calin Barbat
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.cbarbat.mathematica.parser;

import de.cbarbat.mathematica.parser.parselets.ImplicitMultiplicationParselet;
import de.cbarbat.mathematica.parser.parselets.InfixParselet;
import de.cbarbat.mathematica.parser.parselets.PrefixParselet;
import de.cbarbat.mathematica.lexer.MathematicaLexer;

import java.util.ArrayList;

import static de.cbarbat.mathematica.parser.MathematicaElementTypes.*;
import static de.cbarbat.mathematica.parser.ParseletProvider.getInfixParselet;
import static de.cbarbat.mathematica.parser.ParseletProvider.getPrefixParselet;

/**
 * @author patrick (3/27/13)
 */
public class MathematicaParser {

    private static final int MAX_RECURSION_DEPTH = 1024;
    private static final ImplicitMultiplicationParselet IMPLICIT_MULTIPLICATION_PARSELET = new ImplicitMultiplicationParselet();
    private final ImportantLineBreakHandler myImportantLinebreakHandler;
    public MathematicaLexer myLexer = null;
    private int myRecursionDepth;
    public int myParDepth;
    public int myBraceDepth;
    public int myBracketDepth;
    public boolean optional; // flag if a dot (Optional) was seen

    public MathematicaParser(MathematicaLexer ml) {
        myLexer = ml;
        myRecursionDepth = 0;
        myParDepth = 0;
        myBraceDepth = 0;
        myBracketDepth = 0;
        myImportantLinebreakHandler = new ImportantLineBreakHandler();
    }

    /**
     * Function to create information about a parsed expression. In a Pratt parser often you need the last parsed
     * expression to combine it into a new parse-node. So <code >expr1 + expr2</code> is combined into a new node by
     * recognizing the operator <code >+</code>, parsing <code >expr2</code> and combining everything into a new parse
     * node.
     * <p/>
     * Since IDEA uses markers to mark the sequential code into a tree-structure I use this {@link AST} which contains
     * additionally the {@link MathematicaElementType} of the last expression and whether the previous expression was parsed.
     *
     * @param token   The token type of the expression which was parsed, e.g. FUNCTION_CALL_EXPRESSION
     * @param parsedQ Whether the parsing of the expression was successful
     * @return The Result object with the given parsing information.
     */
    public static AST result(MathematicaLexer.Token token, MathematicaElementType type, boolean parsedQ) {
        token.type = type;
        return new AST(token, type, parsedQ);
    }

    /**
     * This is the return value of a parser when errors happened.
     */
    public static AST notParsed() {
        return new AST(null, null, false);
    }

    /**
     * This is the main entry point for the parsing a file. Every tme
     */
    public void parse() {
        myLexer.setImportantLineBreakHandler(myImportantLinebreakHandler);
        try {
            while (!myLexer.eof()) {
                AST expr = parseExpression();
                if (!expr.isParsed()) {
                    error("The last expression could not be parsed correctly.");
                    myLexer.advanceLexer();
                } else {
                    System.out.println("Result: " + expr.toString());
                }
            }
        } catch (CriticalParserError criticalParserError) {
            while (!myLexer.eof()) {
                myLexer.advanceLexer();
            }
            error(criticalParserError.getMessage());
        }
    }

    public AST parseExpression() throws CriticalParserError {
        return parseExpression(0);
    }

    public AST parseExpression(int precedence) throws CriticalParserError {
        if (myLexer.eof()) return notParsed();

        if (myRecursionDepth > MAX_RECURSION_DEPTH) {
            throw new CriticalParserError("Maximal recursion depth exceeded during parsing.");
        }

        MathematicaElementType tokenType = getTokenType();
        if (tokenType == null) {
            return notParsed();
        }

        PrefixParselet prefix = getPrefixParselet(tokenType);
        if (prefix == null) {
            return notParsed();
        }

        increaseRecursionDepth();
        AST left = prefix.parse(this);

        while (left.isParsed()) {
            tokenType = getTokenType();
            InfixParselet infix = getInfixOrMultiplyParselet(tokenType);
            if (infix == null) {
                break;
            }
            if (precedence >= infix.getMyPrecedence()) {
                break;
            }
            left = infix.parse(this, left);
        }
        decreaseRecursionDepth();
        return left;
    }

    private InfixParselet getInfixOrMultiplyParselet(MathematicaElementType token) {
        InfixParselet infixParselet = getInfixParselet(token);
        PrefixParselet prefixParselet = getPrefixParselet(token);

        if (myImportantLinebreakHandler.hadLineBreak() && myParDepth == 0 && myBraceDepth == 0 && myBracketDepth == 0) {
            return null;
        }

        if (infixParselet != null) return infixParselet;
        if (prefixParselet == null) return null;

        return IMPLICIT_MULTIPLICATION_PARSELET;
    }

    public int decreaseRecursionDepth() {
        return --myRecursionDepth;
    }

    public int increaseRecursionDepth() {
        return ++myRecursionDepth;
    }

    public MathematicaElementType getTokenType() {
        if (!myLexer.eof())
            return myLexer.getToken().type;
        else
            return null;
    }

    public MathematicaLexer.Token getToken() {
        if (!myLexer.eof())
            return myLexer.getToken();
        else
            return null;
    }

    public MathematicaLexer.Token getPrevToken() {
        return myLexer.getPrevToken();
    }

    public void advanceLexer() throws CriticalParserError {
        if (myLexer.eof()) {
            throw new CriticalParserError("Unexpected end of input.");
        }
        myImportantLinebreakHandler.reset();
        myLexer.advanceLexer();
    }

    public boolean matchesToken(MathematicaElementType token) {
        final MathematicaElementType testToken = myLexer.lookAhead(0);
        return (testToken != null && testToken.equals(token));
    }

    public boolean matchesToken(MathematicaElementType token1, MathematicaElementType token2) {
        final MathematicaElementType firstToken = myLexer.lookAhead(0);
        final MathematicaElementType secondToken = myLexer.lookAhead(1);
        return (firstToken != null && firstToken.equals(token1)) && (secondToken != null && secondToken.equals(token2));
    }

    /**
     * @param s Error message
     */
    public void error(String s) {
        System.out.println("Lexer error: " + s);
    }

    @SuppressWarnings({"BooleanMethodNameMustStartWithQuestion", "InstanceMethodNamingConvention"})
    public boolean eof() {
        return myLexer.eof();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isNextWhitespace() {
        final MathematicaElementType possibleWhitespace = myLexer.rawLookup(1);
        return WHITE_SPACES.contains(possibleWhitespace);
    }

    /**
     * Registers when a whitespace token was seen. This is important in order to find out whether an <em>implicit
     * multiplication</em> has arisen.
     */
    public class ImportantLineBreakHandler {
        private boolean myLineBreakSeen = false;

        public void onSkip(MathematicaElementType type) {
            if (type.equals(LINE_BREAK))
                myLineBreakSeen = true;
        }

        void reset() {
            myLineBreakSeen = false;
        }

        boolean hadLineBreak() {
            return myLineBreakSeen;
        }

    }

    public static class AST {
        public MathematicaLexer.Token token;
        public MathematicaElementType type;
        public ArrayList<AST> children;
        public boolean parsed;

        public AST(MathematicaLexer.Token token, MathematicaElementType type, boolean parsed) {
            this.token = token;
            this.type = type;
            this.children = new ArrayList<AST>();
            this.parsed = parsed;
        }

        public MathematicaElementType getToken() {
            return token.type;
        }

        public boolean isParsed() {
            return parsed;
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean isValid() {
            return (token != null);
        }

        public String toString() {
            if (this.token != null && this.type != null && this.children != null) {
                StringBuilder sb = new StringBuilder();
                if ((type == NUMBER_EXPRESSION ||
                        type == SYMBOL_EXPRESSION ||
                        type == STRINGIFIED_SYMBOL_EXPRESSION ||
                        type == STRING_LITERAL_EXPRESSION ||
                        type == SLOT ||
                        type == SLOT_SEQUENCE ||
                        type == ASSOCIATION_SLOT)
                        && (children.isEmpty())) {
                    sb.append(this.token.text);
                } else {
                    sb.append(this.token.type.myType);
                    sb.append("[");
                    if (children != null && children.size() > 0) {
                        for (int i = 0; i < children.size() - 1; i++) {
                            sb.append(children.get(i) + ", ");
                        }
                        if (children.get(children.size() - 1) != null)
                            sb.append(children.get(children.size() - 1));
                        else
                            sb.append("Null");
                    }
                    sb.append("]");
                }
                return sb.toString();
            }
            return "Null";
        }
    }
}
