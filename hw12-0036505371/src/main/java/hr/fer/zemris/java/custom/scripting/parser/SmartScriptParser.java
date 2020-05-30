package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Razred koji omogućuje parsiranje danog teksta prema pravilima utvrđenim u 3.
 * zadataku 3. domaće zadaće. Razred kao člansku varijablu ima primjerak
 * {@link SmartScriptLexer}-a te pomoću njega generira nove tokene. Potom na
 * temelju tih tokena stvara sintaksno stablo.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer za generiranje tokena.
	 */
	private SmartScriptLexer lexer;
	/**
	 * Početni čvor sintaksnog stabla.
	 */
	private DocumentNode documentNode;

	/**
	 * Konstruktor koji inicijalizira lexer i započinje parsiranje pozivom metode
	 * parse. Ako je predani String null, baca se {@link NullPointerException}. Ako
	 * prilikom parsiranja dođe do greške, baca se
	 * {@link SmartScriptParserException}.
	 * 
	 * @param documentBody tekst kojeg treba parsirati
	 * @throws NullPointerException       ako je predani tekst null vrijednost
	 * @throws SmartScriptParserException ako dođe do greške prilikom parsiranja
	 */
	public SmartScriptParser(String documentBody) {
		lexer = new SmartScriptLexer(documentBody);
		documentNode = new DocumentNode();
		try {
			documentNode = parse();
		} catch(Exception e) {
			throw new SmartScriptParserException(e.getMessage());
		}
	}

	/**
	 * Getter koji vraća početni čvor sintaksnog stabla.
	 * 
	 * @return početni čvor sintaksnog stabla
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Metoda kojom započinje parsiranje. Kao pomoć pri stvaranju sintaksnog stabla
	 * koristi se stog. Ukoliko se ne poštuju definirana pravila, baca se
	 * {@link SmartScriptParserException}.
	 * 
	 * @return početni čvor stabla
	 * @throws SmartScriptParserException ako se ne poštuju pravila gramatike
	 */
	private DocumentNode parse() {
		ObjectStack stack = new ObjectStack();
		stack.push(documentNode);

		while(true) {
			lexer.nextToken();
			if(tokenIsType(SmartTokenType.EOF)) { // ako smo pročitali sve, završavamo parsiranje
				break;
			}
			
			if(tokenIsType(SmartTokenType.TEXT)) { // ako je naš token tekst, stvaramo novi čvor za tekst
				TextNode textNode = new TextNode((String) lexer.getToken().getValue());
				((Node) stack.peek()).addChildNode(textNode);
				
			} else { // ako naš token nije tekst, onda smo sigurno u nekom tag-u
				lexer.setState(SmartLexerState.TAG);
				lexer.nextToken(); // preskače OPEN_TAG

				if(tokenIsType(SmartTokenType.FOR)) {
					lexer.nextToken(); // preskače FOR, ide na sljedeći
					ForLoopNode forNode = parseFor();
					((Node) stack.peek()).addChildNode(forNode);
					stack.push(forNode);
					
				} else if(tokenIsType(SmartTokenType.EQU) || tokenIsType(SmartTokenType.VARIABLE)) {
					if(tokenIsType(SmartTokenType.EQU)) {
						lexer.nextToken(); // preskače =, ide na sljedeći
					}
					EchoNode echoNode = parseEcho();
					((Node) stack.peek()).addChildNode(echoNode);
					
				} else if(tokenIsType(SmartTokenType.END)) {
					stack.pop();
					if(stack.isEmpty()) {
						throw new SmartScriptParserException(
								"ERROR: Document contains more " + "END tags than FOR tags.");
					}
					lexer.nextToken(); // preskače CLOSE_TAG
					lexer.setState(SmartLexerState.TEXT);
					
				} else {
					throw new SmartScriptParserException("Illegal tag name.");
				}
			}

		}

		if(stack.size() != 1) {
			throw new SmartScriptParserException("END tag missing after FOR.");
		}
		return documentNode;
	}

	/**
	 * Pomoćna metoda za parsiranje equals tag-a. Na kraju parsiranja se stvara i
	 * vraća novi EchoNode. Ako se ne poštuju pravila tog tag-a, baca se
	 * {@link SmartScriptParserException}.
	 * 
	 * @return novi čvor sintaksnog stabla
	 * @throws SmartScriptParserException ako se ne poštuju pravila gramatike
	 */
	private EchoNode parseEcho() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		// koristim ArrayIndexedCollection za pohranu elemenata tag-a

		while(!tokenIsType(SmartTokenType.CLOSE_TAG)) {
			if(tokenIsType(SmartTokenType.EOF)) { // provjerava je li i slučajno EOF
				throw new SmartScriptParserException("Tag must have an end.");
			}
			
			Object value = lexer.getToken().getValue();
			
			if(tokenIsType(SmartTokenType.VARIABLE)) {
				elements.add(new ElementVariable((String) value));
				
			} else if(tokenIsType(SmartTokenType.INTEGER)) {
				elements.add(new ElementConstantInteger((Integer) value));
				
			} else if(tokenIsType(SmartTokenType.DOUBLE)) {
				elements.add(new ElementConstantDouble((Double) value));
				
			} else if(tokenIsType(SmartTokenType.STRING)) {
				elements.add(new ElementString((String) value));
				
			} else if(tokenIsType(SmartTokenType.OPERATION)) {
				elements.add(new ElementOperator((String) value));
				
			} else if(tokenIsType(SmartTokenType.FUNCT)) {
				elements.add(new ElementFunction((String) value));
				
			} else {
				throw new SmartScriptParserException("Illegal argument in echo tag.");
			}
			lexer.nextToken();
		}

		lexer.setState(SmartLexerState.TEXT);
		Element[] echoElems = new Element[elements.size()];
		for(int i = 0; i < elements.size(); i++) {
			echoElems[i] = (Element) elements.get(i);
		}
		return new EchoNode(echoElems);
	}

	/**
	 * Pomoćna metoda za parsiranje for tag-a. Na kraju parsiranja se stvara i vraća
	 * novi ForLoopNode. Ako se ne poštuju pravila tog tag-a, baca se
	 * {@link SmartScriptParserException}.
	 * 
	 * @return novi čvor sintaksnog stabla
	 * @throws SmartScriptParserException ako se ne poštuju pravila gramatike
	 */
	private ForLoopNode parseFor() {
		int count = 0;
		Element[] elements = new Element[4];
		while(!tokenIsType(SmartTokenType.CLOSE_TAG)) {
			if(count >= 4) {
				throw new SmartScriptParserException("Too many arguments - for loop has 3 or 4 arguments!");
			}
			// provjerava i je li slučajno EOF
			if((count == 0 && !tokenIsType(SmartTokenType.VARIABLE)) 
					|| tokenIsType(SmartTokenType.EOF)) {
				throw new SmartScriptParserException("Variable expected after keyword FOR.");
			}
			
			Object value = lexer.getToken().getValue();
			
			if(tokenIsType(SmartTokenType.VARIABLE)) {
				elements[count] = new ElementVariable((String) value);
				
			} else if(tokenIsType(SmartTokenType.INTEGER)) {
				elements[count] = new ElementConstantInteger((Integer) value);
				
			} else if(tokenIsType(SmartTokenType.DOUBLE)) {
				elements[count] = new ElementConstantDouble((Double) value);
				
			} else if(tokenIsType(SmartTokenType.STRING)) {
				elements[count] = new ElementString((String) value);
				
			} else {
				throw new SmartScriptParserException("Illegal argument in for loop.");
			}

			count++;
			lexer.nextToken();
		}

		lexer.setState(SmartLexerState.TEXT);
		if(count < 3) {
			throw new SmartScriptParserException("Too few arguments - for loop has 3 or 4 arguments!");
		}
		return new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2], elements[3]);
	}

	/**
	 * Provjerava je li trenutni token određenog tipa.
	 * 
	 * @param type tip tokena
	 * @return true ako je trenutni token predanog tipa, false ako nije
	 */
	private boolean tokenIsType(SmartTokenType type) {
		return lexer.getToken().getType().equals(type);
	}
}
