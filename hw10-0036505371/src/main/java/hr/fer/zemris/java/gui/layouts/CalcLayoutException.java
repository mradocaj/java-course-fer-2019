package hr.fer.zemris.java.gui.layouts;

/**
 * Razred koji modelira iznimnu situaciju pri radu u razredu {@link CalcLayout}.
 * 
 * @author Maja Radočaj
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka greške
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
