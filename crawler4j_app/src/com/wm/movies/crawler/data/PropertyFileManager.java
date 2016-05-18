package com.wm.movies.crawler.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyFileManager {
	private static final String PROPERTYFILE="com/wm/movies/crawler/data/conf.properties";
	private static Properties prop=null;
	private static final InputStream input = PropertyFileManager.class.getClassLoader().getResourceAsStream(PROPERTYFILE);
	
	public static void readPropertyFile() throws IOException
	{
		prop = new Properties();		
		prop.load(input);
		
	}
	
	public static List<String> getPropertiesValues(String mode, String group)
	{
		String strKey;
		List<String> items = new ArrayList<String>();
		for (Object key: prop.keySet())
		{
			strKey = (String)key;
			if (strKey.contains(mode+"."+group))
				items.add(prop.getProperty(strKey));		
			
		}
		return items;
	}

}
