package com.crawler.wm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.regex.Pattern;

//import org.jsoup.Jsoup;
//import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MovieCrawler extends WebCrawler{
	private static final Logger logger = LoggerFactory.getLogger(MovieCrawler.class);
	private static final int MAX_CHAR = 254;

	private static final Pattern FILTERS = Pattern.compile(
			".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf" +
			"|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	/**
	 * crawlDomains indica los dominios de las url a realizar el crawling
	 * fileStorage indica la ruta donde almacenar las páginas.
	 */
	private String[] myCrawlDomains;
	private String myFileStorage;
	CrawlStat myCrawlStat;
	MyScraper myScraper;

	private CrawlData myCrawlData;

	public MovieCrawler()
	{
		myCrawlStat = new CrawlStat();
	}


	@Override
	public void onStart(){
		myCrawlData = (CrawlData) myController.getCustomData();

		myFileStorage = myCrawlData.getFileStorage();
		myCrawlDomains = myCrawlData.getCrawlerDomains();
	
		
		switch (myCrawlData.getCurrMode()){		
		case STANDARD:
			myScraper = new MovieScraper();
			break;
		case MOVIELIST:
			myScraper= new MovieListScraper();
			break;
		case REVIEWS:
			myScraper = new MovieReviewScrapper();
			break;			
		}
		
		myScraper.setFileStorage(myFileStorage);

	}

	/**
	 * Determina si la page actual se requiere visitar por un filtro especifico o dentro del dominio especificado
	 * @param page referencia a la page a visitar (probablemente podría hacer scrapping de las paginas)
	 * @param url referencia a la url a visitar
	 * @return true si page debe ser visitada, false si no debe ser visitada
	 */
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		
		/**
		 * Se verifica que ningun enlace haga referencia a ficheros que no son permitidos 
		 */
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) {
			return false;
		}	
		
		switch (myCrawlData.getCurrMode()){		
		case STANDARD:
			for (String crawlDomain : myCrawlDomains) {
				if (href.contains(crawlDomain)) {
					return true;
				}
			}
			break;
		case MOVIELIST:
			if (url.getAnchor()!=null && url.getAnchor().contains("Next »"))
			{ 	return true; }
			break;
		case REVIEWS:
			for (String crawlDomain : myCrawlDomains) {
				if (href.contains(crawlDomain)) {
					return true;
				}
			}
			break;
		}		

		return false;
	}
	
	

	/**
	 * Visita la page actual
	 * @param page Referencia a page que se está visitando
	 */
	@Override
	public void visit(Page page) {

		myCrawlStat.incProcessedPages();

		String url = page.getWebURL().getURL();	    

		logger.info("URL: {}", url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			
			/*
			 * Para iniciar scraper se valida el patron de acuerdo al modo de scraper instanciado  
			 * */
			if (myScraper.matchs(htmlParseData.getMetaTags()))
			{
				logger.debug("SCRAPER FOUND: {}", url);
				myScraper.process(url, htmlParseData);	    	  
			}
			
			
			/*
			 * Se modifica el fuente html para pasar limpio de tags al guardar la página 
			 * Jsoup.clean(html, Whitelist.none())
			 */
			
			if (myCrawlData.getCurrMode() == Mode.STANDARD)
				saveFile(url, html);

			Set<WebURL> links = htmlParseData.getOutgoingUrls();

			myCrawlStat.incTotalLinks(links.size());

			try {
				myCrawlStat.incTotalTextSize(text.getBytes("UTF-8").length);
			} catch (UnsupportedEncodingException ignored) {
				// Do nothing
			}
				
		}    


	}

	/**
	 * This function is called by controller to get the local data of this crawler when job is finished
	 */
	@Override
	public Object getMyLocalData() {
		return myCrawlStat;
	}


	/**
	 * Permite almacenar page visitada en el almacenamiento definido en el controlador
	 * @param url referencia al url de la page visitada
	 * @param html contenido html de la page
	 * @param path ruta dodne se almacenan los archivos
	 */
	private void saveFile(String url, String html) {
		String filename = url.replaceAll("[^a-zA-Z0-9.-]", "_").toLowerCase();
		
		int maxLength = (filename.length() < MAX_CHAR)?filename.length():MAX_CHAR;
		filename = filename.substring(0, maxLength)+".txt";

		String filepath = String.format("%s%s", myFileStorage, filename);
		logger.debug("Fichero procesado: " +filepath);			  

		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			writer.write(html);
			writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();
			logger.error("Error al procesar fichero: " +filepath+ " "+e.getMessage());		
		}			
	}
}	 








