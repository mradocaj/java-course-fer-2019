package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Enumeracija koja sadrži tipove tokena pri tokeniziranju i parsiranju u razredima {@link NameBuilderLexer} i 
 * {@link NameBuilderParser}.
 * 
 * @author Maja Radočaj
 *
 */
public enum NameBuilderTokenType {

	/**
	 * Kraj ulaznog niza.
	 */
	END,
	/**
	 * Tekstualni dio.
	 */
	STRING, 
	/**
	 * Oznaka supstitucije.
	 */
	TAG
}
