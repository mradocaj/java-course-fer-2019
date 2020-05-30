package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.hw03.SmartScriptTester;

class SmartScriptParserTest {

	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptParser parser = new SmartScriptParser("");
		assertNotNull(parser.getDocumentNode());
	}

	@Test
	public void testChildren() {
		SmartScriptParser parser = new SmartScriptParser("Hello{$= i$}");
		assertEquals(2, parser.getDocumentNode().numberOfChildren());
		assertEquals("Hello", ((TextNode)parser.getDocumentNode().getChild(0)).getText());
		assertEquals("i", ((EchoNode)parser.getDocumentNode().getChild(1)).getElements()[0].asText());
	}
	
	@Test
	public void testWrongFirstVariable() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("Iznimka{$ for 28 1 2 &}"));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
	}
	
	@Test
	public void testTooManyArguments() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $} "));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 1 3 $}"));
	}
	
	@Test
	public void testTooFewArguments() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year $}"));
	}
	
	@Test 
	public void testMissingEndTag() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR i -1 10 1 $}"));
	}
	@Test
	public void testFunctionArgument() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year @sin 10 $} "));
	}
	
	@Test
	public void testIllegalEnd() {
		String docBody = loader("document1.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void testSyntaxTree() {
		String docBody = loader("document2.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		assertEquals(4, parser.getDocumentNode().numberOfChildren());
		assertEquals(5, parser.getDocumentNode().getChild(3).numberOfChildren());
	}
	
	@Test
	public void testEqualDocumentNodes() {
		String docBody = loader("document3.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void testEqualsEscaping() {
		String docBody = loader("document4.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document.equals(document2));
	}
	
	/*
	 * Metoda za učitavanje dokumenta.
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(java.io.InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}
}
