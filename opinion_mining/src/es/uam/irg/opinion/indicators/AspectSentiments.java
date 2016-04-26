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

import es.uam.irg.nlp.syntax.SyntacticAnalyzer;
import es.uam.irg.nlp.syntax.SyntacticallyAnalyzedSentence;
import es.uam.irg.opinion.OpinionAnalyzer;
import es.uam.irg.opinion.OpinionAspect;
import es.uam.irg.opinion.OpinionEvidence;
import es.uam.irg.opinion.OpinionScore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AspectSentiments
 *
 * Identifies the opinion scores and evidences of a number of item aspects in a document text.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 *   
 */
public class AspectSentiments {

    private Map<OpinionAspect, OpinionScore> scores;
    private Map<OpinionAspect, List<OpinionEvidence>> evidences;
    private String documentText;
    private OpinionAnalyzer analizer;
    
    public String getDocumentText() {
		return documentText;
	}

	public void setDocumentText(String documentText) {
		this.documentText = documentText;
	}

	

    public AspectSentiments() throws Exception {
        
        this.analizer = new OpinionAnalyzer();
    }
    
    public AspectSentiments(String documentText, List<OpinionAspect> aspects) throws Exception {
        this.scores = new HashMap<OpinionAspect, OpinionScore>();
        this.evidences = new HashMap<OpinionAspect, List<OpinionEvidence>>();
        this.documentText = documentText;
        this.analizer = new OpinionAnalyzer();
    }
    
    public void process() throws Exception{
    	
    	List<SyntacticallyAnalyzedSentence> analized;		        
    	analized = SyntacticAnalyzer.analyzeSentences(this.documentText);
    	this.scores = new HashMap<OpinionAspect, OpinionScore>();
        this.evidences = new HashMap<OpinionAspect, List<OpinionEvidence>>(); 

    	for(SyntacticallyAnalyzedSentence a:analized)
    	{
    		this.analizer.analyzeAspects(a);    		
    		this.scores.putAll(this.analizer.getAspects());
    		this.evidences.putAll(this.analizer.getAspectsEvidence());
    	}		    	    	
    	
    }    

    public Map<OpinionAspect, OpinionScore> getScores() {
        return this.scores;
    }

    public Map<OpinionAspect, List<OpinionEvidence>> getEvidences() {
        return this.evidences;
    }

    public String toString() {
        String s = "Sentiments of aspects\n";
        for (OpinionAspect aspect : this.scores.keySet()) {
            s += "\taspect: " + aspect.getName() + "\n";
            s += "\t\tscore: " + this.scores.get(aspect);
            s += "\t\tevidences:\n";
            List<OpinionEvidence> aspectEvidences = this.evidences.get(aspect);
            for (OpinionEvidence evidence : aspectEvidences) {
                s += "\t\t\t" + evidence + "\n";
            }
        }
        return s;
    }
}
