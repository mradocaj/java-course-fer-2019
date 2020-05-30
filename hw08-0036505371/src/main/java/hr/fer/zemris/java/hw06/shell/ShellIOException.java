package hr.fer.zemris.java.hw06.shell;

/**
 * Razred koji modelira iznimnu situaciju nastalu pri komunikaciji s korisnikom u programu {@link MyShell}.
 * 
 * @author Maja Radočaj
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defaultni konstruktor.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka greške
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
