package com.crawler.wm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class MovieListScraper extends MyScraper {

	@Override
	public void process(String url, HtmlParseData htmlParseData) {
		String id,title;
		Pattern idpattern = Pattern.compile("\\/");
		Set<Movie> movieList= new HashSet<Movie>();;
	
		Document doc = Jsoup.parse(htmlParseData.getHtml());
		Elements items = doc.select("div.lister-item");
		Element a;
		Movie movie;
		
		for (Element e: items)
		{
			a = e.select("h3.lister-item-header").select("a").first();
			title = a.text();
			id = idpattern.split(a.attr("href"))[2];
			
			movie = new Movie();
			movie.setId(id);
			movie.setName(title);
			
			movieList.add(movie);
		}
		 
		saveMovieList(movieList);

	}

	private synchronized void saveMovieList(Set<Movie> movieList) {
		BufferedWriter writer;
		
		String filenameMovieList = "comicmovieslist.txt";
		String filepathMovieList = String.format("%s%s", myFileStorage, filenameMovieList);		
		String registroMovie;
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathMovieList, true));
			
			for (Movie i: movieList)
			{
				registroMovie = String.format("%s\t\"%s\"\t%s",
						i.id,i.name,System.getProperty("line.separator"));
				writer.write(registroMovie);	
			}
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar registro : " +filepathMovieList+" "+e.getMessage());
			return;
		}
	}

	@Override
	public boolean matchs(Map<String, String> metaTags) {
		return metaTags.containsKey("pagetype") && metaTags.containsKey("subpagetype") && 
				metaTags.get("pagetype").contains("search") && metaTags.get("subpagetype").contains("keyword");
		
	}

}
