package hr.fer.zemris.java.hw06.shell.commands.massrename;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NameBuilderLexerTest {

	@Test
	void testLexer() {
		NameBuilderLexer lexer = new NameBuilderLexer("gradovi-${2}-${1,03}.jpg");
		assertEquals("gradovi-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertEquals("2", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.TAG, lexer.getToken().getType());
		assertEquals("-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertEquals("1,03", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.TAG, lexer.getToken().getType());
		assertEquals(".jpg", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
	}
	
	@Test
	void testLexerThrows() {
		NameBuilderLexer lexer = new NameBuilderLexer("gradovi-${02}-${1,03}.jpg");
		assertEquals("gradovi-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertThrows(NameBuilderException.class, () -> lexer.nextToken().getValue());
	}

	@Test
	void testLexerThrows2() {
		NameBuilderLexer lexer = new NameBuilderLexer("gradovi-${1, 2}-${1,03}.jpg");
		assertEquals("gradovi-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertThrows(NameBuilderException.class, () -> lexer.nextToken().getValue());
	}
	
	@Test
	void testLexerThrows3() {
		NameBuilderLexer lexer = new NameBuilderLexer("${$1}-${1,03}.jpg");
		assertThrows(NameBuilderException.class, () -> lexer.nextToken().getValue());
	}
	
	@Test
	void testLexerSpaces() {
		NameBuilderLexer lexer = new NameBuilderLexer("gradovi-${2}-${  1     ,       03}.jpg");
		assertEquals("gradovi-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertEquals("2", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.TAG, lexer.getToken().getType());
		assertEquals("-", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
		assertEquals("1,03", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.TAG, lexer.getToken().getType());
		assertEquals(".jpg", lexer.nextToken().getValue());
		assertEquals(NameBuilderTokenType.STRING, lexer.getToken().getType());
	}
}
