package com.wm.movies.parser.dependency;

import java.util.List;

public class DependencyAnalyzedSentence {
	private String sentence; 
	private List<String> tokens;
	private List<Dependency> dependencies;
	
	
	/**
	 * @return the sentence
	 */
	public String getSentence() {
		return sentence;
	}
	/**
	 * @param sentence the sentence to set
	 */
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	/**
	 * @return the tokens
	 */
	public List<String> getTokens() {
		return tokens;
	}
	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	/**
	 * @return the dependencies
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}
	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
	public String getStringTokens()
	{
		String tokens ="";

		tokens = this.tokens.toString().replace(",", "").replace("[", "").replace("]", "");
		if(this.dependencies!=null)
		{
			for(Dependency d:this.dependencies)
			{
				String virtualToken = String.format(" %s-%s", d.getDep().getWord(),d.getGov().getWord());
				tokens += virtualToken;
			}
		}

		return tokens;
	}
	
	public String toString()
	{
		String s="";
		s += String.format("\nSentence: %s", this.sentence);
		s += String.format("\n\tTokens: %s", this.tokens);
		s += String.format("\n\tDependencies: %s", this.dependencies);
		s += String.format("\n\tString tokens: %s", this.getStringTokens());
		s += "\n";
		return s;
		
	}
	
	
	

}
