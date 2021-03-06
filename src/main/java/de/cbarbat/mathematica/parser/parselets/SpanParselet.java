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

package de.cbarbat.mathematica.parser.parselets;

import de.cbarbat.mathematica.lexer.MathematicaLexer;
import de.cbarbat.mathematica.parser.CriticalParserError;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;
import de.cbarbat.mathematica.parser.MathematicaParser;

import java.util.ArrayList;

import static de.cbarbat.mathematica.parser.MathematicaElementTypes.SPAN;
import static de.cbarbat.mathematica.parser.MathematicaElementTypes.SPAN_EXPRESSION;
import static de.cbarbat.mathematica.parser.ParseletProvider.getPrefixParselet;

/**
 * Parses <code>Span</code> constructs. This parselet handles not situations like <code>list[[;;]]</code> or
 * <code>list[[;;i]]</code> because this is a prefix operator and not infix.
 *
 * @author patrick (3/27/13)
 */
public class SpanParselet implements InfixParselet {
    private final int myPrecedence;

    public SpanParselet(int precedence) {
        this.myPrecedence = precedence;
    }


    // Parses things like expr1;;expr2, expr0;; ;;expr1 or expr0;;expr1;;expr2.
    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser, MathematicaParser.ASTNode left) throws CriticalParserError {

        boolean skipped = false;
        ArrayList<MathematicaParser.ASTNode> children = new ArrayList<>();
        MathematicaLexer.Token token;

        children.add(left); // add expr0

        if (parser.matchesToken(SPAN)) {
            token = parser.getToken();
            parser.advanceLexer();
        } else {
            throw new CriticalParserError("SPAN token ';;' expected");
        }


        // if we meet a second ;; right after the first ;; we just skip it
        if (parser.matchesToken(SPAN)) {
            skipped = true;
            token = parser.getToken();
            parser.advanceLexer();
        }

        // since here we met either expr0;; or expr0;;;;. By testing whether the next token is start of a new expression
        // (which requires to start with a prefix operator) we can tell whether we met the form list[[expr0;;]] or
        // which is a syntax error list[expr0;;;;]].
        PrefixParselet nextPrefix = getPrefixParselet(parser.getTokenType());
        if (nextPrefix == null) {
            if (skipped) {
                parser.error("Expression expected after 'expr0;; ;;'");
            } else {
                MathematicaParser.ASTNode all = new MathematicaParser.ASTNode(new MathematicaLexer.Token(MathematicaElementTypes.ALL, "ALL", 0, 3), MathematicaElementTypes.ALL, true);
                children.add(all);
            }
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, left.isParsed() && !skipped);
            tree.children = children;
            return tree;
        }

        MathematicaParser.ASTNode expr1 = parser.parseExpression(myPrecedence);

        // if we had expr0;;;;expr1
        if (skipped) {
            MathematicaParser.ASTNode all = new MathematicaParser.ASTNode(new MathematicaLexer.Token(MathematicaElementTypes.ALL, "ALL", 0, 3), MathematicaElementTypes.ALL, true);
            children.add(all);
            children.add(expr1); // add expr1
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, left.isParsed() && expr1.isParsed());
            tree.children = children;
            return tree;
        }

        if (parser.matchesToken(SPAN)) {
            parser.advanceLexer();
            MathematicaParser.ASTNode expr2 = parser.parseExpression(myPrecedence);
            if (expr2.isParsed()) {
                children.add(expr1); // add expr1
                children.add(expr2); // add expr2
            } else
                parser.error("Expression expected after 'expr0;;expr1;;'");
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, left.isParsed() && expr1.isParsed() && expr2.isParsed());
            tree.children = children;
            return tree;
        } else {
            // we have the form expr0;;expr1
            children.add(expr1); // add expr1
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, left.isParsed() && expr1.isParsed());
            tree.children = children;
            return tree;
        }
    }

    @Override
    public int getMyPrecedence() {
        return myPrecedence;
    }
}
