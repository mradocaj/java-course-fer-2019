package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.util.DocumentVector;
import hr.fer.zemris.java.hw17.trazilica.util.VectorFileVisitor;
import hr.fer.zemris.java.hw17.trazilica.util.Vocabulary;

/**
 * Program koji omogućava korisniku provjeravanje sličnosti i pretraživanje
 * dokumenata na temelju vokabulara stvorenog pri pokretanju programa.
 * <p>Izlaz iz programa obavlja se narebom <code>exit</code>.
 * 
 * <p>Program se pokreće uz jedan argument - putanju do direktorija sa dokumentima.
 * 
 * @author Maja Radočaj
 *
 */
public class Konzola {

	/**
	 * Narerba za izlaz iz programa.
	 */
	private static final String EXIT = "exit";
	/**
	 * Naredba za novi upit.
	 */
	private static final String QUERY = "query";
	/**
	 * Naredba za ispisivanje rezultata prethodnog upita.
	 */
	private static final String RESULTS = "results";
	/**
	 * Naredba za ispisivanje teksta resultata na određenom indeksu.
	 */
	private static final String TYPE = "type";
	/**
	 * Rezultati.
	 */
	private static List<Result> results;
	/**
	 * Riječi u vokabularu i njihove pozicije.
	 */
	private static Map<String, Integer> words;
	
	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Program se pokreće uz jedan argument - putanju do dokumenata!");
			return;
		}
		Vocabulary vocabulary = null;
		try {
			vocabulary = new Vocabulary(args[0]);
		} catch(RuntimeException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		words = vocabulary.getVocabulary();
		System.out.println("Veličina riječnika je " + words.size() + " riječi.");
		
		VectorFileVisitor visitor = new VectorFileVisitor(words);
		try{
			Files.walkFileTree(Paths.get(args[0]), visitor);
		} catch(IOException ex) {
			System.out.println("Greška pri stvaranju vektora.");
			return;
		}
		
		double[] idf = visitor.getIdf();
		List<DocumentVector> documents = new ArrayList<>(visitor.getDocuments());

		for(int i = 0; i < documents.size(); i++) {
			documents.get(i).computeTfIdf(idf);
		}

		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.printf("\nEnter command > ");
			String input = sc.nextLine().trim();
			
			if(input.equals(EXIT)) {
				System.out.println("Kraj rada.");
				break;
				
			} else if(input.startsWith(QUERY)) {
				query(input, idf, documents);
				
			} else if(input.equals(RESULTS)) {
				results();
			
			} else if(input.startsWith(TYPE)) { 
				type(input);
				
			} else {
				System.out.println("Nepoznata naredba.");
			}
		}
		
		sc.close();
		return;
	}

	/**
	 * Naredba koja ispisuje rezultat na danom indeksu.
	 * 
	 * @param input linija naredbe
	 */
	private static void type(String input) {
		if(results == null) {
			System.out.println("Nijedan upit još nije izveden.");
			return;
		}
		 String parts[] = input.split("\\s+");
		 if(parts.length != 2) {
			 System.out.println("Potreban jedan argument uz naredbu.");
			 return;
		 }
		 if(!parts[0].equals(TYPE)) {
			 System.out.println("Nepoznata naredba.");
			 return;
		 }
		 
		 try {
			 int index = Integer.parseInt(parts[1]);
			 Result result = results.get(index);
			 System.out.println("------------------------------------"
			 		+ "---------------------------------------------");
			 System.out.println("Dokument: " + result.getPath());
			 printDocument(result.getPath());
			 
		 } catch(NumberFormatException | IndexOutOfBoundsException ex) {
			 System.out.println("Argument mora biti broj - indeks rezultata upita.");
		 } 
	}

	/**
	 * Naredba koja ispisuje rezultate prethodnog upita (ako takav postoji).
	 */
	private static void results() {
		if(results == null) {
			System.out.println("Nijedan upit još nije izveden.");
		} else {
			for(int i = 0; i < results.size(); i++) {
				System.out.println(String.format("[%d] (%.4f) %s", 
						i, results.get(i).getCos(), results.get(i).getPath()));
			}
		}
	}

	/**
	 * Naredba koja prima upit i ispisuje rezultat pretrage dokumenata.
	 * 
	 * @param input tekst naredbe
	 * @param idf pomoćni vektor nastao analizom vektora
	 * @param documents učitani dokumenti
	 */
	private static void query(String input, double[] idf, List<DocumentVector> documents) {
		String[] parts = input.split("\\s+");
		if(!parts[0].equals(QUERY)) {
			System.out.println("Nepoznata naredba.");
			return;
		}
		if(parts.length == 1) {
			System.out.println("Naredba očekuje argumente.");
			return;
		}
		
		List<String> inputWords = new ArrayList<>();
		int[] tfInput = new int[words.size()];
		
		for(int i = 1; i < parts.length; i++) {
			if(words.containsKey(parts[i])) {
				inputWords.add(parts[i]);
				tfInput[words.get(parts[i])]++;
			}
		}
		
		if(inputWords.size() == 0) {
			System.out.println("Predane riječi nisu dio vokabulara.");
			return;
		}
		
		System.out.println("Query is: [" + String.join(", ", inputWords) + "]");
		
		DocumentVector query = new DocumentVector("query", words.size());
		query.setTf(tfInput);
		query.computeTfIdf(idf);
		
		List<Result> tempResults = new ArrayList<>();
		for(DocumentVector document : documents) {
			double cos = computeCos(document.getTfIdf(), query.getTfIdf());
			tempResults.add(new Result(cos, document.getPath()));
		}
		
		tempResults.sort((l, r) -> Double.compare(r.getCos(), l.getCos()));
		results = new ArrayList<>();

		System.out.println("Najboljih 10 rezultata:");
		for(int i = 0; i < 10; i++) {
			Result res = tempResults.get(i);
			if(res.getCos() > 0) {
				results.add(res);
				System.out.println(String.format("[%d] (%.4f) %s", 
						i, res.getCos(), res.getPath()));
			}
		}
	}

	/**
	 * Pomoćna metoda za ispis teksta dokumenata.
	 * 
	 * @param path putanja do dokumenta
	 */
	private static void printDocument(String path) {
		try {
			System.out.println("------------------------------------"
			 		+ "---------------------------------------------");

			List<String> lines = Files.readAllLines(Paths.get(path));
			lines.forEach(System.out::println);

			System.out.println("------------------------------------"
			 		+ "---------------------------------------------");
			
		} catch(IOException ex) {
			System.out.println("Greška pri čitanju datoteke.");
		}
		
	}

	/**
	 * Metoda za izračun sličnosti dokumenata (kosinusa).
	 * 
	 * @param first prvi vektor
	 * @param second drugi vektor
	 * @return kosinus
	 */
	private static double computeCos(double[] first, double[] second) {
		double product = 0;
		double normFirst = 0;
		double normSecond = 0;
		
		for(int i = 0; i < first.length; i++) {
			product += first[i] * second[i];
			normFirst += first[i] * first[i];
			normSecond += second[i] * second[i];
		}
	
		return product / (Math.sqrt(normFirst) * Math.sqrt(normSecond));
	}
	
	/**
	 * Pomoćna struktura podataka za pohranu rezultata analize upita.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class Result {
		/**
		 * Kosinus.
		 */
		private double cos;
		/**
		 * Putanja do dokumenta.
		 */
		private String path;
		
		/**
		 * Konstruktor.
		 * 
		 * @param cos kosinus
		 * @param path putanja
		 */
		public Result(double cos, String path) {
			this.cos = cos;
			this.path = path;
		}

		/**
		 * Getter koji vraća kosinus.
		 * 
		 * @return kosinus
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * Getter koji vraća putanju rezultata.
		 * 
		 * @return putanja rezultata
		 */
		public String getPath() {
			return path;
		}
	}
}

