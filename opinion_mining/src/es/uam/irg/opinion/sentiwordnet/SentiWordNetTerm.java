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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * SentiWordNetSynset
 *
 * Stores the SentiWordNet synset of a particular term.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SentiWordNetTerm {

    private String term;
    private int pos;
    private List<SentiWordNetSynset> synsets;

    public SentiWordNetTerm(String term, int pos) {
        this.term = term;
        this.pos = pos;
        this.synsets = new ArrayList<SentiWordNetSynset>();
    }

    public void addSynset(SentiWordNetSynset synset) {
        if (synset != null && !this.synsets.contains(synset)) {
            this.synsets.add(synset);
            Collections.sort(this.synsets);
        }
    }

    public String getTerm() {
        return this.term;
    }

    public int getPOS() {
        return this.pos;
    }

    public List<SentiWordNetSynset> getSynsets() {
        return this.synsets;
    }

    public boolean containsSynset(SentiWordNetSynset synset) {
        if (synset != null) {
            return this.synsets.contains(synset);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SentiWordNetTerm other = (SentiWordNetTerm) obj;
        if (!Objects.equals(this.term, other.term)) {
            return false;
        }
        if (this.pos != other.pos) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.term);
        hash = 89 * hash + this.pos;
        return hash;
    }

    @Override
    public String toString() {
        String s = "";
        s += this.term + "\t" + WordNet.typeToString(this.pos);
        for (SentiWordNetSynset synset : this.synsets) {
            s += "\n" + synset;
        }
        return s;
    }
}
