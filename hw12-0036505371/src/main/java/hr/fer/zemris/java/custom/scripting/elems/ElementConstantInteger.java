package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa sa int vrijednošću.
 * Nasljeđuje razred Element te nadjačava njegvu jedinu metodu - asText.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Int vrijednost elementa.
	 */
	private int value;

	/**
	 * Javni konstruktor razreda.
	 * Inicijalizira vrijednost članske varijable value.
	 * 
	 * @param value predana vrijednost
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Getter koji vraća int vrijednost elementa.
	 * 
	 * @return vrijednost elementa
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}
