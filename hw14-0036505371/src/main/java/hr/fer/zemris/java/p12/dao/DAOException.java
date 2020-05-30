package hr.fer.zemris.java.p12.dao;

/**
 * Iznimna situacija nastala pri radu sa bazom podataka.
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
	 */
	public DAOException() {
	}

	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka greške
	 * @param cause uzrok
	 * @param enableSuppression zastavica za onemogućavanje supresije
	 * @param writableStackTrace stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param message poruka greške
	 * @param cause	uzrok
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

	/**
	 * Konstruktor.
	 * 
	 * @param cause uzrok
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}