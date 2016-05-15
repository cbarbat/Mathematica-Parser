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

import static main.java.parser.MathematicaElementTypes.*;

/**
 * Parselet for symbols (identifier). Here, I currently parse not only simple identifiers like Sqrt, variable or
 * $MaxRecursion. Additionally, I parse slot expressions like #1, ## or #abc into symbols and I parse stringified
 * expressions like the rhs of << into one STRINGIFIED_SYMBOL_EXPRESSION node.
 *
 * @author patrick (3/27/13)
 */
public class SymbolParselet implements PrefixParselet {

    public SymbolParselet() {
    }

    public MathematicaParser.AST parse(MathematicaParser parser) throws CriticalParserError {
        MathematicaElementType finalExpressionType;
        MathematicaLexer.Token token = parser.getToken();
        if (token.type.equals(IDENTIFIER)) {
            finalExpressionType = SYMBOL_EXPRESSION;
            if (!parser.pattern) {
                if ((!parser.isNextWhitespace()) && (
                        parser.matchesToken(MathematicaElementTypes.IDENTIFIER, MathematicaElementTypes.BLANK_NULL_SEQUENCE) ||
                                parser.matchesToken(MathematicaElementTypes.IDENTIFIER, MathematicaElementTypes.BLANK_SEQUENCE) ||
                                parser.matchesToken(MathematicaElementTypes.IDENTIFIER, MathematicaElementTypes.BLANK) ||
                                parser.matchesToken(MathematicaElementTypes.IDENTIFIER, MathematicaElementTypes.DEFAULT)
                )) {
                    parser.advanceLexer();
                    MathematicaParser.AST left = MathematicaParser.result(token, finalExpressionType, true);
                    MathematicaParser.AST right = parser.parseExpression(76);
                    MathematicaParser.AST tree = MathematicaParser.result(token, MathematicaElementTypes.PATTERN_EXPRESSION, right.isParsed());
                    tree.children.add(left);
                    if (!parser.optional) {
                        tree.children.add(right);
                        return tree;
                    } else {
                        tree.children.add(right.children.get(0));
                        parser.optional = false;
                        token = parser.getPrevToken();
                        MathematicaParser.AST tree2 = MathematicaParser.result(token, MathematicaElementTypes.OPTIONAL_EXPRESSION, true);
                        tree2.children.add(tree);
                        return tree2;
                    }
                }
            } else {
                parser.pattern = false;
            }
        } else if (token.type.equals(STRINGIFIED_IDENTIFIER)) {
            finalExpressionType = STRINGIFIED_SYMBOL_EXPRESSION;
        } else {
            return MathematicaParser.notParsed();
        }
        parser.advanceLexer();
        return MathematicaParser.result(token, finalExpressionType, true);
    }
}
