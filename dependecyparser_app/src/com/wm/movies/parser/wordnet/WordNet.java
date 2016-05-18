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
package com.wm.movies.parser.wordnet;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WordNet
 *
 * Provides a number of functionalities to access a local WordNet dictionary.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class WordNet {

    public static final String DB_DIRECTORY = "./data/WordNet-2.1/dict/";

    public static final int NOUN = 1;
    public static final int ADJECTIVE = 2;
    public static final int VERB = 3;
    public static final int ADVERB = 4;

    private WordNetDatabase database;

    public WordNet() throws Exception {
        this(DB_DIRECTORY);
    }

    public WordNet(String dbDirectory) throws Exception {
        System.setProperty("wordnet.database.dir", dbDirectory);
        this.database = WordNetDatabase.getFileInstance();
        if (this.database == null) {
            throw new IllegalArgumentException("Invalid WordNet database directory: " + dbDirectory);
        }
    }

    public List<Synset> getSynsetsOf(String term, int type) {
        switch (type) {
            case NOUN:
                return this.getNounSynsetsOf(term);
            case ADJECTIVE:
                return this.getAdjectiveSynsetsOf(term);
            case VERB:
                return this.getVerbSynsetsOf(term);
            case ADVERB:
                return this.getAdverbSynsetsOf(term);
        }
        return new ArrayList<Synset>();
    }

    public List<Synset> getNounSynsetsOf(String term) {
        Synset[] synsets = database.getSynsets(term, SynsetType.NOUN);
        return Arrays.asList(synsets);
    }

    public List<Synset> getAdjectiveSynsetsOf(String term) {
        Synset[] synsets = database.getSynsets(term, SynsetType.ADJECTIVE);
        return Arrays.asList(synsets);
    }

    public List<Synset> getVerbSynsetsOf(String term) {
        Synset[] synsets = database.getSynsets(term, SynsetType.VERB);
        return Arrays.asList(synsets);
    }

    public List<Synset> getAdverbSynsetsOf(String term) {
        Synset[] synsets = database.getSynsets(term, SynsetType.ADVERB);
        return Arrays.asList(synsets);
    }

    public Synset getSynsetOf(String term, int type, int synsetIndex) {
        List<Synset> synsets = this.getSynsetsOf(term, type);
        if (synsetIndex >= 0 && synsetIndex < synsets.size()) {
            return synsets.get(synsetIndex);
        }
        return null;
    }

    public Synset getNounSynsetOf(String term, int synsetIndex) {
        List<Synset> synsets = this.getNounSynsetsOf(term);
        if (synsetIndex >= 0 && synsetIndex < synsets.size()) {
            return synsets.get(synsetIndex);
        }
        return null;
    }

    public Synset getAdjectiveSynsetOf(String term, int synsetIndex) {
        List<Synset> synsets = this.getAdjectiveSynsetsOf(term);
        if (synsetIndex >= 0 && synsetIndex < synsets.size()) {
            return synsets.get(synsetIndex);
        }
        return null;
    }

    public Synset getVerbSynsetOf(String term, int synsetIndex) {
        List<Synset> synsets = this.getVerbSynsetsOf(term);
        if (synsetIndex >= 0 && synsetIndex < synsets.size()) {
            return synsets.get(synsetIndex);
        }
        return null;
    }

    public Synset getAdverbSynsetOf(String term, int synsetIndex) {
        List<Synset> synsets = this.getAdverbSynsetsOf(term);
        if (synsetIndex >= 0 && synsetIndex < synsets.size()) {
            return synsets.get(synsetIndex);
        }
        return null;
    }

    public static String typeToString(int type) {
        if (type == NOUN) {
            return "noun";
        }
        if (type == ADJECTIVE) {
            return "adjective";
        }
        if (type == VERB) {
            return "verb";
        }
        if (type == ADVERB) {
            return "adverb";
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            WordNet wordnet = new WordNet();

            String noun = "revenge";
            System.out.println("Testing noun methods for '" + noun + "'.........................");
            List<Synset> synsetsNoun = wordnet.getNounSynsetsOf(noun);
            for (Synset synset : synsetsNoun) {
                String definition = WordNetSynset.getDefinitionOf(synset);
                System.out.println("d: " + definition);
                List<String> wordForms = WordNetSynset.getWordFormsOf(synset);
                System.out.println("\ts: " + wordForms);
                List<String> usageExamples = WordNetSynset.getUsageExamplesOf(synset);
                for (String usageExample : usageExamples) {
                    System.out.println("\te: " + usageExample);
                }
                List<Synset> hypernyms = WordNetSynset.getNounHypernymsOf(synset);
                for (Synset hypernym : hypernyms) {
                    System.out.println("\t+: " + WordNetSynset.getWordFormsOf(hypernym));
                }
                List<Synset> hyponyms = WordNetSynset.getNounHyponymsOf(synset);
                for (Synset hyponym : hyponyms) {
                    System.out.println("\t-: " + WordNetSynset.getWordFormsOf(hyponym));
                }
            }

            String adjective = "good";
            System.out.println("Testing adjective methods for '" + adjective + "'.........................");
            List<Synset> synsetsAdjective = wordnet.getAdjectiveSynsetsOf(adjective);
            for (Synset synset : synsetsAdjective) {
                String definition = WordNetSynset.getDefinitionOf(synset);
                System.out.println("d: " + definition);
                List<String> wordForms = WordNetSynset.getWordFormsOf(synset);
                System.out.println("\ts: " + wordForms);
                List<String> usageExamples = WordNetSynset.getUsageExamplesOf(synset);
                for (String usageExample : usageExamples) {
                    System.out.println("\te: " + usageExample);
                }
                List<String> antonyms = WordNetSynset.getAdjectiveAntonymsOf(synset);
                System.out.println("\ta: " + antonyms);
            }

            String verb = "stayed";
            System.out.println("Testing verb methods for '" + verb + "'.........................");
            List<Synset> synsetsVerb = wordnet.getVerbSynsetsOf(verb);
            for (Synset synset : synsetsVerb) {
                String definition = WordNetSynset.getDefinitionOf(synset);
                System.out.println("d: " + definition);
                List<String> wordForms = WordNetSynset.getWordFormsOf(synset);
                System.out.println("\ts: " + wordForms);
                List<String> usageExamples = WordNetSynset.getUsageExamplesOf(synset);
                for (String usageExample : usageExamples) {
                    System.out.println("\te: " + usageExample);
                }
                List<Synset> hypernyms = WordNetSynset.getVerbHypernyms(synset);
                for (Synset group : hypernyms) {
                    System.out.println("\t+: " + WordNetSynset.getWordFormsOf(group));
                }
            }

            String adverb = "carefully";
            System.out.println("Testing adverb methods for '" + adverb + "'.........................");
            List<Synset> synsetsAdverb = wordnet.getAdverbSynsetsOf(adverb);
            for (Synset synset : synsetsAdverb) {
                String definition = WordNetSynset.getDefinitionOf(synset);
                System.out.println("d: " + definition);
                List<String> wordForms = WordNetSynset.getWordFormsOf(synset);
                System.out.println("\ts: " + wordForms);
                List<String> usageExamples = WordNetSynset.getUsageExamplesOf(synset);
                for (String usageExample : usageExamples) {
                    System.out.println("\te: " + usageExample);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
