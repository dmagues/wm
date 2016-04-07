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
 * OpinionScore
 *
 * Stores an opinion score, composed of particular subjectivity, positivity and negativity degrees.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class OpinionScore {

    private float subjectivity;
    private float positivity;
    private float negativity;

    public OpinionScore(float subjectivity, float positivity, float negativity) {
        this.subjectivity = subjectivity;
        this.positivity = positivity;
        this.negativity = negativity;
    }

    public float getSubjectivity() {
        return this.subjectivity;
    }

    public float getPositivity() {
        return this.positivity;
    }

    public float getNegativity() {
        return this.negativity;
    }

    public String toString() {
        return "OpinionScore{" + "subjectivity=" + subjectivity + ", positivity=" + positivity + ", negativity=" + negativity + '}';
    }
}
