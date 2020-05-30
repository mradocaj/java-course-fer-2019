package hr.fer.zemris.java.hw03.prob1;

/**
 * Iznimka koja se javlja u slučaju pogreške pri generiranju tokena u razredu
 * Lexer.
 * 
 * @author Maja Radočaj
 *
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prosljeđuje poruku greške svom nadrazredu.
	 * 
	 * @param message poruka greške
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}
