package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Razred koji modelira iznimku koja se javlja pri tokeniziranju u razredu {@link QueryLexer}.
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
	 * Defaultni konstruktor.
	 */
	public QueryLexerException() {
		super();
	}
	
	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka grešles
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}
