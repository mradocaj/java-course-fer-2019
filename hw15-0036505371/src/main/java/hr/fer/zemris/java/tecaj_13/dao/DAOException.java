package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Iznimna situacija pri čitanju iz baze podataka.
 * 
 * @author Maja Radočaj
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. 
	 * 
	 * @param message poruka
	 * @param cause uzrok
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message poruka greške
	 */
	public DAOException(String message) {
		super(message);
	}
}