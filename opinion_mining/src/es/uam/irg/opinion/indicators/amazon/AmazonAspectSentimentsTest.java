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
import es.uam.irg.opinion.indicators.AspectSentiments;

/**
 * AmazonAspectSentimentsTest
 *
 * Identifies the opinion scores and evidences of a number of item aspects  
 * in reviews from a JSON file of the Julian McAuley's Amazon product dataset, 
 * http://jmcauley.ucsd.edu/data/amazon.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 */
public class AmazonAspectSentimentsTest {
	private AmazonReviewReader reviews;

    public void run() throws Exception {
    	
    	AspectSentiments aspects = new AspectSentiments();    	
    	
    	Map<String, Map<String, AmazonReview>> _reviews = reviews.loadReviews(0,10);
		for (String itemId : _reviews.keySet()) {
		    Map<String, AmazonReview> itemReviews = _reviews.get(itemId);
		    for (String userId : itemReviews.keySet()) {
		        System.out.println("--------------------------------------------------");
		        AmazonReview review = itemReviews.get(userId);
				System.out.println(review);
				System.out.println("--------------------------------------------------");
								
				aspects.setDocumentText(review.getText());				
				aspects.process();
				System.out.println(aspects);
		        		 
			 }			
		} 
    }

    public static void main(String[] args) throws Exception {
    	System.setErr(new PrintStream(new OutputStream() {  // this is to hide the meesages from CORE NLP

 		    public void write(int b) {
 		    }
 		}));
    	 
        String reviewsFile = "./data/amazon/amazon_reviews_music.json";
        AmazonReviewReader amazonReviewReader = new AmazonReviewReader(reviewsFile);
        
        AmazonAspectSentimentsTest test = new AmazonAspectSentimentsTest();
        test.setReviews(amazonReviewReader);
        test.run();
    }
    
	public void setReviews(AmazonReviewReader reviewsFile) {
		this.reviews=reviewsFile;
		
	}
}
