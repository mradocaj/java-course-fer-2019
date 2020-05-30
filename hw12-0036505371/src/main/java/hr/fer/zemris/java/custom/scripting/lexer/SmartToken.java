package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Razred koji modelira token kao određeni skup znakova sa određenim tipom.
 * Svaki token ima svoj tip i vrijednost.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartToken {

	/**
	 * Tip tokena.
	 */
	private SmartTokenType type;
	/**
	 * Vrijednost tokena.
	 */
	Object value;
	
	/**
	 * Konstruktor koji inicijalizira token. Predani tip ne smije biti null.
	 * Ako se preda null vrijednost za tip, baca se {@link NullPointerException}.
	 * 
	 * @param type tip tokena
	 * @param value vrijednost tokena
	 * @throws NullPointerException ako je predani tip tokena null
	 */
	public SmartToken(SmartTokenType type, Object value) {
		Objects.requireNonNull(type);
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter koji vraća vrijednost tokena.
	 * 
	 * @return vrijednost tokena
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Getter koji vraća tip tokena.
	 * 
	 * @return tip tokena
	 */
	public SmartTokenType getType() {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmartToken other = (SmartToken) obj;
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
