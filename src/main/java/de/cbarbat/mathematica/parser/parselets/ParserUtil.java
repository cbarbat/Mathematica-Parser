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

import de.cbarbat.mathematica.parser.MathematicaElementType;
import de.cbarbat.mathematica.parser.CriticalParserError;
import de.cbarbat.mathematica.parser.MathematicaParser;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

import java.util.ArrayList;

/**
 * Utility class for parsing.
 *
 * @author patrick (3/30/13)
 */
final class ParserUtil {

    private ParserUtil() {
    }

    /**
     * Parses an expression sequence. These sequences are pretty common in function calls like f[a,b,c,d] or in lists
     * {1,2,3,4} and are just a comma separated list of expressions which go as long as we don't find the right delimiter.
     * Note that we do not accept {1,2,,,3} which would be valid Mathematica syntax but is not used in real applications.
     * Therefore, we will give an error instead because the user most probably did a mistake.
     *
     * @param parser   Parser which provides the token-stream, the builder, etc
     * @param rightDel Token where we will stop the sequence parsing
     * @return The parsing result which is true iff all sub-expressions were successfully parsed.
     * @throws CriticalParserError
     */
    static ArrayList<MathematicaParser.AST> parseSequence(MathematicaParser parser, MathematicaElementType rightDel) throws CriticalParserError {
        MathematicaParser.AST expression;
        ArrayList<MathematicaParser.AST> sequence = new ArrayList<>();
        boolean sequenceParsed = true;

        // The following is not correct regarding the syntax of the Mathematica language because f[a,,b] is equivalent to
        // f[a, Null, b] but most people don't know this and just made an error when they typed
        // a comma with no expression. Therefore we will only regard f[a,b,c,d,..] as correct function calls.
        // Note, that it is always possible to write f[a, Null, b] explicitly, so we don't loose expression power.
        while (true) {
            if (parser.matchesToken(rightDel)) {
                break;
            }

            expression = parser.parseExpression();
            sequence.add(expression);
            if (expression.token == null)
                expression.parsed = true;
            sequenceParsed &= expression.isParsed();

            if (parser.matchesToken(MathematicaElementTypes.COMMA, rightDel)) {
                sequence.add(null);
                parser.advanceLexer();
            }

            if (parser.matchesToken(MathematicaElementTypes.COMMA)) {
                parser.advanceLexer();
            }

        }
        if (!sequenceParsed)
            sequence = null;
        return sequence;
    }
}
