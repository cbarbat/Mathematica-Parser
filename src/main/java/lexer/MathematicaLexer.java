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

package main.java.lexer;

import main.java.parser.MathematicaElementType;
import main.java.parser.MathematicaElementTypes;
import main.java.parser.MathematicaParser;

import java.io.IOException;
import java.util.ArrayList;

public class MathematicaLexer {
    public static class Token {
        public MathematicaElementType type;
        public String text;
        public int start;
        public int end;

        public Token(MathematicaElementType type, String text, int start, int end) {
            this.type = type;
            this.text = text;
            this.start = start;
            this.end = end;
        }
    }

    private final ArrayList<Token> tokens = new ArrayList<>();
    private int index;
    private MathematicaParser.ImportantLineBreakHandler myImportantLineBreakHandler;

    public MathematicaLexer(String text) {
        MathematicaElementType met;
        _MathematicaLexer _ml = new _MathematicaLexer(null);
        _ml.reset(text.toCharArray(), 0, text.length(), 0);
        try {
            met = _ml.advance();
            while (met != null) {
                tokens.add(new Token(met, _ml.yytext(), _ml.getTokenStart(), _ml.getTokenEnd()));
                met = _ml.advance();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        index = 0;
    }

    public void advanceLexer() {
        index++;
        if (eof())
            index = tokens.size();
    }

    private void onSkip(MathematicaElementType type) {
        if (myImportantLineBreakHandler != null) {
            myImportantLineBreakHandler.onSkip(type);
        }
    }

    public void setImportantLineBreakHandler(final MathematicaParser.ImportantLineBreakHandler callback) {
        myImportantLineBreakHandler = callback;
    }

    private void skipWhitespace() {
        while (index < tokens.size() && MathematicaElementTypes.WHITE_SPACE_OR_COMMENTS.contains(tokens.get(index).type)) {
            onSkip(tokens.get(index).type);
            index++;
        }
    }

    public boolean eof() {
        skipWhitespace();
        return (index >= tokens.size());
    }

    public MathematicaElementType rawLookup(int i) {
        if (i + index < tokens.size() && i + index >= 0)
            return tokens.get(i + index).type;
        else
            return null;
    }

    public MathematicaElementType lookAhead(int steps) {
        if (eof()) {
            return null;
        }

        int cur = index;

        while (steps > 0) {
            ++cur;
            while (cur < tokens.size() && MathematicaElementTypes.WHITE_SPACE_OR_COMMENTS.contains(tokens.get(cur).type)) {
                cur++;
            }
            steps--;
        }
        return cur < tokens.size() ? tokens.get(cur).type : null;
    }

    public Token getToken() {
        if (!eof())
            return tokens.get(index);
        else
            return null;
    }

    public Token getPrevToken() {
        if (index > 0)
            return tokens.get(index - 1);
        else
            return null;
    }

// --Commented out by Inspection START (14.05.16 10:13):
//    public MathematicaElementType getTokenType() {
//        if (!eof())
//            return tokens.get(index).type;
//        else
//            return null;
//    }
// --Commented out by Inspection STOP (14.05.16 10:13)

// --Commented out by Inspection START (14.05.16 10:13):
//    public String getTokenText() {
//        if (!eof())
//            return tokens.get(index).text;
//        else
//            return null;
//    }
// --Commented out by Inspection STOP (14.05.16 10:13)

// --Commented out by Inspection START (14.05.16 10:13):
//    public int getTokenStart() {
//        if (!eof())
//            return tokens.get(index).start;
//        else
//            return -1;
//    }
// --Commented out by Inspection STOP (14.05.16 10:13)

// --Commented out by Inspection START (14.05.16 10:12):
//    public int getTokenEnd() {
//        if (!eof())
//            return tokens.get(index).end;
//        else
//            return -1;
//    }
// --Commented out by Inspection STOP (14.05.16 10:12)
}
