package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Iznimka koja se javlja u slučaju greške pri parsiranju u razredu {@link SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptParserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prosljeđuje poruku greške konstruktoru nadrazreda.
	 * 
	 * @param message poruka greške
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
