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

/**
 * Parselet for grouping (2+3)*4
 *
 * @author patrick (3/27/13)
 */
public class GroupParselet implements PrefixParselet {

    public GroupParselet() {
    }

    public MathematicaParser.ASTNode parse(MathematicaParser parser) throws CriticalParserError {
        // should never happen
        if (!parser.getToken().type.equals(MathematicaElementTypes.LEFT_PAR)) {
            return MathematicaParser.notParsed();
        }
        parser.myParDepth++;
        parser.advanceLexer();

        if (parser.eof()) {
            parser.error("More input expected");
            return MathematicaParser.notParsed();
        }

        MathematicaParser.ASTNode result = parser.parseExpression();
        if (parser.matchesToken(MathematicaElementTypes.RIGHT_PAR)) {
            parser.myParDepth--;
            parser.advanceLexer();

            // if we find a closing ) we return the group as parsed successful, no matter whether
            // the containing expression was parsed. Errors in the expression are marked there anyway.
            //result = MathematicaParser.result(token, tokenType, true);
        } else {
            // when the grouped expr was parsed successfully and we just don't find the closing parenthesis we
            // create an error mark there. Otherwise we just return "not parsed" since something seems to be really
            // broken.
            if (result.isParsed()) {
                parser.error("Closing ')' expected");
                //result = MathematicaParser.result(token, tokenType, false);
            } else {
                result = MathematicaParser.notParsed();
            }
        }
        return result;
    }

}
