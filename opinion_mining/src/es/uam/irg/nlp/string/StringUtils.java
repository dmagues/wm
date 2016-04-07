/**
 * Copyright 2016 
 * Ivan Cantador
 * Information Retrieval Group at Universidad Autonoma de Madrid
 *
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * the current software. If not, see <http://www.gnu.org/licenses/>.
 */
package es.uam.irg.nlp.string;

import java.util.ArrayList;
import java.util.List;

/**
 * StringUtils
 *
 * Provides string processing utilities.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class StringUtils {
    // Private attributes

    private static final String PLAIN_ASCII =
            "AaEeIiOoUu" // grave
            + "AaEeIiOoUuYy" // acute
            + "AaEeIiOoUuYy" // circumflex
            + "AaEeIiOoUuYy" // tilde
            + "AaEeIiOoUuYy" // umlaut
            + "Aa" // ring
            + "Cc" // cedilla
            ;
    private static final String UNI_CODE =
            "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9" // grave
            + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD" // acute
            + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177" // circumflex
            + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177" // tilde
            + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF" // umlaut
            + "\u00C5\u00E5" // ring
            + "\u00C7\u00E7" // cedilla
            ;

    /**
     * Converts a non ASCII string to an ASCII string.
     *
     * @param s - the string which characters are going to be converted
     *
     * @return a string which all its characters are ASCII
     */
    public static String toASCII(String s) {
        StringBuffer sb = new StringBuffer();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int pos = UNI_CODE.indexOf(c);
            if (pos > -1) {
                sb.append(PLAIN_ASCII.charAt(pos));
            } else {
                if ((int) c < 256) {
                    sb.append(c);
                } else {
                    sb.append("?");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Computes the Levensthein distance between two given strings.
     *
     * @param s - the first string
     * @param t - the second string
     *
     * @return the Levensthein distance between s and t
     */
    public static int levenshteinDistance(String s, String t) {
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1    
        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);

            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);

                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // Step 6
                d[i][j] = minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
        }

        // Step 7
        return d[n][m];
    }

    private static int minimum(int a, int b, int c) {
        int mi;

        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }

        return mi;
    }

    /**
     * Splits a given string into tokens separated by ' ' or '_', and according 
     * to the Camel case notation.
     *
     * @param s - the string to split into tokens
     *
     * @return the list of tokens in the input string
     */
    public static List<String> tokenize(String s) {
        String currentToken = "";
        List<String> tokens = new ArrayList<String>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ' || c == '_') {
                tokens.add(currentToken);
                currentToken = "";
                continue;
            }

            if (Character.isDigit(c)) {
                continue;
            }

            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
                if (currentToken.isEmpty()) {
                    currentToken += c;
                } else {
                    tokens.add(currentToken);
                    currentToken = "" + c;
                }
            } else {
                currentToken += c;
            }
        }
        tokens.add(currentToken);
        return tokens;
    }
}
