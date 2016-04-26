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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import java.util.stream.Collectors;

import edu.smu.tspell.wordnet.Synset;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.uam.irg.nlp.syntax.SyntacticallyAnalyzedSentence;
import es.uam.irg.nlp.syntax.pos.POSTags;
import es.uam.irg.nlp.syntax.pos.TaggedWord;
import es.uam.irg.nlp.syntax.treebank.SyntacticTreebank;
import es.uam.irg.nlp.syntax.treebank.SyntacticTreebankNode;
import es.uam.irg.nlp.wordnet.WordNet;
import es.uam.irg.nlp.wordnet.WordNetSynset;
import es.uam.irg.opinion.sentiwordnet.SentiWordNet;
import es.uam.irg.opinion.sentiwordnet.SentiWordNetSynset;

/**
 * OpinionAnalyzer
 *
 * Analyze sentence and return subjectivity calculations and aspects processing  
 * 
 * @author Daniel Magües, daniel.magues@estudiante.uam.es
 * @version 2.0 - 25/04/2016
 *   
 */

public class OpinionAnalyzer {

	private static List<OpinionWord> getSynsetsTermsByType(Synset synset, List<OpinionWord> terms) {
		OpinionWord ow = null;
		if (synset.getType().equals(WordNet.NOUN))
		{	
			for(Synset hypernyms: WordNetSynset.getNounHypernymsOf(synset))
			{
				for(String w: WordNetSynset.getWordFormsOf(hypernyms))
				{
					ow = new OpinionWord(w, synset.getType().getCode());
					if (!terms.contains(ow))
						terms.add(ow);
				}
					
			}
			
			for(Synset hyponyms: WordNetSynset.getNounHyponymsOf(synset))
			{
				for(String w: WordNetSynset.getWordFormsOf(hyponyms))
				{
					ow = new OpinionWord(w, synset.getType().getCode());
					if(!terms.contains(ow))
						terms.add(ow);
				}
					
			}
		}
		
		if (synset.getType().equals(WordNet.VERB))
		{	
			for(Synset hypernyms: WordNetSynset.getVerbHypernyms(synset))
			{
				for(String w: WordNetSynset.getWordFormsOf(hypernyms))
				{
					ow = new OpinionWord(w, synset.getType().getCode());
					if(!terms.contains(ow))
						terms.add(ow);
				}					
			}			
		}
		
		return terms;
	}
	private static List<OpinionWord> getSynsetTerms(Synset synset, int pos) {
		
		List<OpinionWord> terms = new ArrayList<OpinionWord>();
		
		for(String w: WordNetSynset.getWordFormsOf(synset))
		{
			terms.add(new OpinionWord(w, pos));
		}
		
		terms = getSynsetsTermsByType(synset, terms);
		
		return terms;
	}
		
	private static int getWordNetType(String tag) {
		switch(tag)
		{
		case POSTags.NN:
			return WordNet.NOUN;
		case POSTags.NNS:
			return WordNet.NOUN;
		case POSTags.NNP:
			return WordNet.NOUN;
		case POSTags.NNPS:
			return WordNet.NOUN;
		case POSTags.JJ:
			return WordNet.ADJECTIVE;
		case POSTags.JJR:
			return WordNet.ADJECTIVE;
		case POSTags.JJS:
			return WordNet.ADJECTIVE;
		case POSTags.RB:
			return WordNet.ADVERB;
		case POSTags.RBR:
			return WordNet.ADVERB;
		case POSTags.RBS:
			return WordNet.ADVERB;
		case POSTags.VB:
			return WordNet.VERB;
		case POSTags.VBD:
			return WordNet.VERB;
		case POSTags.VBG:
			return WordNet.VERB;
		case POSTags.VBN:
			return WordNet.VERB;
		case POSTags.VBP:
			return WordNet.VERB;
		case POSTags.VBZ:
			return WordNet.VERB;
		}
		return 0;
	}
	public static boolean isValidTag(String tag)
	{
		switch(tag)
		{
		case POSTags.NN:
			return true;
		case POSTags.NNS:
			return true;
		case POSTags.NNP:
			return true;
		case POSTags.NNPS:
			return true;
		case POSTags.JJ:
			return true;
		case POSTags.JJR:
			return true;
		case POSTags.JJS:
			return true;
		case POSTags.RB:
			return true;
		case POSTags.RBR:
			return true;
		case POSTags.RBS:
			return true;
		case POSTags.VB:
			return true;
		case POSTags.VBD:
			return true;
		case POSTags.VBG:
			return true;
		case POSTags.VBN:
			return true;
		case POSTags.VBP:
			return true;
		case POSTags.VBZ:
			return true;		
		}
		return false;	
		
	}
	private String sentence;
	private SyntacticTreebank tree;
	private SentiWordNet sentiword; 
	
	private WordNet wordNet;

	private List<String> sentenceWords;
	
	private Map<OpinionAspect, OpinionScore> aspects;

	private Map<OpinionAspect, List<OpinionEvidence>> aspectsEvidence;
	
	public OpinionAnalyzer() throws Exception {
		
		this.sentiword = new SentiWordNet(SentiWordNet.DB_PATH_FILTERED);
		this.wordNet = new WordNet(WordNet.DB_DIRECTORY);
	}
	
	
		
	public OpinionAnalyzer(String sentence, SyntacticTreebank tree) throws Exception {
		this.setSentence(sentence);
		this.setTree(tree);
				
		this.sentiword = new SentiWordNet(SentiWordNet.DB_PATH_FILTERED);
		this.wordNet = new WordNet(WordNet.DB_DIRECTORY);
	}
	

	/**
	 * Analyzed an OpinionAnalyzedSentence sentence, and create OpinionEvidence for each synset that belongs to the sentence. 
	 * Calculate scores for each synset extracted.
	 * 
	 * @return An OpinionAnalyzedSentence with evidences
	 */
	public OpinionAnalyzedSentence analyze()
	{
		OpinionAnalyzedSentence analyzedsentence = extract();
		OpinionScore score=null;
		
		for (Entry<OpinionWord, List<OpinionWord>> entry: analyzedsentence.getSynsets().entrySet()){
			
				List<SentiWordNetSynset> senti = this.sentiword.getSynsets(entry.getKey().getWord(), entry.getKey().getPos());
				score = calculateTermScore(senti);
				if (score!=null)
					analyzedsentence.addOpinionEvidence(entry.getKey(), new OpinionEvidence(entry.getKey().getWord(), entry.getKey().getPos(), score));
		
				if (!entry.getValue().isEmpty())
				{
					for(OpinionWord w:  entry.getValue())
					{
						senti = this.sentiword.getSynsets(w.getWord(), w.getPos());
						score = calculateTermScore(senti);
						if (score!=null)
							analyzedsentence.addOpinionEvidence(w, new OpinionEvidence(w.getWord(), w.getPos(), score));
					}
				}			
				
		}
		
		analyzedsentence.setScore(calculateSentenceScore(analyzedsentence));
		
		return analyzedsentence;
				
	}
	
	/**
	 * Create aspects for each noun identified in SyntacticallyAnalyzedSentence sentence and complements for eahc noun. 
	 * Generate synsets for labels, and calculate evidence for each label and positive and negative values.
	 * 	 * 
	 * @param sentence A SyntacticallyAnalyzedSentence sentence from SyntacticAnalizer
	 * @throws Exception
	 */
	public void analyzeAspects(SyntacticallyAnalyzedSentence sentence) throws Exception
	{
		
		this.aspects = new HashMap<OpinionAspect, OpinionScore>();
		this.aspectsEvidence = new HashMap<OpinionAspect, List<OpinionEvidence>>();
		this.sentenceWords = sentence.getTokens();
		
		for(Integer nounId:sentence.getAnalysisData().getNouns().keySet())
		{
			int pos = OpinionAnalyzer.getWordNetType(sentence.getAnalysisData().getNoun(nounId).getTag());
			String noun =  sentence.getAnalysisData().getNoun(nounId).getWord();
			List<Synset> synsets = this.wordNet.getSynsetsOf(noun, pos);
			List<String> labels = new ArrayList<String>();
			List<OpinionEvidence> evidences = new ArrayList<OpinionEvidence>();
			
			double positive=0, negative=0, subjective=0;
			
			List<OpinionWord> synsetsterms = null;

			if (!synsets.isEmpty())
			{
				/* Ya que no podemos conocer el contexto de los terminos se selcciona la primera definición
				 * y obtenemos los synterms */
				Synset bestSynset = getBestSynset(sentence.getSentence(), synsets);
				synsetsterms =  OpinionAnalyzer.getSynsetTerms(bestSynset, pos);
				
			}			
			
			List<String> positiveValues = new ArrayList<String>();
			List<String> negativeValues = new ArrayList<String>();

			for(TaggedWord word:sentence.getAnalysisData().getComplementsOf(nounId)){
				if (!POSTags.getTypeOfTag(word.getTag()).equals(POSTags.TYPE_DETERMINER))
				{
				
					
					List<SentiWordNetSynset> senti = this.sentiword.getSynsets(word.getWord(), OpinionAnalyzer.getWordNetType(word.getTag()));
					OpinionScore score = calculateTermScore(senti);
					if(score!=null)
					{
						if(score.getPositivity()>score.getNegativity())
							positiveValues.add(word.getWord());						
						else
							negativeValues.add(word.getWord());
						
						evidences.add(new OpinionEvidence(word.getWord(), OpinionAnalyzer.getWordNetType(word.getTag()), score));
						
						positive += score.getPositivity();
						negative += score.getNegativity();
						subjective += score.getSubjectivity();
					}
					
						
				}
			}
			
			
			if(synsetsterms!=null){
				for(OpinionWord term:synsetsterms)
				{
					List<SentiWordNetSynset> senti = this.sentiword.getSynsets(term.getWord(), term.getPos());
					OpinionScore score = calculateTermScore(senti);
					
					if (score!=null)						
					{					
						positive += score.getPositivity();
						negative += score.getNegativity();
						subjective += score.getSubjectivity();
						
						evidences.add(new OpinionEvidence(term.getWord(), term.getPos(), score));
						
						}
				}
				labels = synsetsterms.stream().map(e->e.getWord()).collect(Collectors.toList());
				
			}else
			{
				List<SentiWordNetSynset> senti = this.sentiword.getSynsets(noun, pos);
				OpinionScore score = calculateTermScore(senti);
				
				if(score!=null)
				{
					positive += score.getPositivity();
					negative += score.getNegativity();
					subjective += score.getSubjectivity();	
					
					evidences.add(new OpinionEvidence(noun, pos, score));
				}
				labels.add(noun);	
			}
			
			OpinionAspect aspect = new OpinionAspect(noun, labels, positiveValues, negativeValues);
			OpinionScore score = new OpinionScore(subjective, positive, negative);
			
			aspects.put(aspect, score);
			aspectsEvidence.put(aspect, evidences);

		}
		
	}
	
	
	private OpinionScore calculateSentenceScore(OpinionAnalyzedSentence oas)
	{
		
		if (!oas.getEvidences().isEmpty())
		{
			double wPos = oas.getEvidences().values().stream().mapToDouble(e->e.getScore().getPositivity()).sum()/oas.getSentence().length();
			double wNeg = oas.getEvidences().values().stream().mapToDouble(e->e.getScore().getNegativity()).sum()/oas.getSentence().length();
			double wSubj = oas.getEvidences().values().stream().mapToDouble(e->e.getScore().getSubjectivity()).sum()/oas.getSentence().length();

			OpinionScore os = new OpinionScore(wSubj, wPos, wNeg);			
			return os;
		}
		return null;	
		
	}
	
	
	private OpinionScore calculateTermScore(List<SentiWordNetSynset> sentiset)
	{
		double pos =  sentiset.stream().mapToDouble(s->s.getScorePositivity()).sum();
		double neg =  sentiset.stream().mapToDouble(s->s.getScoreNegativity()).sum();
		double obj =  sentiset.stream().mapToDouble(s->s.getScoreObjectivity()).sum();
		
		if (pos!=0 || neg!=0)
			return new OpinionScore(obj, pos, neg);
		
		return null; 
	}

	/**
	 * Create an OpinionAnalyzedSentence sentence. a sentence's SyntacticTreebankNode is required
	 * For each NOUN, VERB, ADJ or ADVERB, it gets synsets. It obtains the best synset with Bags-of-words.
	 *  
	 * @return An OpinionAnalyzedSentence sentecen with synset a no evidence. 
	 */
	public OpinionAnalyzedSentence extract()
	{
		OpinionAnalyzedSentence analyzedSentence = new OpinionAnalyzedSentence();
		analyzedSentence.setSentence(sentence);
		
		for(Map.Entry<Integer, SyntacticTreebankNode> node: tree.getNodes().entrySet())
		{
			if (OpinionAnalyzer.isValidTag(node.getValue().getTag()))
			{
				
				int pos = OpinionAnalyzer.getWordNetType(node.getValue().getTag());
				OpinionWord term = new OpinionWord(node.getValue().getWord(),pos);
				List<Synset> synsets = this.wordNet.getSynsetsOf(term.getWord(), term.getPos());
								
				
				if (!synsets.isEmpty())
				{
					/* Ya que no podemos conocer el contexto de los terminos se selcciona la primera definición
					 * y obtenemos los synterms */
					Synset bestSynset = getBestSynset(term.getWord(), synsets);
					List<OpinionWord> synsetsterms =  OpinionAnalyzer.getSynsetTerms(bestSynset, term.getPos());
					analyzedSentence.addSynset(term, synsetsterms);
				}
				else
				{
					analyzedSentence.addSynset(term);
				}
			}
		}
		
		return analyzedSentence;
		
	}

	public Map<OpinionAspect, OpinionScore> getAspects() {
		
		return this.aspects;
	}


	
	public Map<OpinionAspect,List<OpinionEvidence>> getAspectsEvidence() {
		// TODO Auto-generated method stub
		return aspectsEvidence;
	}
	
	public Synset getBestSynset(String sentence,List<Synset> synsets)
	{
		
		Map<Integer,List<String>> definitionsBoW = new HashMap<Integer, List<String>>();
		Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        /*Generamos un bag-of-words de cada dfinición dado por WordNet*/
		for(Synset synset: synsets)
		{
			Annotation annotation = new Annotation(synset.getDefinition());
	        pipeline.annotate(annotation);
	        List<CoreLabel> labels = annotation.get(CoreAnnotations.TokensAnnotation.class);
	        List<String> words = labels.stream()
            		.map( w -> w.word())
            		.collect(Collectors.toList());
	        definitionsBoW.put(synsets.indexOf(synset),words);	        
	        
		}
		 
		/* Buscamos cada palabra de la sentence en todas las bags-of-words 
		 * y por medio de un Map contamos para obtener en cual definición se repiten más las
		 * palabras de la sentence */
		Map<Integer, Integer> indexCount = new HashMap<Integer, Integer>();
	    for (String word:this.sentenceWords)
	    {
	    	for(Entry<Integer, List<String>> bow: definitionsBoW.entrySet())
	    	{
	    		if (bow.getValue().contains(word)){
	    			if (!indexCount.containsKey(bow.getKey()))
	    				indexCount.put(bow.getKey(), 1);
	    			else{
	    				Integer i=indexCount.get(bow.getKey());
	    				i+=1;
	    				indexCount.put(bow.getKey(),i);
	    			}

	    		}
	    	}
	    }
	    
	    /* El máximo contador determina el mejor synset sino se puede obtener se retorna el primer synset */
	    Map.Entry<Integer,Integer> maxElt = indexCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);    
	    
	    int index = 0;
	    if (!(maxElt==null))
	    	index = maxElt.getKey();
	    
	    return synsets.get(index);
	}
	
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setSentenceWords(List<String> words) {
		this.sentenceWords= words;		
	}

	public void setTree(SyntacticTreebank tree) {
		this.tree = tree;
	}


	
	
	
}
