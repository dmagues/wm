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
package es.uam.irg.opinion.indicators.amazon;

import es.uam.irg.dataset.amazon.AmazonItemReader;
import es.uam.irg.dataset.amazon.AmazonReviewReader;
import es.uam.irg.nlp.wordnet.WordNet;
import es.uam.irg.opinion.sentiwordnet.SentiWordNet;

/**
 * AmazonDocumentSentimentTest
 *
 * Identifies the opinion scores and evidences of reviews from a JSON file 
 * of the Julian McAuley's Amazon product dataset, 
 * http://jmcauley.ucsd.edu/data/amazon.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonDocumentSentimentTest {
    
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        try {
            String wordNetFolder = "./data/WordNet-2.1/dict/";
            String sentiWordNetFile = "./data/SentiWordNet_3.0.0/SentiWordNet_3.0.0_20130122_filtered.txt";
            String itemsFile = "./data/amazon/amazon_metadata_music.json";
            String reviewsFile = "./data/amazon/amazon_reviews_music.json";

//            WordNet wordNet = new WordNet(wordNetFolder);
//            SentiWordNet sentiWordNet = new SentiWordNet(sentiWordNetFile);
//            AmazonItemReader amazonItemReader = new AmazonItemReader(itemsFile);
//            AmazonReviewReader amazonReviewReader = new AmazonReviewReader(reviewsFile);

            AmazonDocumentSentimentTest test = new AmazonDocumentSentimentTest();
            test.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
