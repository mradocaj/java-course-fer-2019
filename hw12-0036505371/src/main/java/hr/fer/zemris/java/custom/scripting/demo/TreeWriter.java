package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program koji demonstrira rad metoda sučelja {@link INodeVisitor}.
 * Služi za ispis originalnog dokumenta iz onog parsiranog pomoću {@link SmartScriptParser}.
 * <p>Program prima jedan argument - putanju do tekstualne reprezentacije dokumenta
 * kojeg treba parsirati.
 * 
 * @author Maja Radočaj
 *
 */
public class TreeWriter {

	/**
	 * Glavni program
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		String docBody;
		if(args.length != 1) {
			System.out.println("Number of arguments should be 1!");
			return;
		}
		
		String filepath = args[0];
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
//			System.out.println(docBody); // Makni komentar ako zelis vidjeti ispis originalnog u
											// istom prozoru sa ispisom parsiranog
		} catch(IOException ex) {
			System.out.println("Cannot find file.\n");
			return;
		}
		
		SmartScriptParser p = null;
		try {
			p = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
		
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	/**
	 * Razred koji modelira visitora.
	 * Ovaj visitor služi za ispis parsiranog dokumenta.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * String builder.
		 */
		StringBuilder sb = new StringBuilder();
		
		@Override
		public void visitTextNode(TextNode node) {
			String text = node.getText();
			sb.append(text.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\{\\$", "\\\\{\\$"));
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			sb.append("{$ FOR ");
			appendElements(new Element[] { node.getVariable(), 
						node.getStartExpression(),
						node.getEndExpression(), 
						node.getStepExpression() });
			sb.append("$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			sb.append("{$=");
			appendElements(node.getElements());
			sb.append("$}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			createOriginalDocumentBody(node);
			System.out.println(sb.toString());
		}
		
		/**
		 * Pomoćna metoda koja stvara originalni dokument od danog čvora.
		 * 
		 * @param node čvor
		 */
		private void createOriginalDocumentBody(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				if(node.getChild(i) instanceof EchoNode) {
					visitEchoNode((EchoNode) node.getChild(i));
				} else if(node.getChild(i) instanceof TextNode) {
					visitTextNode((TextNode) node.getChild(i));
				} else {
					visitForLoopNode((ForLoopNode) node.getChild(i));
					createOriginalDocumentBody(node.getChild(i));
					sb.append("{$END$}");
				}
			}
		}
		
		/**
		 * Pomoćna metoda koja iz polja elemenata stvara originalni dokument.
		 * 
		 * @param elements elementi
		 */
		private void appendElements(Element[] elements) {
			for(int j = 0; j < elements.length; j++) {
				if(elements[j] instanceof ElementString) {
					sb.append("\"" + escapingString(elements[j]) + "\" ");
				} else if(elements[j] instanceof ElementFunction) {
					sb.append("@" + elements[j].asText() + " ");
				} else {
					sb.append(elements[j].asText() + " "); // ako nije string, normalno se ispiše
				}
			}
		}
		
		/**
		 * Pomoćna metoda koja prima parsirani element te vraća
		 * tekst tog elementa sa primjenjenim pravilima escapeanja.
		 * 
		 * @param element element
		 * @return originalni oblik elementa
		 */
		private static String escapingString(Element element) {
			String s = element.asText();
			return s.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"")
					.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r")
					.replaceAll("\t", "\\\\t");
		}
	}
}
