package com.wm.movies.crawler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wm.movies.crawler.data.PropertyFileManager;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MovieCrawlerController {

	private static final Logger logger = LoggerFactory.getLogger(MovieCrawlerController.class);
	
	CrawlController controller;
	
	 private String myCrawlerStorage;
	 private int numberThreads;
	 private int crawlDeph;
	 private int maxPages = -1;
	 private Mode mode =  Mode.STANDARD;
	
	public MovieCrawlerController(String crawlStorage, int numberThreads, Mode mode, Integer maxPages) {
		
		this.myCrawlerStorage = crawlStorage;
		this.numberThreads = numberThreads;
		this.crawlDeph = -1;
		this.mode = mode;
		if (maxPages!= null) this.maxPages=maxPages;
					
	}
	
	public void deletePathFiles(String storage){
		  File path = new File(String.format("%s%s%s%s", storage,File.separator,"files",File.separator));
		  //File path = new File(storage+"\\files\\");
		  if (path.isDirectory())
		  {
			  String[]entries = path.list();
			  for(String s: entries){
			      File currentFile = new File(path.getPath(),s);
			      currentFile.delete();
			  }
		  }
		  path.delete();
	  }
	  
	  public String createPathFiles(String storage){
		  
		  deletePathFiles(storage);
		  File path = new File(String.format("%s%s%s%s", storage,File.separator,"files",File.separator));
		  //File path = new File(storage+"\\files\\");  
		  path.mkdirs();	  
		  return path.getPath()+File.separator;
	  }
	  
	
	private void processController() throws IOException
	{
		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(myCrawlerStorage);

		
		
		
	    /*
	     * Be polite: Make sure that we don't send more than 1 request per
	     * second (1000 milliseconds between requests).
	     */
	    config.setPolitenessDelay(1000);

	    /*
	     * You can set the maximum crawl depth here. The default value is -1 for
	     * unlimited depth
	     */
	    config.setMaxDepthOfCrawling(crawlDeph);

	    /*
	     * You can set the maximum number of pages to crawl. The default value
	     * is -1 for unlimited number of pages
	     */
	    config.setMaxPagesToFetch(maxPages);

	    /**
	     * Do you want crawler4j to crawl also binary data ?
	     * example: the contents of pdf, or the metadata of images etc
	     */
	    config.setIncludeBinaryContentInCrawling(false);

	    /*
	     * Do you need to set a proxy? If so, you can use:
	     * config.setProxyHost("proxyserver.example.com");
	     * config.setProxyPort(8080);
	     *
	     * If your proxy also needs authentication:
	     * config.setProxyUsername(username); config.getProxyPassword(password);
	     */

	    /*
	     * This config parameter can be used to set your crawl to be resumable
	     * (meaning that you can resume the crawl from a previously
	     * interrupted/crashed crawl). Note: if you enable resuming feature and
	     * want to start a fresh crawl, you need to delete the contents of
	     * rootFolder manually.
	     */
	    config.setResumableCrawling(false);

	    config.setUserAgentString("movie crawler - UAM Madrid - dmagues@estudiante.uam.es");
	    
	    /*
	     * Instantiate the controller for this crawl.
	     */	    
	    PageFetcher pageFetcher = new PageFetcher(config);	    
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();	    
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	    
	    try {
			this.controller = new CrawlController(config, pageFetcher, robotstxtServer);			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	    
	    
	    
	    /**
	     * TODO: Definir un proceso configurable para pasar el seed y el dominio
	     * */
	    
//	    controller.addSeed("http://akas.imdb.com/search/keyword?page=1&sort=moviemeter,asc&keywords=based-on-comic&title_type=movie&mode=detail&ref_=kw_nxt#main");
//	    String[] crawlerDomains = {"http://akas.imdb.com/search/keyword"};
	    
//	    controller.addSeed("http://akas.imdb.com/search/keyword?page=1&sort=moviemeter,asc&keywords=based-on-comic%2Cmarvel-comics&title_type=movie&mode=detail&ref_=kw_nxt#main");
//	    String[] crawlerDomains = {"http://akas.imdb.com/search/keyword"};
	    
//	    controller.addSeed("http://akas.imdb.com/search/keyword?page=1&sort=moviemeter,asc&keywords=based-on-comic%2Cdc-comics&title_type=movie&mode=detail&ref_=kw_nxt#main");
//	    String[] crawlerDomains = {"http://akas.imdb.com/search/keyword"};
	    
//	    controller.addSeed("http://www.reelviews.net/reelviews/");
//	    controller.addSeed("http://www.rogerebert.com/reviews/");
//	    
//	    String[] crawlerDomains = {"http://www.reelviews.net/reelviews/", 
//	    							"http://www.rogerebert.com/reviews/"};

	    
	    PropertyFileManager.readPropertyFile();
		
	    for (String seed :PropertyFileManager.getPropertiesValues(mode.name(), "seed"))
	    {
	    	controller.addSeed(seed);
	    }
	    
	    String[] crawlerDomains = PropertyFileManager.getPropertiesValues(mode.name(), "domain").toArray(new String[0]);
		
		String fileStorage = createPathFiles(myCrawlerStorage);
		CrawlData data = new CrawlData();
		data.setFileStorage (fileStorage);
		data.setCrawlerDomains(crawlerDomains);
		data.setCurrMode(mode);
	    
	    controller.setCustomData(data);	    
		
		
		long startTime = System.currentTimeMillis();

		controller.startNonBlocking(MovieCrawler.class, numberThreads);
		
		controller.waitUntilFinish();

		long estimatedTime = System.currentTimeMillis() - startTime;
		
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
	    long totalLinks = 0;
	    long totalTextSize = 0;
	    int totalProcessedPages = 0;
	    for (Object localData : crawlersLocalData) {
	      CrawlStat stat = (CrawlStat) localData;
	      totalLinks += stat.getTotalLinks();
	      totalTextSize += stat.getTotalTextSize();
	      totalProcessedPages += stat.getTotalProcessedPages();
	    }

	    System.out.println("Statistics:");
	    System.out.println("\tProcessed Pages: "+ totalProcessedPages);
	    System.out.println("\tTotal Links found: "+ totalLinks);
	    System.out.println("\tTotal Text Size: "+ totalTextSize);
	    System.out.println("Time elapsed: "+ estimatedTime);
	}

	public void start() {
		try {
			processController();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}			
	}
	
	

	
}
