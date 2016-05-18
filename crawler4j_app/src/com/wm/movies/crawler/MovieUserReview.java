package com.wm.movies.crawler;

public class MovieUserReview {

	private int id;
	private String movieId;
	private String title;
	private String review;
	private String userName;
	private double ranking;
	private double usefulPeople;
	private String spoilerAlert;

	/**
	 * @return the usefulPeople
	 */
	public double getUsefulPeople() {
		return usefulPeople;
	}
	/**
	 * @param usefulPeople the usefulPeople to set
	 */
	public void setUsefulPeople(double usefulPeople) {
		this.usefulPeople = usefulPeople;
	}
	/**
	 * @return the movieId
	 */
	public String getMovieId() {
		return movieId;
	}
	/**
	 * @param movieId the movieId to set
	 */
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}
	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the ranking
	 */
	public double getRanking() {
		return ranking;
	}
	/**
	 * @param d the ranking to set
	 */
	public void setRanking(double d) {
		this.ranking = d;
	}
	
	/**
	 * @return the spoilerAlert
	 */
	public String getSpoilerAlert() {
		if (spoilerAlert== null)
			return "";
		else					
			return spoilerAlert;
	}
	/**
	 * @param spoilerAlert the spoilerAlert to set
	 */
	public void setSpoilerAlert(String spoilerAlert) {
		this.spoilerAlert = spoilerAlert;
	}
	public String toString()
	{
		String s = "Movie ID: " + this.movieId;		
		s += "\n" + this.title;
		s += "\n\tUser: " + this.userName;
		if (this.ranking!=0) s += "\n\tRanking: " + String.format("%.2f %%",this.ranking*100);
		s += "\n\tUseful: " + String.format("%.2f %%",this.usefulPeople*100);
		if (this.spoilerAlert!=null && !this.spoilerAlert.isEmpty()) s += "\n\t" + this.spoilerAlert; 
		s += "\nReview:";
		s += "\n"+this.review;
		
		return s;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
