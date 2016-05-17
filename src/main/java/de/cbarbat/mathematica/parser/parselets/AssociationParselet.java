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

import java.util.ArrayList;

import static de.cbarbat.mathematica.parser.MathematicaElementTypes.*;

/**
 * Provides functionality to parse Association in Mathematica Created by rsmenon on 3/28/14.
 */
public class AssociationParselet implements PrefixParselet {

    public AssociationParselet() {
    }

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser) throws CriticalParserError {
        boolean parsed = true;
        MathematicaLexer.Token token = parser.getToken();

        if (parser.matchesToken(LEFT_ASSOCIATION)) {
            parser.advanceLexer();
        } else {
            throw new CriticalParserError("Association token does not start with '<|'");
        }

        ArrayList<MathematicaParser.AST> seqResult = ParserUtil.parseSequence(parser, RIGHT_ASSOCIATION);

        if (parser.matchesToken(RIGHT_ASSOCIATION)) {
            parser.advanceLexer();
        } else {
            parser.error("Closing '|>' expected");
            parsed = false;
        }

        MathematicaParser.AST tree = MathematicaParser.result(token, ASSOCIATION_EXPRESSION, parsed && seqResult != null);
        tree.children = seqResult;
        return tree;
    }
}
