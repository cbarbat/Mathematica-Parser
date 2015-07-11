/*
 * Copyright (c) 2015 Calin Barbat
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

import de.cbarbat.mathematica.lexer.MathematicaLexer;
import de.cbarbat.mathematica.parser.MathematicaParser;

import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        String str;
        /* for the lexer test below:
        String tokenType, tokenText;
        int tokenStart, tokenEnd;
        MathematicaLexer.Token token;
        */
        MathematicaLexer ml;
        MathematicaParser mp;

        for (String arg : args) {
            System.out.println();
            System.out.println("Processing file: " + arg);

            try {
                FileReader fr = new FileReader(arg);
                str = "";

                // Read characters
                int j;
                while ((j = fr.read()) != -1) {
                    str += (char) j;
                }

                fr.close();
                //System.out.println(str);

                ml = new MathematicaLexer(str);
                mp = new MathematicaParser(ml);
                mp.parse();

                /* The following commented code is a lexer test:
                int la = 0;
                token = ml.lookAhead(la);
                while (token != null) {
                    tokenType = token.type.myType;
                    tokenText = token.text;
                    tokenStart = token.start;
                    tokenEnd = token.end;
                    System.out.println(tokenType + ":" + tokenStart + ":" + tokenEnd + ":" + tokenText);
                    la++;
                    token = ml.lookAhead(la);
                }

                while (!ml.eof()) {
                    tokenType = ml.getTokenType().myType;
                    tokenText = ml.getTokenText();
                    tokenStart = ml.getTokenStart();
                    tokenEnd = ml.getTokenEnd();
                    System.out.println(tokenType + ":" + tokenStart + ":" + tokenEnd + ":" + tokenText);
                    ml.advanceLexer();
                }
                */
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.toString());
            }
        }
    }
}
