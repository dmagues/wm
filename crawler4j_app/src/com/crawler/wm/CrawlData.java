package com.crawler.wm;

public class CrawlData {

	private Mode currMode; 
	private String fileStorage;
	private String[] crawlerDomains;
	
	public CrawlData()
	{
		this.currMode = Mode.STANDARD;
	}
	
	public String[] getCrawlerDomains() {
		return crawlerDomains;
	}

	public void setCrawlerDomains(String[] crawlerDomains) {
		this.crawlerDomains = crawlerDomains;
	}

	public String getFileStorage() {
		return fileStorage;
	}

	public void setFileStorage(String fileStorage) {
		this.fileStorage = fileStorage;
	}

	/**
	 * @return the currMode
	 */
	public Mode getCurrMode() {
		return currMode;
	}

	/**
	 * @param currMode the currMode to set
	 */
	public void setCurrMode(Mode currMode) {
		this.currMode = currMode;
	}

}
