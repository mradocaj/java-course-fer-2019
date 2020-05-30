package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Razred koji modelira token korišten pri tokeniziranju u razredu {@link QueryLexer}.
 * Svaki token ima svoj tip i vrijednost.
 * 
 * @author Maja Radočaj
 *
 */
public class Token {

	/**
	 * Tip tokena.
	 */
	private TokenType type;
	/**
	 * Vrijednost tokena.
	 */
	String value;
	
	/**
	 * Konstruktor koji inicijalizira tip i vrijednost tokena.
	 * Predane vrijednosti ne smiju biti <code>null</code>.
	 * 
	 * @param type tip tokena
	 * @param value vrijednost tokena
	 * @throws NullPointerException ako je tip ili vrijednost tokena <code>null</code>
	 */
	public Token(TokenType type, String value) {
		this.type = Objects.requireNonNull(type);
		this.value = Objects.requireNonNull(value);
	}

	/**
	 * Getter koji vraća tip tokena.
	 * 
	 * @return tip tokena
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter koji vraća vrijednost tokena.
	 * 
	 * @return vrijednost tokena
	 */
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
}
