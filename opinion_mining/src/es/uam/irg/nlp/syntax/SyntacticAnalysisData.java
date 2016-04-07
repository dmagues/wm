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
package es.uam.irg.nlp.syntax;

import es.uam.irg.nlp.syntax.pos.POSTags;
import es.uam.irg.nlp.syntax.pos.TaggedWord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SyntacticAnalysisData
 *
 * Stores nouns and its complements and dependences, obtained in the syntactic analysis of certain sentence.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SyntacticAnalysisData {

    private Map<Integer, TaggedWord> nouns;
    private Map<Integer, List<TaggedWord>> complements;
    private Map<Integer, List<Integer>> dependences;
    private Map<Integer, Integer> levels;

    public SyntacticAnalysisData() {
        this.nouns = new HashMap<Integer, TaggedWord>();
        this.complements = new HashMap<Integer, List<TaggedWord>>();
        this.dependences = new HashMap<Integer, List<Integer>>();
        this.levels = new HashMap<Integer, Integer>();
    }

    public int addNoun(TaggedWord taggedWord) {
        int id = this.nouns.size();
        this.nouns.put(id, taggedWord);
        return id;
    }

    public Map<Integer, TaggedWord> getNouns() {
        return this.nouns;
    }

    public TaggedWord getNoun(int nounId) {
        return this.nouns.containsKey(nounId) ? this.nouns.get(nounId) : null;
    }

    public void addComplement(int nounId, TaggedWord complement) {
        if (!this.complements.containsKey(nounId)) {
            this.complements.put(nounId, new ArrayList<TaggedWord>());
        }
        this.complements.get(nounId).add(complement);
    }

    public List<TaggedWord> getComplementsOf(int nounId) {
        if (this.complements.containsKey(nounId)) {
            return this.complements.get(nounId);
        }
        return new ArrayList<TaggedWord>();
    }

    public void addDependence(int nounId1, int nounId2) {
        if (!this.dependences.containsKey(nounId1)) {
            this.dependences.put(nounId1, new ArrayList<Integer>());
        }
        this.dependences.get(nounId1).add(nounId2);
    }

    public List<Integer> getDependencesOf(int nounId) {
        if (this.dependences.containsKey(nounId)) {
            return this.dependences.get(nounId);
        }
        return new ArrayList<Integer>();
    }

    public void setLevel(int nounId, int level) {
        this.levels.put(nounId, level);
    }

    public Integer getLevelOf(int nounId) {
        if (this.levels.containsKey(nounId)) {
            return this.levels.get(nounId);
        }
        return -1;
    }

    public String toString() {
        String s = "";

        for (int nounId = 0; nounId < this.nouns.size(); nounId++) {
            TaggedWord noun = this.nouns.get(nounId);
            List<TaggedWord> nounComplements = this.complements.get(nounId);
            List<Integer> nounDependences = this.dependences.get(nounId);

            s += "noun: " + noun + " (level: " + this.getLevelOf(nounId) + ")\n";
            s += "\tcomplements:";
            if (nounComplements != null) {
                for (TaggedWord complement : nounComplements) {
                    s += " " + complement;
                }
            }
            s += "\n";
            s += "\tcomplements (not determiners):";
            if (nounComplements != null) {
                for (TaggedWord complement : nounComplements) {
                    if (!POSTags.getTypeOfTag(complement.getTag()).equals(POSTags.TYPE_DETERMINER)) {
                        s += " " + complement;
                    }
                }
            }
            s += "\n";
            s += "\tdependences:";
            if (nounDependences != null) {
                for (int dependenceId : nounDependences) {
                    s += " " + this.getNoun(dependenceId);
                }
            }
            s += "\n";
        }

        return s;
    }
}
