package com.crawler.test;

import java.util.Map;

import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class MovieListScraper extends MyScraper {

	@Override
	public void process(String url, HtmlParseData htmlParseData) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matchs(Map<String, String> metaTags) {
		return metaTags.containsKey("pagetype") && metaTags.containsKey("subpagetype") && 
				metaTags.get("pagetype").contains("search") && metaTags.get("subpagetype").contains("keyword");
		
	}

}
