package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

class LinkedListIndexedCollectionTest {

	@Test
	void firstConstructorTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.toArray().length);
	}
	
	@Test
	void secondConstructorTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		LinkedListIndexedCollection secondList = new LinkedListIndexedCollection(list);
		assertEquals(2, secondList.get(1));
	}
	
	@Test
	void isEmptyTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertTrue(collection.isEmpty());
		collection.add("Bilbo Baggins");
		assertFalse(collection.isEmpty());
	}
	
	@Test
	void addTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		assertEquals(4, list.size());
		assertEquals(4, list.get(3));
		
		assertThrows(NullPointerException.class, () -> list.add(null));
	}
	
	@Test
	void sizeTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(3);
		assertEquals(1, list.size());
		list.add(4);
		assertEquals(2, list.size());
	}

	@Test
	void containsTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(1);
		list.add("Bobby Fischer");
		list.add(3);
		list.add(4);
		assertTrue(list.contains("Bobby Fischer"));
	}
	
	@Test
	void removeTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add("1");
		list.add("Bobby Fischer");
		list.add(3);
		list.add(4);
		assertEquals(4, list.size());
		
		assertTrue(list.remove("Bobby Fischer"));
		assertEquals(3, list.size());
		assertTrue(list.contains(4));
		
		assertTrue(list.remove("1"));
		assertEquals(2, list.size());
		assertEquals(3, list.get(0));
	}
	
	@Test
	void toArrayTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		Object[] array = list.toArray();
		assertEquals(2, array[1]);
		assertEquals(4, array.length);
	}
	
	@Test
	void forEachTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		class MyProcessor extends Processor {
			int result = 1;
			
			@Override
			public void process(Object value) {
				result *= (int) value;
			}
		}
		
		MyProcessor processor = new MyProcessor();
		list.forEach(processor);
		assertEquals(24, processor.result);
	}
	
	@Test
	void clearTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		assertEquals(4, list.size());
		
		list.clear();
		assertEquals(0, list.size());
	}
	
	@Test
	void getTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(3, list.size());
		
		assertEquals(1, list.get(0));
		assertEquals(2, list.get(1));
		assertEquals(3, list.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-12));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(12));
	}
	
	@Test
	void insertTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		assertEquals(4, list.size());
		
		list.insert(5, 2);
		assertEquals(5, list.get(2));
		assertEquals(5, list.size());
		
		assertTrue(list.contains(4));	//s ovime sam provjeravala jesam li dobro naštimala pokazivače, tj
										//mogu li se prošetati kroz listu
		
		list.insert(9, 0);
		assertEquals(9, list.get(0));
		assertEquals(6, list.size());
		
		assertTrue(list.contains(4));
		
		assertThrows(NullPointerException.class, () -> list.insert(null, 2));
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert(5, -2));
		assertThrows(IndexOutOfBoundsException.class, () -> list.insert(5, 13));
	}
	
	@Test
	void indexOfTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Judit Polgar");
		list.add("Hou Yifan");
		list.add("Susan Polgar");
		assertEquals(1, list.indexOf("Hou Yifan"));
		assertEquals(-1, list.indexOf("Magnus Carlsen"));
	}
	
	@Test
	void removeAtIndexTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Judit Polgar");
		list.add("Hou Yifan");
		list.add("Susan Polgar");
		list.remove(2);
		assertEquals(2, list.size());
		
		list.add("Magnus Carlsen");
		assertEquals(3, list.size());
		
		list.remove(0);
		assertEquals("Hou Yifan", list.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-13));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(13));
	}
	
	@Test 
	void addAllTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("Judit Polgar");
		list.add("Hou Yifan");
		list.add("Susan Polgar");
		
		LinkedListIndexedCollection secondList = new LinkedListIndexedCollection();
		secondList.addAll(list);
		assertEquals(3, secondList.size());
		assertEquals("Susan Polgar", secondList.get(2));
	}
	
}
