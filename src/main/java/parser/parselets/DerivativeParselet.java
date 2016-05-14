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
import main.java.parser.CriticalParserError;
import main.java.parser.MathematicaElementTypes;
import main.java.parser.MathematicaParser;

import static main.java.parser.MathematicaElementTypes.DERIVATIVE;
import static main.java.parser.MathematicaElementTypes.DERIVATIVE_EXPRESSION;

/**
 * Parselet for derivative expression like f''[x] or g' I expect the left operand to be a symbol. Don't know whether
 * this is a requirement, but I cannot think of another form.
 *
 * @author patrick (3/27/13)
 */
public class DerivativeParselet implements InfixParselet {

    private final int myPrecedence;

    public DerivativeParselet(int precedence) {
        myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        Integer repetitions = 0;

        MathematicaLexer.Token token = parser.getToken();
        while (!parser.eof() && parser.getTokenType().equals(DERIVATIVE)) {
            parser.advanceLexer();
            repetitions++;
        }

        MathematicaLexer.Token rtoken = new MathematicaLexer.Token(MathematicaElementTypes.NUMBER_EXPRESSION, repetitions.toString(), 0, repetitions.toString().length());
        MathematicaParser.AST right = new MathematicaParser.AST(rtoken, rtoken.type, true);
        MathematicaParser.AST tree = MathematicaParser.result(token, DERIVATIVE_EXPRESSION, true);
        tree.children.add(right);
        tree.children.add(left);
        return tree;
    }

    @Override
    public int getMyPrecedence() {
        return myPrecedence;
    }
}
