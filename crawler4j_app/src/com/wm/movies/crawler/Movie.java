package com.wm.movies.crawler;

import java.util.Set;

public class Movie {
	String id;
	String url;
	String name;
	String description;
	String publishedDate;
	String posterUrl;
	Set<String>  actors;
	Set<String>	director;
	
	public Movie()
	{
		
	}
	public Movie(String id, String url, String description, String name, 
					String publishedDate, String posterUrl)
	{
		this.id=id;
		this.name = name;
		this.publishedDate = publishedDate;
		this.posterUrl = posterUrl;		
		this.description=description;
		this.url=url;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the publishedDate
	 */
	public String getPublishedDate() {
		return publishedDate;
	}
	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	/**
	 * @return the posterUrl
	 */
	public String getPosterUrl() {
		return posterUrl;
	}
	/**
	 * @param posterUrl the posterUrl to set
	 */
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	/**
	 * @return the actors
	 */
	public Set<String> getActors() {
		return actors;
	}
	/**
	 * @param actors the actors to set
	 */
	public void setActors(Set<String> actors) {
		this.actors = actors;
	}
	/**
	 * @return the director
	 */
	public Set<String> getDirector() {
		return director;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirector(Set<String> director) {
		this.director = director;
	}
	
	

}
