/*
 * Copyright (c) 2016 Calin Barbat
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

package de.cbarbat.mathematica;

import java.io.FileReader;
import de.cbarbat.mathematica.lexer.*;
import de.cbarbat.mathematica.parser.MathematicaParser;

class Main {
    public static void main(String[] args) {
        String str;
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

                ml = new MathematicaLexer(str);
                mp = new MathematicaParser(ml);
                mp.parse();

            } catch (Exception ex) {
                System.out.println("Exception: " + ex.toString());
            }
        }
    }
}