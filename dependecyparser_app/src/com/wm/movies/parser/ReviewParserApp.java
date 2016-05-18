package com.wm.movies.parser;

import java.util.List;

import com.wm.movies.data.MovieUserReview;
import com.wm.movies.data.MovieUserReviewReader;
import com.wm.movies.parser.dependency.DependencyAnalyzedSentence;

public class ReviewParserApp {

	public static  void main(String[] args)
	{
		if (args.length < 2 || args.length > 3) {
		      System.out.println("Parametros requeridos: ");
		      System.out.println("\t crawlStorageFolder \t(Indica la ruta donde se almacenan los resultados)");
		      System.out.println("\t movieID \t(Indica el identificador de la pelicula)");
		      System.out.println("\t mode \t(modificador Indica el modo de ejecucion 1:bow 2:dep 3:dep&for (default) )");
		      return;
		    }
		
		String storageFolder = args[0];
		String movieID = args[1];
		
		String mode="";
		if(args.length==3)
			mode = args[2];
		
		
		
		DependencyAnalyzer analyzer = new DependencyAnalyzer();
		
		switch(mode)
		{
			case "1": //palabras originales (conjugaciones y plurales)
				analyzer.setIncludeDependecy(false);
				analyzer.setIncludeWordForm(false);
				break;
			case "2": //dependencias
				analyzer.setIncludeDependecy(true);
				analyzer.setIncludeWordForm(false);
				break;
		} // default incluye dependencias y formas normales de palabras
		
		List<MovieUserReview> reviews = MovieUserReviewReader
				.readReviewsFile(storageFolder, movieID);

		for(MovieUserReview mur:reviews.subList(1, 100))
		{
			List<DependencyAnalyzedSentence> sentences = analyzer.analyzeSentences(mur.getReview());
			mur.setSentences(sentences);
			MovieUserReviewReader.saveMoviesReviewsTokens(mur, storageFolder, movieID);			
		}

		
	}
}
