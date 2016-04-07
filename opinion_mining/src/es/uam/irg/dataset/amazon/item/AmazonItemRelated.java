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
package es.uam.irg.dataset.amazon.item;

import java.util.ArrayList;
import java.util.List;

/**
 * AmazonItemRelated
 *
 * Stores an item "related" metadata, read from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonItemRelated {

    private String[] also_bought;
    private String[] bought_together;
    private String[] also_viewed;
    private String[] buy_after_viewing;

    public List<String> getAlsoBought() {
        List<String> list = new ArrayList<String>();
        if (this.also_bought != null) {
            for (String i : this.also_bought) {
                list.add(i);
            }
        }
        return list;
    }

    public List<String> getBoughtTogether() {
        List<String> list = new ArrayList<String>();
        if (this.bought_together != null) {
            for (String i : this.bought_together) {
                list.add(i);
            }
        }
        return list;
    }

    public List<String> getAlsoViewed() {
        List<String> list = new ArrayList<String>();
        if (this.also_viewed != null) {
            for (String i : this.also_viewed) {
                list.add(i);
            }
        }
        return list;
    }

    public List<String> getBoughtAfterViewed() {
        List<String> list = new ArrayList<String>();
        if (this.buy_after_viewing != null) {
            for (String i : this.buy_after_viewing) {
                list.add(i);
            }
        }
        return list;
    }

    public String toString() {
        String s = "";

        s += "\n\t\t" + "also_bought:";
        for (String i : this.getAlsoBought()) {
            s += " " + i;
        }
        s += "\n\t\t" + "bought_together:";
        for (String i : this.getBoughtTogether()) {
            s += " " + i;
        }
        s += "\n\t\t" + "also_viewed:";
        for (String i : this.getAlsoViewed()) {
            s += " " + i;
        }
        s += "\n\t\t" + "buy_after_viewing:";
        for (String i : this.getBoughtAfterViewed()) {
            s += " " + i;
        }

        return s;
    }
}
