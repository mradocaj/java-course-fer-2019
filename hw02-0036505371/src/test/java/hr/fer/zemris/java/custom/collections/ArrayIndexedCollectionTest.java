package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	@Test
	void firstConstructorTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertNotNull(collection.toArray());
		assertEquals(0, collection.size());
		assertFalse(collection.contains(2));
	}
	
	@Test
	void secondConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-12));
		ArrayIndexedCollection collection = new ArrayIndexedCollection(5);
		assertNotNull(collection.toArray());
		assertEquals(0, collection.size());
	}
	
	@Test 
	void thirdConstructorTest() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		ArrayIndexedCollection col = new ArrayIndexedCollection(10);
		col.add(2);
		ArrayIndexedCollection collection = new ArrayIndexedCollection(col);
		assertNotNull(collection.toArray());
	}
	
	@Test
	void forthConstructorTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(10);
		col.add(2);
		ArrayIndexedCollection collection = new ArrayIndexedCollection(col, 8);
		assertNotNull(collection.toArray());
	}
	
	@Test
	void sizeTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
		collection.add("Hello");
		collection.add("There");
		collection.add("General");
		collection.add("Kenobi");
		assertEquals(4, collection.size());
	}
	
	@Test
	void isEmptyTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertTrue(collection.isEmpty());
		collection.add("Bilbo Baggins");
		assertFalse(collection.isEmpty());
	}
	
	@Test
	void addTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		assertFalse(collection.contains("Garry Kasparov"));
		assertEquals(0, collection.size());
		collection.add("Magnus Carlsen");
		collection.add("Garry Kasparov");
		collection.add("Hikaru Nakamura");
		assertTrue(collection.contains("Garry Kasparov"));
		assertEquals(3, collection.size());
		collection.add("Anatoly Karpov");
		assertEquals(4, collection.size());
	}
	
	@Test
	void containsTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertFalse(collection.contains("Vassily Ivanchuk"));
		collection.add("Vassily Ivanchuk");
		assertTrue(collection.contains("Vassily Ivanchuk"));
	}
	
	@Test
	void removeTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
		collection.add("Magnus Carlsen");
		collection.add("Garry Kasparov");
		collection.add("Hikaru Nakamura");
		collection.add("Garry Kasparov");
		assertEquals(4, collection.size());
		
		assertTrue(collection.remove("Magnus Carlsen"));
		assertEquals(3, collection.size());
		assertTrue(collection.remove("Garry Kasparov"));
		assertEquals(2, collection.size());	
		assertTrue(collection.remove("Hikaru Nakamura"));
		assertEquals(1, collection.size());	
		assertEquals("Garry Kasparov", collection.get(0));
	}
	
	@Test
	void toArrayTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertFalse(collection.contains("Vassily Ivanchuk"));
		collection.add("Vassily Ivanchuk");
		collection.add("Judit Polgar");
		assertNotNull(collection.toArray());
		assertEquals(2, collection.toArray().length);
		assertEquals("Vassily Ivanchuk", collection.toArray()[0]);
	}
	
	@Test
	void forEachTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertEquals(3, collection.size());
		
		class MyProcessor extends Processor {
			int result = 1;
			
			@Override
			public void process(Object value) {
				result *= (int) value;
			}
		}
		MyProcessor processor = new MyProcessor();
		collection.forEach(processor);
		assertEquals(6, processor.result);
	}
	
	@Test
	void clearTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.add(4);
		assertEquals(4, collection.size());
		
		collection.clear();
		assertEquals(0, collection.size());
	}
	
	@Test
	void getTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.add(4);
		assertEquals(3, collection.get(2));
		assertEquals(1, collection.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(20));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-8));
	}
	
	@Test
	void insertTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(10);
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.add(4);
		assertEquals(3, collection.get(2));
		assertEquals(1, collection.get(0));
		assertEquals(4, collection.size());
		
		collection.insert(9, 2);
		assertEquals(5, collection.size());
		assertEquals(9, collection.get(2));
		assertEquals(4, collection.get(4));
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5, 20));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5, -12));
		assertThrows(NullPointerException.class, () -> collection.insert(null, 3));
	}

	@Test
	void indexOfTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(-1, collection.indexOf(2));
		assertEquals(0, collection.size());
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertEquals(1, collection.indexOf(2));
	}
	
	@Test
	void removeIndexTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertEquals(3, collection.size());
		
		collection.remove(0);
		assertEquals(2, collection.size());
		assertEquals(2, collection.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-12));
	}
	
	@Test 
	void addAllTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.addAll(collection);
		assertEquals(2, col.get(1));
	}
}
