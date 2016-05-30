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
 * Parses <code>Span</code> constructs like <code>list[[;;]]</code> or <code>list[[;;i]]</code> where <code>;;</code> is
 * a prefix operator.
 *
 * @author patrick (3/27/13)
 */
public class PrefixSpanParselet implements PrefixParselet {
    private final int myPrecedence;

    public PrefixSpanParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser) throws CriticalParserError {
        boolean skipped = false;
        ArrayList<MathematicaParser.ASTNode> children = new ArrayList<>();
        MathematicaParser.ASTNode one = new MathematicaParser.ASTNode(new MathematicaLexer.Token(MathematicaElementTypes.NUMBER, "1", 0, 1), MathematicaElementTypes.NUMBER_EXPRESSION, true);
        MathematicaParser.ASTNode all = new MathematicaParser.ASTNode(new MathematicaLexer.Token(MathematicaElementTypes.ALL, "ALL", 0, 3), MathematicaElementTypes.ALL, true);

        MathematicaLexer.Token token;

        if (parser.matchesToken(SPAN)) {
            token = parser.getToken();
            children.add(one);
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

        PrefixParselet nextPrefix = getPrefixParselet(parser.getTokenType());
        if (nextPrefix == null) {
            if (skipped) {
                parser.error("Expression expected after ';; ;;'");
            } else {
                children.add(all);
            }
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, !skipped);
            tree.children = children;
            return tree;
        }

        MathematicaParser.ASTNode expr1 = parser.parseExpression(myPrecedence);

        // if we had ;;;;expr1
        if (skipped) {
            children.add(all);
            children.add(expr1);
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, expr1.isParsed());
            tree.children = children;
            return tree;
        }

        if (parser.matchesToken(SPAN)) {
            token = parser.getToken();
            parser.advanceLexer();
            MathematicaParser.ASTNode expr2 = parser.parseExpression(myPrecedence);
            if (expr2.isParsed()) {
                children.add(expr1);
                children.add(expr2);
            } else
                parser.error("Expression expected after ';;expr1;;'");
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, expr1.isParsed() && expr2.isParsed());
            tree.children = children;
            return tree;
        } else {
            // we have the form ;;expr1
            children.add(expr1);
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SPAN_EXPRESSION, expr1.isParsed());
            tree.children = children;
            return tree;
        }
    }

    public int getPrecedence() {
        return myPrecedence;
    }

}
