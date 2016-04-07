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
package es.uam.irg.opinion;

/**
 * OpinionEvidence
 *
 * Stores an "evidence" of certain opinion score in a given term of a sentence.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class OpinionEvidence {

    private String term;
    private int position;
    private OpinionScore score;

    public OpinionEvidence(String term, int position, OpinionScore score) {
        this.term = term;
        this.position = position;
        this.score = score;
    }

    public String getTerm() {
        return this.term;
    }

    public int getPosition() {
        return this.position;
    }

    public OpinionScore getScore() {
        return this.score;
    }

    public String toString() {
        return "OpinionEvidence{" + "term=" + term + ", position=" + position + ", score=" + score + '}';
    }
}
