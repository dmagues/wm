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

import java.util.List;
import java.util.Objects;

/**
 * SentiWordNetSynset
 *
 * Stores a SentiWordNet synset.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SentiWordNetSynset implements Comparable<SentiWordNetSynset> {

    private String term;
    private int pos;
    private int synset;
    private String definition;
    private List<String> examples;
    private double scorePositivity;
    private double scoreNegativity;
    private double scoreObjectivity;

    public SentiWordNetSynset(String term, int pos, int synset, String definition, List<String> examples, double scorePositivity, double scoreNegativity, double scoreObjectivity) {
        this.term = term;
        this.pos = pos;
        this.synset = synset;
        this.definition = definition;
        this.examples = examples;
        this.scorePositivity = scorePositivity;
        this.scoreNegativity = scoreNegativity;
        this.scoreObjectivity = scoreObjectivity;
    }

    public int getSynset() {
        return this.synset;
    }

    public String getDefinition() {
        return this.definition;
    }

    public List<String> getExamples() {
        return this.examples;
    }

    public double getScorePositivity() {
        return this.scorePositivity;
    }

    public double getScoreNegativity() {
        return this.scoreNegativity;
    }

    public double getScoreObjectivity() {
        return this.scoreObjectivity;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SentiWordNetSynset other = (SentiWordNetSynset) obj;
        if (!Objects.equals(this.term, other.term)) {
            return false;
        }
        if (this.pos != other.pos) {
            return false;
        }
        if (this.synset != other.synset) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.term);
        hash = 31 * hash + this.pos;
        hash = 31 * hash + this.synset;
        return hash;
    }

    @Override
    public int compareTo(SentiWordNetSynset obj) {
        if( obj != null && obj instanceof SentiWordNetSynset ) {
            return this.synset - obj.synset;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        String s = "";
        s += "\t" + this.synset + ":\t" + this.definition;
        s += "\n\t\t" + this.examples;
        s += "\n\t\tpos=" + scorePositivity + "\tneg=" + scoreNegativity + "\tobj=" + scoreObjectivity;
        return s;
    }
}
