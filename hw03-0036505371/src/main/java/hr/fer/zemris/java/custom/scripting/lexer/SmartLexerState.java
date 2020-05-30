package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija koja navodi stanja u kojima se lexer može naći.
 * 
 * @author Maja Radočaj
 *
 */
public enum SmartLexerState {

	/**
	 * Stanje kad lexer čita znakove unutar nekakve labele.
	 */
	TAG,
	/**
	 * Stanje kad lexer čita znakove koje predstavljaju tekst.
	 */
	TEXT;
}
