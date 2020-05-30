package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa sa double vrijednošću.
 * Nasljeđuje razred Element te nadjačava njegvu jedinu metodu - asText.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Double vrijednost elementa.
	 */
	private double value;

	/**
	 * Javni konstruktor razreda.
	 * Inicijalizira vrijednost članske varijable value.
	 * 
	 * @param value predana vrijednost
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Getter koji vraća double vrijednost elementa.
	 * 
	 * @return vrijednost elementa
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
