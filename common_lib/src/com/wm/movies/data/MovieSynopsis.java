package com.wm.movies.data;

import java.util.List;

import com.wm.movies.parser.dependency.DependencyAnalyzedSentence;

public class MovieSynopsis {
	private String movieID;
	private List<DependencyAnalyzedSentence> sentences;
	private String synopsis;
	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * @return the movieID
	 */
	public String getMovieID() {
		return movieID;
	}
	/**
	 * @param movieID the movieID to set
	 */
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	/**
	 * @return the sentences
	 */
	public List<DependencyAnalyzedSentence> getSentences() {
		return sentences;
	}
	/**
	 * @param sentences the sentences to set
	 */
	public void setSentences(List<DependencyAnalyzedSentence> sentences) {
		this.sentences = sentences;
	}
	
	
}
