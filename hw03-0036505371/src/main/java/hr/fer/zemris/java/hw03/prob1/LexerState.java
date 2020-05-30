package hr.fer.zemris.java.hw03.prob1;

/**
 * Definirani načini grupiranja znakova u razredu Lexer.
 * 
 * @author Maja Radočaj
 *
 */
public enum LexerState {

	/**
	 * Osnovni, pretpostavljeni način grupiranja.
	 */
	BASIC,
	/**
	 * Posebni način rada u kojem Lexer sve tretira kao jednu riječ.
	 */
	EXTENDED;
}
