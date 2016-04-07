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
package es.uam.irg.dataset.amazon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.uam.irg.dataset.amazon.review.AmazonReview;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * AmazonItemReader
 *
 * Reads item reviews from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonReviewReader {

    public static final String DATA_PATH = "./data/amazon/amazon_reviews_music.json";
    private String file;

    public AmazonReviewReader(String file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("Null file");
        }
        this.file = file;
    }

    // <itemId, <userId, review>>
    public Map<String, Map<String, AmazonReview>> loadReviews() throws Exception {
        Map<String, Map<String, AmazonReview>> reviews = new HashMap<String, Map<String, AmazonReview>>();

        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonReview review = gson.fromJson(line, AmazonReview.class);
            String userId = review.getUserId();
            String itemId = review.getItemId();

            if (!reviews.containsKey(itemId)) {
                reviews.put(itemId, new HashMap<String, AmazonReview>());
            }
            reviews.get(itemId).put(userId, review);
        }
        reader.close();

        return reviews;
    }

    // firstIndex = inclusive
    // lastIndex = exclusive
    // <itemId, <userId, review>>
    public Map<String, Map<String, AmazonReview>> loadReviews(int firstIndex, int lastIndex) throws Exception {
        Map<String, Map<String, AmazonReview>> reviews = new HashMap<String, Map<String, AmazonReview>>();

        Gson gson = new GsonBuilder().create();

        int i = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonReview review = gson.fromJson(line, AmazonReview.class);
            String userId = review.getUserId();
            String itemId = review.getItemId();
            i++;

            if (firstIndex <= i && i < lastIndex) {
                if (!reviews.containsKey(itemId)) {
                    reviews.put(itemId, new HashMap<String, AmazonReview>());
                }
                reviews.get(itemId).put(userId, review);
            }
            if (i == lastIndex - 1) {
                break;
            }
        }
        reader.close();

        return reviews;
    }

    // <userId, review>
    public Map<String, AmazonReview> loadReviewsOfItem(String id) throws Exception {
        Map<String, AmazonReview> reviews = new HashMap<String, AmazonReview>();

        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonReview review = gson.fromJson(line, AmazonReview.class);
            String userId = review.getUserId();
            String itemId = review.getItemId();

            if (id.equals(itemId)) {
                reviews.put(userId, review);
            }
        }
        reader.close();

        return reviews;
    }

    // <itemId, review>
    public Map<String, AmazonReview> loadReviewsOfUser(String id) throws Exception {
        Map<String, AmazonReview> reviews = new HashMap<String, AmazonReview>();

        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonReview review = gson.fromJson(line, AmazonReview.class);
            String userId = review.getUserId();
            String itemId = review.getItemId();

            if (id.equals(userId)) {
                reviews.put(itemId, review);
            }
        }
        reader.close();

        return reviews;
    }
}
