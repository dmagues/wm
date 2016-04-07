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
package es.uam.irg.opinion.sentiwordnet;

import es.uam.irg.nlp.wordnet.WordNet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SentiWordNet
 *
 * Provides a number of functionalities to access a local SentiWordNet lexicon.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SentiWordNet {

    public static final String DB_PATH = "./data/SentiWordNet_3.0.0/SentiWordNet_3.0.0_20130122.txt";
    public static final String DB_PATH_FILTERED = "./data/SentiWordNet_3.0.0/SentiWordNet_3.0.0_20130122_filtered.txt";
    public static final String DB_PATH_TINY = "./data/SentiWordNet_3.0.0/SentiWordNet_3.0.0_20130122_tiny.txt";
    private static final String POS_ADJECTIVE = "a";
    private static final String POS_NOUN = "n";
    private static final String POS_VERB = "v";
    private static final String POS_ADVERBE = "r";
    private List<SentiWordNetTerm> database;

    public SentiWordNet() throws Exception {
        this(DB_PATH);
    }

    public SentiWordNet(String dbPath) throws Exception {
        System.err.print("Loading SentiWordNet... ");
        
        this.database = new ArrayList<SentiWordNetTerm>();

        BufferedReader reader = new BufferedReader(new FileReader(dbPath));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String _pos = tokenizer.nextToken();
            int pos = 0;
            if (_pos.equals(POS_NOUN)) {
                pos = WordNet.NOUN;
            } else if (_pos.equals(POS_ADJECTIVE)) {
                pos = WordNet.ADJECTIVE;
            } else if (_pos.equals(POS_VERB)) {
                pos = WordNet.VERB;
            } else if (_pos.equals(POS_ADVERBE)) {
                pos = WordNet.ADVERB;
            }

            String id = tokenizer.nextToken();
            //System.out.println(id);

            double scorePositivity = Double.valueOf(tokenizer.nextToken());
            double scoreNegativity = Double.valueOf(tokenizer.nextToken());
            double scoreObjectivity = 1.0 - (scorePositivity + scoreNegativity);

            String term = tokenizer.nextToken();
            List<Integer> synsets = new ArrayList<Integer>();
            List<String> terms = new ArrayList<String>();
            String _terms[] = term.split(" ");
            for (int i = 0; i < _terms.length; i++) {
                int index = _terms[i].indexOf("#");
                String _term = _terms[i].substring(0, index);
                terms.add(_term);
                int _synset = Integer.valueOf(_terms[i].substring(index + 1)) - 1;
                synsets.add(_synset);
            }

            String gloss = tokenizer.nextToken();
            StringTokenizer tokenizer2 = new StringTokenizer(gloss, ";");
            String definition = tokenizer2.nextToken().trim();
            List<String> examples = new ArrayList<String>();
            while (tokenizer2.hasMoreTokens()) {
                examples.add(tokenizer2.nextToken().replace("\"", "").trim());
            }

            for (int i = 0; i < synsets.size(); i++) {
                int _synset = synsets.get(i);
                String _term = terms.get(i);

                SentiWordNetTerm sentiTerm = new SentiWordNetTerm(_term, pos);
                SentiWordNetSynset sentiSynset = new SentiWordNetSynset(_term, pos, _synset, definition, examples, scorePositivity, scoreNegativity, scoreObjectivity);

                if (!this.database.contains(sentiTerm)) {
                    this.database.add(sentiTerm);
                }
                int index = this.database.indexOf(sentiTerm);
                this.database.get(index).addSynset(sentiSynset);
            }
        }
        reader.close();
        
        System.err.println("[OK]");
    }

    public List<SentiWordNetSynset> getSynsets(String term, int pos) {
        SentiWordNetTerm sentiTerm = new SentiWordNetTerm(term, pos);
        if (this.database.contains(sentiTerm)) {
            int index = this.database.indexOf(sentiTerm);
            return this.database.get(index).getSynsets();
        }
        return new ArrayList<SentiWordNetSynset>();
    }

    public static void main(String[] args) {
        try {
            SentiWordNet sentiWordNet = new SentiWordNet(SentiWordNet.DB_PATH_FILTERED);
            List<SentiWordNetSynset> sentiSynsets = sentiWordNet.getSynsets("funny", WordNet.ADJECTIVE);
            //List<SentiWordNetSynset> sentiSynsets = sentiWordNet.getSynsets("superfine", WordNet.ADJECTIVE);
            for (SentiWordNetSynset sentiSynset : sentiSynsets) {
                System.out.println(sentiSynset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
