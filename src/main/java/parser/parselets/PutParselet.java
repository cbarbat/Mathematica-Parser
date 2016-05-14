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

/**
 * @author patrick (3/27/13)
 */
public class PutParselet implements InfixParselet {
    private final int myPrecedence;

    public PutParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        if (!left.isValid()) return MathematicaParser.notParsed();

        final MathematicaLexer.Token token = parser.getToken();
        final MathematicaElementType type = token.type.equals(MathematicaElementTypes.PUT) ? MathematicaElementTypes.PUT_EXPRESSION : MathematicaElementTypes.PUT_APPEND_EXPRESSION;
        parser.advanceLexer();
        if (parser.matchesToken(MathematicaElementTypes.STRINGIFIED_IDENTIFIER) || parser.matchesToken(MathematicaElementTypes.STRING_LITERAL_BEGIN)) {
            final MathematicaParser.AST right = parser.parseExpression(myPrecedence);
            MathematicaParser.AST tree = MathematicaParser.result(token, type, right.isParsed());
            tree.children.add(left);
            tree.children.add(right);
            return tree;
        } else {
            parser.error("Could not parse right hand side of Put or PutAppend expression");
            return MathematicaParser.result(token, type, false);
        }
    }

    public int getMyPrecedence() {
        return myPrecedence;
    }
}