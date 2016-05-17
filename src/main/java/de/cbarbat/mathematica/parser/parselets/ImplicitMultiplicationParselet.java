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
import de.cbarbat.mathematica.parser.MathematicaParser;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

/**
 * This parselet is special, because it is <em>not</em> bound to a special operator. Basically, this is called when
 * there is no infix operator between two prefix operators and it would lead to an error otherwise. This seems fragile
 * to me but at the moment it does a reasonable job.
 *
 * @author patrick (4/13/13)
 */
public class ImplicitMultiplicationParselet implements InfixParselet {

    private static final int PRECEDENCE = 42;

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        if (!left.isValid()) return MathematicaParser.notParsed();
        MathematicaParser.AST tree = null;

        MathematicaParser.AST right = parser.parseExpression(PRECEDENCE);
        if (right.isParsed()) {
            MathematicaLexer.Token token = new MathematicaLexer.Token(MathematicaElementTypes.TIMES, " ", right.token.end + 1,  right.token.end + 2);
            tree = new MathematicaParser.AST(token, MathematicaElementTypes.TIMES_EXPRESSION, true);
            tree.children.add(left);
            tree.children.add(right);
        } else {
            parser.error("More input expected");
        }
        return tree;
    }

    @Override
    public int getMyPrecedence() {
        return PRECEDENCE;
    }

}
