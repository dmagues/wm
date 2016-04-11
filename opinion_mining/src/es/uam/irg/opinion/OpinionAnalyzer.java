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
import es.uam.irg.nlp.syntax.pos.POSTags;
import es.uam.irg.nlp.syntax.treebank.SyntacticTreebank;
import es.uam.irg.nlp.syntax.treebank.SyntacticTreebankNode;
import es.uam.irg.nlp.wordnet.WordNet;
import es.uam.irg.nlp.wordnet.WordNetSynset;
import es.uam.irg.opinion.sentiwordnet.SentiWordNet;
import es.uam.irg.opinion.sentiwordnet.SentiWordNetSynset;

public class OpinionAnalyzer {

	private String sentence;
	private SyntacticTreebank tree;
		
	private SentiWordNet sentiword;
	private WordNet wordNet;
	private List<String> sentenceWords; 
	
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public void setTree(SyntacticTreebank tree) {
		this.tree = tree;
	}

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
	

	public OpinionAnalyzedSentence analyze()
	{
		OpinionAnalyzedSentence analyzedsentence = extract();
		OpinionScore score=null;
		
		for (Entry<OpinionWord, List<OpinionWord>> entry: analyzedsentence.getSynsets().entrySet()){
			
				List<SentiWordNetSynset> senti = this.sentiword.getSynsets(entry.getKey().getWord(), entry.getKey().getPos());
				score = calculateScore(senti);
				if (score!=null)
					analyzedsentence.addOpinionEvidence(entry.getKey(), new OpinionEvidence(entry.getKey().getWord(), 1, score));
		
				if (!entry.getValue().isEmpty())
				{
					for(OpinionWord w:  entry.getValue())
					{
						senti = this.sentiword.getSynsets(w.getWord(), w.getPos());
						score = calculateScore(senti);
						if (score!=null)
							analyzedsentence.addOpinionEvidence(w, new OpinionEvidence(w.getWord(), 2, score));
					}
				}			
				
		}
		
		return analyzedsentence;
				
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
	
	private OpinionScore calculateScore(List<SentiWordNetSynset> sentiset)
	{
		double pos =  sentiset.stream().mapToDouble(s->s.getScorePositivity()).sum();
		double neg =  sentiset.stream().mapToDouble(s->s.getScoreNegativity()).sum();
		double obj =  sentiset.stream().mapToDouble(s->s.getScoreObjectivity()).sum();
		
		if (pos!=0 || neg!=0)
			return new OpinionScore(obj, pos, neg);
		
		return null; 
	}

	public void setSentenceWords(List<String> words) {
		this.sentenceWords= words;		
	}


	
	
	
}
