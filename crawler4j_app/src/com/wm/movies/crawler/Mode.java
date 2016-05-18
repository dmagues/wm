package com.wm.movies.crawler;

public enum Mode {
	STANDARD, MOVIELIST, REVIEWS, USER_REVIEWS;
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
