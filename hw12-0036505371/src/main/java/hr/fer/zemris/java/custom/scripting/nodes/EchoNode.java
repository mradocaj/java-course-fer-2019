package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Razred koji modelira čvor sintaksnog stabla te nasljeđuje Node.
 * Ovaj čvor predstavlja labelu echo pri parsiranju u razredu {@link SmartScriptParser}.
 * Labela može imati više elemenata tipa Element.
 * 
 * @author Maja Radočaj
 *
 */
public class EchoNode extends Node {

	/**
	 * Elementi labele.
	 */
	private Element[] elements;

	/**
	 * Konstruktor koji inicijalizira vrijednost elements.
	 * Ako su predani elementi null, baca se {@link NullPointerException}.
	 * 
	 * @param elements predani elementi labele
	 * @throws NullPointerException
	 */
	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements);
		this.elements = elements;	
	}

	/**
	 * Getter koji vraća  polje elemenata.
	 * 
	 * @return polje elemenata
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
