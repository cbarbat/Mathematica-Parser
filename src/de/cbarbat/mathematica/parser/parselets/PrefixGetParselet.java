/*
 * Copyright (c) 2014 Patrick Scheibe
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
import de.cbarbat.mathematica.parser.ParseletProvider;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

/**
 * Parses <<package`name`
 *
 * @author patrick (3/27/13)
 */
public class PrefixGetParselet implements PrefixParselet {
  private final int myPrecedence;

  public PrefixGetParselet(int precedence) {
    this.myPrecedence = precedence;
  }

  @Override
  public MathematicaParser.Result parse(MathematicaParser parser) throws CriticalParserError {
    MathematicaElementType nodeToken = MathematicaElementTypes.GET_PREFIX;
    parser.advanceLexer();
    boolean result;
    if (parser.matchesToken(MathematicaElementTypes.STRINGIFIED_IDENTIFIER)) {
      final PrefixParselet prefixParselet = ParseletProvider.getPrefixParselet(MathematicaElementTypes.STRINGIFIED_IDENTIFIER);
      result = prefixParselet.parse(parser).isParsed();
    } else {
      parser.error("File- or package-path, or string expected");
      result = false;
    }
    return MathematicaParser.result(nodeToken, result);
  }

  public int getPrecedence() {
    return myPrecedence;
  }
}
