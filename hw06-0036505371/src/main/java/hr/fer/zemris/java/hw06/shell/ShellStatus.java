package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeracija koja definira moguće statuse nakon izvršavanja naredbe.
 * 
 * @author Maja Radočaj
 *
 */
public enum ShellStatus {

	/**
	 * Nastaviti s radom ljuske nesmetano.
	 */
	CONTINUE,
	/**
	 * Prekinuti rad.
	 */
	TERMINATE;
}
