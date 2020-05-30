package hr.fer.zemris.java.hw17.trazilica.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji posjećuje dokumente te pohranjuje informacije o njima.
 * 
 * @author Maja Radočaj
 *
 */
public class VectorFileVisitor extends SimpleFileVisitor<Path>{
	
	/**
	 * Riječi vokabulara.
	 */
	private Map<String, Integer> words;
	/**
	 * Pomoćni idf vektor.
	 */
	private Map<String, Integer> idfHelp = new HashMap<>();
	/**
	 * Idf vektor.
	 */
	private double[] idf;
	/**
	 * Dokumenti.
	 */
	private Set<DocumentVector> documents = new HashSet<>();
	
	/**
	 * Konstruktor.
	 * 
	 * @param words riječi vokabulara
	 */
	public VectorFileVisitor(Map<String, Integer> words) {
		this.words = words;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		DocumentVector document = new DocumentVector(file.toString(), words.size());

		BufferedReader br = new BufferedReader(
				new InputStreamReader(
				new BufferedInputStream(Files.newInputStream(file)), 
				StandardCharsets.UTF_8));
		Set<String> occuringWords = new HashSet<>();
		
		int c = br.read();
		while(c != -1) {	
			if(Character.isAlphabetic(c)) {
				StringBuilder sb = new StringBuilder();
				
				while(Character.isAlphabetic(c)) {
					if(c == -1) break;
					sb.append((char) c);
					c = br.read();
				}
				
				String word = sb.toString().toLowerCase();
				if(words.containsKey(word)) {
					int index = words.get(word);
					if(!occuringWords.contains(word)) {
						occuringWords.add(word);
						idfHelp.putIfAbsent(word, 0);
						idfHelp.put(word, idfHelp.get(word) + 1);
						document.setTf(index, 1);
					} else {
						document.setTf(index, document.getTf(index) + 1);
					}
				}
			}
			
			c = br.read();
		}
		
		documents.add(document);
		br.close();
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Getter koji vraća broj dokumenata.
	 * 
	 * @return broj dokumenata
	 */
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * Getter koji vraća idf vektor.
	 * 
	 * @return idf vektor
	 */
	public double[] getIdf() {
		if(idf == null) {
			computeIdf();
		}
		return idf;
	}

	/**
	 * Getter koji vraća dokumente.
	 * 
	 * @return dokumente
	 */
	public Set<DocumentVector> getDocuments() {
		return documents;
	}
	
	/**
	 * Pomoćna metoda za računanje idf vektora.
	 */
	private void computeIdf() {
		idf = new double[words.size()];
		idfHelp.forEach((k, v) -> idf[words.get(k)] = Math.log10(documents.size() / v));
	}

}
