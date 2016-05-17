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
import de.cbarbat.mathematica.parser.MathematicaElementTypes;
import de.cbarbat.mathematica.parser.MathematicaParser;

/**
 * Parselet for parsing a single _.
 *
 * @author patrick (3/27/13)
 */
public class PrefixDefaultParselet implements PrefixParselet {

    public PrefixDefaultParselet() {
    }

    @Override
    public MathematicaParser.AST parse(MathematicaParser parser) throws CriticalParserError {
/*
        MathematicaLexer.Token token = parser.getToken(); //_.
        parser.advanceLexer();
        return MathematicaParser.result(token, MathematicaElementTypes.DEFAULT_EXPRESSION, true);
*/
        parser.optional = true;
        MathematicaLexer.Token token2 = parser.getToken(); //_.
        parser.advanceLexer();
        MathematicaLexer.Token token1 = new MathematicaLexer.Token(MathematicaElementTypes.BLANK, "_", token2.start, token2.start+1); //_
        token2.start++; //.
        MathematicaParser.AST subtree = MathematicaParser.result(token1, MathematicaElementTypes.BLANK_EXPRESSION, true);
        MathematicaParser.AST    tree = MathematicaParser.result(token2, MathematicaElementTypes.OPTIONAL_EXPRESSION, true);
        tree.children.add(subtree);
        return tree;
    }

}
