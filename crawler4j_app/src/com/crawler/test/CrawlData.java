package com.crawler.test;

public class CrawlData {

	private String fileStorage;
	private String[] crawlerDomains;
	
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

}
