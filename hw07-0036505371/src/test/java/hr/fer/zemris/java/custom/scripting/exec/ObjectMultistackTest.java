package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	ObjectMultistack stack;
	
	@BeforeEach
	void init() {
		stack = new ObjectMultistack();
	}
	
	@Test
	void testConstructor() {
		assertNotNull(stack);
	}
	
	@Test
	void testPush() {
		stack.push("key", new ValueWrapper(8));
		assertEquals(8, stack.peek("key").getValue());
	}
	
	@Test
	void testPushMultiple() {
		stack.push("key", new ValueWrapper(8));
		stack.push("key2", new ValueWrapper(9));
		stack.push("key3", new ValueWrapper(10));
		assertEquals(8, stack.peek("key").getValue());
		assertEquals(9, stack.peek("key2").getValue());
		assertEquals(10, stack.peek("key3").getValue());
	}
	
	@Test
	void testPeek() {
		stack.push("newkey", new ValueWrapper("Ivica"));
		assertEquals("Ivica", stack.peek("newkey").getValue());
	}
	
	@Test
	void testPop() {
		stack.push("key", new ValueWrapper(8));
		stack.push("key", new ValueWrapper(9));
		stack.push("key", new ValueWrapper(10));
		assertEquals(10, stack.pop("key").getValue());
		assertEquals(9, stack.pop("key").getValue());
		assertEquals(8, stack.pop("key").getValue());
	}
	
	@Test
	void testIllegalPeek() {
		assertThrows(IllegalStateException.class, () -> stack.peek("someting"));
	}
	
	@Test
	void testIllegalPop() {
		assertThrows(IllegalStateException.class, () -> stack.pop("someting"));
		
		stack.push("key", new ValueWrapper(9));
		stack.push("key", new ValueWrapper(10));
		assertEquals(10, stack.pop("key").getValue());
		assertEquals(9, stack.pop("key").getValue());
		assertThrows(IllegalStateException.class, () -> stack.pop("key"));
	}

	@Test
	void testIsEmpty() {
		assertTrue(stack.isEmpty("nonExistantKey"));
		
		stack.push("key", new ValueWrapper(8));
		assertFalse(stack.isEmpty("key"));
		assertTrue(stack.isEmpty("nonExistantKey"));
	}
	
	@Test
	void testPushNull() {
		assertThrows(NullPointerException.class, () -> stack.push("key", null));
		assertThrows(NullPointerException.class, () -> stack.push(null, new ValueWrapper(8)));
	}
	
	@Test
	void testPeekNull() {
		assertThrows(NullPointerException.class, () -> stack.peek(null));
	}
	
	
}
