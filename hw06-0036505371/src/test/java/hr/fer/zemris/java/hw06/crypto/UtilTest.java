package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHexToByte() {
		 byte[] result = Util.hextobyte("01aE22");
		 
		 assertEquals(3, result.length);
		 assertEquals(1, result[0]);
		 assertEquals(-82, result[1]);
		 assertEquals(34, result[2]);
	}
	
	@Test
	void testHexToByteEmpty() {
		 byte[] result = Util.hextobyte("");
		 
		 assertEquals(0, result.length);
	}
	
	@Test
	void testByteToHex() {
		String result =  Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals(6, result.length());
		assertEquals("01ae22", result);
	}
	
	@Test
	void testWrongHex() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aEžž22"));
	}
	
	@Test
	void testNullHex() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}
}
