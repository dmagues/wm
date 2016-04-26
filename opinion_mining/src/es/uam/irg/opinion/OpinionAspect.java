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

import java.util.List;
import java.util.Objects;

/**
 * OpinionAspect
 *
 * Stores opinion-based data of an item aspect.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 * 
 */
public class OpinionAspect {
    private String name;
    private List<String> labels;
    private List<String> positiveValues;
    private List<String> negativeValues;

    public OpinionAspect(String name, List<String> labels, List<String> positiveValues, List<String> negativeValues) {
        this.name = name;
        this.labels = labels;
        this.positiveValues = positiveValues;
        this.negativeValues = negativeValues;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getLabels() {
        return this.labels;
    }


    public List<String> getNegativeValues() {
        return this.negativeValues;
    }

    public List<String> getPositiveValues() {
        return this.positiveValues;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OpinionAspect other = (OpinionAspect) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    public String toString() {
        return "OpinionAspect{" + "name=" + name + ", labels=" + labels + ", positiveValues=" + positiveValues + ", negativeValues=" + negativeValues + '}';
    }
}
