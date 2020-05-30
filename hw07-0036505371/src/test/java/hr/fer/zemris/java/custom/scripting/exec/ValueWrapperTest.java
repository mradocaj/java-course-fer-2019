package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	void testConstructor() {
		ValueWrapper value = new ValueWrapper(null);
		assertNotNull(value);
	}
	
	@Test
	void testNullvalue() {
		ValueWrapper value = new ValueWrapper(null);
		assertNull(value.getValue());
	}
	
	@Test
	void testAddNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	void testAddDouble() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(13.0, v3.getValue());
		assertTrue(v3.getValue() instanceof Double);
		assertEquals(1, v4.getValue());
	}
	
	@Test
	void testAddInteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(13, v5.getValue());
		assertTrue(v5.getValue() instanceof Integer);
		assertEquals(1, v6.getValue());
	}
	
	@Test
	void testIllegalAddArgument() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
	}
	
	@Test
	void testSubtractInteger() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(4);
		v1.subtract(v2.getValue());
		assertEquals(1, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
	}
	
	@Test
	void testSubtractDouble() {
		ValueWrapper v1 = new ValueWrapper("18.05");
		ValueWrapper v2 = new ValueWrapper(4);
		v1.subtract(v2.getValue());
		assertEquals(14.05, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
	}
	
	@Test
	void testIllegalSubtractArgument() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
	}
	
	@Test
	void testMultiplyInteger() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(4);
		v1.multiply(v2.getValue());
		assertEquals(20, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
	}
	
	@Test
	void testMultiplyDouble() {
		ValueWrapper v1 = new ValueWrapper("2.00");
		ValueWrapper v2 = new ValueWrapper(4);
		v1.multiply(v2.getValue());
		assertEquals(8.00, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
	}
	
	@Test
	void testDivideInteger() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(4);
		v1.divide(v2.getValue());
		assertEquals(1, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
	}
	
	@Test
	void testDivideDouble() {
		ValueWrapper v1 = new ValueWrapper("3.00");
		ValueWrapper v2 = new ValueWrapper(2);
		v1.divide(v2.getValue());
		assertEquals(1.5, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(2, v2.getValue());
	}
	
	@Test
	void testNumCompareNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		assertEquals(0, v1.numCompare(v2.getValue()));
		
		ValueWrapper v3 = new ValueWrapper(0);
		assertEquals(0, v3.numCompare(v2.getValue()));
	}
	
	@Test
	void testNumCompareDouble() {
		ValueWrapper v1 = new ValueWrapper(Math.PI);
		assertEquals(0, v1.numCompare(3.1415926));
		assertTrue(v1.numCompare(3) > 0);
		assertTrue(v1.numCompare(4) < 0);
	}
	
	@Test
	void testNumCompareInteger() {
		ValueWrapper v1 = new ValueWrapper(8);
		assertTrue(v1.numCompare(8) == 0);
		assertTrue(v1.numCompare(3) > 0);
		assertTrue(v1.numCompare(10) < 0);
	}
	
	@Test
	void testNumCompareIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(8);
		assertTrue(v1.numCompare(8) == 0);
		assertTrue(v1.numCompare("3.83") > 0);
		assertTrue(v1.numCompare(10) < 0);
	}
}
