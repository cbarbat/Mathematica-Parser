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
import de.cbarbat.mathematica.parser.*;

/**
 * Parses _expr. Note expr must not be present
 *
 * @author patrick (3/27/13)
 */
public class PrefixBlankParselet implements PrefixParselet {
    private final int myPrecedence;

    public PrefixBlankParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser) throws CriticalParserError {
        parser.optional = false;
        parser.pattern = false;
        MathematicaLexer.Token token = parser.getToken(); //_
        MathematicaElementType tokenType = MathematicaElementTypes.BLANK_EXPRESSION;
        MathematicaParser.ASTNode result;
        MathematicaParser.ASTNode tree = null;
        if (!parser.isNextWhitespace()) {
            parser.advanceLexer();
            if (parser.matchesToken(MathematicaElementTypes.IDENTIFIER)) {
                parser.pattern = true;
                result = parser.parseExpression(myPrecedence);
                if (result != null) {
                    tree = MathematicaParser.result(token, tokenType, !result.isValid() || result.isParsed());
                    tree.children.add(result);
                }
            } else if (parser.matchesToken(MathematicaElementTypes.POINT)) {
                MathematicaParser.ASTNode subtree = MathematicaParser.result(token, tokenType, true);
                parser.optional = true;
                token = parser.getToken();
                tree = MathematicaParser.result(token, MathematicaElementTypes.OPTIONAL_EXPRESSION, true);
                tree.children.add(subtree);
                parser.advanceLexer();
            } else {
                tree = MathematicaParser.result(token, tokenType, true);
            }
        } else {
            parser.advanceLexer();
            tree = MathematicaParser.result(token, tokenType, true);
        }
        return tree;
    }
}