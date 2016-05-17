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
import de.cbarbat.mathematica.parser.ParseletProvider;

/**
 * Parselet for all <em>non-special</em> binary infix operators like *, +, ==. This contrasts special infix operators
 * like a /: b := c which consist of more than two operands or need certain requirements on their operands.
 *
 * @author patrick (3/27/13)
 */
public class InfixOperatorParselet implements InfixParselet {

    private final int myPrecedence;
    private final boolean myRightAssociative;

    public InfixOperatorParselet(int precedence, boolean rightAssociative) {
        this.myPrecedence = precedence;
        this.myRightAssociative = rightAssociative;
    }

    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        if (!left.isValid()) return MathematicaParser.notParsed();

        MathematicaParser.AST tree = null;
        MathematicaLexer.Token token = parser.getToken();
        MathematicaElementType tokenType = ParseletProvider.getInfixElement(this);

        parser.advanceLexer();

        MathematicaParser.AST right = parser.parseExpression(myPrecedence - (myRightAssociative ? 1 : 0));
        if (right != null && right.isParsed()) {
            tree = MathematicaParser.result(token, tokenType, true);
            tree.children.add(left);
            tree.children.add(right);
        } else {
            parser.error("More input expected");
        }
        if (tree != null) {
            return tree;
        }
        return left;
    }

    public int getMyPrecedence() {
        return myPrecedence;
    }

}
