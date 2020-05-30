package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Enumeracija koja navodi osnovne tipove tokena.
 * 
 * @author Maja Radočaj
 *
 */
public enum TokenType {
	
	/**
	 * Identifikator (String koji sadrži samo slova).
	 */
	IDENTIFICATOR,
	/**
	 * Logički operator.
	 */
	OPERATOR,
	/**
	 * String okružen navodnicima.
	 */
	STRING_LITERAL,
	/**
	 * Kraj ulaznih podataka.
	 */
	END;
}
