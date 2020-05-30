package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testText() {
		SmartScriptLexer lexer = new SmartScriptLexer("Hello there");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("Hello there", lexer.getToken().getValue());
	}
	
	@Test
	public void testEquals() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= i $}");
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EQU, lexer.nextToken().getType());
		assertEquals(SmartTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals("i", lexer.getToken().getValue());
		assertEquals(SmartTokenType.CLOSE_TAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testForTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("eh {$   foR j8_i 58 12.41$}");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("eh ", lexer.getToken().getValue());
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.FOR, lexer.nextToken().getType());
		assertEquals(SmartTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals("j8_i", lexer.getToken().getValue());
		assertEquals(SmartTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(58, lexer.getToken().getValue());
		assertEquals(SmartTokenType.DOUBLE, lexer.nextToken().getType());
		assertEquals(12.41, lexer.getToken().getValue());
	}
	
	@Test
	public void testTextEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    {");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("   \r\n\t    {", lexer.getToken().getValue());
	}
	
	@Test
	public void testNumberEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ 58");
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.INTEGER, lexer.nextToken().getType());
	}
	@Test
	public void testEscapingText() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("Example { bla } blu {$=1$}. Nothing interesting {=here}.", lexer.getToken().getValue());
	}
	
	@Test
	public void testWrongEscapingText() {
		SmartScriptLexer lexer = new SmartScriptLexer("Baš je \\lijep\\ dan.");
		assertThrows(SmartLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNonEscaping() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("Example {$=1$}. Now actually write one ", lexer.getToken().getValue());
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EQU, lexer.nextToken().getType());
		assertEquals(SmartTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(SmartTokenType.CLOSE_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testEscapingTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}");
		assertEquals(SmartTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EQU, lexer.nextToken().getType());
		assertEquals(SmartTokenType.STRING, lexer.nextToken().getType());
		assertEquals("Joe \"Long\" Smith", lexer.getToken().getValue());
		assertEquals(SmartTokenType.CLOSE_TAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testStringTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= \"0.000\" $}");
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EQU, lexer.nextToken().getType());
		assertEquals(SmartTokenType.STRING, lexer.nextToken().getType());
		assertEquals("0.000", lexer.getToken().getValue());
		assertEquals(SmartTokenType.CLOSE_TAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$= @sin $}");
		assertEquals(SmartTokenType.OPEN_TAG, lexer.nextToken().getType());
		assertEquals(SmartTokenType.EQU, lexer.nextToken().getType());
		assertEquals(SmartTokenType.FUNCT, lexer.nextToken().getType());
		assertEquals("sin", lexer.getToken().getValue());
		assertEquals(SmartTokenType.CLOSE_TAG, lexer.nextToken().getType());
	}
	
	@Test
	public void testDiverseForTag() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");
		SmartScriptLexer lexer2 = new SmartScriptLexer("{$ FOR i -1.35 bbb \"1\" $}");
		lexer1.nextToken();
		lexer2.nextToken();
		lexer1.nextToken();
		lexer2.nextToken();
		assertEquals(SmartTokenType.VARIABLE, lexer1.nextToken().getType());
		assertEquals("i", lexer1.getToken().getValue());
		assertEquals(lexer1.getToken(), lexer2.nextToken());
		assertEquals(SmartTokenType.DOUBLE, lexer1.nextToken().getType());
		assertEquals(-1.35, lexer1.getToken().getValue());
		assertEquals(lexer1.getToken(), lexer2.nextToken());
		assertEquals(SmartTokenType.VARIABLE, lexer1.nextToken().getType());
		assertEquals("bbb", lexer1.getToken().getValue());
		assertEquals(lexer1.getToken(), lexer2.nextToken());
		assertEquals(SmartTokenType.STRING, lexer1.nextToken().getType());
		assertEquals("1", lexer1.getToken().getValue());
		assertEquals(lexer1.getToken(), lexer2.nextToken());
	}
	

}
