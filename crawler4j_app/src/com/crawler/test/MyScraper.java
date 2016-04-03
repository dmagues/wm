package com.crawler.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public abstract class MyScraper {

	protected static final Logger logger = LoggerFactory.getLogger(MovieScraper.class);

	public abstract void process(String url, HtmlParseData htmlParseData);

	public abstract boolean matchs(Map<String, String> metaTags);

	protected String myFileStorage;

	public MyScraper() {
		super();
	}

	public void setFileStorage(String myFileStorage) {
		this.myFileStorage = myFileStorage;
	}

}