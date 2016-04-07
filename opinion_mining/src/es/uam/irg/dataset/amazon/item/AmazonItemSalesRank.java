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

import com.google.gson.annotations.SerializedName;

/**
 * AmazonItemSalesRank
 *
 * Stores an item "sales rank" metadata, read from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
class AmazonItemSalesRank {

    public static final String CATEGORY_BOOKS = "Books";
    public static final String CATEGORY_MOVIES = "Movies";
    public static final String CATEGORY_MUSIC = "Music";
    private Integer Books;
    private Integer Movies;
    @SerializedName("Movies & TV")
    private Integer MoviesTV;
    private Integer Music;

    public int getRank() {
        String category = this.getCategory();
        if (category != null) {
            return this.getRank(category);
        }
        return -1;
    }

    public String getCategory() {
        if (this.Books != null) {
            return CATEGORY_BOOKS;
        }
        if (this.Movies != null || this.MoviesTV != null) {
            return CATEGORY_MOVIES;
        }
        if (this.Music != null) {
            return CATEGORY_MUSIC;
        }
        return null;
    }

    public int getRank(String category) {
        int rank = -1;
        switch (category) {
            case CATEGORY_BOOKS:
                if (this.Books != null) {
                    rank = this.Books;
                }
                break;
            case CATEGORY_MOVIES:
                if (this.Movies != null) {
                    rank = this.Movies;
                }
                if (this.MoviesTV != null) {
                    rank = this.MoviesTV;
                }
                break;
            case CATEGORY_MUSIC:
                if (this.Music != null) {
                    rank = this.Music;
                }
                break;
        }
        return rank;
    }

    public String toString() {
        String s = "";

        int rank = this.getRank();
        if (rank != -1) {
            s += rank;
        }

        return s;
    }
}
