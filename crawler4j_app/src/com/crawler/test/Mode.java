package com.crawler.test;

public enum Mode {
	STANDARD, MOVIELIST, REVIEWS;
	public static final Mode[] modes =  Mode.values();
	public static final Mode fromInt(int value){
		try
		{
			return modes[value];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return Mode.STANDARD;
        }
		
	}
}
