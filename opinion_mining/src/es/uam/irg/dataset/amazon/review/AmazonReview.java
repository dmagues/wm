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
package es.uam.irg.dataset.amazon.review;

import java.util.StringTokenizer;

/**
 * AmazonReview
 *
 * Stores an item review, read from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonReview {

    private String asin;
    private String reviewerID;
    private String reviewerName;
    private Float overall;
    private String reviewText;
    private String summary;
    private Integer[] helpful;
    private Long unixReviewTime;
    private String reviewTime;

    public String getItemId() {
        return this.asin;
    }

    public String getUserId() {
        return this.reviewerID;
    }

    public String getUserName() {
        return this.reviewerName;
    }

    public float getRating() {
        return this.overall;
    }

    public String getText() {
        return this.reviewText;
    }

    public String getSummary() {
        return this.summary;
    }

    public int getHelpfulnessPositiveVotes() {
        if (this.helpful != null) {
            return this.helpful[0];
        }
        return 0;
    }

    public int getHelpfulnessNegativeVotes() {
        if (this.helpful != null) {
            return this.helpful[1] - this.helpful[0];
        }
        return 0;
    }

    public float getHelpfulnessRatio() {
        if (this.helpful != null) {
            return (float) this.helpful[0] / this.helpful[1];
        }
        return 0;
    }

    public int getHelpfulnessPopularity() {
        if (this.helpful != null) {
            return this.helpful[1];
        }
        return 0;
    }

    public long getTimestamp() {
        return unixReviewTime;
    }

    public String getTimestampDay() {
        StringTokenizer tokenizer = new StringTokenizer(this.reviewTime, " ,");
        String month = tokenizer.nextToken();
        String day = tokenizer.nextToken();
        String year = tokenizer.nextToken();
        return day;
    }

    public String getTimestampMonth() {
        StringTokenizer tokenizer = new StringTokenizer(this.reviewTime, " ,");
        String month = tokenizer.nextToken();
        String day = tokenizer.nextToken();
        String year = tokenizer.nextToken();
        return month;
    }

    public String getTimestampYear() {
        StringTokenizer tokenizer = new StringTokenizer(this.reviewTime, " ,");
        String month = tokenizer.nextToken();
        String day = tokenizer.nextToken();
        String year = tokenizer.nextToken();
        return year;
    }

    public String toString() {
        String s = "";

        s += "item: " + this.getItemId() + " user: " + this.getUserId() + " (" + this.getUserName() + ")";
        s += "\n\trating: " + this.getRating();
        s += "\n\ttext: " + this.getText();
        s += "\n\tsummary: " + this.getSummary();
        s += "\n\thelpfulness: " + this.getHelpfulnessRatio() + " (" + this.getHelpfulnessPopularity() + ") [pos=" + this.getHelpfulnessPositiveVotes() + ", neg=" + this.getHelpfulnessNegativeVotes() + "]";
        s += "\n\tdate (yyyy/mm/dd): " + this.getTimestampYear() + "/" + this.getTimestampMonth() + "/" + this.getTimestampDay();

        return s;
    }
}
