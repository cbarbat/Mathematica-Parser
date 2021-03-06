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

import de.cbarbat.mathematica.parser.CriticalParserError;
import de.cbarbat.mathematica.parser.MathematicaParser;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

import java.util.ArrayList;

/**
 * Parselet for MessageName's like symbol::usage or Sin::tag. There are some specialties about this because the left
 * operand is required to be a symbol. The right operand can be a symbol or a string.
 *
 * @author patrick (3/27/13)
 */
public class MessageNameParselet implements InfixParselet {

    private final int myPrecedence;

    public MessageNameParselet(int precedence) {
        this.myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser, MathematicaParser.ASTNode left) throws CriticalParserError {

        if (left.token.type != MathematicaElementTypes.SYMBOL_EXPRESSION) {
            parser.error("MessageName expects Symbol here");
            left.parsed = false;
            return left;
        }

        MathematicaParser.ASTNode tree;
        ArrayList<MathematicaParser.ASTNode> sequence = new ArrayList<>();
        sequence.add(left);

        parser.advanceLexer();
        MathematicaParser.ASTNode result = parser.parseExpression(myPrecedence);

        if (result.isParsed()) {
            // Check whether we have a symbol or a string in usage message
            if ((!result.getToken().equals(MathematicaElementTypes.SYMBOL_EXPRESSION)) &&
                    (!result.getToken().equals(MathematicaElementTypes.STRING_LITERAL_EXPRESSION))) {
                parser.error("MessageName expects Symbol or String here");
            } else { //if (result.getToken().equals(MathematicaElementTypes.SYMBOL_EXPRESSION)) {
                sequence.add(result);
                tree = MathematicaParser.result(left.token, MathematicaElementTypes.STRINGIFIED_SYMBOL_EXPRESSION, result.isParsed());
                tree.children = sequence;
            }

            // Check whether we have the form symbol::name::language
            if (parser.matchesToken(MathematicaElementTypes.DOUBLE_COLON)) {
                parser.advanceLexer();
                result = parser.parseExpression(myPrecedence);
                if (result.isParsed() && ((!result.getToken().equals(MathematicaElementTypes.SYMBOL_EXPRESSION)) &&
                        (!result.getToken().equals(MathematicaElementTypes.STRING_LITERAL_EXPRESSION)))) {
                    parser.error("MessageName expects Symbol or String here");
                } else { //if (result.isValid() && result.getToken().equals(MathematicaElementTypes.SYMBOL_EXPRESSION)) {
                    sequence.add(result);
                    tree = MathematicaParser.result(left.token, MathematicaElementTypes.STRINGIFIED_SYMBOL_EXPRESSION, result.isParsed());
                    tree.children = sequence;
                }
            }
        } else {
            parser.error("Symbol or String expected as tag in symbol\\:\\:tag");
        }

        if (parser.matchesToken(MathematicaElementTypes.DOUBLE_COLON)) {
            parser.error("MessageName expects only 2 or 3 arguments here");
            left.parsed = false;
            return left;
        }

        tree = MathematicaParser.result(left.token, MathematicaElementTypes.MESSAGE_NAME_EXPRESSION, result.isParsed());
        tree.children = sequence;
        return tree;

    }

    @Override
    public int getMyPrecedence() {
        return myPrecedence;
    }
}
