package com.wm.movies.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieUserReviewReader {
	
	public  static List<MovieUserReview> readReviewsFile(String storageFolder, String movieID)
	{
		String path = String.format("%s\\moviesusersreviews.txt", storageFolder);
		List<MovieUserReview> reviews =  new ArrayList<MovieUserReview>();
		
		try {
			 BufferedReader brs = new BufferedReader(new FileReader(path));
			 String line;
	            while((line = brs.readLine()) != null){
	            		            		
	            	String[] items = line.split("\t");
	            	if (movieID.matches(items[1]))
	            	{
	            		MovieUserReview mur = new MovieUserReview();
	            		mur.setId(Integer.parseInt(items[0])); // movieid columna
	            		mur.setMovieId(items[1]); // movieid columna
	            		mur.setTitle(items[2]); // title columna
	            		mur.setUserName(items[3]); // username columna
	            		mur.setRanking(Double.parseDouble(items[4])); // ranking columna
	            		mur.setUsefulPeople(Double.parseDouble(items[5])); // useful columna
	            		mur.setSpoilerAlert(items[6]); // spolieralert columna
	            		mur.setReview(items[7]); // review columna
	            		reviews.add(mur);
	            	}

					
						                
	            }	
	            brs.close();
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return reviews;
		//System.out.println(reviews);
	}
	
	public static void saveMoviesReviewsTokens(MovieUserReview mur, String path, String movieID) {
		BufferedWriter writer;
		
		String filenameMovie;
		String filepathMovies;
		
		String registroMovie = ""; 
	
		try
		{
			
			
				filenameMovie = String.format("%s_review_%04d.txt",movieID,mur.getId());
				filepathMovies = String.format("%s\\%s", path, filenameMovie);
								
				writer = new BufferedWriter(new FileWriter(filepathMovies, true));
				registroMovie = String.format("%s",mur.getTokens());
				writer.write(registroMovie);
				writer.close();
				System.out.println(String.format("%s review %d saved!", movieID,mur.getId()));
			
			
			
		}
		catch(IOException e)
		{ 
			e.printStackTrace();			
			return;
		}
	}

}
