package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Klasa koje modelira osnovni element. 
 * Postoji samo jedna metoda koja vraća String reprezentaciju elementa - u ovom razredu vraća se samo prazni String.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class Element {

	/**
	 * Metoda koja vraća element u obliku Stringa.
	 * 
	 * @return String reprezentaciju elementa
	 */
	public String asText() {
		return "";
	}
}
