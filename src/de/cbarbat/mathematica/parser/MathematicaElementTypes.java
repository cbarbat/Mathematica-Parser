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

import java.util.Arrays;
import java.util.List;

/**
 * <p> This interface provides token types which are used by the Lexer and later by the parser. Some {@link List<MathematicaElementType>}'s
 * are defined which are used for the basic highlighter. Every {@link List<MathematicaElementType>} is then used to define a group of tokens
 * which are highlighted in the same color. Check ... . As a last part this interface
 * </p> <p> is used to define the {@link MathematicaElementType}'s which are used to _mark the AST during parsing. Since I use a
 * Pratt parser where every token gets its own small parser (called parselet) most lexer token types have one or more
 * corresponding parser element types which are then used as nodes in the AST tree. </p>
 *
 * @author patrick (12/27/12)
 */
public interface MathematicaElementTypes {

  MathematicaElementType WHITE_SPACE = new MathematicaElementType("WHITE_SPACE");
  MathematicaElementType LINE_BREAK = new MathematicaElementType("LINE_BREAK");
  /**
   * The following {@link List<MathematicaElementType>}'s are used for the basic highlighter.
   */
  List<MathematicaElementType> WHITE_SPACES = Arrays.asList(WHITE_SPACE, LINE_BREAK);

  MathematicaElementType BAD_CHARACTER =  new MathematicaElementType("BAD_CHARACTER");
  MathematicaElementType COMMENT_START = new MathematicaElementType("COMMENT_START");
  MathematicaElementType COMMENT_END = new MathematicaElementType("COMMENT_END");
  MathematicaElementType COMMENT_CONTENT = new MathematicaElementType("COMMENT_CONTENT");
  MathematicaElementType COMMENT_SECTION = new MathematicaElementType("COMMENT_SECTION");
  MathematicaElementType COMMENT_ANNOTATION = new MathematicaElementType("COMMENT_ANNOTATION");
  List<MathematicaElementType> COMMENTS = Arrays.asList(
      COMMENT_CONTENT, COMMENT_END, COMMENT_START, COMMENT_SECTION, COMMENT_ANNOTATION
  );
  @SuppressWarnings("UnusedDeclaration")
  List<MathematicaElementType> WHITE_SPACE_OR_COMMENTS = Arrays.asList(
      WHITE_SPACE, LINE_BREAK, COMMENT_CONTENT, COMMENT_END, COMMENT_START, COMMENT_SECTION, COMMENT_ANNOTATION
  );
  MathematicaElementType STRING_LITERAL = new MathematicaElementType("STRING_LITERAL");
  MathematicaElementType STRING_LITERAL_BEGIN = new MathematicaElementType("STRING_LITERAL_BEGIN");
  MathematicaElementType STRING_LITERAL_END = new MathematicaElementType("STRING_LITERAL_END");
  List<MathematicaElementType> STRING_LITERALS = Arrays.asList(
      STRING_LITERAL, STRING_LITERAL_END, STRING_LITERAL_BEGIN
  );
  MathematicaElementType IDENTIFIER = new MathematicaElementType("IDENTIFIER");
  MathematicaElementType ASSOCIATION_SLOT = new MathematicaElementType("ASSOCIATION_SLOT");
  MathematicaElementType STRINGIFIED_IDENTIFIER = new MathematicaElementType("STRINGIFIED_IDENTIFIER");
  MathematicaElementType NUMBER = new MathematicaElementType("NUMBER");
  List<MathematicaElementType> LITERALS = Arrays.asList(
      NUMBER
  );
  MathematicaElementType RIGHT_PAR = new MathematicaElementType("RIGHT_PAR");
  MathematicaElementType LEFT_PAR = new MathematicaElementType("LEFT_PAR");
  MathematicaElementType LEFT_BRACE = new MathematicaElementType("LEFT_BRACE");
  MathematicaElementType RIGHT_BRACE = new MathematicaElementType("RIGHT_BRACE");
  MathematicaElementType LEFT_BRACKET = new MathematicaElementType("LEFT_BRACKET");
  MathematicaElementType PART_BEGIN = new MathematicaElementType("PART_BEGIN");
  MathematicaElementType RIGHT_BRACKET = new MathematicaElementType("RIGHT_BRACKET");
  List<MathematicaElementType> BRACES = Arrays.asList(
      LEFT_BRACE, LEFT_BRACKET, LEFT_PAR,
      RIGHT_BRACE, RIGHT_BRACKET, RIGHT_PAR
  );
  MathematicaElementType LEFT_ASSOCIATION = new MathematicaElementType("LEFT_ASSOCIATION");
  MathematicaElementType RIGHT_ASSOCIATION = new MathematicaElementType("RIGHT_ASSOCIATION");
  MathematicaElementType ACCURACY = new MathematicaElementType("ACCURACY");
  MathematicaElementType COMMA = new MathematicaElementType("COMMA");
  MathematicaElementType PREFIX = new MathematicaElementType("PREFIX");
  MathematicaElementType POSTFIX = new MathematicaElementType("POSTFIX");
  MathematicaElementType COMPOSITION = new MathematicaElementType("COMPOSITION");
  MathematicaElementType RIGHT_COMPOSITION = new MathematicaElementType("RIGHT_COMPOSITION");
  MathematicaElementType MAP = new MathematicaElementType("MAP");
  MathematicaElementType MAP_ALL = new MathematicaElementType("MAP_ALL");
  MathematicaElementType APPLY = new MathematicaElementType("APPLY");
  MathematicaElementType APPLY1 = new MathematicaElementType("APPLY1");
  MathematicaElementType REPLACE_ALL = new MathematicaElementType("REPLACE_ALL");
  MathematicaElementType REPLACE_REPEATED = new MathematicaElementType("REPLACE_REPEATED");
  MathematicaElementType POWER = new MathematicaElementType("POWER");
  MathematicaElementType TIMES = new MathematicaElementType("TIMES");
  MathematicaElementType NON_COMMUTATIVE_MULTIPLY = new MathematicaElementType("NON_COMMUTATIVE_MULTIPLY");
  MathematicaElementType PLUS = new MathematicaElementType("PLUS");
  MathematicaElementType MINUS = new MathematicaElementType("MINUS");
  MathematicaElementType DIVIDE = new MathematicaElementType("DIVIDE");
  MathematicaElementType DIVIDE_BY = new MathematicaElementType("DIVIDE_BY");
  MathematicaElementType TIMES_BY = new MathematicaElementType("TIMES_BY");
  MathematicaElementType SUBTRACT_FROM = new MathematicaElementType("SUBTRACT_FROM");
  MathematicaElementType ADD_TO = new MathematicaElementType("ADD_TO");
  MathematicaElementType INCREMENT = new MathematicaElementType("INCREMENT");
  MathematicaElementType DECREMENT = new MathematicaElementType("DECREMENT");
  MathematicaElementType SAME_Q = new MathematicaElementType("SAME_Q");
  MathematicaElementType UNSAME_Q = new MathematicaElementType("UNSAME_Q");
  MathematicaElementType EQUAL = new MathematicaElementType("EQUAL");
  MathematicaElementType UNEQUAL = new MathematicaElementType("UNEQUAL");
  MathematicaElementType LESS_EQUAL = new MathematicaElementType("LESS_EQUAL");
  MathematicaElementType GREATER_EQUAL = new MathematicaElementType("GREATER_EQUAL");
  MathematicaElementType LESS = new MathematicaElementType("LESS");
  MathematicaElementType GREATER = new MathematicaElementType("GREATER");
  MathematicaElementType SET = new MathematicaElementType("SET");
  MathematicaElementType SET_DELAYED = new MathematicaElementType("SET_DELAYED");
  @SuppressWarnings("UnusedDeclaration")
  List<MathematicaElementType> ASSIGNMENTS = Arrays.asList(SET, SET_DELAYED);
  MathematicaElementType UNSET = new MathematicaElementType("UNSET");
  MathematicaElementType TAG_SET = new MathematicaElementType("TAG_SET");
  MathematicaElementType UP_SET = new MathematicaElementType("UP_SET");
  MathematicaElementType UP_SET_DELAYED = new MathematicaElementType("UP_SET_DELAYED");
  MathematicaElementType RULE = new MathematicaElementType("RULE");
  MathematicaElementType RULE_DELAYED = new MathematicaElementType("RULE_DELAYED");
  MathematicaElementType BLANK = new MathematicaElementType("BLANK");
  MathematicaElementType BLANK_SEQUENCE = new MathematicaElementType("BLANK_SEQUENCE");
  MathematicaElementType BLANK_NULL_SEQUENCE = new MathematicaElementType("BLANK_NULL_SEQUENCE");
  MathematicaElementType REPEATED = new MathematicaElementType("REPEATED");
  MathematicaElementType REPEATED_NULL = new MathematicaElementType("REPEATED_NULL");
  MathematicaElementType CONDITION = new MathematicaElementType("CONDITION");
  MathematicaElementType DEFAULT = new MathematicaElementType("DEFAULT");
  MathematicaElementType COLON = new MathematicaElementType("COLON");
  MathematicaElementType DOUBLE_COLON = new MathematicaElementType("DOUBLE_COLON");
  MathematicaElementType SEMICOLON = new MathematicaElementType("SEMICOLON");
  MathematicaElementType SPAN = new MathematicaElementType("SPAN");
  MathematicaElementType OUT = new MathematicaElementType("OUT");
  MathematicaElementType STRING_JOIN = new MathematicaElementType("STRING_JOIN");
  MathematicaElementType STRING_EXPRESSION = new MathematicaElementType("STRING_EXPRESSION");
  MathematicaElementType STRING_LITERAL_EXPRESSION = new MathematicaElementType("STRING_LITERAL_EXPRESSION");
  MathematicaElementType POINT = new MathematicaElementType("POINT");
  MathematicaElementType AND = new MathematicaElementType("AND");
  MathematicaElementType OR = new MathematicaElementType("OR");
  MathematicaElementType ALTERNATIVE = new MathematicaElementType("ALTERNATIVE");
  MathematicaElementType DERIVATIVE = new MathematicaElementType("DERIVATIVE");
  MathematicaElementType EXCLAMATION_MARK = new MathematicaElementType("EXCLAMATION_MARK");
  MathematicaElementType QUESTION_MARK = new MathematicaElementType("QUESTION_MARK");
  MathematicaElementType SLOT = new MathematicaElementType("SLOT");
  MathematicaElementType SLOT_SEQUENCE = new MathematicaElementType("SLOT_SEQUENCE");
  List<MathematicaElementType> SLOTS = Arrays.asList(
      SLOT, SLOT_SEQUENCE, ASSOCIATION_SLOT
  );
  MathematicaElementType FUNCTION = new MathematicaElementType("FUNCTION");
  MathematicaElementType BACK_TICK = new MathematicaElementType("BACK_TICK");
  MathematicaElementType INFIX_CALL = new MathematicaElementType("INFIX_CALL");
  MathematicaElementType PREFIX_CALL = new MathematicaElementType("PREFIX_CALL");
  MathematicaElementType GET = new MathematicaElementType("GET");
  MathematicaElementType PUT = new MathematicaElementType("PUT");
  MathematicaElementType PUT_APPEND = new MathematicaElementType("PUT_APPEND");
  List<MathematicaElementType> OPERATORS = Arrays.asList(
      ACCURACY, ADD_TO, ALTERNATIVE, AND, APPLY, APPLY1,
      BACK_TICK, BLANK, BLANK_NULL_SEQUENCE, BLANK_SEQUENCE,
      COLON, CONDITION,
      DECREMENT, DIVIDE, DIVIDE_BY, DOUBLE_COLON, EQUAL,
      EXCLAMATION_MARK,
      FUNCTION,
      GET, GREATER, GREATER_EQUAL,
      INCREMENT, INFIX_CALL,
      LESS, LESS_EQUAL,
      MAP, MINUS,
      NON_COMMUTATIVE_MULTIPLY,
      DEFAULT, OR, OUT,
      PLUS, POSTFIX, PREFIX, PUT, PUT_APPEND,
      QUESTION_MARK,
      REPEATED, REPEATED_NULL, REPLACE_ALL, REPLACE_REPEATED, RULE, RULE_DELAYED,
      SAME_Q, SEMICOLON, SET, SET_DELAYED,
      TAG_SET, TIMES, TIMES_BY,
      UNEQUAL, UNSAME_Q, UNSET, UP_SET, UP_SET_DELAYED, PREFIX_CALL, STRING_JOIN, STRING_LITERAL_EXPRESSION
  );
  MathematicaElementType GROUP_EXPRESSION = new MathematicaElementType("GROUP_EXPRESSION");


  // THIS SECTION IS AUTOMATICALLY CREATED WITH MATHEMATICA
  MathematicaElementType LIST_EXPRESSION = new MathematicaElementType("LIST_EXPRESSION");
  MathematicaElementType ASSOCIATION_EXPRESSION = new MathematicaElementType("ASSOCIATION_EXPRESSION");
  MathematicaElementType NUMBER_EXPRESSION = new MathematicaElementType("NUMBER_EXPRESSION");
  MathematicaElementType SYMBOL_EXPRESSION = new MathematicaElementType("SYMBOL_EXPRESSION");
  MathematicaElementType STRINGIFIED_SYMBOL_EXPRESSION = new MathematicaElementType("STRINGIFIED_SYMBOL_EXPRESSION");
  MathematicaElementType STRING = new MathematicaElementType("STRING");
  MathematicaElementType MESSAGE_NAME_EXPRESSION = new MathematicaElementType("MESSAGE_NAME_EXPRESSION");
  MathematicaElementType BLANK_EXPRESSION = new MathematicaElementType("BLANK_EXPRESSION");
  MathematicaElementType BLANK_SEQUENCE_EXPRESSION = new MathematicaElementType("BLANK_SEQUENCE_EXPRESSION");
  MathematicaElementType BLANK_NULL_SEQUENCE_EXPRESSION = new MathematicaElementType("BLANK_NULL_SEQUENCE_EXPRESSION");
  MathematicaElementType GET_PREFIX = new MathematicaElementType("GET_PREFIX");
  MathematicaElementType PATTERN_TEST_EXPRESSION = new MathematicaElementType("PATTERN_TEST_EXPRESSION");
  MathematicaElementType FUNCTION_CALL_EXPRESSION = new MathematicaElementType("FUNCTION_CALL_EXPRESSION");
  MathematicaElementType PART_EXPRESSION = new MathematicaElementType("PART_EXPRESSION");
  MathematicaElementType INCREMENT_POSTFIX = new MathematicaElementType("INCREMENT_POSTFIX");
  MathematicaElementType DECREMENT_POSTFIX = new MathematicaElementType("DECREMENT_POSTFIX");
  MathematicaElementType PRE_INCREMENT_PREFIX = new MathematicaElementType("PRE_INCREMENT_PREFIX");
  MathematicaElementType PRE_DECREMENT_PREFIX = new MathematicaElementType("PRE_DECREMENT_PREFIX");
  MathematicaElementType PREFIX_CALL_EXPRESSION = new MathematicaElementType("PREFIX_CALL_EXPRESSION");
  MathematicaElementType INFIX_CALL_EXPRESSION = new MathematicaElementType("INFIX_CALL_EXPRESSION");
  MathematicaElementType MAP_EXPRESSION = new MathematicaElementType("MAP_EXPRESSION");
  MathematicaElementType MAP_ALL_EXPRESSION = new MathematicaElementType("MAP_ALL_EXPRESSION");
  MathematicaElementType APPLY_EXPRESSION = new MathematicaElementType("APPLY_EXPRESSION");
  MathematicaElementType APPLY1_EXPRESSION = new MathematicaElementType("APPLY1_EXPRESSION");
  MathematicaElementType FACTORIAL_POSTFIX = new MathematicaElementType("FACTORIAL_POSTFIX");
  MathematicaElementType DERIVATIVE_EXPRESSION = new MathematicaElementType("DERIVATIVE_EXPRESSION");
  MathematicaElementType STRING_JOIN_EXPRESSION = new MathematicaElementType("STRING_JOIN_EXPRESSION");
  MathematicaElementType POWER_EXPRESSION = new MathematicaElementType("POWER_EXPRESSION");
  MathematicaElementType NON_COMMUTATIVE_MULTIPLY_EXPRESSION = new MathematicaElementType("NON_COMMUTATIVE_MULTIPLY_EXPRESSION");
  MathematicaElementType DOT_EXPRESSION = new MathematicaElementType("DOT_EXPRESSION");
  MathematicaElementType UNARY_MINUS_PREFIX = new MathematicaElementType("UNARY_MINUS_PREFIX");
  MathematicaElementType UNARY_PLUS_PREFIX = new MathematicaElementType("UNARY_PLUS_PREFIX");
  MathematicaElementType DIVIDE_EXPRESSION = new MathematicaElementType("DIVIDE_EXPRESSION");
  MathematicaElementType TIMES_EXPRESSION = new MathematicaElementType("TIMES_EXPRESSION");
  MathematicaElementType PLUS_EXPRESSION = new MathematicaElementType("PLUS_EXPRESSION");
  MathematicaElementType MINUS_EXPRESSION = new MathematicaElementType("MINUS_EXPRESSION");
  MathematicaElementType SPAN_EXPRESSION = new MathematicaElementType("SPAN_EXPRESSION");
  MathematicaElementType EQUAL_EXPRESSION = new MathematicaElementType("EQUAL_EXPRESSION");
  MathematicaElementType UNEQUAL_EXPRESSION = new MathematicaElementType("UNEQUAL_EXPRESSION");
  MathematicaElementType GREATER_EXPRESSION = new MathematicaElementType("GREATER_EXPRESSION");
  MathematicaElementType GREATER_EQUAL_EXPRESSION = new MathematicaElementType("GREATER_EQUAL_EXPRESSION");
  MathematicaElementType LESS_EXPRESSION = new MathematicaElementType("LESS_EXPRESSION");
  MathematicaElementType LESS_EQUAL_EXPRESSION = new MathematicaElementType("LESS_EQUAL_EXPRESSION");
  MathematicaElementType SAME_Q_EXPRESSION = new MathematicaElementType("SAME_Q_EXPRESSION");
  MathematicaElementType UNSAME_Q_EXPRESSION = new MathematicaElementType("UNSAME_Q_EXPRESSION");
  MathematicaElementType NOT_PREFIX = new MathematicaElementType("NOT_PREFIX");
  MathematicaElementType AND_EXPRESSION = new MathematicaElementType("AND_EXPRESSION");
  MathematicaElementType OR_EXPRESSION = new MathematicaElementType("OR_EXPRESSION");
  MathematicaElementType REPEATED_POSTFIX = new MathematicaElementType("REPEATED_POSTFIX");
  MathematicaElementType REPEATED_NULL_POSTFIX = new MathematicaElementType("REPEATED_NULL_POSTFIX");
  MathematicaElementType ALTERNATIVE_EXPRESSION = new MathematicaElementType("ALTERNATIVE_EXPRESSION");
  MathematicaElementType PATTERN_EXPRESSION = new MathematicaElementType("PATTERN_EXPRESSION");
  MathematicaElementType OPTIONAL_EXPRESSION = new MathematicaElementType("OPTIONAL_EXPRESSION");
  MathematicaElementType DEFAULT_EXPRESSION = new MathematicaElementType("DEFAULT_EXPRESSION");
  MathematicaElementType STRING_EXPRESSION_EXPRESSION = new MathematicaElementType("STRING_EXPRESSION_EXPRESSION");
  MathematicaElementType CONDITION_EXPRESSION = new MathematicaElementType("CONDITION_EXPRESSION");
  MathematicaElementType RULE_EXPRESSION = new MathematicaElementType("RULE_EXPRESSION");
  MathematicaElementType RULE_DELAYED_EXPRESSION = new MathematicaElementType("RULE_DELAYED_EXPRESSION");
  MathematicaElementType REPLACE_ALL_EXPRESSION = new MathematicaElementType("REPLACE_ALL_EXPRESSION");
  MathematicaElementType REPLACE_REPEATED_EXPRESSION = new MathematicaElementType("REPLACE_REPEATED_EXPRESSION");
  MathematicaElementType ADD_TO_EXPRESSION = new MathematicaElementType("ADD_TO_EXPRESSION");
  MathematicaElementType SUBTRACT_FROM_EXPRESSION = new MathematicaElementType("SUBTRACT_FROM_EXPRESSION");
  MathematicaElementType TIMES_BY_EXPRESSION = new MathematicaElementType("TIMES_BY_EXPRESSION");
  MathematicaElementType DIVIDE_BY_EXPRESSION = new MathematicaElementType("DIVIDE_BY_EXPRESSION");
  MathematicaElementType FUNCTION_POSTFIX = new MathematicaElementType("FUNCTION_POSTFIX");
  MathematicaElementType POSTFIX_EXPRESSION = new MathematicaElementType("POSTFIX_EXPRESSION");
  MathematicaElementType COMPOSITION_EXPRESSION = new MathematicaElementType("COMPOSITION_EXPRESSION");
  MathematicaElementType RIGHT_COMPOSITION_EXPRESSION = new MathematicaElementType("RIGHT_COMPOSITION_EXPRESSION");
  MathematicaElementType SET_EXPRESSION = new MathematicaElementType("SET_EXPRESSION");
  MathematicaElementType SET_DELAYED_EXPRESSION = new MathematicaElementType("SET_DELAYED_EXPRESSION");
  MathematicaElementType UP_SET_EXPRESSION = new MathematicaElementType("UP_SET_EXPRESSION");
  MathematicaElementType UP_SET_DELAYED_EXPRESSION = new MathematicaElementType("UP_SET_DELAYED_EXPRESSION");
  MathematicaElementType TAG_SET_EXPRESSION = new MathematicaElementType("TAG_SET_EXPRESSION");
  MathematicaElementType TAG_SET_DELAYED_EXPRESSION = new MathematicaElementType("TAG_SET_DELAYED_EXPRESSION");
  MathematicaElementType TAG_UNSET_EXPRESSION = new MathematicaElementType("TAG_UNSET_EXPRESSION");
  MathematicaElementType UNSET_EXPRESSION = new MathematicaElementType("UNSET_EXPRESSION");
  MathematicaElementType PUT_EXPRESSION = new MathematicaElementType("PUT_EXPRESSION");
  MathematicaElementType PUT_APPEND_EXPRESSION = new MathematicaElementType("PUT_APPEND_EXPRESSION");
  MathematicaElementType COMPOUND_EXPRESSION_EXPRESSION = new MathematicaElementType("COMPOUND_EXPRESSION_EXPRESSION");
  MathematicaElementType FAILBACK = new MathematicaElementType("FAILBACK");
  
}
