package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa u obliku Stringa. Nasljeđuje razred Element te
 * nadjačava njegvu jedinu metodu - asText. Pohranjuje vrijednosti tokena pri
 * parsiranju u razredu
 * {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementString extends Element {

	/**
	 * Vrijednost Stringa.
	 */
	private String value;

	/**
	 * Javni konstruktor razreda. Inicijalizira vrijednost članske varijable value
	 * 
	 * @param value predana vrijednost
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Getter koji vraća vrijednost elementa.
	 * 
	 * @return String vrijednost elementa
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return value;
	}

}
