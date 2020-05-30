package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Sučelje koje modelira visitora u Visitor design pattern.
 * <p>Ovaj visitor posjećuje čvorove nastale pri parsiranju u razredu {@link SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public interface INodeVisitor {

	/**
	 * Metoda koja prima tekstualni čvor 
	 * (čvor nastao pri parsiranju koji sadrži tekst) te nad njime vrši operacije.
	 * 
	 * @param node tekstualni čvor
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Metoda koja prima čvor for petlje 
	 * (čvor nastao pri parsiranju koji sadrži for petlju) te nad njime vrši operacije.
	 * 
	 * @param node tekstualni čvor
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Metoda koja prima Echo čvor 
	 * (čvor nastao pri parsiranju koji sadrži varijable) te nad njime vrši operacije.
	 * 
	 * @param node echo čvor
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Metoda koja prima čvor dokumenta 
	 * (čvor nastao pri parsiranju koji sadrži sve druge čvorove) te nad njime vrši operacije.
	 * 
	 * @param node čvor dokumenta
	 */
	public void visitDocumentNode(DocumentNode node);
}
