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
package es.uam.irg.nlp.syntax;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.*;
import es.uam.irg.dataset.amazon.item.AmazonItem;
import es.uam.irg.dataset.amazon.review.AmazonReview;
import es.uam.irg.nlp.syntax.treebank.SyntacticTreebank;
import es.uam.irg.dataset.amazon.AmazonItemReader;
import es.uam.irg.dataset.amazon.AmazonReviewReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * SyntacticAnalyzer
 *
 * Performs a syntactic analysis on the sentences of a given text, storing their syntactic treebanks, 
 * and their nouns and their complements and dependences.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SyntacticAnalyzer {

    public static List<SyntacticallyAnalyzedSentence> analyzeSentences(String text) throws Exception {
        List<SyntacticallyAnalyzedSentence> _sentences = new ArrayList<SyntacticallyAnalyzedSentence>();

        Properties props = new Properties();
        //props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
        props.put("annotators", "tokenize, ssplit, pos, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        
        text = text.replace("...", ".");

        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        List<CoreMap> analyzedSentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        if (analyzedSentences != null && !analyzedSentences.isEmpty()) {
            for (CoreMap analyzedSentence : analyzedSentences) {
                String sentence = analyzedSentence.toString();

                Tree tree = analyzedSentence.get(TreeCoreAnnotations.TreeAnnotation.class);
                String treeDescription = tree.skipRoot().pennString();
                SyntacticTreebank treebank = new SyntacticTreebank(treeDescription, true);

                SyntacticallyAnalyzedSentence _sentence = new SyntacticallyAnalyzedSentence(sentence, treebank);
                _sentences.add(_sentence);
            }
        }
        return _sentences;
    }

    public static void main(String[] args) {
        try {
            String DATA_PATH = "./data/amazon/amazon_reviews_music.json";

            System.setErr(new PrintStream(new OutputStream() {  // this is to hide the meesages from CORE NLP

                public void write(int b) {
                }
            }));

            List<SyntacticallyAnalyzedSentence> analyzedSentences = null;

            System.out.println("==================================================");
            System.out.println("ITEM REVIEWS");
            System.out.println("==================================================");
            AmazonReviewReader amazonReviewReader = new AmazonReviewReader(DATA_PATH);
            //Map<String, Map<String, AmazonReview>> reviews = amazonReviewReader.loadReviews();  // load all reviews
            Map<String, Map<String, AmazonReview>> reviews = amazonReviewReader.loadReviews(0, 5);
            for (String itemId : reviews.keySet()) {
                Map<String, AmazonReview> itemReviews = reviews.get(itemId);
                for (String userId : itemReviews.keySet()) {
                    System.out.println("--------------------------------------------------");
                    AmazonReview review = itemReviews.get(userId);
                    System.out.println(review);

                    System.out.println("# summary");
                    analyzedSentences = SyntacticAnalyzer.analyzeSentences(review.getSummary());
                    for (SyntacticallyAnalyzedSentence analyzedSentence : analyzedSentences) {
                        System.out.println(analyzedSentence.getTreebank());
                        System.out.println(analyzedSentence.getAnalysisData());
                    }

                    System.out.println("# text");
                    analyzedSentences = SyntacticAnalyzer.analyzeSentences(review.getText());
                    for (SyntacticallyAnalyzedSentence analyzedSentence : analyzedSentences) {
                        System.out.println(analyzedSentence.getTreebank());
                        System.out.println(analyzedSentence.getAnalysisData());
                    }
                }
            }

            System.out.println("==================================================");
            System.out.println("ITEM METADATA");
            System.out.println("==================================================");
            AmazonItemReader amazonItemReader = new AmazonItemReader(DATA_PATH);
            //Map<String, AmazonItem> items = amazonItemReader.loadItems();  // load all items
            Map<String, AmazonItem> items = amazonItemReader.loadItems(0, 5);
            for (String itemId : items.keySet()) {
                System.out.println("--------------------------------------------------");
                AmazonItem item = items.get(itemId);
                System.out.println(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
