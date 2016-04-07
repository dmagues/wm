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
package es.uam.irg.opinion.indicators;

import es.uam.irg.opinion.OpinionEvidence;
import es.uam.irg.opinion.OpinionScore;
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentSentiment
 *
 * Identifies the global opinion score and evidences of the sentences in a document text.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class DocumentSentiment {

    private String id;
    private OpinionScore score;
    private List<List<OpinionEvidence>> evidences;

    public DocumentSentiment(String documentId, String documentText) {
        this.id = documentId;
        this.evidences = new ArrayList<List<OpinionEvidence>>();

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return this.id;
    }

    public OpinionScore getScore() {
        return this.score;
    }

    public List<List<OpinionEvidence>> getEvidences() {
        return this.evidences;
    }

    public String toString() {
        String s = "Sentiment of document: " + this.id + "\n";
        s += "\tscore: " + this.score + "\n";
        int sentence = 1;
        for (List<OpinionEvidence> sentenceEvidences : this.evidences) {
            s += "\t\tsentence: " + sentence + "\n";
            s += "\t\t\tevidences:\n";
            for (OpinionEvidence evidence : sentenceEvidences) {
                s += "\t\t\t\t" + evidence + "\n";
            }
            sentence++;
        }
        return s;
    }
}
