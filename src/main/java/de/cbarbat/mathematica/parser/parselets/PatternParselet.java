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
 * Parselet for Pattern.
 *
 * @author patrick (7/1/13)
 */
public class PatternParselet implements InfixParselet {
    private final int myPrecedence;

    public PatternParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        if (!left.isValid()) return MathematicaParser.notParsed();

        MathematicaLexer.Token token = parser.getToken();
        parser.advanceLexer();
        MathematicaParser.AST right = parser.parseExpression(myPrecedence);

        MathematicaElementType expressionType = left.getToken().equals(MathematicaElementTypes.SYMBOL_EXPRESSION) ?
                MathematicaElementTypes.PATTERN_EXPRESSION : MathematicaElementTypes.OPTIONAL_EXPRESSION;

        if (!right.isParsed()) {
            parser.error("Could not parse pattern or optional argument expression");
        }

        MathematicaParser.AST tree = MathematicaParser.result(token, expressionType, right.isParsed());
        tree.children.add(left);
        tree.children.add(right);
        return tree;
    }

    public int getMyPrecedence() {
        return myPrecedence;
    }

}
