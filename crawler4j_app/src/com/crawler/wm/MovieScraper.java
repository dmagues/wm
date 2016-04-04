package com.crawler.wm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class MovieScraper extends MyScraper {
	/**
	 * Evalua el tipo y subtipo de pagina en los meta-tags e idnetifica si es una pelicula
	 * @param metaTags envia los meta-tags para idntificar el tipo de pagina
	 * @return
	 */
	public boolean matchs(Map<String, String> metaTags) {
		
		return metaTags.containsKey("pagetype") && metaTags.containsKey("subpagetype") && 
				metaTags.get("pagetype").contains("title") && metaTags.get("subpagetype").contains("main");
		
	}


	/**
	 * Procesa la pagina dpara generar un registro en la bd.
	 * @param url la url de la pagina actual
	 * @param htmlParseData la pagina html pre-parse 
	 */
	public void process(String url, HtmlParseData htmlParseData) {
		String id,name,published, description, posterurl;
		Set<String> directors = new HashSet<String>();
		Set<String> actors = new HashSet<String>();
		
		Document doc = Jsoup.parse(htmlParseData.getHtml());
		id = doc.select("meta[property=pageid]").first().attr("content");
		
		Element overview = doc.select("div.title-overview").first();
		
		Element title = overview.select("div.title_wrapper").first();
		name=title.select("h1[itemprop=name]").first().text();
		
		if (title.select("[itemprop=datePublished]").size()==1){
			published=title.select("[itemprop=datePublished]").first().attr("content");	
		}else published ="-";	
		
		
		Element poster = overview.select("div.poster").first();
		posterurl = poster.select("img[itemprop=image]").first().attr("src");
		
		Element summary = overview.select("div.plot_summary").first();
		description = summary.select("[itemprop=description]").first().text().replace("\"","'");
		
		Elements creatorsElement = summary.select("[itemprop=creator]"); 
		for(Element n: creatorsElement){
			directors.add(n.select("[itemprop=name]").first().text());
		}
		
		Elements directorsElement = summary.select("[itemprop=director]"); 
		for(Element n: directorsElement){			
			directors.add(n.select("[itemprop=name]").first().text());
		}
		
		Elements actorsElement = summary.select("[itemprop=actors]"); 
		for(Element n: actorsElement){
			actors.add(n.select("[itemprop=name]").first().text());
		}
		
		Movie aMovie = new Movie(id, url, description, name, published, posterurl);
		aMovie.setActors(actors);
		aMovie.setDirector(directors);
		 
		saveMovie(aMovie);		
		
	}


	/**
	 * Alamcena en la bd la pelicula actual
	 * @param movie
	 */
	private void saveMovie(Movie movie) {
		BufferedWriter writer;
		
		String filenameMovie = "movies.txt";
		String filenameDirectors = "directors.txt";
		String filenameActors = "actors.txt";

		String filepathMovies = String.format("%s%s", myFileStorage, filenameMovie);
		String filepathDirectors = String.format("%s%s", myFileStorage, filenameDirectors);
		String filepathActors = String.format("%s%s", myFileStorage, filenameActors);
		
		String registroMovie = String.format("%s \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" %s",
				movie.getId(),movie.getName(),movie.getDescription(),
				movie.getPublishedDate(), movie.getUrl(), movie.getPosterUrl(),
				System.getProperty("line.separator"));
		
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathMovies, true));
			writer.write(registroMovie);
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar registro "+ movie.getId() + ": " +filepathMovies+ " "+e.getMessage());
			return;
		}
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathDirectors, true));
			for (String name: movie.getDirector()){
				writer.write(String.format("%s;%s %s",movie.getId(),name, System.getProperty("line.separator")));
			}
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar registro "+ movie.getId() + ": " +filepathDirectors+ " "+e.getMessage());		
		}
		
		try
		{
			writer = new BufferedWriter(new FileWriter(filepathActors, true));
			for (String name: movie.getActors()){
				writer.write(String.format("%s;%s %s",movie.getId(),name, System.getProperty("line.separator")));
			}
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar registro "+ movie.getId() + ": " +filepathActors+ " "+e.getMessage());		
		}
	}

}
