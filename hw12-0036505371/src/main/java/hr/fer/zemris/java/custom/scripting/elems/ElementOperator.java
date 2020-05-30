package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa u obliku operatora.
 * Nasljeđuje razred Element te nadjačava njegvu jedinu metodu - asText.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementOperator extends Element {

	/**
	 * Simbol trenutnog operatora.
	 */
	private String symbol;

	/**
	 * Javni konstruktor razreda.
	 * Inicijalizira vrijednost članske varijable symbol.
	 * 
	 * @param symbol predana vrijednost
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Getter koji vraća simbol operatora.
	 * 
	 * @return simbol operatora
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}
