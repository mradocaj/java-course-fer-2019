package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Razred koji modelira token kao određeni skup znakova.
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
	Object value;

	/**
	 * Konstruktor koji generira novi token. Tip ne smije biti null, inače se baca
	 * {@link NullPointerException}.
	 * 
	 * @param type  tip tokena
	 * @param value vrijednost tokena
	 * @throws NullPointerException ako je predani tip tokena null
	 */
	public Token(TokenType type, Object value) {
		Objects.requireNonNull(type);
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter za vrijednost tokena.
	 * 
	 * @return vrijednost tokena.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter za tip tokena.
	 * 
	 * @return tip tokena.
	 */
	public TokenType getType() {
		return type;
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
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if(type != other.type)
			return false;
		if(value == null) {
			if(other.value != null)
				return false;
		} else if(!value.equals(other.value))
			return false;
		return true;
	}

}
