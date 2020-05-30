package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Razred koji modelira čvor sintaksnog stabla te nasljeđuje Node. Ovaj čvor
 * predstavlja tekstualni dio pri parsiranju u razredu
 * {@link SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class TextNode extends Node {

	/**
	 * Tekst koji čvor pohranjuje.
	 */
	private String text;

	/**
	 * Konstruktor koji inicijalizira tekst. Baca {@link NullPointerException} ako
	 * je predana vrijednost teksta null.
	 * 
	 * @param text predana vrijednost teksta
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text);
		this.text = text;
	}

	/**
	 * Getter koji vraća pohranjeni tekst.
	 * 
	 * @return tekst
	 */
	public String getText() {
		return text;
	}

}
