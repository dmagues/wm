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
 * AmazonItem
 *
 * Stores an item metadata, read from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonItem {

    private String asin;
    private String title;
    private String[][] categories;
    private String brand;
    private String imUrl;
    private Float price;
    private AmazonItemSalesRank salesRank;
    private AmazonItemRelated related;

    public String getId() {
        return this.asin;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getCategories() {
        List<String> _categories = new ArrayList<String>();

        for (int i = 0; i < this.categories.length; i++) {
            for (int j = 0; j < this.categories[i].length; j++) {
                if (!_categories.contains(this.categories[i][j])) {
                    _categories.add(this.categories[i][j]);
                }
            }
        }

        return _categories;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getImageURL() {
        return this.imUrl;
    }

    public float getPrice() {
        return this.price;
    }

    public AmazonItemSalesRank getSalesRank() {
        return this.salesRank;
    }

    public AmazonItemRelated getRelated() {
        return this.related;
    }

    public String toString() {
        String s = "";

        s += "id: " + this.getId();
        s += "\n\t" + "title: " + this.getTitle();
        s += "\n\t" + "categories: " + this.getCategories();
        s += "\n\t" + "brand: " + this.getBrand();
        s += "\n\t" + "imageURL: " + this.getImageURL();
        s += "\n\t" + "price: " + this.getPrice();
        s += "\n\t" + "salesRank: " + this.getSalesRank();
        s += "\n\t" + "related: " + this.getRelated();

        return s;
    }
}
