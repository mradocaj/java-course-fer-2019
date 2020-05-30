package hr.fer.zemris.java.hw17.trazilica.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji dohvaća riječi vokabulara i pohranjuje informacije o njemu.
 * 
 * @author Maja Radočaj
 *
 */
public class Vocabulary {

	/**
	 * Riječi vokabulara.
	 */
	private Map<String, Integer> words = new HashMap<>();
	/**
	 * Putanja do dokumenta sa stop riječima.
	 */
	private static final String STOP_WORDS = "src/main/resources/hrvatski_stoprijeci.txt";
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param path putanja do direktorija sa dokumentima
	 */
	public Vocabulary(String path) {
		fillVocabulary(path);
	}

	/**
	 * Pomoćna metoda koja puni vokabular.
	 * 
	 * @param path putanja do direktorija sa dokumentima
	 * @throws RuntimeException u slučaju greške pri dohvatu dokumenata
	 */
	private void fillVocabulary(String path) {
		Path directory = null;
		try {
			directory = Paths.get(path);
		} catch(InvalidPathException ex) {
			throw new RuntimeException("Staza " + path + " nije ispravna staza.");
		}
		
		if(!Files.exists(directory)) {
			throw new RuntimeException("Direktorij " + path + " ne postoji.");
		}
		
		VocabularyFileVisitor visitor = new VocabularyFileVisitor();
		
		try {
			Files.walkFileTree(directory, visitor);
			Set<String> wordsSet = visitor.getWords();
			int k = 0;
			for(String word : wordsSet) {
				words.put(word, k);
				k++;
			}
		} catch(Exception ex) {
			throw new RuntimeException("Greška pri dohvatu vokabulara.");
		}
	}
	
	/**
	 * Metoda koja vraća vokabular.
	 * 
	 * @return vokabular
	 */
	public Map<String, Integer> getVocabulary() {
		return words;
	}
	
	/**
	 * Pomoćna struktura podataka za obilazak dokumenata i stvaranje vokabulara.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class VocabularyFileVisitor extends SimpleFileVisitor<Path> {
		/**
		 * Riječi vokabulara.
		 */
		private Set<String> words = new HashSet<>();
		/**
		 * Stop riječi.
		 */
		private List<String> stopWords;
		
		/**
		 * Konstruktor.
		 */
		public VocabularyFileVisitor() {
			try {
				stopWords = Files.readAllLines(Paths.get(STOP_WORDS));
			} catch(IOException ex) {
				throw new RuntimeException("Greška pri učitavanju stop riječi.");
			}
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
					new BufferedInputStream(Files.newInputStream(file)), 
					StandardCharsets.UTF_8));

			int c = br.read();
			while(c != -1) {	
				if(Character.isAlphabetic(c)) {
					StringBuilder sb = new StringBuilder();
					
					while(Character.isAlphabetic(c)) {
						if(c == -1) break;
						sb.append((char) c);
						c = br.read();
					}
					if(!stopWords.contains(sb.toString().toLowerCase())) {
						words.add(sb.toString().toLowerCase());
					}
				}
				
				c = br.read();
			}
			
			br.close();
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			
			throw new RuntimeException("Nemoguće pristupiti dokumentu " + file.toString() + ".");
		}
		
		/**
		 * Metoda koja vraća riječi vokabulara.
		 * 
		 * @return riječi vokabulara
		 */
		public Set<String> getWords() {
			return words;
		}
	}
}
