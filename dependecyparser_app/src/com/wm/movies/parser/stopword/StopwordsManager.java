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
package com.wm.movies.parser.stopword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * StopwordsManager
 *
 * Manages the stopwords appearing in provided terms.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class StopwordsManager {
    // Private attributes

    private HashMap<String, String> words1;
    private HashMap<String, String> words2;
    private HashMap<String, String> words3;

    // ============
    // CONSTRUCTORS
    // ============
    /**
     * Creates a new instance of StopwordsManager
     * 
     * @param dictionaryFilePath the absolute path of the file which contains the stop words managed by this module
     */
    public StopwordsManager(String dictionaryFilePath) throws Exception {
        this.words1 = new HashMap<String, String>();
        this.words2 = new HashMap<String, String>();
        this.words3 = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String term = line.trim().toLowerCase();
            int numTokens = term.split(" ").length;

            switch (numTokens) {
                case 1:
                    this.words1.put(term, null);
                    break;
                case 2:
                    this.words2.put(term, null);
                    break;
                case 3:
                    this.words3.put(term, null);
                    break;
            }
        }
        reader.close();
    }

    // =======================
    // GENERAL PURPOSE METHODS
    // =======================
    /**
     * Removes stop words from the given term
     *
     * @param term the term to be filtered
     * 
     * @return the term without stop words
     */
    public String filter(String term) {
        if (term == null) {
            return null;
        }
        term = term.trim().toLowerCase();

        String tokens[] = term.split(" ");
        int numTokens = tokens.length;

        if (numTokens >= 3) {
            for (int i = 0; i <= numTokens - 3; i++) {
                String subTerm = tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2];
                if (this.words3.containsKey(subTerm)) {
                    String newTerm = "";
                    for (int j = 0; j < i; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    for (int j = i + 3; j < numTokens; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    newTerm = newTerm.trim();
                    return this.filter(newTerm);
                }
            }
        }

        if (numTokens >= 2) {
            for (int i = 0; i <= numTokens - 2; i++) {
                String subTerm = tokens[i] + " " + tokens[i + 1];
                if (this.words2.containsKey(subTerm)) {
                    String newTerm = "";
                    for (int j = 0; j < i; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    for (int j = i + 2; j < numTokens; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    newTerm = newTerm.trim();
                    return this.filter(newTerm);
                }
            }
        }

        if (numTokens >= 1) {
            for (int i = 0; i <= numTokens - 1; i++) {
                String subTerm = tokens[i];
                if (this.words1.containsKey(subTerm)) {
                    String newTerm = "";
                    for (int j = 0; j < i; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    for (int j = i + 1; j < numTokens; j++) {
                        newTerm += tokens[j] + " ";
                    }
                    newTerm = newTerm.trim();
                    return this.filter(newTerm);
                }
            }
        }

        return term;
    }

    /**
     * Determines whether the given term is a stop word
     *
     * @param term the term to evaluate
     * 
     * @return a boolean value indicating whether the term is a stop word
     */
    public boolean isStopWord(String term) {
        term = term.trim().toLowerCase();
        return this.words1.containsKey(term) || this.words2.containsKey(term) || this.words3.containsKey(term);
    }

    /**
     * Returns a list of pairs <position, lenght> with the positions and lengths of all the stop words of the given term
     *
     * @param term the term from which the stop words position/lenghts have to be obtained
     * 
     * @return a list of pairs <position, lenght> of the stop words
     */
    public ArrayList<int[]> getIndexes(String term) {
        if (term == null) {
            return null;
        }
        term = term.trim().toLowerCase();

        String tokens[] = term.split(" ");
        int numTokens = tokens.length;

        ArrayList<int[]> indexes = new ArrayList<int[]>();

        for (int i = 0; i < numTokens;) {
            if (numTokens - i >= 3) {
                String subTerm = tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2];
                if (this.words3.containsKey(subTerm)) {
                    int index[] = new int[2];
                    index[0] = i;
                    index[1] = 3;
                    indexes.add(index);
                    i += 3;
                    continue;
                }
            }

            if (numTokens - i >= 2) {
                String subTerm = tokens[i] + " " + tokens[i + 1];
                if (this.words2.containsKey(subTerm)) {
                    int index[] = new int[2];
                    index[0] = i;
                    index[1] = 2;
                    indexes.add(index);
                    i += 2;
                    continue;
                }
            }

            if (numTokens - i >= 1) {
                String subTerm = tokens[i];
                if (this.words1.containsKey(subTerm)) {
                    int index[] = new int[2];
                    index[0] = i;
                    index[1] = 1;
                    indexes.add(index);
                    i += 1;
                    continue;
                }
            }

            i++;
        }

        return indexes;
    }

    /**
     * Convert into lower case the stopswords of a given term
     *
     * @param term the term from which the stop words have to be converted into lower case
     * 
     * @return the filtered term
     */
    public String toLowerCase(String term) {
        if (term == null) {
            return null;
        }

        String tokens[] = term.trim().split(" ");
        int numTokens = tokens.length;

        ArrayList<int[]> indexes = this.getIndexes(term);
        if (indexes != null) {
            for (int i = 0; i < indexes.size(); i++) {
                int[] index = indexes.get(i);

                for (int j = 0; j < index[1]; j++) {
                    tokens[index[0] + j] = tokens[index[0] + j].toLowerCase();
                }
            }
        }

        term = "";
        for (int i = 0; i < numTokens; i++) {
            term += tokens[i] + " ";
        }

        return term.trim();
    }
}
