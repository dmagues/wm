package com.crawler.test;

public class MovieApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 3 || args.length > 4) {
		      System.out.println("Parametros requeridos: ");
		      System.out.println("\t crawlStorageFolder \t(indica la ruta donde se almacenan los resultados)");
		      System.out.println("\t threadNumber \t(indica el numero de hilos a ejecutar)");
		      System.out.println("\t crawlDeph \t(indica la profundidad de enlaces a recorrer)");
		      System.out.println("\t (Opcional) maxNumberOfPages \t(indica el numero de paginas a procesar, valor predeterminado 1000)");
		      return;
		    }
		
		
		Integer maxPages = null;
		
		if (args[0].length()<3)
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t crawlStorageFolder \t(ruta incorrecta)");
			return;
		}
		
		if (!isBetween(args[1], 1, 5))
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t threadNumber \t(entre 1 y 5)");
			return;
		}
		
		if (!isBetween(args[2], 1, 10))
		{
			System.out.println("Parámetro Incorrecto: ");
			System.out.println("\t crawlDeph \t(entre 1 y 10)");
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
		
		
		
		MovieCrawlerController controller = new MovieCrawlerController(args[0],
				Integer.parseInt(args[1]),Integer.parseInt(args[2]), maxPages);		
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
