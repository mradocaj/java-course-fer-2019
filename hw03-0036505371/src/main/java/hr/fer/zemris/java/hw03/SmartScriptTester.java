package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Razred koji služi za demonstraciju rada parsera. Program se mora pokrenuti uz
 * jedan argument - adresu dokumenta.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptTester {

	/**
	 * Glavni program.
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
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody }

	}

	/**
	 * Metoda koja iz danog početnog čvora sintaksnog stabla konstruira dokument
	 * koji je bio predložak za stvaranje stabla.
	 * 
	 * @param document čvor sintaksnog stabla
	 * @return String reprezentacija originalnog dokumenta
	 */
	public static String createOriginalDocumentBody(Node document) {
		String string = "";
		for(int i = 0; i < document.numberOfChildren(); i++) {
			if(document.getChild(i) instanceof EchoNode) {
				Element[] elements = ((EchoNode) document.getChild(i)).getElements(); 
				// dohvaćamo elemente čvora
				string += "{$=" + printElements(elements) + " $}";
				
			} else if(document.getChild(i) instanceof TextNode) {
				string += printText(((TextNode) document.getChild(i)).getText());
				
			} else {
				ForLoopNode forNode = (ForLoopNode) document.getChild(i);
				string += "{$ FOR " + printElements(new Element[] { forNode.getVariable(), 
						forNode.getStartExpression(),
						forNode.getEndExpression(), 
						forNode.getStepExpression() }) + " $}";
				string = string + createOriginalDocumentBody(document.getChild(i)) + "{$END$}";
			}
		}
		return string;
	}

	/**
	 * Metoda koja uređuje ispis za stringove koji sadrže neki od elemenata koji se
	 * treba escapeati.
	 * 
	 * @param element element koji sadrži tekst
	 * @return novi tekst koji će se ponovno moći parsirati
	 */
	private static String escapingString(Element element) {
		String s = element.asText();
		return s.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\"")
				.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r")
				.replaceAll("\t", "\\\\t");
	}

	/**
	 * Pomoćna metoda koja uređuje ispis unutar ECHO ili FOR tagova. Ako se naiđe na
	 * jedan od znakova koji treba escapeati ('"', '\', '\n', '\r' ili '\t'), poziva
	 * se metoda koja uređuje ispis.
	 * 
	 * @param elements polje elemenata koje treba ispisati
	 * @return uređeni ispis
	 */
	private static String printElements(Element[] elements) {
		String string = "";
		
		for(int j = 0; j < elements.length; j++) {
			if(elements[j] instanceof ElementString) {
				String elementString = "\""; // ako se radi o ElementStringu,
												// mora se ispisati sa početnim navodnicima
				elementString += escapingString(elements[j]);
				elementString += "\""; // završava ispis stringa u tagu, dodaju se navodnici na kraju
				string += elementString + " ";
				
			} else if(elements[j] instanceof ElementFunction) {
				string += "@" + elements[j].asText() + " ";
				
			} else {
				string += elements[j].asText() + " "; // ako nije string, normalno se ispiše
			}
		}
		return string;
	}

	/**
	 * Pomoćna metoda koja uređuje ispis unutar tekstualnog dijela dokumenta. Ako se
	 * pojavi neki od znakova koji treba escapeati, umetne se znak \ pri ispisu.
	 * 
	 * @param text tekst koji treba urediti
	 * @return uređeni tekst koji se ponovno može parsirati
	 */
	private static String printText(String text) {
		return text.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\{\\$", "\\\\{\\$");
	}
}
