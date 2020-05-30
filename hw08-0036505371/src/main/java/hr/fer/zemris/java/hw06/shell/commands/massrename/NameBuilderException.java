package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Razred koji modelira iznimnu situaciju pri parsiranju i tokeniziranju imena datoteka.
 * 
 * @author Maja Radočaj
 *
 */
public class NameBuilderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Javni konstruktor.
	 */
	public NameBuilderException() {
		super();
	}
	
	/**
	 * Konstruktor koji prima poruku greške.
	 * 
	 * @param message poruka greške
	 */
	public NameBuilderException(String message) {
		super(message);
	}
}
