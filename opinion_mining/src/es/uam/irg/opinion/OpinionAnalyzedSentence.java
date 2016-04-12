package es.uam.irg.opinion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		
//		for (Entry<OpinionWord, List<OpinionWord>> words: this.synsets.entrySet())
//		{
//			s+="\n" + words.toString();
//			s+="\n\t" + this.getEvidences().get(words.getKey());
//			for(OpinionWord ow:words.getValue())
//			{s+="\n\t\t" + this.getEvidences().get(ow);}
//		}
		
		return s;
	}
	
	
	
	
	
	
	
	
}
