package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Razred koji modelira čvor sintaksnog stabla te nasljeđuje Node.
 * Ovaj čvor predstavlja početni čvor stabla pri parsiranju u razredu {@link SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
