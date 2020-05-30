package hr.fer.zemris.java.custom.scripting.exec.util;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;

/**
 * Razred koji nudi statičke metode za parsiranje i provjeravanje argumenata u razredu {@link ObjectMultistack}.
 * 
 * @author Maja Radočaj
 *
 */
public class ArgumentsUtil {

	/**
	 * Metoda koja prima dva argumenta - dvije vrijednosti, te ih potom parsira.
	 * Ako je jedna od danih vrijednosti <code>Double</code>, i druga vrijednost će biti <code>Double</code>.
	 * U slučaju greške, baca se {@link RuntimeException}.
	 * 
	 * @param first prva vrijednost
	 * @param second druga vrijednost
	 * @return parsirana prva vrijednost
	 * @throws RuntimeException ako argument nije validan
	 */
	public static Object getArgument(Object first, Object second) {
		checkArguments(first, second);
		Object firstParsed = parseArgument(first);
		Object secondParsed = parseArgument(second);
		if(secondParsed instanceof Double && !(firstParsed instanceof Double)) {
			return Double.valueOf((Integer) firstParsed);
		}
		return firstParsed;
	}
	
	/**
	 * Pomoćna metoda za parsiranje argumenata.
	 * <code>Null</code> vrijednost tumači se kao <code>Integer</code> sa vrijednosti 0.
	 * <code>String</code> mora pretstavljati decimalni ili cijeli broj.
	 * Ako ne predstavlja, baca se {@link RuntimeException}.
	 * 
	 * @param argument vrijednost koju treba parsirati
	 * @return parsirana vrijednost
	 * @throws RuntimeException ako se <code>String</code> ne može parsirati
	 */
	private static Object parseArgument(Object argument) {
		if(argument == null) return Integer.valueOf(0);
		if(argument instanceof String) {
			try {
				if(((String) argument).contains(".") || ((String) argument).contains("E")) {
					return Double.parseDouble((String) argument);
				} else {
					return Integer.parseInt((String) argument);
				}
			} catch (NumberFormatException ex) {
				throw new RuntimeException("Expected String representation of Double or Integer.");
			}
		}
		return argument;
	}
	
	/**
	 * Metoda koja provjerava jesu li dani argumenti <code>String</code>, <code>Integer</code>, <code>Double</code>
	 * ili <code>null</code>. 
	 * Ako nisu, baca se {@link RuntimeException}.
	 * 
	 * @param argument predana vrijednost koju treba provjeriti
	 * @param value trenutna vrijednost koju treba provjeriti
	 */
	public static void checkArguments(Object argument, Object value) {
		if (isNotValidArgument(argument) || isNotValidArgument(value)) {
			throw new RuntimeException("Arguments of methods and current values must be null or instances of Integer,"
					+ " Double or String.");
		}
	}
	
	/**
	 * Metoda koja provjerava je li dani argument <code>String</code>, <code>Integer</code>, <code>Double</code>
	 * ili <code>null</code>. 
	 * 
	 * @param argument predani argument
	 * @return <code>false</code> ako je argument instanca jednog od navedenih razreda, <code>true</code> ako nije
	 */
	private static boolean isNotValidArgument(Object argument) {
		return argument != null && !(argument instanceof Integer) && !(argument instanceof Double)
				&& !(argument instanceof String);
	}
}
