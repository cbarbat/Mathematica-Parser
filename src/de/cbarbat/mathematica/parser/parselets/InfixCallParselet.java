/*
 * Copyright (c) 2013 Patrick Scheibe
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
 * This parses infix calls of functions. Usually, functions are called like this f[a,b]. In Mathematica you can write
 * this as a~f~b. The difference to other infix operators is that it always has to be a pair of ~. Therefore, the first
 * ~ in a~f~b triggers the call of {@link InfixCallParselet#parse} which needs to ensure that we find a second ~ and the
 * expression b.
 *
 * @author patrick (3/27/13)
 */
public class InfixCallParselet implements InfixParselet {

  private final int myPrecedence;

  public InfixCallParselet(int precedence) {
    myPrecedence = precedence;
  }

  @Override
  public MathematicaParser.Result parse(MathematicaParser parser, MathematicaParser.Result left) throws CriticalParserError {

    parser.advanceLexer();
    MathematicaParser.Result operator = parser.parseExpression(myPrecedence);

    if (parser.matchesToken(MathematicaElementTypes.INFIX_CALL)) {
      parser.advanceLexer();
      MathematicaParser.Result operand2 = parser.parseExpression(myPrecedence);
      if (!operand2.isParsed()) {
        parser.error("Argument arg2 missing in 'arg1 ~ op ~ arg2'");
      }
      return MathematicaParser.result(MathematicaElementTypes.INFIX_CALL_EXPRESSION, operator.isParsed() && operand2.isParsed());
    } else {
      // if the operator was not parsed successfully we will not display a parsing error
      if (operator.isParsed()) {
        parser.error("'~' expected in infix notation");
      } else {
        parser.error("Operator op in 'arg1 ~ op ~ arg2' expected for infix notation");
      }
      return MathematicaParser.result(MathematicaElementTypes.INFIX_CALL_EXPRESSION, false);
    }
  }

  @Override
  public int getMyPrecedence() {
    return myPrecedence;
  }
}
