package com.crawler.wm;

import java.io.IOException;

public class MovieApp {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
			
		
		if (args.length < 3 || args.length > 4) {
		      System.out.println("Parametros requeridos: ");
		      System.out.println("\t crawlStorageFolder \t(Indica la ruta donde se almacenan los resultados)");
		      System.out.println("\t threadNumber \t(Indica el número de hilos a ejecutar)");
		      System.out.println("\t mode \t(Indica el modo de ejecución del crawler. Valores posibles 0:STANDARD, 1:MOVIELIST, 2:REVIEWS)");
		      //System.out.println("\t crawlDeph \t(indica la profundidad de enlaces a recorrer)");
		      System.out.println("\t (Opcional) maxNumberOfPages \t(Indica el número de páginas a procesar, valor predeterminado 1000)");
		      return;
		    }
		
		
		String storageFolder = args[0];
		String threads = args[1];
		String mode = args[2] ;
		Integer maxPages = null;
		
		if (storageFolder.length()<3)
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t crawlStorageFolder \t(ruta incorrecta)");
			return;
		}
		
		if (!isBetween(threads, 1, 5))
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t threadNumber \t(entre 1 y 5)");
			return;
		}
		
		if (!isBetween(mode, 0, 2))
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t mode \t(valores posibles 0:STANDARD, 1:MOVIELIST, 2:REVIEWS) ");
			return;
		}
		
		
		if (args.length == 4){
			
			
			if (!isBetween(args[3], -1, 1000))
			{
				System.out.println("Parámetro Incorrecto: ");
				System.out.println("\t maxNumberOfPages \t(entre 1 y 1000)");
				return;
			}
			
			maxPages = Integer.parseInt(args[3]);
			
		} 
		
		
		
		MovieCrawlerController controller = new MovieCrawlerController(storageFolder,
				Integer.parseInt(threads),Mode.fromInt(Integer.parseInt(mode)), maxPages);
		
		controller.start();

	}
	
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	public static boolean isBetween(String arg, int inicio, int fin){
		if (!isNumeric(arg)) return false;
		return Integer.parseInt(arg)>=inicio && Integer.parseInt(arg)<=fin;
	}

}
