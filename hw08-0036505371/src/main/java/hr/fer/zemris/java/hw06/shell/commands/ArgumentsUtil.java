package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji nudi statičku metodu za parsiranje argumenata.
 * 
 * @author Maja Radočaj
 *
 */
public class ArgumentsUtil {

	/**
	 * Metoda koja prima ulazni String te iz njega vraća argumente.
	 * Argumenti su odvojeni razmacima ili mogu biti unutar navodnika - ako su unutar navodnika, omogućuje
	 * se escapeanje znaka '\' ili znaka '"' - oni mogu biti dio patha.
	 * U slučaju pogrešno danih argumenata, baca se {@link IllegalArgumentException}.
	 * 
	 * @param text ulazni tekst kojeg treba parsirati
	 * @return lista argumenata
	 * @throws IllegalArgumentException ako su argumenti krivo zadani ili je predana <code>null</code> vrijednost
	 */
	public static List<String> argumentParser(String text) {	
		if(text == null) {
			throw new IllegalArgumentException("Cannot parse null.");
		}
		List<String> list = new ArrayList<>();
		char[] chars = text.trim().toCharArray();
		int i = 0;
		StringBuilder sb = new StringBuilder();
		
		while(i < chars.length) {
			if(Character.isWhitespace(chars[i])) {
				i++;
				if(i == chars.length) break;
				continue;
			}
			
			if(chars[i] == '"') {
				if(sb.length() != 0) {
					list.add(sb.toString());
					sb = new StringBuilder();
				}
				i++;
				if(i == chars.length) {
					throw new IllegalArgumentException("String path must end with '\"'.");
				}
				
				while(chars[i] != '"') {
					if(chars[i] == '\\' && i + 1 < chars.length && (chars[i + 1] == '\\' || chars[i + 1] == '\"')) {
						i++;
					}
					sb.append(chars[i]);
					i++;
					if(i == chars.length) { 
						throw new IllegalArgumentException("String path must end with '\"'.");
					}
				}
				i++;
				if(i != chars.length && !Character.isWhitespace(chars[i])) {
					throw new IllegalArgumentException("Only whitespaces allowed after ending string with '\"'.");
				}
				list.add(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(chars[i]);
				i++;
				if(i == chars.length || Character.isWhitespace(chars[i])) {
					list.add(sb.toString());
					sb = new StringBuilder();
				}
			}
		}

		return list;
	}

}
