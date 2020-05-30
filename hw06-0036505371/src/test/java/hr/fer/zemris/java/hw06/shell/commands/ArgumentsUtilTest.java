package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ArgumentsUtilTest {

	@Test
	void testWrongString() {
		String s = "\"C:\\fi le\".txt";
		assertThrows(IllegalArgumentException.class, () -> ArgumentsUtil.argumentParser(s));
	}
	
	@Test
	void testTwoArguments() {
		String s = "\"C:/Program Files/Program1/info.txt\" C:/tmp/informacije.txt";
		List<String> arguments = ArgumentsUtil.argumentParser(s);
		assertEquals(2, arguments.size());
		assertEquals("C:/Program Files/Program1/info.txt", arguments.get(0));
		assertEquals("C:/tmp/informacije.txt", arguments.get(1));
	}
	
	@Test
	void testTwoArgumentsNonString() {
		String s = " /home/john/info.txt /home/john/backupFolder ";
		List<String> arguments = ArgumentsUtil.argumentParser(s);
		assertEquals(2, arguments.size());
		assertEquals("/home/john/info.txt", arguments.get(0));
		assertEquals("/home/john/backupFolder", arguments.get(1));
	}

	@Test
	void testEscaping() {
		String s = "\"C:\\Documents and Settings\\Users\\javko\"";
		List<String> arguments = ArgumentsUtil.argumentParser(s);
		assertEquals(1, arguments.size());
		assertEquals("C:\\Documents and Settings\\Users\\javko", arguments.get(0));
	}
	
	@Test
	void testEscapingQuotation() {
		String s = "\"C:\\Documents and \\\"Settings\\Users\\javko\"";
		List<String> arguments = ArgumentsUtil.argumentParser(s);
		assertEquals(1, arguments.size());
		assertEquals("C:\\Documents and \"Settings\\Users\\javko", arguments.get(0));
	}
}
