package com.wm.movies.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.wm.movies.parser.dependency.DependencyAnalyzedSentence;

public class MovieSynopsisReader {

	public  static MovieSynopsis readSynopsisFile(String storageFolder, String movieID)
	{
		String path = String.format("%s\\%s.txt", storageFolder, movieID);
		MovieSynopsis synopsis =  null;

		try {
			BufferedReader brs = new BufferedReader(new FileReader(path));
			String text="", line="";
			synopsis = new MovieSynopsis();
			while((line = brs.readLine()) != null){
				text += line + " ";
			}
			synopsis.setSynopsis(text);
			synopsis.setMovieID(movieID);
			brs.close();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return synopsis;
		//System.out.println(reviews);
	}
	
	public static void saveMoviesSynopsisTokens(MovieSynopsis synopsis, String path, String movieID) {
		BufferedWriter writer;
		
		String filenameMovie;
		String filepathMovies;
		
		try
		{
				filenameMovie = String.format("%s_synopsis.txt",movieID);
				filepathMovies = String.format("%s\\%s", path, filenameMovie);
								
				writer = new BufferedWriter(new FileWriter(filepathMovies, true));
				
				for(DependencyAnalyzedSentence s:synopsis.getSentences())
				{
					writer.write(String.format("%s\n", s.getStringTokens()));
				}				
				writer.close();
		}
		catch(IOException e)
		{ 
			e.printStackTrace();			
			return;
		}
	}
}
