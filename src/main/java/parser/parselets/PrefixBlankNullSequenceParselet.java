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
import main.java.parser.MathematicaElementTypes;
import main.java.parser.MathematicaParser;

/**
 * Parses ___expr. Note expr must be present.
 *
 * @author patrick (3/27/13)
 */
public class PrefixBlankNullSequenceParselet implements PrefixParselet {
    private final int myPrecedence;

    public PrefixBlankNullSequenceParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser) throws CriticalParserError {
        parser.optional = false;
        MathematicaLexer.Token token = parser.getToken(); //___
        MathematicaElementType tokenType = MathematicaElementTypes.BLANK_NULL_SEQUENCE_EXPRESSION;
        MathematicaParser.AST result;
        MathematicaParser.AST tree = null;
        if (!parser.isNextWhitespace()) {
            parser.advanceLexer();
            if (!parser.matchesToken(MathematicaElementTypes.POINT)) {
                result = parser.parseExpression(myPrecedence);
                if (result != null) {
                    tree = MathematicaParser.result(token, tokenType, !result.isValid() || result.isParsed());
                    tree.children.add(result);
                }
            } else {
                MathematicaParser.AST subtree = MathematicaParser.result(token, tokenType, true);
                parser.optional = true;
                token = parser.getToken();
                tree = MathematicaParser.result(token, MathematicaElementTypes.OPTIONAL_EXPRESSION, true);
                tree.children.add(subtree);
                parser.advanceLexer();
            }
        } else {
            parser.advanceLexer();
            tree = MathematicaParser.result(token, tokenType, true);
        }
        return tree;
    }

}
