package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.Objects;

/**
 * Razred koji pretstavlja jedan token pri tokeniziranju i parsiranju u razredima {@link NameBuilderLexer} i 
 * {@link NameBuilderParser}.
 * Svaki token ima tip i vrijednost.
 * 
 * @author Maja Radočaj
 *
 */
public class NameBuilderToken {

	/**
	 * Tip tokena,
	 */
	private NameBuilderTokenType type;
	/**
	 * Vrijednsot tokena.
	 */
	private String value;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param type tip tokena
	 * @param value vrijednost tokena
	 * @throws NullPointerException ako je neka od danih vrijednosti <code>null</code>
	 */
	public NameBuilderToken(NameBuilderTokenType type, String value) {
		this.type = Objects.requireNonNull(type);
		this.value = Objects.requireNonNull(value);
	}
	
	/**
	 * Getter koji vraća tip tokena.
	 * @return
	 */
	public NameBuilderTokenType getType() {
		return type;
	}

	/**
	 * Getter koji vraća vrijednost tokena.
	 * @return
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
		NameBuilderToken other = (NameBuilderToken) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
