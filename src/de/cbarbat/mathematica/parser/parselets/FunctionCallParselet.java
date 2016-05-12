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

import java.util.ArrayList;

/**
 * Parses functions calls like f[x] and array element access like l[[i]] since both start with an opening bracket.
 *
 * @author patrick (3/27/13)
 */
public class FunctionCallParselet implements InfixParselet {

    private final int myPrecedence;

    public FunctionCallParselet(int precedence) {
        myPrecedence = precedence;
    }

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser, MathematicaParser.AST left) throws CriticalParserError {
        // should never happen
        if ((!parser.getTokenType().equals(MathematicaElementTypes.LEFT_BRACKET)) && !left.isValid()) {
            return MathematicaParser.notParsed();
        }

        MathematicaLexer.Token token = parser.getToken();

        // parse the start. Could be one of the following:
        //   1. a Part expression like list[[
        //   2. a function call f[
        //   3. a slot expression like #[ which could be a function call or an Association lookup
        boolean isPartExpr = false;
        boolean isAssociationSlot = false;
        if (parser.matchesToken(MathematicaElementTypes.LEFT_BRACKET, MathematicaElementTypes.LEFT_BRACKET)) {
            isPartExpr = true;
            parser.advanceLexer();
            parser.myBracketDepth++;
            parser.advanceLexer();
            parser.myBracketDepth++;
        } else if (left.getToken().equals(MathematicaElementTypes.SLOT)) {
            isAssociationSlot = true;
            parser.advanceLexer();
            parser.myBracketDepth++;
        } else {
            parser.advanceLexer();
            parser.myBracketDepth++;
        }

        ArrayList<MathematicaParser.AST> sequence = null;
        boolean hasArgs = false;
        if (!parser.matchesToken(MathematicaElementTypes.RIGHT_BRACKET)) {
            sequence = ParserUtil.parseSequence(parser, MathematicaElementTypes.RIGHT_BRACKET);
            hasArgs = true;
        }

        if (parser.matchesToken(MathematicaElementTypes.RIGHT_BRACKET)) {
            if (isPartExpr && parser.matchesToken(MathematicaElementTypes.RIGHT_BRACKET, MathematicaElementTypes.RIGHT_BRACKET)) {
                if (!hasArgs) {
                    parser.error("Part expression in list[[...]] cannot be empty");
                }
                parser.advanceLexer();
                parser.myBracketDepth--;
                parser.advanceLexer();
                parser.myBracketDepth--;

                MathematicaParser.AST tree = MathematicaParser.result(token, MathematicaElementTypes.PART_EXPRESSION, hasArgs);
                if (hasArgs) tree.children = sequence;
                tree.children.add(0, left);
                return tree;
            } else if (isPartExpr) {
                parser.advanceLexer();
                parser.myBracketDepth--;
                parser.error("Closing ']]' expected");

                return MathematicaParser.result(token, MathematicaElementTypes.PART_EXPRESSION, false);
            } else if (isAssociationSlot) {
                parser.advanceLexer();
                parser.myBracketDepth--;

                MathematicaParser.AST tree = MathematicaParser.result(token, MathematicaElementTypes.SLOT_EXPRESSION, true);
                if (hasArgs) tree.children = sequence;
                tree.children.add(0, left);
                return tree;
            } else {
                parser.advanceLexer();
                parser.myBracketDepth--;

                MathematicaParser.AST tree = MathematicaParser.result(token, MathematicaElementTypes.FUNCTION_CALL_EXPRESSION, true);
                if (hasArgs) tree.children = sequence;
                tree.children.add(0, left);
                return tree;
            }
        }

        parser.error("Closing ']' expected");
        MathematicaElementType expressionType = isPartExpr ? MathematicaElementTypes.PART_EXPRESSION : MathematicaElementTypes.FUNCTION_CALL_EXPRESSION;

        return MathematicaParser.result(token, expressionType, false);

    }

    @Override
    public int getMyPrecedence() {
        return myPrecedence;
    }
}
