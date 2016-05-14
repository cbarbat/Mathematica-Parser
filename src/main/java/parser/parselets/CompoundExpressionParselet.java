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

package main.java.parser.parselets;

import main.java.lexer.MathematicaLexer;
import main.java.parser.MathematicaElementType;
import main.java.parser.CriticalParserError;
import main.java.parser.MathematicaParser;
import main.java.parser.MathematicaElementTypes;

import java.util.ArrayList;

/**
 * @author patrick (3/27/13)
 */
public class CompoundExpressionParselet implements InfixParselet {
    private final int myPrecedence;

    public CompoundExpressionParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    public int getMyPrecedence() {
        return myPrecedence;
    }

    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        if (!left.isValid()) return MathematicaParser.notParsed();

        ArrayList<MathematicaParser.AST> sequence = new ArrayList<>();
        sequence.add(left);
        MathematicaLexer.Token token = parser.getToken();
        MathematicaElementType tokenType = MathematicaElementTypes.COMPOUND_EXPRESSION_EXPRESSION;
        parser.advanceLexer();

        boolean parsed = true;

        while (true) {
            MathematicaParser.AST tree = parser.parseExpression(myPrecedence);
            if (!tree.isValid()) break;
            parsed &= tree.isParsed();
            sequence.add(tree);
            if (parser.matchesToken(MathematicaElementTypes.SEMICOLON)) parser.advanceLexer();
            else break;
        }

        MathematicaParser.AST result = MathematicaParser.result(token, tokenType, parsed);
        result.children = sequence;
        return result;
    }
}
