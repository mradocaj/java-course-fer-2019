package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Razred koji modelira čvor sintaksnog stabla te nasljeđuje Node. Ovaj čvor
 * predstavlja labelu FOR pri parsiranju u razredu {@link SmartScriptParser}.
 * Labela mora imati varijablu, početni izraz i završni izraz. Izraz inkrementa
 * može i ne mora imati.
 * 
 * @author Maja Radočaj
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Varijabla for petlje.
	 */
	private ElementVariable variable;
	/**
	 * Početni izraz for petlje.
	 */
	private Element startExpression;
	/**
	 * Završni izraz for petlje.
	 */
	private Element endExpression;
	/**
	 * Inkrement for petlje.
	 */
	private Element stepExpression;

	/**
	 * Konstruktor za inicijaliziranje for petlje. Baca se
	 * {@link NullPointerException} ako je za ijedan od prva tri izraza dana null
	 * vrijednost.
	 * 
	 * @param variable        varijabla
	 * @param startExpression početni izraz
	 * @param endExpression   završni izraz
	 * @param stepExpression  inkrement
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		Objects.requireNonNull(variable);
		Objects.requireNonNull(startExpression);
		Objects.requireNonNull(endExpression);
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter koji vraća varijablu for petlje.
	 * 
	 * @return varijabla
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter koji vraća početni izraz for petlje.
	 * 
	 * @return početni izraz
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter koji vraća završni izraz for petlje.
	 * 
	 * @return završni izraz
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter koji vraća inkrement for petlje.
	 * 
	 * @return inkrement
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
