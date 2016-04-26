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
import java.util.List;
import java.util.Map;
import es.uam.irg.dataset.amazon.AmazonReviewReader;
import es.uam.irg.dataset.amazon.review.AmazonReview;
import es.uam.irg.nlp.syntax.SyntacticAnalyzer;
import es.uam.irg.nlp.syntax.SyntacticallyAnalyzedSentence;
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
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 * 
 */
public class AmazonSentenceSubjectivityTest {
    private AmazonReviewReader reviews;
    
    public void run() throws Exception {
    	
    	SentenceSubjectivity subjectivity = new SentenceSubjectivity();    	
    	
    	Map<String, Map<String, AmazonReview>> _reviews = reviews.loadReviews(0,10);
		for (String itemId : _reviews.keySet()) {
		    Map<String, AmazonReview> itemReviews = _reviews.get(itemId);
		    for (String userId : itemReviews.keySet()) {
		        System.out.println("--------------------------------------------------");
		        AmazonReview review = itemReviews.get(userId);
				System.out.println(review);
				System.out.println("--------------------------------------------------");
								
				List<SyntacticallyAnalyzedSentence> analized;		        
		        analized = SyntacticAnalyzer.analyzeSentences(review.getText());				
		        
		        for(SyntacticallyAnalyzedSentence a:analized)
		        {
		        	subjectivity.setSentence(a.getSentence());
					subjectivity.process(a);
					System.out.println(subjectivity);
		        }		 
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
		
		AmazonSentenceSubjectivityTest test = new AmazonSentenceSubjectivityTest();
		
		test.setReviews(amazonReviewReader);
		test.run();
    }

	public void setReviews(AmazonReviewReader amazonReviewReader) {
		this.reviews=amazonReviewReader;
		
	}

}
