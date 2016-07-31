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
import de.cbarbat.mathematica.parser.MathematicaElementType;
import de.cbarbat.mathematica.parser.CriticalParserError;
import de.cbarbat.mathematica.parser.MathematicaParser;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

/**
 * Parselet for all <em>tag setting operations</em> like a /: b = c or a /: b =.
 *
 * @author patrick (3/27/13)
 */
public class TagSetParselet implements InfixParselet {

    private final int myPrecedence;

    public TagSetParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser, MathematicaParser.ASTNode left) throws CriticalParserError {
        if (left.type != MathematicaElementTypes.SYMBOL_EXPRESSION) {
            throw new CriticalParserError("Expected symbol before token TAG_SET");
        }
        MathematicaLexer.Token token1 = parser.getToken();
        if (parser.matchesToken(MathematicaElementTypes.TAG_SET)) {
            parser.advanceLexer();
        } else {
            throw new CriticalParserError("Expected token TAG_SET");
        }
        // In the next line we parse expr1 of expr0/:expr1 and we reduce the precedence by one because it is
        // right associative. Using SetDelayed (:=) which has the same precedence the following expression:
        // a /: b := c := d is then correctly parsed as a /: b := (c := d)
        MathematicaParser.ASTNode expr1 = parser.parseExpression(myPrecedence - 1);

        if (!expr1.isValid()) {
            parser.error("Missing 'expr' between '/\\:' and '\\:\\='");
            return MathematicaParser.result(token1, MathematicaElementTypes.TAG_SET_EXPRESSION, false);
        }

        MathematicaElementType treeType;
        MathematicaLexer.Token token;
        if (expr1.type == MathematicaElementTypes.UNSET_EXPRESSION) {
            treeType = MathematicaElementTypes.TAG_UNSET_EXPRESSION;
            token = new MathematicaLexer.Token(treeType, "/:", token1.start, token1.start+2);
        } else
        if (expr1.type == MathematicaElementTypes.SET_EXPRESSION) {
            treeType = MathematicaElementTypes.TAG_SET_EXPRESSION;
            token = new MathematicaLexer.Token(treeType, "/:", token1.start, token1.start+2);
        } else
        if (expr1.type == MathematicaElementTypes.SET_DELAYED_EXPRESSION) {
            treeType = MathematicaElementTypes.TAG_SET_DELAYED_EXPRESSION;
            token = new MathematicaLexer.Token(treeType, "/:", token1.start, token1.start+2);
        } else {
            // if we are here, the second operator (:=, = or =.) is missing and we give up
            parser.error("Missing '\\:\\=','\\=' or '\\=.' needed to complete TagSet");
            return MathematicaParser.result(token1, MathematicaElementTypes.TAG_SET_EXPRESSION, false);
        }
        MathematicaParser.ASTNode tree = MathematicaParser.result(token, treeType, expr1.isParsed());
        tree.children = expr1.children;
        tree.children.add(0, left);
        return tree;
    }

    @Override
    public int getMyPrecedence() {
        return myPrecedence;
    }
}
