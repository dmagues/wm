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
package es.uam.irg.nlp.wordnet;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordSense;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WordNetSynset
 *
 * Accesses some data of a WordNet synset.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class WordNetSynset {

    public static String getDefinitionOf(Synset synset) {
        String definition = "";
        if (synset != null) {
            definition = synset.getDefinition();
        }
        return definition;
    }

    public static List<String> getUsageExamplesOf(Synset synset) {
        List<String> usageExamples = new ArrayList<String>();
        if (synset != null) {
            usageExamples = Arrays.asList(synset.getUsageExamples());
        }
        for (int i = 0; i < usageExamples.size(); i++) {
            String example = usageExamples.get(i);
            example = example.replace("\"", "").trim();
            usageExamples.set(i, example);
        }
        return usageExamples;
    }

    public static List<String> getWordFormsOf(Synset synset) {
        List<String> wordForms = new ArrayList<String>();
        if (synset != null) {
            wordForms = Arrays.asList(synset.getWordForms());
        }
        return wordForms;
    }

    public static List<Synset> getNounHypernymsOf(Synset synset) {
        List<Synset> hypernyms = new ArrayList<Synset>();
        if (synset != null && synset.getType().equals(SynsetType.NOUN)) {
            NounSynset _synset = (NounSynset) synset;
            NounSynset[] _hypernyms = _synset.getHypernyms();
            hypernyms = Arrays.asList((Synset[]) _hypernyms);
        }
        return hypernyms;
    }

    public static List<Synset> getNounHyponymsOf(Synset synset) {
        List<Synset> hyponyms = new ArrayList<Synset>();
        if (synset != null && synset.getType().equals(SynsetType.NOUN)) {
            NounSynset _synset = (NounSynset) synset;
            NounSynset[] _hyponyms = _synset.getHyponyms();
            hyponyms = Arrays.asList((Synset[]) _hyponyms);
        }
        return hyponyms;
    }

    public static List<String> getAdjectiveAntonymsOf(Synset synset) {
        List<String> antonyms = new ArrayList<String>();
        if (synset != null && synset.getType().equals(SynsetType.ADJECTIVE)) {
            List<String> wordForms = getWordFormsOf(synset);
            for (String wordForm : wordForms) {
                WordSense[] _antonyms = synset.getAntonyms(wordForm);
                for (WordSense _antonym : _antonyms) {
                    String antonym = _antonym.getWordForm();
                    if (!antonyms.contains(antonym)) {
                        antonyms.add(antonym);
                    }
                }
            }
        }
        return antonyms;
    }

    public static List<Synset> getVerbHypernyms(Synset synset) {
        List<Synset> hypernyms = new ArrayList<Synset>();
        if (synset != null && synset.getType().equals(SynsetType.VERB)) {
            VerbSynset _synset = (VerbSynset) synset;
            VerbSynset[] _hypernyms = _synset.getHypernyms();
            hypernyms = Arrays.asList((Synset[]) _hypernyms);
        }
        return hypernyms;
    }

    public static int getTypeOf(Synset synset) {
        if (synset != null) {
            SynsetType type = synset.getType();
            if (type.equals(SynsetType.NOUN)) {
                return WordNet.NOUN;
            }
            if (type.equals(SynsetType.ADJECTIVE)) {
                return WordNet.ADJECTIVE;
            }
            if (type.equals(SynsetType.VERB)) {
                return WordNet.VERB;
            }
            if (type.equals(SynsetType.ADVERB)) {
                return WordNet.ADVERB;
            }
        }
        return -1;
    }
}
