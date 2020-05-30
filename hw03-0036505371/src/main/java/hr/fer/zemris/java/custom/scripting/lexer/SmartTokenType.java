package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija koja navodi sve moguće tipove tokena.
 * 
 * @author Maja Radočaj
 *
 */
public enum SmartTokenType {

	/**
	 * Oznaka da više nema tokena.
	 */
	EOF,
	/**
	 * Ključna riječ FOR.
	 */
	FOR,
	/**
	 * Ključna riječ END.
	 */
	END,
	/**
	 * Ključna riječ EQU.
	 */
	EQU,
	/**
	 * Varijabla.
	 */
	VARIABLE,
	/**
	 * Funkcija.
	 */
	FUNCT,
	/**
	 * Oznaka za otvoreni tag.
	 */
	OPEN_TAG,
	/**
	 * Oznaka za zatvoreni tag.
	 */
	CLOSE_TAG,
	/**
	 * Integer vrijednost.
	 */
	INTEGER,
	/**
	 * Double vrijednost.
	 */
	DOUBLE,
	/**
	 * Tekst.
	 */
	TEXT,
	/**
	 * Znak operacije.
	 */
	OPERATION,
	/**
	 * String vrijednost.
	 */
	STRING;
}
