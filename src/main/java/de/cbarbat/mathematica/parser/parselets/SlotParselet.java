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

import static de.cbarbat.mathematica.parser.MathematicaElementTypes.*;

/**
 * Parselet for symbols (identifier). Here, I currently parse not only simple identifiers like Sqrt, variable or
 * $MaxRecursion. Additionally, I parse slot expressions like #1, ## or #abc into symbols and I parse stringified
 * expressions like the rhs of << into one STRINGIFIED_SYMBOL_EXPRESSION node.
 *
 * @author patrick (3/27/13)
 */
public class SlotParselet implements PrefixParselet {

    public SlotParselet() {
    }

    @Override
    public MathematicaParser.ASTNode parse(MathematicaParser parser) throws CriticalParserError {
        final MathematicaLexer.Token token = parser.getToken();
        final MathematicaElementType tokenType = token.type;
        if (tokenType.equals(SLOT)) {
            parser.advanceLexer();
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SLOT_EXPRESSION, true);
            Integer len = 1;
            if (token.text.length() > 1) {
                String str = token.text.substring(1, token.text.length());
                len = Integer.parseInt(str);
            }
            MathematicaLexer.Token token1 = new MathematicaLexer.Token(NUMBER, len.toString(), token.start+1, token.end);
            MathematicaParser.ASTNode number = MathematicaParser.result(token1, NUMBER_EXPRESSION, true);
            tree.children.add(number);
            return tree;
        } else
        if (tokenType.equals(SLOT_SEQUENCE)) {
            parser.advanceLexer();
            MathematicaParser.ASTNode tree = MathematicaParser.result(token, SLOT_SEQUENCE_EXPRESSION, true);
            Integer len = 1;
            if (token.text.length() > 2) {
                String str = token.text.substring(2, token.text.length());
                len = Integer.parseInt(str);
            }
            MathematicaLexer.Token token1 = new MathematicaLexer.Token(NUMBER, len.toString(), token.start+1, token.end);
            MathematicaParser.ASTNode number = MathematicaParser.result(token1, NUMBER_EXPRESSION, true);
            tree.children.add(number);
            return tree;
        } else {
            return MathematicaParser.notParsed();
        }

    }

}
