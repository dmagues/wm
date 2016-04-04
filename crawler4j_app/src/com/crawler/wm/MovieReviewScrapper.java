package com.crawler.wm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class MovieReviewScrapper extends MyScraper {
	private static final Pattern REGEXMOVIENAME = Pattern.compile("^(.*\\,)\\s{1}(The|A|An)(.*)$");
	
	@Override
	public void process(String url, HtmlParseData htmlParseData) {
		
		if (url.contains("reelviews"))
		{
			reelviewsProcess(htmlParseData);	
		}
		
		if (url.contains("rogerebert")){
			rogerebertProcess(htmlParseData);	
		}
		
		
		
	}

	private void rogerebertProcess(HtmlParseData htmlParseData) {
		String name, review, genre;
		Document doc = Jsoup.parse(htmlParseData.getHtml());
		Elements pReviews;
		
		if (doc.select("p.genres").size()==0)
			genre = "unknown";
		else
			genre = doc.select("p.genres").first().text().toLowerCase();
		
		name = doc.select("h1[itemprop=name]").first().text();
		pReviews = doc.select("div[itemprop=reviewBody]").first().select("p");
		review = Jsoup.clean(pReviews.html(), Whitelist.none());
		
		saveMovieReview(name,review,genre);
	}

	/**
	 * @param htmlParseData
	 */
	private void reelviewsProcess(HtmlParseData htmlParseData) {
		String name, review, genre;
		Elements pReviews;
		
		Document doc = Jsoup.parse(htmlParseData.getHtml());
		name = doc.select("meta[itemprop=name]").first().attr("content").split("\\|")[0].trim();
		name = nameCorrector(name);		
		
		pReviews = doc.getElementById("content").getElementsByTag("p");
		review = Jsoup.clean(pReviews.html(), Whitelist.none());		
		
		genre = doc.getElementsByClass("details").first().select("a[href*=genre]").first().text().toLowerCase().replace("/", ", ");
		
		saveMovieReview(name,review,genre);
	}

	@Override
	public boolean matchs(Map<String, String> metaTags) {
		return (metaTags.containsKey("keywords") && metaTags.get("keywords").contains("Movie Reviews") &&
				metaTags.containsKey("description") && !metaTags.get("description").contains("Reelviews Movies")) 
				|| (metaTags.containsKey("dc.title") && metaTags.get("dc.title").matches("^.*Movie Review.*\\(\\w{4}\\).*\\|.*$"));
	}
	
	private synchronized void saveMovieReview(String name, String review, String genre) {
		BufferedWriter writer;
		
		String filenameMovie = "moviesreviews.txt";

		String filepathMovies = String.format("%s%s", myFileStorage, filenameMovie);
		
		String registroMovie = String.format("%s\t\"%s\"\t\"%s\"%s",
				name,genre,review,System.getProperty("line.separator"));
		
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathMovies, true));
			writer.write(registroMovie);
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar registro "+ name + ": " +filepathMovies+ " "+e.getMessage());
			return;
		}	
		
	}
	
	private String nameCorrector(String name)
	{
		Matcher tokens = REGEXMOVIENAME.matcher(name);
		
		if (!tokens.matches()){return name;}
		
		List<String> nameParts = new ArrayList<String>();
		
		nameParts.add(tokens.group(1));
		nameParts.add(tokens.group(2));
		nameParts.add(tokens.group(3));
				
		return String.format("%s %s%s ", nameParts.get(1).trim(), nameParts.get(0).replace(",", "").trim(),nameParts.get(2).trim());
	}

}
