package com.wm.movies.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.wm.movies.data.MovieUserReview;
import com.wm.movies.data.MovieUserReviewReader;
import com.wm.movies.parser.dependency.Dependency;
import com.wm.movies.parser.dependency.DependencyAnalyzedSentence;
import com.wm.movies.parser.pos.POSTags;
import com.wm.movies.parser.pos.TaggedWord;
import com.wm.movies.parser.stopword.StopwordsManager;
import com.wm.movies.parser.wordnet.WordNet;
import com.wm.movies.parser.wordnet.WordNetSynset;

import edu.smu.tspell.wordnet.Synset;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class DependencyAnalyzer {

	public static final String DICTIONARYFILE = "data/stop-words.txt";
	private boolean includeDependecy = true;
	private boolean includeWordForm = true;
	
	/**
	 * @param includeDependecy the includeDependecy to set
	 */
	public void setIncludeDependecy(boolean includeDependecy) {
		this.includeDependecy = includeDependecy;
	}

	/**
	 * @param includeWordForm the includeWordForm to set
	 */
	public void setIncludeWordForm(boolean includeWordForm) {
		this.includeWordForm = includeWordForm;
	}

	public static  void main(String[] args)
	{
		DependencyAnalyzer analyzer = new DependencyAnalyzer();

		List<MovieUserReview> reviews = MovieUserReviewReader
				.readReviewsFile("C:\\crawler\\data2\\files", "tt3498820");

		for(MovieUserReview mur:reviews.subList(0, 5))
		{

			List<DependencyAnalyzedSentence> sentences = analyzer.analyzeSentences(mur.getReview());
			mur.setSentences(sentences);
			MovieUserReviewReader.saveMoviesReviewsTokens(mur, "C:\\crawler\\data2\\files", "tt3498820");
		}

		

	}


	private StopwordsManager stopwords;
	private WordNet wordnet;



	public DependencyAnalyzer()
	{
		try {
			this.stopwords = new StopwordsManager(DICTIONARYFILE);
			this.wordnet = new WordNet();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DependencyAnalyzedSentence> analyzeSentences(String text)
	{
		List<DependencyAnalyzedSentence> sentences = new ArrayList<DependencyAnalyzedSentence>();
		Properties props = new Properties();

		props.put("annotators", "tokenize, ssplit, pos, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		text = text.replace("...", ".");
		text = text.replace("!", " ");
		text = text.replace(".", ". ");

		Annotation annotation = new Annotation(text);
		pipeline.annotate(annotation);

		List<CoreMap> analyzedSentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);;

		if (analyzedSentences != null && !analyzedSentences.isEmpty()) {
			for (CoreMap analyzedSentence : analyzedSentences) {
				DependencyAnalyzedSentence _sentence =  new DependencyAnalyzedSentence();

				

				_sentence.setSentence(analyzedSentence.toString());
				_sentence.setTokens(sentenceTokenizer(analyzedSentence.get(CoreAnnotations.TokensAnnotation.class)));
				
				if (this.includeDependecy)
				{
					Tree tree = analyzedSentence.get(TreeCoreAnnotations.TreeAnnotation.class);
					TreebankLanguagePack tlp = new PennTreebankLanguagePack();
					// Uncomment the following line to obtain original Stanford Dependencies
					tlp.setGenerateOriginalDependencies(true);
					GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
					GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
					Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
					_sentence.setDependencies(mapToDependencies(tdl));
				}
				//System.out.println(_sentence);
				sentences.add(_sentence);

			}
		}        
		return sentences;

	}

	private boolean isValidDependency(TypedDependency d){

		try
		{
			if(d.reln().getShortName()=="root") return false;
			if(d.gov().word().isEmpty()) return false;

			if(d.gov().tag().contains(POSTags.NN))			
				return true;

			if(d.gov().tag().contains(POSTags.VB))
				if(d.dep().tag().contains(POSTags.NN))
					return true;

			if(d.gov().tag().contains(POSTags.JJ))
				if(d.dep().tag().contains(POSTags.NN))
					return true;
			
		}
		catch(Exception e)
		{
			System.err.println("Error: " + d.toString());
			e.printStackTrace();
		}
		return false;
		
	}	

	private List<Dependency> mapToDependencies(Collection<TypedDependency> dependencies)
	{
		List<Dependency> items = new ArrayList<Dependency>();

		for(TypedDependency td:dependencies)
		{
			Dependency d = new Dependency();
			if (isValidDependency(td))
			{
				try {
					
					String govWord, depWord;
					
					if (this.includeWordForm)
					{
						govWord=wordNormalizer(td.gov().word(),td.gov().tag());
						depWord=wordNormalizer(td.dep().word(),td.dep().tag());
					}
					else
					{
						govWord=td.gov().word();
						depWord=td.dep().word();
					}
					
					d.setGov(new TaggedWord(govWord, td.gov().tag()));
					d.setDep(new TaggedWord(depWord, td.dep().tag()));
					d.setRelation(td.reln().getShortName());
					items.add(d);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return items;
	}

	private List<String> sentenceTokenizer(List<CoreLabel> tokens) {
			
		List<String> strTokens = new ArrayList<String>();
		String word, tag;

		for(CoreLabel label:tokens)
		{
			word = label.word().replaceAll("[^a-zA-Z|\\-]", "");
			tag = label.tag();
			
			if (this.stopwords.isStopWord(word)) continue;
			if (this.includeWordForm) word = wordNormalizer(word,tag);
			if (!word.isEmpty()) strTokens.add(word);
		}	
				
		return strTokens;
	}

	/**
	 * Retorna la primera forma del primer synset de la palabra, si no existe, retorna la misma palabra
	 * @param word
	 * @param tag
	 * @return word transformada o la misma
	 */
	private String wordNormalizer(String word, String tag) {
		
		Synset synsets = null;
		
		if (tag.contains(POSTags.VB))
			synsets = this.wordnet.getVerbSynsetOf(word, 0);
		
		if (tag.contains(POSTags.JJ))
			synsets = this.wordnet.getAdjectiveSynsetOf(word, 0);
		
		if (tag.contains(POSTags.RB))
			synsets = this.wordnet.getAdverbSynsetOf(word, 0);
		
		if(synsets!=null)
		{	
			List<String> wordForms = WordNetSynset.getWordFormsOf(synsets);
			if (wordForms.size()>0) word = wordForms.get(0);
		}
		
		return word;
	}

}
