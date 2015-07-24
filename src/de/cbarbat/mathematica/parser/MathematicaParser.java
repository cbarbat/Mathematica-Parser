/*
 * Copyright (c) 2013 Patrick Scheibe & 2015 Calin Barbat
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

package de.cbarbat.mathematica.parser;

import de.cbarbat.mathematica.parser.parselets.ImplicitMultiplicationParselet;
import de.cbarbat.mathematica.parser.parselets.InfixParselet;
import de.cbarbat.mathematica.parser.parselets.PrefixParselet;
import de.cbarbat.mathematica.lexer.MathematicaLexer;

import static de.cbarbat.mathematica.parser.MathematicaElementTypes.LINE_BREAK;
import static de.cbarbat.mathematica.parser.MathematicaElementTypes.WHITE_SPACES;
import static de.cbarbat.mathematica.parser.ParseletProvider.getInfixParselet;
import static de.cbarbat.mathematica.parser.ParseletProvider.getPrefixParselet;

/**
 * @author patrick (3/27/13)
 */
public class MathematicaParser {

  private static final int MAX_RECURSION_DEPTH = 1024;
  private static final ImplicitMultiplicationParselet IMPLICIT_MULTIPLICATION_PARSELET = new ImplicitMultiplicationParselet();
  private final ImportantLineBreakHandler myImportantLinebreakHandler;
  private MathematicaLexer myLexer = null;
  private int myRecursionDepth;


  public MathematicaParser(MathematicaLexer ml) {
    myLexer = ml;
    myRecursionDepth = 0;
    myImportantLinebreakHandler = new ImportantLineBreakHandler();
  }

  /**
   * Function to create information about a parsed expression. In a Pratt parser often you need the last parsed
   * expression to combine it into a new parse-node. So <code >expr1 + expr2</code> is combined into a new node by
   * recognizing the operator <code >+</code>, parsing <code >expr2</code> and combining everything into a new parse
   * node.
   * <p/>
   * Since IDEA uses markers to mark the sequential code into a tree-structure I use this {@link Result} which contains
   * additionally the {@link MathematicaElementType} of the last expression and whether the previous expression was parsed.
   *
   * @param token
   *     The token type of the expression which was parsed, e.g. FUNCTION_CALL_EXPRESSION
   * @param parsedQ
   *     Whether the parsing of the expression was successful
   * @return The Result object with the given parsing information.
   */
  public static Result result(MathematicaElementType token, boolean parsedQ) {
    return new Result(token, parsedQ);
  }

  /**
   * This is the return value of a parser when errors happened.
   *
   * @return A special return Result saying <em >the expression could not be parsed</em>. Note this does not mean that
   * the expression was parsed and errors occurred! It says the parser could do absolutely nothing. This is returned if
   * the parser could identify a meaningful operator from the token-stream. See {@link #parseExpression(int)} for use
   * cases.
   */
  public static Result notParsed() {
    return new Result(null, false);
  }

  /**
   * This is the main entry point for the parsing a file. Every tme
   *
   */
  public void parse() {
    myLexer.setImportantLineBreakHandler(myImportantLinebreakHandler);
    try {
      while (!myLexer.eof()) {
        Result expr = parseExpression();
        if (!expr.isParsed()) {
          error("The last expression could not be parsed correctly.");
          myLexer.advanceLexer();
        } else {
          //System.out.println("Result: " + expr.getToken().myType);
        }
      }
    } catch (CriticalParserError criticalParserError) {
      while (!myLexer.eof()) {
        myLexer.advanceLexer();
      }
      error(criticalParserError.getMessage());
    }
  }

  public Result parseExpression() throws CriticalParserError {
    return parseExpression(0);
  }

  public Result parseExpression(int precedence) throws CriticalParserError {
    if (myLexer.eof()) return notParsed();

    if (myRecursionDepth > MAX_RECURSION_DEPTH) {
      throw new CriticalParserError("Maximal recursion depth exceeded during parsing.");
    }

    MathematicaElementType token = getTokenType();
    if (token == null) {
      return notParsed();
    }

    PrefixParselet prefix = getPrefixParselet(token);
    if (prefix == null) {
      return notParsed();
    }

    increaseRecursionDepth();
    Result left = prefix.parse(this);

    while (left.isParsed()) {
      token = getTokenType();
      InfixParselet infix = getInfixOrMultiplyParselet(token);
      if (infix == null) {
        break;
      }
      if (precedence >= infix.getMyPrecedence()) {
        break;
      }
      left = infix.parse(this, left);
    }
    decreaseRecursionDepth();
    return left;
  }

  private InfixParselet getInfixOrMultiplyParselet(MathematicaElementType token) {
    InfixParselet infixParselet = getInfixParselet(token);
    PrefixParselet prefixParselet = getPrefixParselet(token);

    if (infixParselet != null) return infixParselet;

    if (prefixParselet == null) {
      return null;
    }

    if (myImportantLinebreakHandler.hadLineBreak() && myRecursionDepth == 1) {
      return null;
    }

    return IMPLICIT_MULTIPLICATION_PARSELET;
  }

  public int decreaseRecursionDepth() {
    return --myRecursionDepth;
  }

  public int increaseRecursionDepth() {
    return ++myRecursionDepth;
  }

  public MathematicaElementType getTokenType() {
    if (!myLexer.eof())
      return myLexer.getToken().type;
    else
      return null;
  }

  public void advanceLexer() throws CriticalParserError {
    if (myLexer.eof()) {
      throw new CriticalParserError("Unexpected end of input.");
    }
    myImportantLinebreakHandler.reset();
    myLexer.advanceLexer();
  }

  public boolean matchesToken(MathematicaElementType token) {
    final MathematicaElementType testToken = myLexer.lookAhead(0);
    return (testToken != null && testToken.equals(token));
  }

  public boolean matchesToken(MathematicaElementType token1, MathematicaElementType token2) {
    final MathematicaElementType firstToken = myLexer.lookAhead(0);
    final MathematicaElementType secondToken = myLexer.lookAhead(1);
    return (firstToken != null && firstToken.equals(token1)) && (secondToken != null && secondToken.equals(token2));
  }

  /**
   * @param s
   *     Error message
   */
  public void error(String s) {
    System.out.println("Lexer error: " + s);
  }

  @SuppressWarnings({"BooleanMethodNameMustStartWithQuestion", "InstanceMethodNamingConvention"})
  public boolean eof() {
    return myLexer.eof();
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public boolean isNextWhitespace() {
    final MathematicaElementType possibleWhitespace = myLexer.rawLookup(1);
    return WHITE_SPACES.contains(possibleWhitespace);
  }

  /**
   * For the Pratt parser we need the left side which was already parsed. An instance of this will provide all necessary
   * information required to know what expression was parsed on the left of an infix operator.
   */
  public static final class Result {

    private final MathematicaElementType myLeftToken;
    private final boolean myParsed;

    private Result(MathematicaElementType leftToken, boolean parsed) {
      this.myLeftToken = leftToken;
      this.myParsed = parsed;
      if (leftToken != null)
        System.out.println("Result: " + this.myLeftToken.myType + " parsed: " + this.myParsed);
      else
        System.out.println("Result: null" + " parsed: " + this.myParsed);
    }

    public MathematicaElementType getToken() {
      return myLeftToken;
    }

    /**
     * True, iff an expression could be parsed correctly. This method can be used to check, whether the result of the
     * parsing of a sub-expression was successful. For instance in <code >expr1 + expr2</code>: you can test if <code
     * >expr2</code> was parsed successfully and decide what to do in the parsing of Plus, if it wasn't
     *
     * @return true if an expression was parsed correctly.
     */
    public boolean isParsed() {
      return myParsed;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isValid() {
      return (myLeftToken != null);
    }
  }

  /**
   * Registers when a whitespace token was seen. This is important in order to find out whether an <em>implicit
   * multiplication</em> has arisen.
   */
  public class ImportantLineBreakHandler {
    private boolean myLineBreakSeen = false;

    public void onSkip(MathematicaElementType type) {
      if (type.equals(LINE_BREAK))
        myLineBreakSeen = true;
    }

    public void reset() {
      myLineBreakSeen = false;
    }

    public boolean hadLineBreak() {
      return myLineBreakSeen;
    }

  }
}
