package hr.fer.zemris.java.custom.collections;

/**
 * Iznimka koja se javlja u slučaju pokušavaja pristupa elementima praznog
 * stoga.
 * 
 * @author Maja Radočaj
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Prazan konstruktor koji poziva konstruktor svog nadrazreda RuntimeException.
	 */
	public EmptyStackException() {
		super();
	}
}
