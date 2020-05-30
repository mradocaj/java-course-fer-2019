package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeracija koja definira 4 različita tipa tokena.
 * 
 * @author Maja Radočaj
 *
 */
public enum TokenType {

	/**
	 * Oznaka za kraj dokumenta.
	 */
	EOF,
	/**
	 * Riječ, tj. niz slova.
	 */
	WORD,
	/**
	 * Broj.
	 */
	NUMBER,
	/**
	 * Simbol.
	 */
	SYMBOL;
}
