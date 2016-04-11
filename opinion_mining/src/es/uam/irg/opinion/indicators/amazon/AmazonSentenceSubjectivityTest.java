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

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import es.uam.irg.dataset.amazon.AmazonReviewReader;
import es.uam.irg.dataset.amazon.review.AmazonReview;
import es.uam.irg.opinion.indicators.SentenceSubjectivity;



/**
 * AmazonSentenceSubjectivityTest
 *
 * Identifies the subjectivity scores and evidences of sentences in reviews 
 * from a JSON file of the Julian McAuley's Amazon product dataset, 
 * http://jmcauley.ucsd.edu/data/amazon.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonSentenceSubjectivityTest {
    private AmazonReviewReader reviews;
    
    public void run() {
    	
    	SentenceSubjectivity subjectivity = new SentenceSubjectivity();    	
    	
    	try {
    		Map<String, Map<String, AmazonReview>> _reviews = reviews.loadReviews(0,1);
    		for (String itemId : _reviews.keySet()) {
                Map<String, AmazonReview> itemReviews = _reviews.get(itemId);
                for (String userId : itemReviews.keySet()) {
                    System.out.println("--------------------------------------------------");
                    AmazonReview review = itemReviews.get(userId);
					System.out.println(review);
					System.out.println("--------------------------------------------------");
					
					subjectivity.setSentence(review.getText());
					subjectivity.process();
					break;					 
				 }
				 break;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
    		
    	  	
    }
    

    
    public static void main(String[] args) {
        try {
        	System.setErr(new PrintStream(new OutputStream() {  // this is to hide the meesages from CORE NLP

                public void write(int b) {
                }
            }));
            //String wordNetFolder = "./data/WordNet-2.1/dict/";
            //String sentiWordNetFile = "./data/SentiWordNet_3.0.0/SentiWordNet_3.0.0_20130122_filtered.txt";
            //String itemsFile = "./data/amazon/amazon_metadata_music.json";
            String reviewsFile = "./data/amazon/amazon_reviews_music.json";
            
            AmazonReviewReader amazonReviewReader = new AmazonReviewReader(reviewsFile);
            
            AmazonSentenceSubjectivityTest test = new AmazonSentenceSubjectivityTest();
            
            test.setReviews(amazonReviewReader);
            test.run();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }

	private void setReviews(AmazonReviewReader amazonReviewReader) {
		this.reviews=amazonReviewReader;
		
	}

}
