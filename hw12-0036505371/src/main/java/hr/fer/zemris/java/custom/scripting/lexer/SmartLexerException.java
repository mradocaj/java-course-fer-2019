package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred koji modelira iznimku koja se javlja u slučaju pogreške pri tokeniziranju u razredu {@link SmartScriptLexer}.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prosljeđuje poruku greške konstruktoru nadrazreda.
	 * 
	 * @param message poruka greške
	 */
	public SmartLexerException(String message) {
		super(message);
	}
}
