package com.wm.movies.crawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;



import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class MovieUserReviewsScrapper extends MyScraper {

	private static final Pattern REGEXUSEFUL = Pattern.compile("^([0-9]+)\\s+out of\\s+([0-9]+).*$");
	private static final Pattern REGEXMOVIEID = Pattern.compile("^.*(tt[0-9]+).*$");
	public static volatile int index = 1;
	private List<MovieUserReview> reviews;
	
	
	@Override
	public void process(String url, HtmlParseData htmlParseData) {
		Element allReviewsPage;
		
		Document doc = Jsoup.parse(htmlParseData.getHtml());
		doc.outputSettings().escapeMode(EscapeMode.xhtml);
		doc.outputSettings().charset(CharEncoding.UTF_8);
		
		reviews = new ArrayList<MovieUserReview>();
		
		allReviewsPage = doc.getElementById("tn15content");
		String movieID =  getMovieID(url);
		
		for(Element item:allReviewsPage.children())
		{
			/**
			 * Procesa la cabecera del review: use, ranking, useful
			 */
			if (item.tagName()=="div" && item.className().isEmpty())
			{
				MovieUserReview userReview = new MovieUserReview();
				userReview.setMovieId(movieID);
				for(Element headerItem:item.children())
				{
					if(headerItem.tagName()=="small")
					{
						userReview.setUsefulPeople(getUsefulRate(headerItem.ownText()));
					}
					if(headerItem.tagName()=="h2")
					{
						userReview.setTitle(
								removeUrl(
										StringEscapeUtils.unescapeHtml4(headerItem.ownText())));
					}
					if(headerItem.tagName()=="img")
					{
						userReview.setRanking(getRankingRate(headerItem.attr("alt")));						
					}
					
					if(headerItem.tagName()=="b")
					{
						Element user = headerItem.nextElementSibling();
						if(user.tagName()=="a")
						{
							userReview.setUserName(user.ownText());							
						}
					}
					
					if(headerItem.tagName()=="p")
					{
						userReview.setSpoilerAlert(Jsoup.clean(headerItem.html(), Whitelist.none()));
					}					
										
				}				
				Element reviewText = item.nextElementSibling();
				if(reviewText.tagName()=="p")
				{					 
					userReview.setReview(
							removeUrl(
									StringEscapeUtils.unescapeHtml4(
											Jsoup.clean(reviewText.html(),Whitelist.none()))));
				}
				
				//System.out.println(userReview);
				reviews.add(userReview);
			}
			
		}
		
		saveMovieUsersReview(reviews);
		
		
		
	}

	@Override
	public boolean matchs(Map<String, String> metaTags) {
		return (metaTags.containsKey("og:url") && metaTags.get("og:url").matches("^.*title/tt[0-9]+/reviews.*$"));
	}
	
	private double getUsefulRate(String text)
	{
		Matcher tokens = REGEXUSEFUL.matcher(text);
		
		if (!tokens.matches()){return 0;}
		
		double useful = Integer.parseInt(tokens.group(1));
		double total = Integer.parseInt(tokens.group(2));
				
		return useful/total;
	}
	
	private double getRankingRate(String text)
	{
		double ranking = Integer.parseInt(text.split("/")[0]);
		double total = Integer.parseInt(text.split("/")[1]);
		return ranking/total;
				
	}
	
	private String getMovieID(String text)
	{
		Matcher tokens = REGEXMOVIEID.matcher(text);
		
		if (!tokens.matches()){return null;}
			
		return tokens.group(1);
	}
	
	private String removeUrl(String str) {
	    String regex = "\\b(https?|ftp|file|telnet|http|Unsure)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	    str = str.replaceAll(regex, "");
	    return str;
	}
	
	private synchronized void saveMovieUsersReview(List<MovieUserReview> reviews) {
		BufferedWriter writer;
		
		String filenameMovie = "moviesusersreviews.txt";

		String filepathMovies = String.format("%s%s", myFileStorage, filenameMovie);
		
		String registroMovie = ""; 
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathMovies, true));
			
			for(MovieUserReview review:reviews)
			{
				registroMovie = String.format(Locale.ENGLISH,"%d\t%s\t%s\t%s\t%.2f\t%.2f\t%s\t%s%s"
						,index
						,review.getMovieId()
						,review.getTitle()
						,review.getUserName()
						,review.getRanking()
						,review.getUsefulPeople()
						,review.getSpoilerAlert()
						,review.getReview()
						,System.getProperty("line.separator")
						
						);
				writer.write(registroMovie);
				index++;
			}
			
			
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();			
			return;
		}
	}

}
