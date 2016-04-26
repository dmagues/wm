/**
 * Copyright 2016 
 * Daniel Magües
 * Web Mining at Universidad Autonoma de Madrid
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
package es.uam.irg.opinion;
/**
 * OpinionWord
 *
 * Represent a sentence with a subjetivity score and evidences.  
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 *   
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpinionAnalyzedSentence {

	private String sentence;
	
	private Map<OpinionWord,List<OpinionWord>> synsets;
	
	private Map<OpinionWord,OpinionEvidence> evidences;
	private OpinionScore score=null;
	
	public OpinionAnalyzedSentence(){
		this.synsets = new HashMap<OpinionWord, List<OpinionWord>>();
		this.evidences = new HashMap<OpinionWord, OpinionEvidence>();			
	}
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
		
	public Map<OpinionWord, List<OpinionWord>> getSynsets() {
		return synsets;
	}

	public void addSynset(OpinionWord term) {
		if (!this.synsets.containsKey(term)){
			this.synsets.put(term, new ArrayList<OpinionWord>());
		}		
	}
	
	public void addSynset(OpinionWord term, List<OpinionWord> synterms) {
		if (!this.synsets.containsKey(term)){
			this.synsets.put(term, new ArrayList<OpinionWord>());
		}		
		this.synsets.get(term).addAll(synterms);
		
	}

	public Map<OpinionWord, OpinionEvidence> getEvidences() {
		return this.evidences;
	}	
	public void addOpinionEvidence(OpinionWord term, OpinionEvidence evidence)
	{
		this.evidences.put(term, evidence);
	}
	
	public OpinionScore getScore() {
		return score;
	}
	public void setScore(OpinionScore score) {
		this.score = score;
	}
	
	public String toString()
	{
		String s = "\n["+this.sentence+"]";
		s += "\n["+this.score+"]";
		
		return s;
	}
	
	
	
	
	
	
	
	
}
