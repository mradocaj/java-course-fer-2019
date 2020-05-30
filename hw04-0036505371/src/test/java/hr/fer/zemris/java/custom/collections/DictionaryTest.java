package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testConstructor() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		assertNotNull(dictionary);
		assertEquals(0, dictionary.size());
	}
	
	@Test
	void testPutNull() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dictionary.put(null, "Jerko"));
	}
	
	@Test
	void testGet() {
		Dictionary<Integer, String> dictionary = defaultDictionary();
		assertEquals("Marijana", dictionary.get(3));
	}
	
	@Test
	void testPut() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "Tomislav");
		assertEquals("Tomislav", dictionary.get(1));
		assertEquals(1, dictionary.size());
	}
	
	@Test
	void testIsEmpty() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		assertTrue(dictionary.isEmpty());
		dictionary = defaultDictionary();
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	void testSize() {
		Dictionary<Integer, String> dictionary = defaultDictionary();
		assertEquals(4, dictionary.size());
	}
	
	@Test
	void testClear() {
		Dictionary<Integer, String> dictionary = defaultDictionary();
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}
	
	@Test
	void testDoubles() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "Siniša");
		dictionary.put(1, "Marko");
		dictionary.put(1, "Josip");
		dictionary.put(1, "Alen");
		assertEquals("Alen", dictionary.get(1));
		assertEquals(1, dictionary.size());
	}

	@Test
	void testGetNonExisting() {
		Dictionary<Integer, String> dictionary = defaultDictionary();
		assertEquals(null, dictionary.get(12));
	}
	
	@Test 
	void testGetNull() {
		Dictionary<Integer, String> dictionary = defaultDictionary();
		assertEquals(null, dictionary.get(null));
	}
	
	
	private Dictionary<Integer, String> defaultDictionary() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "Krešimir");
		dictionary.put(2, "Janko");
		dictionary.put(3, "Marijana");
		dictionary.put(4, null);
		dictionary.put(2, "Željko");
		return dictionary;
	}
}
