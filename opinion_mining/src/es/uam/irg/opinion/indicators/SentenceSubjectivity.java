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
import es.uam.irg.opinion.OpinionAnalyzedSentence;
import es.uam.irg.opinion.OpinionAnalyzer;
import es.uam.irg.opinion.OpinionEvidence;
import es.uam.irg.opinion.OpinionScore;
import java.util.ArrayList;
import java.util.List;

/**
 * SentenceSubjectivity
 *
 * Identifies the global opinion score and evidences of a sentence.
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class SentenceSubjectivity {

    private String sentence;
    private OpinionScore score;    
    private List<OpinionEvidence> evidences;
    private OpinionAnalyzer analizer;

    
    public SentenceSubjectivity() {
        this.evidences = new ArrayList<OpinionEvidence>();
        try {
			this.analizer = new OpinionAnalyzer();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
    }
    
    public SentenceSubjectivity(String sentence) {
        this.sentence = sentence;
        this.evidences = new ArrayList<OpinionEvidence>();
        try {
			this.analizer = new OpinionAnalyzer();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
    }
    
    public void process() throws Exception
    {

        List<SyntacticallyAnalyzedSentence> analized;
        List<OpinionAnalyzedSentence> opinion = new ArrayList<OpinionAnalyzedSentence>();
        
        analized = SyntacticAnalyzer.analyzeSentences(sentence);	
		System.out.println(analized.toString());
					
		for(SyntacticallyAnalyzedSentence _sentence:analized)
		{
			this.analizer.setSentence(_sentence.getSentence());
			this.analizer.setSentenceWords(_sentence.getTokens());
			this.analizer.setTree(_sentence.getTreebank());
			opinion.add(this.analizer.analyze());
		}
		System.out.println(opinion);
        		
        
    }

    public String getSentence() {
        return this.sentence;
    }

    public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public OpinionScore getScore() {
        return this.score;
    }

    public List<OpinionEvidence> getEvidences() {
        return this.evidences;
    }

    public String toString() {
        String s = "Subjectivitiy of sentence: " + this.sentence + "\n";
        s += "\tscore: " + this.score + "\n";
        s += "\tevidences:\n";
        for (OpinionEvidence evidence : this.evidences) {
            s += "\t\t" + evidence + "\n";
        }
        return s;
    }
}
