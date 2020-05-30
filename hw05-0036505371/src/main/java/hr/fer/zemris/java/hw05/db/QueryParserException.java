package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji modelira iznimku koja se javlja pri parsiranju u razredu {@link QueryParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class QueryParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor.
	 */
	public QueryParserException() {
		super();
	}
	
	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka greške
	 */
	public QueryParserException(String message) {
		super(message);
	}
}
