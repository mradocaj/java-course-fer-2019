package hr.fer.zemris.java.custom.scripting.exec.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import static hr.fer.zemris.java.custom.scripting.exec.util.ArgumentsUtil.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

class ArgumentsUtilTest {

	@Test
	void testCheckArguments() {
		assertThrows(RuntimeException.class, () -> checkArguments(new HashMap<String, Integer>(), "key"));
		assertThrows(RuntimeException.class, () -> checkArguments(8, new ValueWrapper(8)));
		assertDoesNotThrow(() -> checkArguments(8, "1")); 
	}
	
	@Test
	void testGetArgument() {
		assertEquals(85.0, getArgument("8.5E1", 2));
		assertEquals(85.0, getArgument("85", 2.0));
		assertEquals(85, getArgument("85", 2));
	}
	
	@Test
	void testGetWrongArgument() {
		assertThrows(RuntimeException.class, () -> getArgument("Ivica", 3));
	}

}
